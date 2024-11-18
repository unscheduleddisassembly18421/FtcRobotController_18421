package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.DriveConstants.DOCK_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.DUNK_POSITION;
import static org.firstinspires.ftc.teamcode.DriveConstants.VERTICAL_POSITION;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
// blah blah

/***
 * This is the 2024-2025 Main Autonomous program containing 4 main auto sub codes, and 6 sub codes in total
 *  1. Autonomous Paths
 *      - Travels to submersible and places specimen, then they park in the observation zone
 *  2. controls
 *      - Dpad up and down cycles through a list of the six main autonomous sub codes
 *      - Dpad left and right cycles through a list of the three test autonomous sub codes
 *      - When in the test cycle code just press Dpad up or down to exit the test code cycle and reset to Blue Basket or Red Mid
 */

@Config

@Autonomous(name = "Auto Playground")
public class AutoPlayground extends LinearOpMode {

    private static final double SELECTOR_DELAY_TIME = 500;
    public static double ARM_ROTATE_DELAY_TIME = 2;
    public static double CLAW_ROTATE_DELAY_TIME = 0.25;
    private final ElapsedTime runtime = new ElapsedTime();

    public enum AutoSelector {BASKET, OBZ, YADA}

    public AutoSelector autoSelector = AutoSelector.BASKET;

    @Override
    public void runOpMode() throws InterruptedException {

        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        HardwareRobotAuto r = new HardwareRobotAuto(telemetry, hardwareMap);
        while (opModeInInit()) {
            double time = runtime.milliseconds();

            if (((gamepad1.dpad_left) && (time > SELECTOR_DELAY_TIME))) {
                autoSelector = AutoSelector.BASKET;
                runtime.reset();
                telemetry.addData("autonomous selected ", autoSelector);
                telemetry.update();
            } else if ((gamepad1.dpad_right) && (time > SELECTOR_DELAY_TIME)) {
                autoSelector = AutoSelector.OBZ;
                runtime.reset();
                telemetry.addData("autonomous selected ", autoSelector);
                telemetry.update();
            } else if ((gamepad1.dpad_up) && (time > SELECTOR_DELAY_TIME)) {
                autoSelector = AutoSelector.YADA;
                runtime.reset();
                telemetry.addData("autonomous selected ", autoSelector);
                telemetry.update();
            }
        }

        Pose2d initialPose;
        if (autoSelector == AutoSelector.BASKET) {
            initialPose = new Pose2d(36, 64, Math.toRadians(90));
        } else if (autoSelector == AutoSelector.OBZ) {
            initialPose = new Pose2d(-12, 64, Math.toRadians(90));
        } else {
            initialPose = new Pose2d(36, 64, Math.toRadians(90));
        }

        // else statement for test autos and for errors i guess

        r.drive.pose = initialPose;

        // blue basket unique movements

        TrajectoryActionBuilder action0_0 = r.drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(-90))
                .splineTo(new Vector2d(6, 34.5), Math.toRadians(-90));
        TrajectoryActionBuilder action0_1 = action0_0.fresh()
                .setTangent(Math.toRadians(90))
                .lineToY(38);
        Action Obz_0 = action0_0.fresh()
                .setTangent(Math.toRadians(180))
                .splineTo(new Vector2d(-60, 60), Math.toRadians(90))
                .build();

        // blue obz unique movements

        TrajectoryActionBuilder action1_0 = r.drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(-90))
                .splineTo(new Vector2d(-6, 32.75), Math.toRadians(-90));
        Action Obz_1 = action1_0.fresh()
                .setTangent(Math.toRadians(180))
                .splineTo(new Vector2d(-60, 60), Math.toRadians(90))
                .build();

        TrajectoryActionBuilder yada1 = r.drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(-90))
                .splineTo(new Vector2d(6, 34.5), Math.toRadians(-90))
                .waitSeconds(5)
                .endTrajectory();
        TrajectoryActionBuilder yada2 = yada1.fresh()
                .setTangent(Math.toRadians(90))
                .lineToY(38)
                .waitSeconds(5)
                .endTrajectory();
        TrajectoryActionBuilder yada3 = yada2.fresh()
                .setTangent(Math.toRadians(180))
                .splineTo(new Vector2d(-60, 60), Math.toRadians(90));


        telemetry.addData("Current Auto Selected", autoSelector);
        telemetry.addData("Runtime", runtime.seconds());
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;
        // blue basket
        Action cool = yada1.build();
        Action cool2 = yada2.build();
        Action cool3 = yada3.build();


        //run code
        if (autoSelector == AutoSelector.BASKET) {
            Actions.runBlocking(
                    new SequentialAction(

                    )
            );
        } else if (autoSelector == AutoSelector.OBZ) {
            Actions.runBlocking(
                    new SequentialAction(

                    )
            );
        } else if (autoSelector == AutoSelector.YADA) {
            Actions.runBlocking(
                    new SequentialAction(
                            cool,
                            cool2,
                            cool3
                    )
            );
        }
    }
}