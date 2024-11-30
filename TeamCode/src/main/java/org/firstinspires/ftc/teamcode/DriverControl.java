
package org.firstinspires.ftc.teamcode;

//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import static org.firstinspires.ftc.teamcode.DriveConstants.HIGH_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_CLAW_CLOSE;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_CLAW_OPEN;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.LOW_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.TRANSFER_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.VERTICAL_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.target;

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
import org.firstinspires.ftc.teamcode.SimpleExamples.LiftStateMachine;
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

    boolean xCurrent = false;
    boolean xToggle = false;
    boolean xLast = false;


    boolean aVerticalCurrent = false;
    boolean aVerticalToggle = true;
    boolean aVerticalLast = false;

    boolean modeCurrent = false;
    boolean modeToggle = false;
    boolean modeLast = false;



    public enum Mode {HORIZONTAL, VERTICAL}
    Mode mode;

    public enum Transfer {LOW, HIGH, FLOAT, TRANSFER}
    Transfer transfer;

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

        double drive =  -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x;



        robot.drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        drive,
                        -turn
                ),
                strafe
        ));
        //switch mode
        modeCurrent = gamepad1.right_bumper;

        if(modeCurrent && !modeLast && clock.milliseconds() > TOGGLE_DELAY){
            modeToggle = !modeToggle;
            clock.reset();
        }
        if(modeToggle){
            mode = Mode.VERTICAL;
        }
        else{
            mode = Mode.HORIZONTAL;
        }
        modeLast = modeCurrent;

        //arm
        if (gamepad1.dpad_right || gamepad1.dpad_left){
            robot.horizontal();
        }
        else if(gamepad1.dpad_up){
            robot.vertical();
        }

        switch (mode){
            case HORIZONTAL:
                //extension
                robot.extension.setTargetPosition((int) (gamepad1.right_trigger - gamepad1.left_trigger));

                //intake claw
                aCurrent = gamepad1.a;

                if(aCurrent && !aLast && clock.milliseconds() > TOGGLE_DELAY){
                    aToggle = !aToggle;
                    clock.reset();
                }
                if(aToggle){
                    robot.intake_close();
                }
                else{
                    robot.intake_open();
                }
                aLast = aCurrent;

                //intake flip
                xCurrent = gamepad1.x;

                if(xCurrent && !xLast && clock.milliseconds() > TOGGLE_DELAY){
                    xToggle = !xToggle;
                    clock.reset();
                }
                if(xToggle){
                    robot.intake();
                }
                else{
                    robot.transfer();
                }
                xLast = xCurrent;

                //intake turn
                bCurrent = gamepad1.b;

                if(bCurrent && !bLast && clock.milliseconds() > TOGGLE_DELAY){
                    bToggle = !bToggle;
                    clock.reset();
                }
                if(bToggle){
                    robot.turned();
                }
                else{
                    robot.normal();
                }
                bLast = bCurrent;

                break;

            case VERTICAL:
                //extension
                robot.verticalExtension.setTargetPosition((int) (gamepad1.right_trigger - gamepad1.left_trigger));

                //arm claw
                aVerticalCurrent = gamepad1.a;

                if(aVerticalCurrent && !aVerticalLast && clock.milliseconds() > TOGGLE_DELAY){
                    aVerticalToggle = !aVerticalToggle;
                    clock.reset();
                }
                if(aVerticalToggle){
                    robot.close_claw();
                }
                else{
                    robot.open_claw();
                }
                aVerticalLast = aVerticalCurrent;
                break;
        }

        switch (transfer){
            case LOW:
                if (target != LOW_POSITION) {target = LOW_POSITION;}
                if (gamepad1.y){
                    transfer = Transfer.TRANSFER;
                }
                break;

            case HIGH:
                if (target != HIGH_POSITION){target = HIGH_POSITION;}
                if (gamepad1.y){
                    transfer = Transfer.TRANSFER;
                }
                break;

            case  FLOAT:
                if (target < LOW_POSITION){//Some checks to make sure we don't try to float our way passed the limits of the lift.
                    target = LOW_POSITION;
                }
                else if (target > HIGH_POSITION){
                    target = HIGH_POSITION;
                }
                if (gamepad1.y){
                    transfer = Transfer.TRANSFER;
                }
                break;

            case TRANSFER:
                //transfer stuff
                break;
        }



        robot.drive.updatePoseEstimate();

        packet.fieldOverlay().setStroke("#3F51B5");
        Drawing.drawRobot(packet.fieldOverlay(), robot.drive.pose);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
        // telemetry.addData("claw position", robot.claw.getPosition());
        telemetry.addData("intake flip position", robot.intakeFlip.getPosition());
        //telemetry.addData("arm position", robot.arm.getPosition());
        // something i came up with
        /*
        if(robot.arm.getPosition() == VERTICAL_POSITION) {
            telemetry.addLine("vertical position");
        } else if(robot.arm.getPosition() == DUNK_POSITION) {
            telemetry.addLine("start position");
        } else if(robot.arm.getPosition() == DOCK_POSITION) {
            telemetry.addLine("grab position");
        }
        */
        //telemetry.addData("armToggle", armToggle);
        telemetry.update();

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + clock.toString());
        //telemetry.addData("Motors", "left (%.2f), right (%.2f)", robot.getLeftPower(), robot.getRightPower());
    }

    @Override
    public void stop() {
    }

}
