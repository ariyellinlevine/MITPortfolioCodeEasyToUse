package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.utils.Robot;

public class PoseGenerator {

    Robot.Alliance alliance;
    public PoseGenerator(Robot.Alliance alliance) {
        this.alliance = alliance;
    }

    public Pose newPose(double x, double y, double h) {
        Pose pose = new Pose(x, y, h);
        if (alliance == Robot.Alliance.BLUE) {
            pose = pose.mirror();
        }
        return pose;
    }
}
