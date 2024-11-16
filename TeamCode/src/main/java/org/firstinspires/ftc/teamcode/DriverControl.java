
package org.firstinspires.ftc.teamcode;

//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import static org.firstinspires.ftc.teamcode.DriveConstants.DOCK_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.DUNK_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.VERTICAL_POSITION;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RoadRunner.Drawing;
import org.firstinspires.ftc.teamcode.SimpleExamples.TwoWheelDriveHardware;

//The thing to notice in this OpMode is how short it is.  Most of the logic is happening in the hardware class that is stored
//in the variable "robot".  Note how we just construct a robot object using the Hardware Class, pass it some variables from the opmode
//to be used in the hardware class, and then call methods from the hardware class.  There are also some values that are being called
//from the Drive Constants class.  This kind of process of splitting up parts of the code into different files / classes is called
//"Encapsulation" and it helps keep the logic separated into distinct bites.  When writing your own code, think about inputs and outputs.
//What information needs to come in and what needs to go out, and then you can write a class or a method that encapsulates that information
//Keeping all of the logic locked inside.

@Config
@TeleOp(name="Driver Control", group="Comp")
//@Disabled
public class DriverControl extends OpMode
{
    private static final double TOGGLE_DELAY = 250;
    ElapsedTime clock = new ElapsedTime();
    HardwareRobot robot;//new TwoWheelDriveHardware(telemetry, hardwareMap);
    boolean aCurrent = false;
    boolean aToggle = true;
    boolean aLast = false;

    boolean bCurrent = false;
    boolean bToggle = false;
    boolean bLast = false;

    boolean flipCurrent = false;
    boolean flipToggle = false;
    boolean flipLast = false;

    boolean intakeCurrent = false;
    boolean intakeToggle = false;
    boolean intakeLast = false;



    int armToggle = 0;  // the current "position" of the arm
    boolean xCurrent = false;
    boolean xLast = false;



    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        robot = new HardwareRobot(telemetry,hardwareMap);
        robot.close_claw();
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        clock.reset();
    }

    @Override
    public void loop() {
        TelemetryPacket packet = new TelemetryPacket();

        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.left_stick_x;
        double strafe = -gamepad1.right_stick_x;
        boolean inSlowMode = gamepad1.right_bumper;



        robot.drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                       drive,
                        -turn
                ),
                strafe
        ));
        //intake swivel toggle
        flipCurrent = gamepad1.right_bumper;

        if(flipCurrent && !flipLast && clock.milliseconds() > TOGGLE_DELAY){
            flipToggle = !flipToggle;
            clock.reset();
        }
        if(flipToggle){
            robot.intake();
        }
        else{
            robot.transfer();
        }
        flipLast = flipCurrent;

        // intake
        intakeCurrent = gamepad1.left_bumper;
        if(intakeCurrent && !intakeLast && clock.milliseconds() > TOGGLE_DELAY){
            intakeToggle = !intakeToggle;
            clock.reset();
        }
        if(intakeToggle){
            robot.nomnom();
        }
        else{
            robot.nomnomStop();
        }

        if(gamepad1.y){
            robot.reverseIntake();
        }
        else{
            robot.forwardIntake();
        }
        //other button to reverse direction

        //claw toggle to close and open
        aCurrent = gamepad1.a;

        if(aCurrent && !aLast && clock.milliseconds() > TOGGLE_DELAY){
            aToggle = !aToggle;
            clock.reset();
        }
        if(aToggle){
            robot.close_claw();
        }
        else{
            robot.open_claw();
        }
         aLast = aCurrent;

        //extension toggle
//        bCurrent = gamepad1.b;
//
//        if(bCurrent && !bLast && clock.milliseconds() > TOGGLE_DELAY){
//            bToggle = !bToggle;
//            clock.reset();
//        }
//        if(bToggle){
//            robot.extension();
//        }
//        else{
//            robot.retract();
//        }
//        bLast = bCurrent;
        robot.intake.setPower((gamepad1.right_trigger-gamepad1.left_trigger)/2);
        //robot.extend(gamepad1.right_trigger-gamepad1.left_trigger);
        // cool

        //arm has to rotate to (docked, staging position for placement, first rung position,
        //TODO Should we move to DPAD so we can move these in any order?
        if(gamepad1.dpad_up){
            robot.arm.setPosition(VERTICAL_POSITION);
        }
        else if(gamepad1.dpad_down){
            robot.arm.setPosition(DUNK_POSITION);
        }
        else if(gamepad1.dpad_right || gamepad1.dpad_left){
            robot.arm.setPosition(DOCK_POSITION);
        }

//        xCurrent = gamepad1.x;
//        if(xCurrent && !xLast){
//            clock.reset();
//            if (armToggle == 0){
//                armToggle = 1;
//            }
//            else if (armToggle == 1){
//                armToggle = 2;
//            }
//            else if (armToggle == 2){
//                armToggle = 3;
//            }
//            else if (armToggle == 3){
//                armToggle = 0;
//            }
//        }
//        if (armToggle == 1){
//            robot.vertical();
//        }
//        else if (armToggle == 2){
//            robot.align();
//        }
//        else if (armToggle == 3){
//            robot.dunk();
//        }
//        else {
//            robot.dock();
//        }
//        xLast = xCurrent;


        robot.drive.updatePoseEstimate();

        packet.fieldOverlay().setStroke("#3F51B5");
        Drawing.drawRobot(packet.fieldOverlay(), robot.drive.pose);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
        telemetry.addData("claw position", robot.claw.getPosition());
        telemetry.addData("intake flip position", robot.intakeFlip.getPosition());
        //telemetry.addData("arm position", robot.arm.getPosition());
        // something i came up with
        if(robot.arm.getPosition() == VERTICAL_POSITION) {
            telemetry.addLine("vertical position");
        } else if(robot.arm.getPosition() == DUNK_POSITION) {
            telemetry.addLine("start position");
        } else if(robot.arm.getPosition() == DOCK_POSITION) {
            telemetry.addLine("grab position");
        }
        telemetry.addData("armToggle", armToggle);
        telemetry.update();

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + clock.toString());
        //telemetry.addData("Motors", "left (%.2f), right (%.2f)", robot.getLeftPower(), robot.getRightPower());
    }

    @Override
    public void stop() {
    }

}
