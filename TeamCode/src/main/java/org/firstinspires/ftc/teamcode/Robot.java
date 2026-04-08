package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    DcMotorEx front;
    DcMotorEx right;
    DcMotorEx back;
    DcMotorEx left;

    public Robot(HardwareMap hardwareMap){
        front = hardwareMap.get(DcMotorEx.class, "front");
        right = hardwareMap.get(DcMotorEx.class, "right");
        back = hardwareMap.get(DcMotorEx.class, "back");
        left = hardwareMap.get(DcMotorEx.class, "left");
    }
}
