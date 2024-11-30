package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.DriveConstants.BASE_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.CLAW_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.CLAW_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.EXTENSION_SPEED;
import static org.firstinspires.ftc.teamcode.DriveConstants.HORIZONTAL_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_CLAW_CLOSE;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_CLAW_OPEN;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.NORMAL_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.TRANSFER_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.TURN_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.VERTICAL_POSITION;
import static org.firstinspires.ftc.teamcode.SimpleExamples.TwoWheelDriveConstants.MAX_SPEED;
import static org.firstinspires.ftc.teamcode.SimpleExamples.TwoWheelDriveConstants.SLOW_MODE_SPEED;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

import java.util.List;
@Config
public class HardwareRobot {
    //Note that there is a class called "TwoWheelDriveConstants that contain some values that are imported here
    //This allows us to store all of our constants in one convenient place.

    /* Declare OpMode members. */
    // For getting access to OpMode functionality.
    final Telemetry telemetry;
    final HardwareMap hardwareMap;
    MecanumDrive drive = null;

    public DcMotor extension = null;
    public DcMotor verticalExtension = null;
    public Servo intakeClaw = null;
    public Servo intakeFlip = null;
    public Servo intakeTurn = null;
    public Servo arm = null;
    public Servo armClaw = null;

    // Define Motor and Servo objects  (Make them private so they can't be accessed externally)
    Pose2d startPose = new Pose2d(0,0,Math.toRadians(0));


    List<LynxModule> allHubs;

    // Define a constructor that allows objects common in OpMode to be used in this class.
    // OpMode have telemetry and hardwareMap built in, but other classes don't, so to have access to them
    // We need to have variables to hold onto that data.
    public HardwareRobot(Telemetry telemetry, HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        /*drive = new MecanumDrive(hardwareMap,startPose);
        allHubs = hardwareMap.getAll(LynxModule.class);
        extension = hardwareMap.get(DcMotor.class,"extension");    //CH Port
        verticalExtension = hardwareMap.get(DcMotor.class,"verticalExtension");
        intakeClaw = hardwareMap.get(Servo.class, "intakeClaw");
        intakeFlip = hardwareMap.get(Servo.class, "intakeFlip");
        intakeTurn = hardwareMap.get(Servo.class, "intakeTurn");
        arm = hardwareMap.get(Servo.class,"arm");
        armClaw = hardwareMap.get(Servo.class, "armClaw");*/

        //set directions of all motors and servos
        extension.setDirection(DcMotor.Direction.FORWARD);
        verticalExtension.setDirection(DcMotor.Direction.REVERSE);
        intakeFlip.setDirection(Servo.Direction.FORWARD);
        intakeClaw.setDirection(Servo.Direction.REVERSE);
        intakeTurn.setDirection(Servo.Direction.FORWARD);
        arm.setDirection(Servo.Direction.FORWARD);
        armClaw.setDirection(Servo.Direction.REVERSE);

        //set the initial position for all servos
        armClaw.setPosition(CLAW_CLOSED_POSITION);
        intakeFlip.setPosition(TRANSFER_POSITION);
        arm.setPosition(HORIZONTAL_POSITION);
        //TEST THE CHANGE

        //Set motor behavior
        extension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extension.setTargetPosition(BASE_POSITION);
        extension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extension.setPower(EXTENSION_SPEED);




        //a specific piece of code used for "bulk reads".  Read gm0 for more info on Bulk Reads.
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        //Reverse one of the motors due to pointing in opposite directions


        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy

    }

    //arm claw
    public void close_claw(){
        armClaw.setPosition(CLAW_CLOSED_POSITION);
    }

    public void open_claw(){
        armClaw.setPosition(CLAW_OPEN_POSITION);
    }

    //intake claw
    public void intake_open(){
        intakeClaw.setPosition(INTAKE_CLAW_OPEN);
    }

    public void intake_close(){
        intakeClaw.setPosition(INTAKE_CLAW_CLOSE);
    }


    //arm
    public void vertical(){
        arm.setPosition(VERTICAL_POSITION);
    }

    public void horizontal(){
        arm.setPosition(HORIZONTAL_POSITION);
    }

    //intake flip
    public void transfer(){
        intakeFlip.setPosition(TRANSFER_POSITION);
    }

    public void intake(){
        intakeFlip.setPosition(INTAKE_POSITION);
    }

    //intake turn
    public void normal(){
        intakeTurn.setPosition(NORMAL_POSITION);
    }

    public void turned(){
        intakeTurn.setPosition(TURN_POSITION);
    }

}
