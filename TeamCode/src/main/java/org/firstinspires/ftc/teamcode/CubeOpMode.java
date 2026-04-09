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
            byte[] cubeState = cube.getCubeState();
            String cubeBytes = "";
            if (cubeState != null) {
                for (byte b : cubeState) {
                    cubeBytes = cubeBytes + b;
                    cubeBytes = cubeBytes + " ";
                }
            }
            telemetry.addData("Last Move", move);
            if(cubeState != null)
                telemetry.addData("Cube State", cubeState.toString());
            telemetry.addData("Cube Bytes", cubeBytes);
            telemetry.addData("Status", cube.isConnected() ? "Connected" : "Searching...");
            telemetry.addData("Move Count", cube.getMoveCount());
            telemetry.addData("Cube Solved", cube.cubeSolved());
            telemetry.update();
            if(gamepad1.b){
                cube.resetCube();
            }
            // Logic: If move == "U", run motor for 90 degrees, etc.
        }

        cube.disconnect();
    }
}