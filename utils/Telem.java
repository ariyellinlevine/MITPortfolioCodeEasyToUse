package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Collection of basic telemetry that we'll always want
 * @author Natan Meyer
 * @author Ari Yellin-Levine
 */

public class Telem {
    OpMode op;
    Robot robot;
    Telemetry t;
    Gamepad g;
    DriveBase drive;
    ArrayList<Subsystem> subsystems;

    public Telem(OpMode o, Robot r, DriveBase drive, Telemetry t, ArrayList<Subsystem> subsystems) {
        this.op = o;
        this.robot = r;
        this.t = t;
        this.g = op.gamepad1;
        this.drive = drive;
        this.subsystems = subsystems;
    }

    public Telem(OpMode o, Robot r, DriveBase drive, Telemetry t) {
        this(o, r, drive, t, new ArrayList<Subsystem>());
    }

    /**
     * Prints all relevant telemetry
     */
    public void print(){
        Pose pose = drive.getPos();

        t.addLine(String.format(Locale.US, "X: %f; Y: %f; Heading: %f", pose.getX(), pose.getY(), Math.toDegrees(pose.getHeading())));

        for (Subsystem s : subsystems) {
            t.addLine(s.toString());
        }


    }
}

