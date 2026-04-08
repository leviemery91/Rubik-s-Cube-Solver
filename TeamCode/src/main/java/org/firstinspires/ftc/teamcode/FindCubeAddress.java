package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Find My Cube MAC")
public class FindCubeAddress extends LinearOpMode {
    @Override
    public void runOpMode() {
        CubeScanner cubeScanner = new CubeScanner();

        telemetry.addLine("Press PLAY to start scanning...");
        telemetry.update();

        waitForStart();

        cubeScanner.startScanning();

        while (opModeIsActive()) {
            telemetry.addLine("SCANNING... (Check Logcat too)");
            // Display found devices on the driver station screen
            for (String device : cubeScanner.getDiscoveredDevices()) {
                telemetry.addLine(device);
            }
            telemetry.update();
            sleep(500); // Slow down the loop
        }

        cubeScanner.stopScanning();
    }
}