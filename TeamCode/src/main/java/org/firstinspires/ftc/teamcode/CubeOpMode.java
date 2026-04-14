package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SmartCubeController;

@TeleOp(name="Cube Solver Bot")
public class CubeOpMode extends LinearOpMode {
    SmartCubeController cube;
    boolean lastX;
    boolean lastY;
    boolean lastB;
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
            if(gamepad1.b && !lastB){
                cube.resetCube();
                cube.solution = "";
            }else if(gamepad1.y && !lastY)
            {
                cube.findSolution();
            }else if(gamepad1.x && !lastX){
                if(cube.guidedSolve){
                    cube.stopGuidedSolve();
                }else{
                    cube.startGuidedSolve();
                }
            }
            lastB = gamepad1.b;
            lastX = gamepad1.x;
            lastY = gamepad1.y;

            telemetry.addData("Last Move", move);
            if(cubeState != null)
                telemetry.addData("Cube State", cubeState.toString());
            telemetry.addData("Cube Bytes", cubeBytes);
            telemetry.addData("Status", cube.isConnected() ? "Connected" : "Searching...");
            telemetry.addData("Move Count", cube.getMoveCount());
            telemetry.addData("Cube Solved", cube.cubeSolved());
            telemetry.addData("Solution", cube.returnSolution());
            telemetry.addData("Next Move", cube.getNextMove());
            telemetry.addData("Guided Solve", cube.guidedSolve);
            displayCube(cube.returnCube());
            telemetry.addData("\nFacelet", cube.returnCube().convertToFacelet());
            telemetry.update();
            // Logic: If move == "U", run motor for 90 degrees, etc.
        }

        cube.disconnect();
    }

    public void displayCube(Cube c){
        for(int i = 0; i<12;i++) {
            String row = "";
            if(i <= 2 || i >= 6){
                row = row + "            ";
            }
            if(i == 0){
                row = row + "" + c.cube[3][2][2].getI() + " " + c.cube[3][2][1].getI() + " " + c.cube[3][2][0].getI();
            }else if(i == 1){
                row = row + "" + c.cube[3][1][2].getI() + " " + c.cube[3][1][1].getI() + " " + c.cube[3][1][0].getI();
            }else if(i == 2){
                row = row + "" + c.cube[3][0][2].getI() + " " + c.cube[3][0][1].getI() + " " + c.cube[3][0][0].getI();
            }else if(i == 3){
                row = row + "" + c.cube[4][2][0].getI() + " " + c.cube[4][1][0].getI() + " " + c.cube[4][0][0].getI() + " " + c.cube[0][0][0].getI() + " " + c.cube[0][0][1].getI() + " " + c.cube[0][0][2].getI() + " " + c.cube[2][0][2].getI() + " " + c.cube[2][1][2].getI() + " " + c.cube[2][2][2].getI();
            }else if(i == 4){
                row = row + "" + c.cube[4][2][1].getI() + " " + c.cube[4][1][1].getI() + " " + c.cube[4][0][1].getI() + " " + c.cube[0][1][0].getI() + " " + c.cube[0][1][1].getI() + " " + c.cube[0][1][2].getI() + " " + c.cube[2][0][1].getI() + " " + c.cube[2][1][1].getI() + " " + c.cube[2][2][1].getI();
            }else if(i == 5){
                row = row + "" + c.cube[4][2][2].getI() + " " + c.cube[4][1][2].getI() + " " + c.cube[4][0][2].getI() + " " + c.cube[0][2][0].getI() + " " + c.cube[0][2][1].getI() + " " + c.cube[0][2][2].getI() + " " + c.cube[2][0][0].getI() + " " + c.cube[2][1][0].getI() + " " + c.cube[2][2][0].getI();
            }if(i == 6){
                row = row + "" + c.cube[1][0][0].getI() + " " + c.cube[1][0][1].getI() + " " + c.cube[1][0][2].getI();
            }if(i == 7){
                row = row + "" + c.cube[1][1][0].getI() + " " + c.cube[1][1][1].getI() + " " + c.cube[1][1][2].getI();
            }if(i == 8){
                row = row + "" + c.cube[1][2][0].getI() + " " + c.cube[1][2][1].getI() + " " + c.cube[1][2][2].getI();
            }if(i == 9){
                row = row + "" + c.cube[5][0][0].getI() + " " + c.cube[5][0][1].getI() + " " + c.cube[5][0][2].getI();
            }if(i == 10){
                row = row + "" + c.cube[5][1][0].getI() + " " + c.cube[5][1][1].getI() + " " + c.cube[5][1][2].getI();
            }if(i == 11){
                row = row + "" + c.cube[5][2][0].getI() + " " + c.cube[5][2][1].getI() + " " + c.cube[5][2][2].getI();
            }
            telemetry.addData("", row);
        }
    }


}