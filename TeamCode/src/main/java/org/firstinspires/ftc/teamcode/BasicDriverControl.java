
package org.firstinspires.ftc.teamcode;


//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Four Wheel Drive", group="")
@Disabled
public class BasicDriverControl extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime clock = new ElapsedTime();
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;
    private DcMotor leftBackMotor = null;
    private DcMotor rightBackMotor = null;
    private Servo myFavServo = null;
    public static double MAX_SPEED = 1;
    public static double SLOW_MODE_SPEED = 0.3;

    @Override
    public void init() {
        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        leftMotor  = hardwareMap.get(DcMotor.class, "left");
        rightMotor = hardwareMap.get(DcMotor.class, "right");
        leftBackMotor  = hardwareMap.get(DcMotor.class, "leftBack");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBack");

        //myFavServo = hardwareMap.get(Servo.class,"favservo");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotor.Direction.FORWARD);
        //setDirection((Servo.Direction.FORWARD));

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
        double leftPower;
        double rightPower;
        double leftBackPower;
        double rightBackPower;

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x * 1.1;
        double straf = gamepad1.right_stick_x;

        //calculate speeds
        double SPEED_CAP = MAX_SPEED;
        if (gamepad1.right_bumper) { // slow mode button for fine driving.
            SPEED_CAP = SLOW_MODE_SPEED;
        }
        leftPower = Range.clip(drive + turn, -SPEED_CAP, SPEED_CAP);
        rightPower = Range.clip(drive - turn, -SPEED_CAP, SPEED_CAP);
        leftBackPower = Range.clip(drive + turn, -SPEED_CAP, SPEED_CAP);
        rightBackPower = Range.clip(drive - turn, -SPEED_CAP, SPEED_CAP);

        leftMotor.setPower(drive + turn + straf);
        leftBackMotor.setPower(drive - turn + straf);
        rightMotor.setPower(drive - turn - straf);
        rightBackMotor.setPower(drive + turn - straf);

        // Send calculated power to wheels
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
        leftBackMotor.setPower(leftBackPower);
        rightBackMotor.setPower(rightBackPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("SLOW_MODE", gamepad1.right_bumper);
        telemetry.addData("Status", "Run Time: " + clock.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Motors", "leftBack (%.2f), rightBack (%.2f)", leftBackPower, rightBackPower);

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
