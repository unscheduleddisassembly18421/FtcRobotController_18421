package org.firstinspires.ftc.teamcode;

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

/***
 * This is the 2024-2025 Main Autonomous program containing 2 main auto sub codes, and 10 sub codes in total
 *  1. Autonomous Paths
 *      - BASKET: can place specimen or sample then places three other samples into basket and parks in ascent
 *      - OBZ: hangs specimen and then pushes two samples into the observation zone for the human player
 *             to make into specimens, it hangs 4 specimens in total
 *  2. controls
 *      - Dpad up and down cycles through a list of the three main autonomous sub codes
 *      - Dpad left and right cycles through a list of the three test autonomous sub codes
 *      - When in the test cycle code just press Dpad up or down to exit the test code cycle and reset to Basket or Mid
 *      - press right bumper to toggle having a delay at the beginning of autonomous
 *      - press left bumper to toggle between having a specimen at the beginning or sample
 */
@Config

@Autonomous(name = "Autonomous_2024-2025")
public class Auto extends LinearOpMode {

    private static final double SELECTOR_DELAY_TIME = 500;
    public static double ARM_ROTATE_DELAY_TIME = 1;
    public static double CLAW_ROTATE_DELAY_TIME = 0.25;
    private final ElapsedTime runtime = new ElapsedTime();
    public enum AutoSelector {BASKET, OBZ, MID, TEST, TEST2, TEST3}
    public AutoSelector autoSelector = AutoSelector.BASKET;
    double ROBOT_DELAY_TIME = 5;
    boolean delay = false;

    /* specimen means multiple things by the way (both relate to specimen)
        obz - if true it means you have specimen preloaded and human player has specimen
        basket - if true it means your preloaded with specimen
     */

    boolean specimen = true;

    @Override
    public void runOpMode() throws InterruptedException {

        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Status", "Initialized");
        telemetry.addData("autonomous selected", autoSelector);
        telemetry.update();
        HardwareRobotAuto r = new HardwareRobotAuto(telemetry, hardwareMap);

        while (opModeInInit()) {
            double time = runtime.milliseconds();

            if(((gamepad1.dpad_up) && (time > SELECTOR_DELAY_TIME) && (autoSelector == AutoSelector.OBZ)) ||
                    ((gamepad1.dpad_down) && (time > SELECTOR_DELAY_TIME) && (autoSelector == AutoSelector.MID))) {
                autoSelector = AutoSelector.BASKET;
                runtime.reset();
                telemetry.addData("autonomous selected", autoSelector);
                telemetry.update();
            } else if(((gamepad1.dpad_up) && (time > SELECTOR_DELAY_TIME) && (autoSelector == AutoSelector.MID)) ||
                    ((gamepad1.dpad_down) && (time > SELECTOR_DELAY_TIME) && (autoSelector == AutoSelector.BASKET))) {
                autoSelector = AutoSelector.OBZ;
                runtime.reset();
                telemetry.addData("autonomous selected", autoSelector);
                telemetry.update();
            } else if(((gamepad1.dpad_up) && (time > SELECTOR_DELAY_TIME) && (autoSelector == AutoSelector.BASKET)) ||
                    ((gamepad1.dpad_down) && (time > SELECTOR_DELAY_TIME) && (autoSelector == AutoSelector.OBZ))) {
                autoSelector = AutoSelector.MID;
                runtime.reset();
                telemetry.addData("autonomous selected", autoSelector);
                telemetry.update();
            }
            if (((gamepad1.dpad_left) && (autoSelector != AutoSelector.TEST) && (autoSelector != AutoSelector.TEST2) &&
                    (time > SELECTOR_DELAY_TIME)) || ((gamepad1.dpad_right) && (autoSelector == AutoSelector.TEST2) &&
                    (time > SELECTOR_DELAY_TIME))) {
                autoSelector = AutoSelector.TEST;
                runtime.reset();
                telemetry.addData("autonomous selected", autoSelector);
                telemetry.update();
            } else if (((gamepad1.dpad_left) && (autoSelector == AutoSelector.TEST) && (time > SELECTOR_DELAY_TIME)) ||
                    ((gamepad1.dpad_right) && (autoSelector == AutoSelector.TEST3) && (time > SELECTOR_DELAY_TIME))) {
                autoSelector = AutoSelector.TEST2;
                runtime.reset();
                telemetry.addData("autonomous selected", autoSelector);
                telemetry.update();
            } else if (((gamepad1.dpad_left) && (autoSelector != AutoSelector.TEST3) && (time > SELECTOR_DELAY_TIME)) ||
                    ((gamepad1.dpad_right) && (autoSelector != AutoSelector.TEST2) && (time > SELECTOR_DELAY_TIME))) {
                autoSelector = AutoSelector.TEST3;
                runtime.reset();
                telemetry.addData("autonomous selected", autoSelector);
                telemetry.update();
            }

            if (((gamepad1.dpad_up) && (autoSelector == AutoSelector.TEST) && (time > SELECTOR_DELAY_TIME)) ||
                    ((gamepad1.dpad_up) && (autoSelector == AutoSelector.TEST2) && (time > SELECTOR_DELAY_TIME)) ||
                    ((gamepad1.dpad_up) && (autoSelector == AutoSelector.TEST3) && (time > SELECTOR_DELAY_TIME))) {
                autoSelector = AutoSelector.BASKET;
                runtime.reset();
                telemetry.addData("autonomous selected", autoSelector);
                telemetry.update();
            } else if (((gamepad1.dpad_down) && (autoSelector == AutoSelector.TEST) && (time > SELECTOR_DELAY_TIME)) ||
                    ((gamepad1.dpad_down) && (autoSelector == AutoSelector.TEST2) && (time > SELECTOR_DELAY_TIME)) ||
                    ((gamepad1.dpad_down) && (autoSelector == AutoSelector.TEST3) && (time > SELECTOR_DELAY_TIME))) {
                autoSelector = AutoSelector.MID;
                runtime.reset();
                telemetry.addData("autonomous selected", autoSelector);
                telemetry.update();
            }
            if ((gamepad1.right_bumper) && (time > SELECTOR_DELAY_TIME) && (!delay)) {
                delay = true;
                runtime.reset();
                telemetry.addData("delay:",delay);
                telemetry.update();
            } else if((gamepad1.right_bumper) && (time > SELECTOR_DELAY_TIME)) {
                delay = false;
                runtime.reset();
                telemetry.addData("delay:",delay);
                telemetry.update();
            }
            if((gamepad1.left_bumper) && (time > SELECTOR_DELAY_TIME) && (!specimen)) {
                specimen = true;
                runtime.reset();
                telemetry.addData("specimen:",specimen);
                telemetry.update();
            } else if((gamepad1.left_bumper) && (time > SELECTOR_DELAY_TIME) && (specimen)) {
                specimen = false;
                runtime.reset();
                telemetry.addData("specimen:",specimen);
                telemetry.update();
            }
        }

        Pose2d initialPose;
        if (autoSelector == AutoSelector.BASKET) {
            initialPose = new Pose2d(36, 64, Math.toRadians(270));
        } else if (autoSelector == AutoSelector.OBZ) {
            initialPose = new Pose2d(-12, 64, Math.toRadians(270));
        } else if (autoSelector == AutoSelector.MID) {
            initialPose = new Pose2d(12, 64, Math.toRadians(270));
        } else {
            initialPose = new Pose2d(0,0,0);
        }

        r.drive.pose = initialPose;

        // basket path with specimen

        TrajectoryActionBuilder action0_0_0 = r.drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(270))
                .splineTo(new Vector2d(6, 33), Math.toRadians(270))
                .endTrajectory();
        TrajectoryActionBuilder action0_1 = action0_0_0.fresh()
                .setTangent(Math.toRadians(45))
                .splineToSplineHeading(new Pose2d(48,48,Math.toRadians(90)),0)
                .setTangent(Math.toRadians(270))
                .lineToY(35)
                .endTrajectory();
        TrajectoryActionBuilder action0_2 = action0_1.fresh()
                .strafeToSplineHeading(new Vector2d(56,56),Math.toRadians(45))
                .endTrajectory();
        TrajectoryActionBuilder action0_3 = action0_2.fresh()
                .strafeToSplineHeading(new Vector2d(58,40),Math.toRadians(90))
                .lineToY(35)
                .endTrajectory();
        TrajectoryActionBuilder action0_4 = action0_3.fresh()
                .strafeToSplineHeading(new Vector2d(56,56),Math.toRadians(45))
                .endTrajectory();
        TrajectoryActionBuilder action0_5 = action0_4.fresh()
                .strafeToSplineHeading(new Vector2d(52,25.5),Math.toRadians(180))
                .setTangent(Math.toRadians(180))
                .lineToX(60)
                .endTrajectory();
        TrajectoryActionBuilder action0_6 = action0_5.fresh()
                .strafeToSplineHeading(new Vector2d(56,56),Math.toRadians(45))
                .endTrajectory();
        TrajectoryActionBuilder action0_7 = action0_6.fresh()
                .setTangent(Math.toRadians(225))
                .splineTo(new Vector2d(28,0),Math.toRadians(180));

        // basket path with sample

        TrajectoryActionBuilder action0_0_1 = r.drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(270))
                .splineTo(new Vector2d(56,56),Math.toRadians(45))
                .endTrajectory();

        // obz unique movements

        TrajectoryActionBuilder action1_0 = r.drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-6,33))
                .endTrajectory();
        TrajectoryActionBuilder action1_1 = action1_0.fresh()
                .setTangent(Math.toRadians(135))
                .splineToSplineHeading(new Pose2d(-30,30,Math.toRadians(180)),Math.toRadians(270))
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-38,12),Math.toRadians(180))
                .setTangent(Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-46,24),Math.toRadians(90))
                .lineToY(56)
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-53,12),Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-60,24),Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .lineToY(50)
                .setTangent(0)
                .splineToSplineHeading(new Pose2d(-36,60,Math.toRadians(90)),Math.toRadians(45))
                .endTrajectory();
        TrajectoryActionBuilder action1_2 = action1_1.fresh()
                .strafeToSplineHeading(new Vector2d(-2,33),Math.toRadians(-90))
                .endTrajectory();
        TrajectoryActionBuilder action1_3 = action1_2.fresh()
                .strafeToSplineHeading(new Vector2d(-36,60),Math.toRadians(90))
                .endTrajectory();
        TrajectoryActionBuilder action1_4 = action1_3.fresh()
                .strafeToSplineHeading(new Vector2d(0,33),Math.toRadians(-90))
                .endTrajectory();
        TrajectoryActionBuilder action1_5 = action1_4.fresh()
                .strafeToSplineHeading(new Vector2d(-36,60),Math.toRadians(90))
                .endTrajectory();
        TrajectoryActionBuilder action1_6 = action1_5.fresh()
                .strafeToSplineHeading(new Vector2d(2,33),Math.toRadians(-90))
                .endTrajectory();
        TrajectoryActionBuilder action1_7 = action1_6.fresh()
                .strafeTo(new Vector2d(-36,60));

        telemetry.addData("Runtime", runtime.seconds());
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        // declare basket actions

        Action beginBasket;

        Action submersible;
        Action spikeOne;
        Action basketOne;
        Action spikeTwo;
        Action basketTwo;
        Action spikeThree;
        Action basketThree;
        Action ascent;

        // declare obz actions

        Action submersibleOne;
        Action pushAndGrabObz;
        Action submersibleTwo;
        Action grabObzTwo;
        Action submersibleThree;
        Action grabObzThree;
        Action submersibleFour;
        Action Obz;

        // basket paths with specimen

        submersible = action0_0_0.build();
        spikeOne = action0_1.build();
        basketOne = action0_2.build();
        spikeTwo = action0_3.build();
        basketTwo = action0_4.build();
        spikeThree = action0_5.build();
        basketThree = action0_6.build();
        ascent = action0_7.build();

        // basket path without specimen

        beginBasket = action0_0_1.build();

        // obz paths

        submersibleOne = action1_0.build();
        pushAndGrabObz = action1_1.build();
        submersibleTwo = action1_2.build();
        grabObzTwo = action1_3.build();
        submersibleThree = action1_4.build();
        grabObzThree = action1_5.build();
        submersibleFour = action1_6.build();
        Obz = action1_7.build();



        //run code

        if (autoSelector == AutoSelector.BASKET) {
            // no delay and specimen start
            if((!delay) && (specimen)) {
                Actions.runBlocking(
                        new SequentialAction(
                                submersible,
                                spikeOne,
                                basketOne,
                                spikeTwo,
                                basketTwo,
                                spikeThree,
                                basketThree,
                                ascent
                        )
                );
            } if((delay) && (specimen)) {
                Actions.runBlocking(
                        new SequentialAction(
                                new SleepAction(ROBOT_DELAY_TIME),
                                submersible,
                                spikeOne,
                                basketOne,
                                spikeTwo,
                                basketTwo,
                                spikeThree,
                                basketThree,
                                ascent
                        )
                );
            } if((delay) && (!specimen)) {
                Actions.runBlocking(
                        new SequentialAction(
                                new SleepAction(ROBOT_DELAY_TIME),
                                beginBasket,
                                spikeOne,
                                basketOne,
                                spikeTwo,
                                basketTwo,
                                spikeThree,
                                basketThree,
                                ascent
                        )
                );
            } if((!delay) && (!specimen)) {
                Actions.runBlocking(
                        new SequentialAction(
                                beginBasket,
                                spikeOne,
                                basketOne,
                                spikeTwo,
                                basketTwo,
                                spikeThree,
                                basketThree,
                                ascent
                        )
                );
            }
        } else if (autoSelector == AutoSelector.OBZ) {
            if((!delay) && (specimen)) {
                Actions.runBlocking(
                        new SequentialAction(
                                submersibleOne,
                                pushAndGrabObz,
                                submersibleTwo,
                                grabObzTwo,
                                submersibleThree,
                                grabObzThree,
                                submersibleFour,
                                Obz
                        )
                );
            } if((delay) && (specimen)) {
                Actions.runBlocking(
                        new SequentialAction(
                                new SleepAction(ROBOT_DELAY_TIME),
                                submersibleOne,
                                pushAndGrabObz,
                                submersibleTwo,
                                grabObzTwo,
                                submersibleThree,
                                grabObzThree,
                                submersibleFour,
                                Obz
                        )
                );
            } if((delay) && (!specimen)) {
                Actions.runBlocking(
                        new SequentialAction(
                                submersibleOne,
                                pushAndGrabObz,
                                submersibleTwo,
                                grabObzTwo,
                                submersibleThree,
                                Obz
                        )
                );
            } if((!delay) && (!specimen)) {
                Actions.runBlocking(
                        new SequentialAction(
                                new SleepAction(ROBOT_DELAY_TIME),
                                submersibleOne,
                                pushAndGrabObz,
                                submersibleTwo,
                                grabObzTwo,
                                submersibleThree,
                                Obz
                        )
                );
            }
        } else if (autoSelector == AutoSelector.MID) {
            if((!delay) && (specimen)) {
                Actions.runBlocking(
                        new SequentialAction(
                                submersible,
                                spikeOne,
                                basketOne,
                                spikeTwo,
                                basketTwo,
                                spikeThree,
                                basketThree,
                                ascent
                        )
                );
            if((delay) && (specimen))
                Actions.runBlocking(
                        new SequentialAction(
                                new SleepAction(ROBOT_DELAY_TIME),
                                submersible,
                                spikeOne,
                                basketOne,
                                spikeTwo,
                                basketTwo,
                                spikeThree,
                                basketThree,
                                ascent
                        )
                );
            }
            if((delay) && (!specimen)) {
                Actions.runBlocking(
                        new SequentialAction(
                                new SleepAction(ROBOT_DELAY_TIME),
                                beginBasket,
                                spikeOne,
                                basketOne,
                                spikeTwo,
                                basketTwo,
                                spikeThree,
                                basketThree,
                                ascent
                        )
                );
            }
            if((!delay) && (!specimen)) {
                Actions.runBlocking(
                        new SequentialAction(
                                beginBasket,
                                spikeOne,
                                basketOne,
                                spikeTwo,
                                basketTwo,
                                spikeThree,
                                basketThree,
                                ascent
                        )
                );
            }
        }

        // test stuff

        if (autoSelector == AutoSelector.TEST) {
            Actions.runBlocking(
                    new SequentialAction(

                    )
            );
        } else if (autoSelector == AutoSelector.TEST2) {
            Actions.runBlocking(
                    new SequentialAction(

                    )
            );

        } else if (autoSelector == AutoSelector.TEST3) {
            Actions.runBlocking(
                    new SequentialAction(

                    )
            );
        }
    }
}