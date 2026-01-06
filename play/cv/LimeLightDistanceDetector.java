package org.firstinspires.ftc.teamcode.play.cv;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

/**
 * Finds the position of the first object detected by the limelights object detection pipeline.
 * Object must be on the ground
 */
@Disabled
@Config
@TeleOp(name = "Lime Light Distance Detector", group = "proofs")
public class LimeLightDistanceDetector extends LinearOpMode {

    //Height of camera from the ground
    public static double height = 5.5;
    double tx, ty, ta, tl;
    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        while (opModeIsActive()) {

            LLResult result = limelight.getLatestResult();

            if (result != null) {
                Pose3D botpose = result.getBotpose();

                if (result.isValid()) {
                    telemetry.addData("tx", result.getTx());
                    telemetry.addData("txnc", result.getTxNC());
                    telemetry.addData("ty", result.getTy());
                    telemetry.addData("tync", result.getTyNC());

                    telemetry.addData("Botpose", botpose.toString());

                    List<LLResultTypes.DetectorResult> detectorResults = result.getDetectorResults();
                    LLResultTypes.DetectorResult sample = null;
                    for (LLResultTypes.DetectorResult dr : detectorResults) {
                        sample = dr;
                        telemetry.addData("Detector", "Class: %s, Area: %.2f", dr.getClassName(), dr.getTargetArea());
                        break;
                    }

                    if (sample != null) {
                        tx = sample.getTargetXDegrees();
                        ty = sample.getTargetYDegrees();
                    }

                    double xDist = height/Math.tan(Math.toRadians(Math.abs(ty)));
                    telemetry.addData("X Distance", xDist);
                    telemetry.addData("Y Distance", xDist * Math.tan(Math.toRadians(tx)));

                } else {
                    telemetry.addData("Limelight", "No data available");
                }
            } else {
                telemetry.addData("Limelight", "No data available");

            }

            telemetry.update();

        }

        limelight.stop();
    }

    public void initialize() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);


        limelight.start();

        telemetry.addData(">", "Robot Ready.  Press Play.");
        telemetry.update();
    }
}
