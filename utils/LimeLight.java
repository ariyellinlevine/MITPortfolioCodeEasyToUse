package org.firstinspires.ftc.teamcode.utils;

import android.graphics.RadialGradient;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.ftc.InvertedFTCCoordinates;
import com.pedropathing.ftc.PoseConverter;
import com.pedropathing.geometry.PedroCoordinates;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;
import java.util.Locale;

/**
 * @author Assistant Noah Lapp
 * @Fixedby: Ari Yellin-Levine, <br/>
 * "Generally we make methods public if we want to use them, and I'm fairly sure we'd rather be able to use our code"
 * - The Honorable glorious eternal supreme software commander General Colonel Captain Admiral Airman Marshal (pronounced Marshall) Ari L. Yellin-Levine"
 * <h1>April Tag localization using a limelight!</h1>
 *
 */
public class LimeLight {
    private final Follower follower;
    private final Limelight3A limelight;
    private final Telemetry telemetry;
    private Turret turret;
    private DriveBase drive;
    Pose lastPedroPos = null;

    private double lastTime = 0;
    boolean localized = false;
    int aprilTagPipeline = 0;
    int visionPipeline = 1;
    int currentPipeline;
    int[] localizationTags = {20,24};

    public LimeLight(Robot r, DriveBase drive, Turret t){
        this.limelight = r.limelight;
        this.drive = drive;
        this.follower = drive.follower;
        this.telemetry = r.t;
        this.turret = t;


        currentPipeline = aprilTagPipeline;
        limelight.pipelineSwitch(currentPipeline);
    }

    /**
    Checks to see if the limelight sees anything, if it sees an april tag it sets the followers positon;
     */
    public void updatePosWithAprilTags() {
        if (limelight == null) {
            telemetry.addLine("Null");
            return;
        }
//        limelight.updateRobotOrientation(turret.shooterAngle);

        telemetry.addLine("before pipeline");

        if (currentPipeline != 0) {
            //takes time, it might be bad if it doesn't hang
            limelight.pipelineSwitch(aprilTagPipeline);
            currentPipeline = aprilTagPipeline;
        }


        LLResult llResult = limelight.getLatestResult();

        telemetry.addLine("after pipeline");
        telemetry.addData("result is not null", llResult != null);
        if (llResult != null) {
            telemetry.addData("result is valid", llResult.isValid());
        }

        if(llResult != null && llResult.isValid()) {
            telemetry.addLine("result is valid");
            Pose3D pose = llResult.getBotpose_MT2();
            if (pose != null) {
                telemetry.addLine("pose is not null");

                Pose roboPose = PoseConverter.pose2DToPose(new Pose2D(DistanceUnit.METER, pose.getPosition().x, pose.getPosition().y, AngleUnit.RADIANS, follower.getHeading()), InvertedFTCCoordinates.INSTANCE);
                lastPedroPos = roboPose;
                lastTime = System.currentTimeMillis();
                drive.setPos(roboPose);
                localized = true;
            }
        }
    }

    public boolean getLocalized() {
        return localized;
    }

    public String toString() {
        if (limelight == null) {
            return "Limelight is null";
        }
        return String.format(Locale.US, "Limelight; Pipeline: %d; Delta Time: %f", limelight.getStatus().getPipelineIndex(), (System.currentTimeMillis()-lastTime)/1000);
    }

}
