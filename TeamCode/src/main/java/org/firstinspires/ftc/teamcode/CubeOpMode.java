package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SmartCubeController;

@TeleOp(name="Cube Solver Bot")
public class CubeOpMode extends LinearOpMode {
    SmartCubeController cube;

    @Override
    public void runOpMode() {
        cube = new SmartCubeController();
        // Replace with your Cube's MAC address found during scanning
        cube.connect(hardwareMap.appContext, "E7:A1:D1:4A:7C:48");

        waitForStart();

        while (opModeIsActive()) {
            cube.startScan();
            String move = cube.getLastMove();
            telemetry.addData("Last Move", move);
            telemetry.addData("Status", cube.isConnected() ? "Connected" : "Searching...");
            telemetry.update();

            // Logic: If move == "U", run motor for 90 degrees, etc.
        }

        cube.disconnect();
    }
}