package org.firstinspires.ftc.teamcode;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class CubeScanner {
    private BluetoothAdapter adapter;
    private BluetoothLeScanner scanner;
    private List<String> discoveredDevices = new ArrayList<>();

    public CubeScanner() {
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            Log.e("CUBE_SCAN", "Device has no Bluetooth hardware.");
            return;
        }

        if (!adapter.isEnabled()) {
            Log.e("CUBE_SCAN", "Bluetooth is OFF. Attempting to enable...");
            // This is a "quiet" enable request
            adapter.enable();
        }

        // Try to get the scanner
        scanner = adapter.getBluetoothLeScanner();
    }

    public void startScanning() {
        if (scanner == null) {
            Log.e("CUBE_SCAN", "SCANNER IS NULL");
            return;
        }

        android.bluetooth.le.ScanSettings settings = new android.bluetooth.le.ScanSettings.Builder()
                .setScanMode(android.bluetooth.le.ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();

        // Passing null for filters tells Android to show EVERYTHING
        scanner.startScan(null, settings, new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                ScanRecord record = result.getScanRecord();
                if (record != null && record.getServiceUuids() != null) {
                    for (android.os.ParcelUuid uuid : record.getServiceUuids()) {
                        // This is the short version of your serviceUUID
                        if (uuid.toString().toLowerCase().contains("6e400001")) {
                            Log.i("CUBE_SCAN", "🎯 MATCH FOUND!");
                            Log.i("CUBE_SCAN", "MAC ADDRESS: " + result.getDevice().getAddress());
                            Log.i("CUBE_SCAN", "DEVICE NAME: " + result.getDevice().getName());
                        }
                    }
                }
            }

            @Override
            public void onScanFailed(int errorCode) {
                Log.e("CUBE_SCAN", "Scan Failed Error Code: " + errorCode);
            }
        });

        Log.i("CUBE_SCAN", "Scan initiated...");
    }

    public List<String> getDiscoveredDevices() {
        return discoveredDevices;
    }

    public void stopScanning() {
        if (scanner != null) scanner.stopScan(new ScanCallback() {});
    }
}