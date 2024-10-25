
package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SimpleExamples.LiftStateMachine;

@TeleOp(name="Four Wheel Drive", group="")
//@Disabled
public class BasicDriverControl extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime clock = new ElapsedTime();
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;
    private DcMotor leftBackMotor = null;
    private DcMotor rightBackMotor = null;
    private DcMotor slideMotor = null;
    //private Servo myFavServo = null;
    public static double MAX_SPEED = 1;
    public static double SLOW_MODE_SPEED = 0.3;
    public static int LOW_POSITION = 0;
    public static int HIGH_POSITION = 600;
    public static double LIFT_SPEED = 0.8;
    public static double FLOAT_SPEED = 5;
    public static double TRIGGER_LIMIT = .05;
    public static int target = LOW_POSITION;



    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        leftMotor  = hardwareMap.get(DcMotor.class, "leftFront");//port 3
        rightMotor = hardwareMap.get(DcMotor.class, "rightFront");//port 1
        leftBackMotor  = hardwareMap.get(DcMotor.class, "leftBack");//port 2
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBack");//port 0
        slideMotor = hardwareMap.get(DcMotorEx.class, "slideMotor");

        //myFavServo = hardwareMap.get(Servo.class,"favservo");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotor.Direction.FORWARD);
        slideMotor.setDirection(DcMotor.Direction.FORWARD);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //setDirection((Servo.Direction.FORWARD));

        //leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setTargetPosition(LOW_POSITION);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(LIFT_SPEED);

        telemetry.addData("Lift Position", slideMotor.getCurrentPosition());
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
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftFrontPower;
        double rightFrontPower;
        double leftBackPower;
        double rightBackPower;

        boolean UP_PRESSED = gamepad1.dpad_up;
        boolean DOWN_PRESSED = gamepad1.dpad_down;
        double rightTrigger = gamepad1.right_trigger;
        double leftTrigger = gamepad1.left_trigger;
        boolean RIGHT_TRIGGER_HELD = rightTrigger > TRIGGER_LIMIT;
        boolean LEFT_TRIGGER_HELD = leftTrigger > TRIGGER_LIMIT;

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x * 1.1;
        double strafe = -gamepad1.left_stick_x;

        //calculate speeds
        double SPEED_CAP = MAX_SPEED;
        if (gamepad1.right_bumper) { // slow mode button for fine driving.
            SPEED_CAP = SLOW_MODE_SPEED;
        }

        leftFrontPower = Range.clip(drive + turn - strafe, -SPEED_CAP, SPEED_CAP);
        rightFrontPower = Range.clip(drive - turn + strafe, -SPEED_CAP, SPEED_CAP);
        leftBackPower = Range.clip(drive + turn + strafe, -SPEED_CAP, SPEED_CAP);
        rightBackPower = Range.clip(drive - turn - strafe, -SPEED_CAP, SPEED_CAP);


        // Send calculated power to wheels
        leftMotor.setPower(leftFrontPower);
        rightMotor.setPower(rightFrontPower);
        leftBackMotor.setPower(leftBackPower);
        rightBackMotor.setPower(rightBackPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("SLOW_MODE", gamepad1.right_bumper);
        telemetry.addData("Status", "Run Time: " + clock.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftFrontPower, rightFrontPower);
        telemetry.addData("Motors", "leftBack (%.2f), rightBack (%.2f)", leftBackPower, rightBackPower);

        if(gamepad1.dpad_down){
            target = LOW_POSITION;
        }
        else if (gamepad1.dpad_up){
            target = HIGH_POSITION;
        }

        if (target < LOW_POSITION){//Some checks to make sure we don't try to float our way passed the limits of the lift.
            target = LOW_POSITION;
        }
        else if (target > HIGH_POSITION){
            target = HIGH_POSITION;
        }

        else {target = target + (int) (rightTrigger*FLOAT_SPEED) - (int)(leftTrigger*FLOAT_SPEED);}

        slideMotor.setTargetPosition(target);

        telemetry.addData("Status", "Run Time: " + clock.toString());
        telemetry.addData("Lift Position", slideMotor.getCurrentPosition());
        telemetry.addData("Lift Target Position", target);

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
