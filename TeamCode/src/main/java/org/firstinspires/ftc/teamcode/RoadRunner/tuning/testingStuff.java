package org.firstinspires.ftc.teamcode.RoadRunner.tuning;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous
public class testingStuff extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        //MecanumDrive bot = new MecanumDrive(hardwareMap,36,65.25,Math.toRadians(270));
        Pose2d startPose = new Pose2d(36, 65.25,Math.toRadians(270));
        MecanumDrive bot = new MecanumDrive(hardwareMap,startPose);

            //.setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
            .followTrajectorySequence(drive ->
                    drive.trajectorySequenceBuilder(new Pose2d(36, 65.25,Math.toRadians(270)))
                            .forward(6)
                            .splineTo(new Vector2d(6, 38), Math.toRadians(270))
                            .addDisplacementMarker(() -> {
                                //claw stuff
                            })
                            .strafeLeft(42)
                            .addDisplacementMarker(() -> {
                                //pick up stuff
                            })
                            .splineTo(new Vector2d(56, 56), Math.toRadians(135))
                            .addDisplacementMarker(() -> {
                                //place in basket
                            })
                            .lineToSplineHeading(new Pose2d(58, 38, Math.toRadians(270)))
                            .addDisplacementMarker(() -> {
                                //grab
                            })
                            .lineToSplineHeading(new Pose2d(56,56,Math.toRadians(45)))
                            .addDisplacementMarker(() -> {
                                //place in basket
                            })

                            .build());
    }
}
