
package org.firstinspires.ftc.teamcode;

//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import static org.firstinspires.ftc.teamcode.DriveConstants.ARM_CLOSE_DELAY;
import static org.firstinspires.ftc.teamcode.DriveConstants.ARM_MOVE_DELAY;
import static org.firstinspires.ftc.teamcode.DriveConstants.ARM_MOVE_DELAY2;
import static org.firstinspires.ftc.teamcode.DriveConstants.ARM_TRANSFER_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.CLAW_CLOSED_POSITION;

import static org.firstinspires.ftc.teamcode.DriveConstants.EXTENSION_RECTRACT_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.HIGH_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.HORIZONTAL_EXTENSION_SPEED;
import static org.firstinspires.ftc.teamcode.DriveConstants.HORIZONTAL_WIGGLE_ROOM;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_CLAW_CLOSE;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_CLAW_OPEN;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_MOVE_DELAY;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_OPEN_DELAY;
import static org.firstinspires.ftc.teamcode.DriveConstants.INTAKE_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.LOW_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.TRANSFER_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.VERTICAL_EXTENSION_SPEED;
import static org.firstinspires.ftc.teamcode.DriveConstants.VERTICAL_EXTENSION_SPEED_MULTIPLIER;
import static org.firstinspires.ftc.teamcode.DriveConstants.VERTICAL_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.VERTICAL_WIGGLE_ROOM;


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
    ElapsedTime transferClock = new ElapsedTime();
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

    int verticalTarget = LOW_POSITION;
    int horizontalTarget = EXTENSION_RECTRACT_POSITION;



    public enum Mode {HORIZONTAL, VERTICAL, TRANSFER}
    Mode mode;

    public enum Transfer {LOW, HIGH, FLOAT, TRANSFER_RETRACT,TRANSFER_ARM_MOVE}
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
        robot.verticalExtension.setTargetPosition(horizontalTarget);
        robot.extension.setTargetPosition(verticalTarget);
        transferClock.reset();
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

        //arm //TODO should this still be here?
        if (gamepad1.dpad_right || gamepad1.dpad_left){
            robot.horizontal();
        }
        else if(gamepad1.dpad_up){
            robot.vertical();
        }

        if (transfer != Transfer.LOW || transfer != Transfer.HIGH || transfer != Transfer.FLOAT){
            //auto switch gamepad mode to "Transfer" so it doesn't interfere
            mode = Mode.TRANSFER;
        }

        switch (mode){
            case HORIZONTAL:
                //extension
                //TODO FIXED so that you change the target in the state machine and update the setTarget outside.
                horizontalTarget = horizontalTarget + (int) (gamepad1.right_trigger*VERTICAL_EXTENSION_SPEED_MULTIPLIER)
                                                    - (int)(gamepad1.left_trigger*VERTICAL_EXTENSION_SPEED_MULTIPLIER);
                //robot.extension.setTargetPosition(EXTENSION_POSITION); TODO REMOVE

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
                verticalTarget = verticalTarget + (int) (gamepad1.right_trigger*VERTICAL_EXTENSION_SPEED_MULTIPLIER)
                                                - (int)(gamepad1.left_trigger*VERTICAL_EXTENSION_SPEED);

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
            case TRANSFER:
                //dummy case so that we don't get conflicts between state machines.
                break;
        }

        switch (transfer){
            case LOW:
                if (verticalTarget != LOW_POSITION) {verticalTarget = LOW_POSITION;}
                if (gamepad1.y){
                    transfer = Transfer.TRANSFER_RETRACT;
                }
                break;

            case HIGH:
                if (verticalTarget != HIGH_POSITION){verticalTarget = HIGH_POSITION;}
                if (gamepad1.y){
                    transfer = Transfer.TRANSFER_RETRACT;
                }
                break;

            case  FLOAT:
                if (verticalTarget < LOW_POSITION){//Some checks to make sure we don't try to float our way passed the limits of the lift.
                    verticalTarget = LOW_POSITION;
                }
                else if (verticalTarget > HIGH_POSITION){
                    verticalTarget = HIGH_POSITION;
                }
                if (gamepad1.y){
                    transfer = Transfer.TRANSFER_RETRACT;
                    //ADD CLOCK RESET HERE
                    clock.reset();
                }
                break;

            case TRANSFER_RETRACT:
                //bring the horizontal extension and the vertical extension into transfer position
                //TODO - will this cause a conflict if they arrive simultaneously?
                verticalTarget = LOW_POSITION;
                int verticalCurrentPosition = robot.verticalExtension.getCurrentPosition();
                horizontalTarget = EXTENSION_RECTRACT_POSITION; //no need to set these, as it is done outside switch
                int horizontalCurrentPosition = robot.extension.getCurrentPosition();
                if (Math.abs(verticalTarget-verticalCurrentPosition)<VERTICAL_WIGGLE_ROOM &&
                    Math.abs(horizontalTarget-horizontalCurrentPosition)<HORIZONTAL_WIGGLE_ROOM){
                    //this will wait for the extensions to move.
                    robot.open_claw();
                    robot.transfer();
                    transferClock.reset();
                    transfer = Transfer.TRANSFER_ARM_MOVE;
                    //bring moving the arm to transfer position with claw open
                }

                break;
            case TRANSFER_ARM_MOVE:
                if (transferClock.milliseconds()>ARM_MOVE_DELAY){
                    robot.armTransfer(); //TODO is this the right position?
                    //move arm to "transfer" position?
                }
                if (transferClock.milliseconds()>ARM_MOVE_DELAY+ARM_CLOSE_DELAY){
                    //I am adding these delays so that they happen in order.  Might be annoying to tune the times.
                    robot.close_claw();
                    //close arm claw
                }
                if (transferClock.milliseconds()>ARM_MOVE_DELAY+ARM_CLOSE_DELAY+INTAKE_OPEN_DELAY){
                    robot.intake_open();//opens the intake claw
                }
                if (transferClock.milliseconds()>ARM_MOVE_DELAY+ARM_CLOSE_DELAY+INTAKE_OPEN_DELAY+ARM_MOVE_DELAY2){
                    robot.vertical();// move the arm to vertical position
                }
                if (transferClock.milliseconds()>ARM_MOVE_DELAY+ARM_CLOSE_DELAY+INTAKE_OPEN_DELAY+ARM_MOVE_DELAY2+INTAKE_MOVE_DELAY){
                    robot.intake();//flip the intake to the intake position
                }

                break;
        }




        robot.drive.updatePoseEstimate();

        packet.fieldOverlay().setStroke("#3F51B5");
        Drawing.drawRobot(packet.fieldOverlay(), robot.drive.pose);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
        // telemetry.addData("claw position", robot.claw.getPosition());
        telemetry.addData("intake Right flip position", robot.intakeRight.getPosition());
        telemetry.addData("intake Left flip position", robot.intakeLeft.getPosition());
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
        robot.extension.setTargetPosition(horizontalTarget);
        robot.verticalExtension.setTargetPosition(verticalTarget);
    }

    @Override
    public void stop() {
    }

}
