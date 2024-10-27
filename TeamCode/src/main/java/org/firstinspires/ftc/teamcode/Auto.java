package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Config

@Autonomous(name = "Autonomous2024-2025")
public class Auto extends LinearOpMode {

    private static final double SELECTOR_DELAY_TIME = 500;
    private final ElapsedTime runtime = new ElapsedTime();
    public enum AutoSelector {BLUE_BASKET,RED_BASKET,BLUE_OBZ,RED_OBZ,BLUE_MID,RED_MID}
    public AutoSelector autoSelector = AutoSelector.BLUE_BASKET;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        while(opModeInInit()){
            double time = runtime.milliseconds();
            if(((gamepad1.dpad_down) && (autoSelector == AutoSelector.RED_MID) && (time > SELECTOR_DELAY_TIME)) ||
                    ((gamepad1.dpad_up) && (autoSelector == AutoSelector.RED_BASKET) && (time > SELECTOR_DELAY_TIME))){
                autoSelector = AutoSelector.BLUE_BASKET;
                runtime.reset();
            }
            else if(((gamepad1.dpad_down) && (autoSelector == AutoSelector.BLUE_BASKET) && (time > SELECTOR_DELAY_TIME)) ||
                ((gamepad1.dpad_up) && (autoSelector == AutoSelector.BLUE_OBZ) && (time > SELECTOR_DELAY_TIME))){
                autoSelector = AutoSelector.RED_BASKET;
                runtime.reset();
            }
            else if(((gamepad1.dpad_down) && (autoSelector == AutoSelector.RED_BASKET) && (time > SELECTOR_DELAY_TIME)) ||
                    ((gamepad1.dpad_up) && (autoSelector == AutoSelector.RED_OBZ) && (time > SELECTOR_DELAY_TIME))){
                autoSelector = AutoSelector.BLUE_OBZ;
                runtime.reset();
            }
            else if(((gamepad1.dpad_down) && (autoSelector == AutoSelector.BLUE_OBZ) && (time > SELECTOR_DELAY_TIME)) ||
                    ((gamepad1.dpad_up) && (autoSelector == AutoSelector.BLUE_MID) && (time > SELECTOR_DELAY_TIME))){
                autoSelector = AutoSelector.RED_OBZ;
                runtime.reset();
            }
            else if(((gamepad1.dpad_down) && (autoSelector == AutoSelector.RED_OBZ) && (time > SELECTOR_DELAY_TIME)) ||
                    ((gamepad1.dpad_up) && (autoSelector == AutoSelector.RED_MID) && (time > SELECTOR_DELAY_TIME))){
                autoSelector = AutoSelector.BLUE_MID;
                runtime.reset();
            }
            else if(((gamepad1.dpad_down) && (autoSelector == AutoSelector.BLUE_MID) && (time > SELECTOR_DELAY_TIME)) ||
                ((gamepad1.dpad_up) && (autoSelector == AutoSelector.BLUE_BASKET) && (time > SELECTOR_DELAY_TIME))) {
                autoSelector = AutoSelector.RED_MID;
                runtime.reset();
            }
        }
        Pose2d initialPose;
        if(autoSelector == AutoSelector.BLUE_BASKET){
            initialPose = new Pose2d(36, 65.25, Math.toRadians(270));
        }
        else if(autoSelector == AutoSelector.BLUE_OBZ){
            initialPose = new Pose2d(-12,65.25, Math.toRadians(270));
        }
        else if(autoSelector == AutoSelector.RED_BASKET){
            initialPose = new Pose2d(-36,-65.25,Math.toRadians(90));
        }
        else if(autoSelector == AutoSelector.RED_OBZ){
            initialPose = new Pose2d(12,-65.25,Math.toRadians(90));
        }
        else if(autoSelector == AutoSelector.BLUE_MID) {
            initialPose = new Pose2d(0, 65.25, Math.toRadians(270));
        }
        // red mid just so you know ;)
        else {
            initialPose = new Pose2d(0,-65.5,Math.toRadians(90));
        }

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        int visionOutputPosition = 1;

        TrajectoryActionBuilder action0_0 = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-6,-38),Math.toRadians(90));
        TrajectoryActionBuilder action0_1 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(Math.toRadians(0)))
                .lineToXSplineHeading(-48,Math.toRadians(270));
        TrajectoryActionBuilder action0_2 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(-90))
                .splineTo(new Vector2d(-56,-56),Math.toRadians(225));
        TrajectoryActionBuilder action0_3 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(90))
                .lineToYSplineHeading(-38,Math.toRadians(-90));
        Action basketTwo_0 = action0_0.fresh()
                .lineToYSplineHeading(-56,Math.toRadians(225))
                .build();

        TrajectoryActionBuilder action1_0 = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-6,38),Math.toRadians(270));
        TrajectoryActionBuilder action1_1 = drive.actionBuilder(initialPose)
                .setTangent(0)
                .lineToXSplineHeading(-48,Math.toRadians(90));
        TrajectoryActionBuilder action1_2 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(90))
                .lineToY(56);
        TrajectoryActionBuilder action1_3 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(245))
                .lineToY(38);
        Action Obz2_0 = action0_1.fresh()
                .setTangent(Math.toRadians(90))
                .lineToY(56)
                .build();

        TrajectoryActionBuilder action2_0 = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-6,-38),Math.toRadians(90));
        TrajectoryActionBuilder action2_1 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(Math.toRadians(0)))
                .lineToXSplineHeading(-48,Math.toRadians(270));
        TrajectoryActionBuilder action2_2 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(-90))
                .splineTo(new Vector2d(-56,-56),Math.toRadians(225));
        TrajectoryActionBuilder action2_3 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(90))
                .lineToYSplineHeading(-38,Math.toRadians(-90));
        Action basketTwo_1 = action2_0.fresh()
                .lineToYSplineHeading(-56,Math.toRadians(225))
                .build();

        TrajectoryActionBuilder action3_0 = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(6,-38),Math.toRadians(90));
        TrajectoryActionBuilder action3_1 = drive.actionBuilder(initialPose)
                .setTangent(0)
                .lineToXSplineHeading(48,Math.toRadians(270));
        TrajectoryActionBuilder action3_2 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(-90))
                .lineToY(-56);
        TrajectoryActionBuilder action3_3 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(65))
                .lineToY(-38);
        Action Obz2_1 = action3_0.fresh()
                .setTangent(Math.toRadians(90))
                .lineToY(-56)
                .build();

        TrajectoryActionBuilder action4_0 = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-6,-38),Math.toRadians(90));
        TrajectoryActionBuilder action4_1 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(Math.toRadians(0)))
                .lineToXSplineHeading(-48,Math.toRadians(270));
        TrajectoryActionBuilder action4_2 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(-90))
                .splineTo(new Vector2d(-56,-56),Math.toRadians(225));
        TrajectoryActionBuilder action4_3 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(90))
                .lineToYSplineHeading(-38,Math.toRadians(-90));
        Action basketTwo_2 = action4_0.fresh()
                .lineToYSplineHeading(-56,Math.toRadians(225))
                .build();

        TrajectoryActionBuilder action5_0 = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-6,-38),Math.toRadians(90));
        TrajectoryActionBuilder action5_1 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(Math.toRadians(0)))
                .lineToXSplineHeading(-48,Math.toRadians(270));
        TrajectoryActionBuilder action5_2 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(-90))
                .splineTo(new Vector2d(-56,-56),Math.toRadians(225));
        TrajectoryActionBuilder action5_3 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(90))
                .lineToYSplineHeading(-38,Math.toRadians(-90));
        Action basketTwo_3 = action2_0.fresh()
                .lineToYSplineHeading(-56,Math.toRadians(225))
                .build();



        telemetry.addData("Current Auto Selected", autoSelector);
        telemetry.addData("Runtime", runtime.seconds());
        telemetry.update();
        while (!isStopRequested() && !opModeIsActive()) {
            int position = visionOutputPosition;
            telemetry.addData("Position during Init", position);
            telemetry.update();
        }

        int startPosition = visionOutputPosition;
        telemetry.addData("Starting Position", startPosition);
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        Action submersible_0;
        Action spikeOne_0;
        Action basketOne_0;
        Action spikeTwo_0;

        Action submersible_1;
        Action spikeOne_1;
        Action Obz1_0;
        Action spikeTwo_1;

        Action submersible_2;
        Action spikeOne_2;
        Action basketOne_1;
        Action spikeTwo_2;

        Action submersible_3;
        Action spikeOne_3;
        Action Obz1_1;
        Action spikeTwo_3;

        Action submersible_4;
        Action spikeOne_4;
        Action basketOne_2;
        Action spikeTwo_4;

        Action submersible_5;
        Action spikeOne_5;
        Action basketOne_3;
        Action spikeTwo_5;

        submersible_0 = action0_0.build();
        spikeOne_0 = action0_1.build();
        basketOne_0 = action0_2.build();
        spikeTwo_0 = action0_3.build();

        submersible_1 = action1_0.build();
        spikeOne_1 = action1_1.build();
        Obz1_0 = action1_2.build();
        spikeTwo_1 = action1_3.build();

        submersible_2 = action2_0.build();
        spikeOne_2 = action2_1.build();
        basketOne_1 = action2_2.build();
        spikeTwo_2 = action2_3.build();

        submersible_3 = action3_0.build();
        spikeOne_3 = action3_1.build();
        Obz1_1 = action3_2.build();
        spikeTwo_3 = action3_3.build();

        submersible_4 = action4_0.build();
        spikeOne_4 = action4_1.build();
        basketOne_2 = action4_2.build();
        spikeTwo_4 = action4_3.build();

        submersible_5 = action5_0.build();
        spikeOne_5 = action5_1.build();
        basketOne_3 = action5_2.build();
        spikeTwo_5 = action5_3.build();

        //run code
        if(autoSelector == AutoSelector.BLUE_BASKET) {
            Actions.runBlocking(
                    new SequentialAction(
                            submersible_0,
                            spikeOne_0,
                            basketOne_0,
                            spikeTwo_0,
                            basketTwo_0
                    )
            );
        }
        else if(autoSelector == AutoSelector.BLUE_OBZ) {
            Actions.runBlocking(
                    new SequentialAction(
                            submersible_1,
                            spikeOne_1,
                            Obz1_0,
                            spikeTwo_1,
                            Obz2_0
                    )
            );
        }
        else if(autoSelector == AutoSelector.RED_BASKET) {
            Actions.runBlocking(
                    new SequentialAction(
                            submersible_2,
                            spikeOne_2,
                            basketOne_1,
                            spikeTwo_2,
                            basketTwo_1
                    )
            );
        }
        else if(autoSelector == AutoSelector.RED_OBZ) {
            Actions.runBlocking(
                    new SequentialAction(
                            submersible_3,
                            spikeOne_3,
                            Obz1_1,
                            spikeTwo_3,
                            Obz2_1
                    )
            );
        }
        else if(autoSelector == AutoSelector.BLUE_MID) {
            Actions.runBlocking(
                    new SequentialAction(
                            submersible_4,
                            spikeOne_4,
                            basketOne_2,
                            spikeTwo_4,
                            basketTwo_2
                    )
            );
        }
        else if(autoSelector == AutoSelector.RED_MID) {
            Actions.runBlocking(
                    new SequentialAction(
                            submersible_5,
                            spikeOne_5,
                            basketOne_3,
                            spikeTwo_5,
                            basketTwo_3
                    )
            );
        }
    }
}