package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import org.firstinspires.ftc.teamcode.min2phase.s.Search;

import java.util.UUID;

/**
 * Direct BLE Implementation for FTC Control Hub
 * Handles connection and move decoding for Smart Cubes
 */
public class SmartCubeController {
    Cube cube = new Cube();

    private static final String TAG = "SmartCube";

    private final UUID SERVICE_UUID = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    private final UUID TX_CHAR_UUID = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    private final UUID CLIENT_CONFIG_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothGatt bluetoothGatt;
    private String lastMove = "";
    private byte[] cubeState = null;
    private boolean connected = false;
    private int moveCount = 0;

    public SmartCubeController() {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void connect(Context context, String deviceAddress) {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);
        bluetoothGatt = device.connectGatt(context, false, gattCallback);
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.i(TAG, "Connected to Cube. Discovering services...");
                connected = true;
                gatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.i(TAG, "Disconnected from Cube.");
                connected = false;
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            BluetoothGattService service = gatt.getService(SERVICE_UUID);
            if (service != null) {
                BluetoothGattCharacteristic txChar = service.getCharacteristic(TX_CHAR_UUID);
                // Enable Notifications
                gatt.setCharacteristicNotification(txChar, true);

                // This part is crucial for Android: writing the descriptor to start flow
                android.bluetooth.BluetoothGattDescriptor descriptor = txChar.getDescriptor(CLIENT_CONFIG_DESCRIPTOR);
                descriptor.setValue(android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                gatt.writeDescriptor(descriptor);

                gatt.readCharacteristic(txChar);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            byte[] data = characteristic.getValue();
            processCubeData(data);
            cubeState = data;
        }
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                byte[] data = characteristic.getValue();
                cubeState = data;
            }
        }
    };

    private void processCubeData(byte[] data) {
        int turn = data[3] & 0xFF;

        String move = "";
            String[] moves = {"B", "B'", "F", "F'", "U", "U'", "D", "D'", "R", "R'", "L", "L'"};
            if (turn < moves.length) {
                move = moves[turn];
            }
        lastMove = move;
        moveCount++;
        updateCube(move);
    }

    private String decodeSlice(int code) {
//        switch(code) {
//            case 0x10: return "M";
//            case 0x11: return "M'";
//            case 0x07: return "E";
//            case 0x06: return "E'";
//            case 0x03: return "S";
//            case 0x02: return "S'";
//            default: return "";
//        }
        return "";
    }

    public byte[] getCubeState(){ return cubeState; }
    public String getLastMove() { return lastMove; }
    public boolean isConnected() { return connected; }

    public void disconnect() {
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
            bluetoothGatt.close();
        }
    }

    public void startScan() {
        bluetoothAdapter.getBluetoothLeScanner().startScan(new android.bluetooth.le.ScanCallback() {
            @Override
            public void onScanResult(int callbackType, android.bluetooth.le.ScanResult result) {
                BluetoothDevice device = result.getDevice();
                // This will print "Found Device: GAN-123456 | Address: AA:BB:CC:DD:EE:FF"
                telemetry.addData("SmartCubeScan", "Found Device: " + device.getName() + " | Address: " + device.getAddress());
            }
        });
    }

    public void updateCube(String turn){
        cube.updateCube(turn);
    }
    public int getMoveCount(){
        return moveCount;
    }
    public boolean cubeSolved(){
        return cube.cubeSolved();
    }
    public void resetCube(){
        cube.resetCube();
    }
    public Cube returnCube(){
        return cube;
    }
    public String returnSolution(){
        return simpleSolve(cube.convertToFacelet());
    }

    public static String simpleSolve(String scrambledCube) {
        String result = new Search().solution(scrambledCube, 21, 100000000, 0, 0);
        return result;
    }

    public static void outputControl(String scrambledCube) {
        String result = new Search().solution(scrambledCube, 21, 100000000, 0, Search.APPEND_LENGTH);
        System.out.println(result);
        // R2 U2 B2 L2 F2 U' L2 R2 B2 R2 D  B2 F  L' F  U2 F' R' D' L2 R' (21f)

        result = new Search().solution(scrambledCube, 21, 100000000, 0, Search.USE_SEPARATOR | Search.INVERSE_SOLUTION);
        System.out.println(result);
        // R  L2 D  R  F  U2 F' L  F' .  B2 D' R2 B2 R2 L2 U  F2 L2 B2 U2 R2
    }

    public static void findShorterSolutions(String scrambledCube) {
        //Find shorter solutions (try more probes even a solution has already been found)
        //In this example, we try AT LEAST 10000 phase2 probes to find shorter solutions.
        String result = new Search().solution(scrambledCube, 21, 100000000, 10000, 0);
        System.out.println(result);
        // L2 U  D2 R' B  U2 L  F  U  R2 D2 F2 U' L2 U  B  D  R'
    }

    public static void continueSearch(String scrambledCube) {
        //Continue to find shorter solutions
        Search searchObj = new Search();
        String result = searchObj.solution(scrambledCube, 21, 500, 0, 0);
        System.out.println(result);
        // R2 U2 B2 L2 F2 U' L2 R2 B2 R2 D  B2 F  L' F  U2 F' R' D' L2 R'

        result = searchObj.next(500, 0, 0);
        System.out.println(result);
        // D2 L' D' L2 U  R2 F  B  L  B  D' B2 R2 U' R2 U' F2 R2 U' L2

        result = searchObj.next(500, 0, 0);
        System.out.println(result);
        // L' U  B  R2 F' L  F' U2 L  U' B' U2 B  L2 F  U2 R2 L2 B2

        result = searchObj.next(500, 0, 0);
        System.out.println(result);
        // Error 8, no solution is found after 500 phase2 probes. Let's try more probes.

        result = searchObj.next(500, 0, 0);
        System.out.println(result);
        // L2 U  D2 R' B  U2 L  F  U  R2 D2 F2 U' L2 U  B  D  R'
    }

}