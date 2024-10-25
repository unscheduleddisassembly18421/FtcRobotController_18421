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
import org.firstinspires.ftc.teamcode.SimpleExamples.AutonomousSelectorExample;

@Config

@Autonomous
public class Auto extends LinearOpMode {
    private static final double SELECTOR_DELAY_TIME = 500;
    private ElapsedTime runtime = new ElapsedTime();
    public enum AutoSelector {BLUE_BASKET,RED_BASKET,BLUE_OBZ,RED_OBZ}
    public AutoSelector autoSelector = AutoSelector.BLUE_BASKET;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        while(opModeInInit()){
            double time = runtime.milliseconds();
            if(((gamepad1.dpad_down) && (autoSelector == AutoSelector.RED_OBZ) && (time > SELECTOR_DELAY_TIME)) ||
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
                    ((gamepad1.dpad_up) && (autoSelector == AutoSelector.BLUE_BASKET) && (time > SELECTOR_DELAY_TIME))){
                autoSelector = AutoSelector.RED_OBZ;
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
        else{
            initialPose = new Pose2d(12,-65.25,Math.toRadians(90));
        }

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        int visionOutputPosition = 1;

        TrajectoryActionBuilder action0_0 = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-6,-38),Math.toRadians(90));
        TrajectoryActionBuilder action1_0 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(Math.toRadians(0)))
                .lineToXSplineHeading(-48,Math.toRadians(270));
        TrajectoryActionBuilder action2_0 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(-90))
                .splineTo(new Vector2d(-56,-56),Math.toRadians(225));
        TrajectoryActionBuilder action3_0 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(90))
                .lineToYSplineHeading(-38,Math.toRadians(-90));
        Action basketTwo = action0_0.fresh()
                .lineToYSplineHeading(-56,Math.toRadians(225))
                .build();

        TrajectoryActionBuilder action0_1 = drive.actionBuilder(initialPose)
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
        Action Obz2 = action0_1.fresh()
                .setTangent(Math.toRadians(90))
                .lineToY(56)
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

        Action submersible0;
        Action spikeOne0;
        Action basketOne;
        Action spikeTwo;

        Action submersible1;
        Action spikeOne1;
        Action Obz1;
        Action spikeTwo1;

        submersible0 = action0_0.build();
        spikeOne0 = action1_0.build();
        basketOne = action2_0.build();
        spikeTwo = action3_0.build();

        submersible1 = action1_0.build();
        spikeOne1 = action1_1.build();
        Obz1 = action1_2.build();
        spikeTwo1 = action1_3.build();


        //run code
        if(autoSelector == AutoSelector.BLUE_BASKET) {
            Actions.runBlocking(
                    new SequentialAction(
                            submersible0,
                            spikeOne0,
                            basketOne,
                            spikeTwo,
                            basketTwo
                    )
            );
        }
        else if(autoSelector == AutoSelector.BLUE_OBZ) {
            Actions.runBlocking(
                    new SequentialAction(
                            submersible1,
                            spikeOne1,
                            Obz1,
                            spikeTwo1,
                            Obz2
                    )
            );
        }
    }
}
