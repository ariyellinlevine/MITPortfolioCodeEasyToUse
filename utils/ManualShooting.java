package org.firstinspires.ftc.teamcode.utils;

import com.arcrobotics.ftclib.util.InterpLUT;
import com.pedropathing.geometry.Pose;

public class ManualShooting {
    InterpLUT velocities = new InterpLUT();
    KinematicEquations kins;
    public ManualShooting(KinematicEquations kins) {
        velocities.add(1.383362,1000);
        velocities.add(1.644816,1100);
        velocities.add(2.026546,1140);
        velocities.add(2.266517,1220);
        velocities.add(2.839,1300);

        velocities.add(3.89,2000);


        this.kins = kins;

        velocities.createLUT();
    }

    public double getVelocity(Pose pos) {
         kins.velocitySolve(pos.getX()/39.37, pos.getY()/39.37, 52.166);
        double distance = kins.xdis;
        try {
            return velocities.get(distance);
        } catch (Exception e) {
            return 0;
//            throw new RuntimeException(String.format("X: %f, Y: %f", pos.getX(), pos.getY()));
        }
    }

}
