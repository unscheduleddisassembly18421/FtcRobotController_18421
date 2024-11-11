package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.DriveConstants.ALIGN_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.BASE_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.CLAW_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.CLAW_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.DOCK_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.DUNK_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.EXTENSION_SPEED;
import static org.firstinspires.ftc.teamcode.DriveConstants.FULL_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_SPEED;
import static org.firstinspires.ftc.teamcode.DriveConstants.TRANSFER_POSITION;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

import java.util.List;

public class HardwareRobotAuto {
    //Note that there is a class called "TwoWheelDriveConstants that contain some values that are imported here
    //This allows us to store all of our constants in one convenient place.

    /* Declare OpMode members. */
    // For getting access to OpMode functionality.
    final Telemetry telemetry;
    final HardwareMap hardwareMap;
    MecanumDrive drive = null;

    private DcMotor leftExtension = null;
    private DcMotor rightExtension = null;
    private DcMotor intake = null;
    private Servo intakeFlip = null;
    private DcMotor arm = null;
    public Servo claw = null;

    // Define Motor and Servo objects  (Make them private so they can't be accessed externally)
    Pose2d startPose = new Pose2d(0,0,Math.toRadians(0));


    List<LynxModule> allHubs;

    // Define a constructor that allows objects common in OpMode to be used in this class.
    // OpMode have telemetry and hardwareMap built in, but other classes don't, so to have access to them
    // We need to have variables to hold onto that data.
    public HardwareRobotAuto(Telemetry telemetry, HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        drive = new MecanumDrive(hardwareMap,startPose);
        allHubs = hardwareMap.getAll(LynxModule.class);
        leftExtension = hardwareMap.get(DcMotor.class,"leftExtension");    //CH Port _
        rightExtension = hardwareMap.get(DcMotor.class,"rightExtension");
        intake = hardwareMap.get(DcMotor.class,"intake");
        intakeFlip = hardwareMap.get(Servo.class, "intakeFlip");
        arm = hardwareMap.get(DcMotor.class,"arm");
        claw = hardwareMap.get(Servo.class, "claw");

        //set directions of all motors and servos
        leftExtension.setDirection(DcMotor.Direction.FORWARD);
        rightExtension.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.FORWARD);
        intakeFlip.setDirection(Servo.Direction.REVERSE);
        arm.setDirection(DcMotor.Direction.REVERSE);
        claw.setDirection(Servo.Direction.FORWARD);

        //set the initial position for all servos
        claw.setPosition(CLAW_CLOSED_POSITION);
        intakeFlip.setPosition(TRANSFER_POSITION);

        //Set motor behavior
        leftExtension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightExtension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftExtension.setTargetPosition(BASE_POSITION);
        leftExtension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftExtension.setPower(EXTENSION_SPEED);

        rightExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightExtension.setTargetPosition(BASE_POSITION);
        rightExtension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightExtension.setPower(EXTENSION_SPEED);

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(BASE_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(EXTENSION_SPEED);

        //a specific piece of code used for "bulk reads".  Read gm0 for more info on Bulk Reads.
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        //Reverse one of the motors due to pointing in opposite directions


        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy

    }
    public class ClawClose implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            claw.setPosition(CLAW_CLOSED_POSITION);
            return false;
        }
    }

    public Action clawClose(){return new ClawClose();}

    public class ClawOpen implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            claw.setPosition(CLAW_OPEN_POSITION);
            return false;
        }
    }

    public Action clawOpen(){return new ClawOpen();}

    public class ArmExtended implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            arm.setTargetPosition(FULL_POSITION);

            //return Math.abs(arm.getCurrentPosition()-FULL_POSITION)>10;
            //return arm.isBusy();

            return false;
        }
    }

    public Action armExtended(){return new ArmExtended();}








}
