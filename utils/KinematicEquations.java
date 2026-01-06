package org.firstinspires.ftc.teamcode.utils;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * @author Noah Lapp
 * <h1>Class to  calculate the kinematics to shoot a ball to any location on the field</h1>
 */
public class KinematicEquations {
    private static final double gravitAccel = 9.81;
    private double goalHeight;
    private double robotHeight;
    public double outtakeVel;
    private double height;

    public double goalx;
    public double goaly;
    public double xdis = 0;

    /**
     * @param goalHeightStart the height of the gaol
     * @param robotHeightStart the distance from the ground the shooter is
     * @param outtakeSpeedStart the outtake velocity in units/s
     * @param goalxStart the x location of the goal
     * @param goalyStart the y location of the goal
     */

    public KinematicEquations(double goalHeightStart, double robotHeightStart, double outtakeSpeedStart, double goalxStart, double goalyStart){
        this.goalHeight = goalHeightStart;
        this.robotHeight = robotHeightStart;
        this.outtakeVel = outtakeSpeedStart;

        this.height = this.goalHeight - this.robotHeight;

        this.goalx = goalxStart;
        this.goaly = goalyStart;
    }


/**
 * @return array of type double of {the angle the shooter must face, the angle of the shooter, time till it lands} or empty if impossible
 * @param robotxIn location of the shooter x
 * @param robotyIn location of the shooter y
* */
    public double[] kinematicSolve(double robotxIn, double robotyIn) {


        double robotx = robotxIn;
        double roboty = robotyIn;

        double adjacent = goalx - robotx;
        double opposite = goaly - roboty;

        //make sure all angles are right degrees/radians
        xdis = Math.hypot(adjacent, opposite);

        double facingAngle = Math.atan2(opposite, adjacent);
        double facingAngleDeg = Math.toDegrees(facingAngle);

        double gravityCalc = (gravitAccel*(xdis*xdis)) / (2*(outtakeVel*outtakeVel));
        double discriminant = Math.sqrt((xdis*xdis) - 4*(gravityCalc)*(gravityCalc+height));
        if (Double.isNaN(discriminant)) return new double[]{facingAngleDeg, .67676767, .67676767};
        double tanThetaPlus = (xdis + discriminant) / gravityCalc;
        double tanThetaMinus = (xdis - discriminant) / gravityCalc;

        double shooterAngle1 = Math.atan(tanThetaMinus);
        double shooterAngle2 = Math.atan(tanThetaPlus);




        @SuppressWarnings("RedundantLocalVariable")
        double[] returnAngles = {facingAngleDeg, Math.toDegrees(shooterAngle1), Math.toDegrees(shooterAngle2)};
        return returnAngles;
    }

    /**
     * @return velocity per input angle
     * @param angle that is being used for solving for velocity inputing in DEGREES!!!!!!
     * WARNING RADIANS WILL NOT WORK ARI!!!!! NO RADIANS ONLY DEGREES!!! //Ok this made me laugh too
     * Degrees were decided so it must be that! //No they weren't
     */
    public double[] velocitySolve(double robotx, double roboty, double angle) {
        double adjacent = goalx - robotx;
        double opposite = goaly - roboty;

        double facingAngle = Math.atan2(opposite, adjacent);
        double facingAngleDeg = Math.toDegrees(facingAngle);

        double rad = Math.toRadians(angle);

        //make sure all angles are right degrees/radians
        xdis = Math.hypot(adjacent, opposite);

        double theThingToBeSquareRooted = (gravitAccel * (xdis * xdis))/((2*cos(rad) * cos(rad)) * (xdis*sin(rad) - cos(rad) * height));
        if (theThingToBeSquareRooted <= 0) return new double[]{facingAngleDeg, 0.6767676767};

        double vel = Math.sqrt(theThingToBeSquareRooted);

        return new double[] {facingAngleDeg, vel};
    }
}
