
package org.firstinspires.ftc.teamcode;

//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
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
    ElapsedTime clock = new ElapsedTime();
    HardwareRobot robot;//new TwoWheelDriveHardware(telemetry, hardwareMap);
    boolean aCurrent = false;
    boolean aToggle = false;
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
        double turn  =  gamepad1.right_stick_x;
        double strafe = -gamepad1.left_stick_x;
        boolean inSlowMode = gamepad1.right_bumper;

        if (gamepad1.b){
            robot.retract();
        }

        robot.drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                       drive,
                        -turn
                ),
                strafe
        ));
// yada
        //intake swivel toggle
        flipCurrent = gamepad1.right_bumper;

        if(flipCurrent && !flipLast){
            flipToggle = !flipToggle;
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
        if(intakeCurrent && !intakeLast){
            intakeToggle = !intakeToggle;
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

        if(aCurrent && !aLast){
            aToggle = !aToggle;
        }
        if(aToggle){
            robot.close_claw();
        }
        else{
            robot.open_claw();
        }
         aLast = aCurrent;

        //extension toggle
        bCurrent = gamepad1.b;

        if(bCurrent && !bLast){
            bToggle = !bToggle;
        }
        if(bToggle){
            robot.extension();
        }
        else{
            robot.retract();
        }
        bLast = bCurrent;



        //arm has to rotate to (docked, staging position for placement, first rung position,
        xCurrent = gamepad1.x;

        if(xCurrent && !xLast){
            if (armToggle == 0){
                armToggle = 1;
            }
            else if (armToggle == 1){
                armToggle = 2;
            }
            else if (armToggle == 2){
                armToggle = 0;
            }
        }
        if (armToggle == 1){
            robot.align();
        }
        else if (armToggle == 2){
            robot.dunk();
        }
        else {
            robot.dock();
        }
        xLast = xCurrent;


        robot.drive.updatePoseEstimate();

        packet.fieldOverlay().setStroke("#3F51B5");
        Drawing.drawRobot(packet.fieldOverlay(), robot.drive.pose);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + clock.toString());
        //telemetry.addData("Motors", "left (%.2f), right (%.2f)", robot.getLeftPower(), robot.getRightPower());
    }

    @Override
    public void stop() {
    }

}