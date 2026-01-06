package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.Robot;

@TeleOp(name = "Blue Teleop")
public class BlueTeleop1 extends Teleop1 {
    public Robot.Alliance alliance() {
        return Robot.Alliance.BLUE;
    }
}
