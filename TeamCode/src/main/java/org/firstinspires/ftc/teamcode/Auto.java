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

/* TODO
    Trajectories
    - only need 3 trajectories and only need 2 unique sub-codes
    - fix trajectories by adding .endTrajectory(); at end of each paths
    - remake trajectories

*/
@Config

@Autonomous(name = "Autonomous2024-2025")
public class Auto extends LinearOpMode {

    private static final double SELECTOR_DELAY_TIME = 500;
    public static double ARM_ROTATE_DELAY_TIME = 2;
    public static double CLAW_ROTATE_DELAY_TIME = 0.25;
    private final ElapsedTime runtime = new ElapsedTime();
    public enum AutoSelector {BASKET, OBZ, MID, TEST, TEST2, TEST3}
    public AutoSelector autoSelector = AutoSelector.BASKET;

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

        // else statement for test autos and for errors i guess

        r.drive.pose = initialPose;

        //  basket path

        TrajectoryActionBuilder action0_0 = r.drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(270))
                .splineTo(new Vector2d(6, 33), Math.toRadians(270))
                .endTrajectory();
        TrajectoryActionBuilder action0_1 = action0_0.fresh()
                .setTangent(Math.toRadians(45))
                .splineToSplineHeading(new Pose2d(48,48,Math.toRadians(90)),0)
                .setTangent(Math.toRadians(270))
                .lineToY(40)
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

        // blue obz unique movements

        TrajectoryActionBuilder action1_0 = r.drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(-90))
                .splineTo(new Vector2d(-6, 33), Math.toRadians(-90))
                .endTrajectory();
        TrajectoryActionBuilder action1_1 = action1_0.fresh()
                .lineToY(40)
                .endTrajectory();
        Action Obz_1 = action1_0.fresh()
                .setTangent(Math.toRadians(180))
                .lineToX(-36)
                .setTangent(Math.toRadians(180))
                .splineTo(new Vector2d(-60,60),Math.toRadians(90))
                .build();

        // red basket unique movements

        TrajectoryActionBuilder action2_0 = r.drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(90))
                .splineTo(new Vector2d(-6,-33),Math.toRadians(90));
        TrajectoryActionBuilder action2_1 = action2_0.fresh()
                .setTangent(Math.toRadians(-90))
                .lineToY(-38);
        Action Obz_2 = action2_1.fresh()
                .setTangent(0)
                .splineTo(new Vector2d(60,-60),Math.toRadians(270))
                .build();

        // red obz unique movements

        TrajectoryActionBuilder action3_0 = r.drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(90))
                .splineTo(new Vector2d(6, -33), Math.toRadians(270));
        Action Obz_3 = action3_0.fresh()
                .setTangent(0)
                .splineTo(new Vector2d(60,-60),Math.toRadians(270))
                .build();

        telemetry.addData("Current Auto Selected", autoSelector);
        telemetry.addData("Runtime", runtime.seconds());
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;
        // blue basket
        Action submersible_0;
        Action spikeOne;
        Action basket_0;
        Action spikeTwo;
        Action basket_1;
        Action spikeThree;
        Action basket_2;
        Action ascent;
        // blue obz
        Action submersible_1;
        Action moveBack_1;
        // red basket
        Action submersible_2;
        Action moveBack_2;
        // red obz
        Action submersible_3;

        // basket
        submersible_0 = action0_0.build();
        spikeOne = action0_1.build();
        basket_0 = action0_2.build();
        spikeTwo = action0_3.build();
        basket_1 = action0_4.build();
        spikeThree = action0_5.build();
        basket_2 = action0_6.build();
        ascent = action0_7.build();
        // blue obz UNIQUE actions
        submersible_1 = action1_0.build();
        moveBack_1 = action1_1.build();
        // red basket UNiQUE actions
        submersible_2 = action2_0.build();
        moveBack_2 = action2_1.build();
        // red obz UNIQUE actions
        submersible_3 = action3_0.build();

        //run code
        if (autoSelector == AutoSelector.BASKET) {
            Actions.runBlocking(
                    new SequentialAction(
                            submersible_0,
                            spikeOne,
                            basket_0,
                            spikeTwo,
                            basket_1,
                            spikeThree,
                            basket_2,
                            ascent
                    )
            );
        } else if (autoSelector == AutoSelector.OBZ) {
            Actions.runBlocking(
                    new SequentialAction(
                            r.rotateArm(VERTICAL_POSITION),
                            new SleepAction(ARM_ROTATE_DELAY_TIME),
                            submersible_1,
                            r.rotateArm(DUNK_POSITION),
                            new SleepAction(ARM_ROTATE_DELAY_TIME),
                            r.clawOpen(),
                            new SleepAction(CLAW_ROTATE_DELAY_TIME),
                            moveBack_1,
                            Obz_1
                    )
            );
        } else if (autoSelector == AutoSelector.MID) {
            Actions.runBlocking(
                    new SequentialAction(
                            submersible_0,
                            spikeOne,
                            basket_0,
                            spikeTwo,
                            basket_1,
                            spikeThree,
                            basket_2,
                            ascent
                    )
            );

        }

        // test stuff

        if (autoSelector == AutoSelector.TEST) {
            //telemetry.setAutoClear(false);
            Actions.runBlocking(
                    new SequentialAction(
                            telemetryPacket -> {
                                telemetry.addData("servo position", r.claw.getPosition());
                                telemetry.update();
                                return false;
                            },
                            r.clawOpen(),
                            telemetryPacket -> {
                                telemetry.addData("servo position", r.claw.getPosition());
                                telemetry.update();
                                return false;
                            },
                            new SleepAction(2),
                            r.clawClose(),
                            telemetryPacket -> {
                                telemetry.addData("servo position", r.claw.getPosition());
                                telemetry.update();
                                return false;
                            },
                            new SleepAction(2)
                    )
            );
        } else if (autoSelector == AutoSelector.TEST2) {
            Actions.runBlocking(
                    new SequentialAction(
                            r.rotateArm(DOCK_POSITION),
                            telemetryPacket -> {
                                telemetry.addData("dock position",r.arm.getPosition());
                                telemetry.update();
                                return false;
                            },
                            new SleepAction(ARM_ROTATE_DELAY_TIME),
                            r.rotateArm(VERTICAL_POSITION),
                            telemetryPacket -> {
                                telemetry.addData("vertical position", r.arm.getPosition());
                                telemetry.update();
                                return false;  
                            },
                            new SleepAction(ARM_ROTATE_DELAY_TIME),
                            r.rotateArm(DUNK_POSITION),
                            telemetryPacket -> {
                                telemetry.addData("dunk position", r.arm.getPosition());
                                telemetry.update();
                                return false;
                            },
                            new SleepAction(ARM_ROTATE_DELAY_TIME),
                            r.clawOpen(),
                            telemetryPacket -> {
                                telemetry.addData("claw open",r.claw.getPosition());
                                telemetry.addLine("sequence complete");
                                telemetry.update();
                                return false;
                            },
                            new SleepAction(CLAW_ROTATE_DELAY_TIME)
                    )
            );

        } else if (autoSelector == AutoSelector.TEST3) {
            Actions.runBlocking(
                    new SequentialAction(
                            r.rotateArm(VERTICAL_POSITION),
                            r.rotateArm(DUNK_POSITION),
                            new SleepAction(2),
                            r.clawOpen(),
                            new SleepAction(3),
                            r.rotateArm(DOCK_POSITION),
                            new SleepAction(3)
                    )
            );
        }
    }
}