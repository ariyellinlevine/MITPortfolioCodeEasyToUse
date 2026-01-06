package org.firstinspires.ftc.teamcode.pedropathing;


import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;




import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


/**
 * Constants library to keep track of all the constants for Pedro
 */
@Configurable
public class Constants {

    public static double lateralDeceleration = -73.95533;
    public static double forwardDeceleration = -35.83033333333;
    public static double centripetalScaling = 0;
    public static double xVelocity = 78.0365;
    public static double yVelocity = 336.157/6;

    public static double brakingStrength = 1.3;

    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(26.6)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.09, 0, 0.0052, 0.02))
            .headingPIDFCoefficients(new PIDFCoefficients(0.8, 0, 0.05, 0.03))
            .drivePIDFCoefficients(
                    new FilteredPIDFCoefficients(0.25, 0.005, 0.001, 0.6, 0.02)
            )

            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.0048,0,0.008,0.025))

            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(0.8,0,0.01,0.025))
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0,0,0,0,0))

            .useSecondaryDrivePIDF(false)
            .useSecondaryHeadingPIDF(true)
            .useSecondaryTranslationalPIDF(true)

            .forwardZeroPowerAcceleration(forwardDeceleration)
            .lateralZeroPowerAcceleration(lateralDeceleration)

            .centripetalScaling(centripetalScaling)
            .drivePIDFSwitch(2)


            ;


    public static MecanumConstants driveConstants = new MecanumConstants()
            .leftFrontMotorName("frontLeft")
            .leftRearMotorName("backLeft")
            .rightFrontMotorName("frontRight")
            .rightRearMotorName("backRight")

            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)

            .xVelocity(xVelocity)
            .yVelocity(yVelocity)
            .useBrakeModeInTeleOp(true)
            ;

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .distanceUnit(DistanceUnit.INCH)
            .forwardPodY(0)
            .strafePodX(0.36035433)
            .hardwareMapName("odo")
            .encoderResolution(
                    GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD
            )
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);

    public static PathConstraints pathConstraints = new PathConstraints(
            0.99,
            100,
            brakingStrength,
            1
    );

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .build();
    }
}
