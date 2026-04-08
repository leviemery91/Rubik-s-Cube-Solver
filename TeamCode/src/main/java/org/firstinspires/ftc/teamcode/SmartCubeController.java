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

import java.util.UUID;

/**
 * Direct BLE Implementation for FTC Control Hub
 * Handles connection and move decoding for Smart Cubes
 */
public class SmartCubeController {
    private static final String TAG = "SmartCube";

    // UUIDs from your JavaScript code
    private final UUID SERVICE_UUID = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    private final UUID TX_CHAR_UUID = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    private final UUID CLIENT_CONFIG_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothGatt bluetoothGatt;
    private String lastMove = "";
    private boolean connected = false;

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
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            byte[] data = characteristic.getValue();
            processCubeData(data);
        }
    };

    private void processCubeData(byte[] data) {
        if (data.length < 5) return;

        int turn = data[3] & 0xFF;
        int sliceTurn = data[4] & 0xFF;

        // Check for Slices first (M, E, S)
        String move = decodeSlice(sliceTurn);

        // If no slice, check standard turns
        if (move.equals("")) {
            String[] moves = {"B", "B'", "F", "F'", "U", "U'", "D", "D'", "R", "R'", "L", "L'"};
            if (turn < moves.length) {
                move = moves[turn];
            }
        }

        if (!move.equals("")) {
            lastMove = move;
            Log.d(TAG, "Cube Move: " + move);
        }
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
}