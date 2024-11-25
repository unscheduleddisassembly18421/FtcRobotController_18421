package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import org.jetbrains.annotations.Nullable;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(78, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
                String blahblah = "blueauto3";

                if (blahblah.equals("blueauto1")) {
                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(12, 64, Math.toRadians(270)))
                                    .setTangent(Math.toRadians(270))
                            .splineTo(new Vector2d(6, 33), Math.toRadians(270))
                                    .strafeTo(new Vector2d(6,48))


                                    //.setTangent(Math.toRadians(45))
                                    //.splineToSplineHeading(new Pose2d(48,48,Math.toRadians(90)),0)
                                    .setTangent(Math.toRadians(225))
                                    .strafeToSplineHeading(new Vector2d(48,48),Math.toRadians(90))
                                    .setTangent(Math.toRadians(270))
                                    .lineToY(35)
                                    .strafeToSplineHeading(new Vector2d(56,56),Math.toRadians(45))
                                    .strafeToSplineHeading(new Vector2d(58,40),Math.toRadians(90))
                                    .setTangent(Math.toRadians(270))
                                    .lineToY(35)
                                    .strafeToSplineHeading(new Vector2d(56,56),Math.toRadians(45))
                                    .strafeToSplineHeading(new Vector2d(52,25.5),Math.toRadians(180))
                                    .setTangent(Math.toRadians(180))
                                    .lineToX(60)
                                    .strafeToSplineHeading(new Vector2d(56,56),Math.toRadians(45))
                                     //       .strafeToSplineHeading(new Vector2d(26,0),0)
                                    .setTangent(Math.toRadians(225))
                                    .splineToSplineHeading(new Pose2d(28,0,Math.toRadians(180)),Math.toRadians(180))
                            .build());

                    meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                            .setDarkMode(true)
                            .setBackgroundAlpha(0.95f)
                            .addEntity(myBot)
                            .start();
                } else if (blahblah.equals("blueauto3")) {
                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12,64, Math.toRadians(270)))
                                    //.setTangent(Math.toRadians(270))
                                    //.splineTo(new Vector2d(-6,33),Math.toRadians(270))
                                    .strafeTo(new Vector2d(-6,33))
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
                                            //.strafeToSplineHeading(new Vector2d(-36,60),Math.toRadians(90))
                                            .strafeToSplineHeading(new Vector2d(-2,33),Math.toRadians(-90))
                                            .strafeToSplineHeading(new Vector2d(-36,60),Math.toRadians(90))
                            .strafeToSplineHeading(new Vector2d(0,33),Math.toRadians(-90))
                            .strafeToSplineHeading(new Vector2d(-36,60),Math.toRadians(90))
                            .strafeToSplineHeading(new Vector2d(2,33),Math.toRadians(-90))
                            .strafeTo(new Vector2d(-36,60))


                            /*
                                    .setTangent(Math.toRadians(90))
                                    .lineToY(40)
                            .setTangent(Math.toRadians(180))
                            .splineToSplineHeading(new Pose2d(-34,0,0),Math.toRadians(270))

                                            .setTangent(Math.toRadians(135))
                                            .splineToConstantHeading(new Vector2d(-30,30),Math.toRadians(270))
                                            .lineToY(25)
                                            .setTangent(Math.toRadians(270))
                                            .splineToSplineHeading(new Pose2d(-43,12,Math.toRadians(180)),Math.toRadians(180))
                                            .lineToX(-53)
                                            .setTangent(Math.toRadians(90))
                                            .lineToY(56)
                                            .strafeToSplineHeading(new Vector2d(-58,38),Math.toRadians(90))
                                            .setTangent(Math.toRadians(90))
                                            .lineToY(60)
                                            .setTangent(0)
                                            .splineToSplineHeading(new Pose2d(-6,33,Math.toRadians(-90)),Math.toRadians(270))
                                             .setTangent(Math.toRadians(90))


                                    .lineToY(40)
                            .setTangent(Math.toRadians(180))
                            .splineToSplineHeading(new Pose2d(-34,0,0),Math.toRadians(270))
*/

                                    //.splineToConstantHeading()
                            .build());
                            meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                                    .setDarkMode(true)
                                    .setBackgroundAlpha(0.95f)
                                    .addEntity(myBot)
                                    .start();
                } else if(blahblah.equals("redauto1")){
                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-36,-64,Math.toRadians(270)))
                                    .setTangent(Math.toRadians(90))
                                    .splineTo(new Vector2d(-6,-33),Math.toRadians(90))
                                    .setTangent(Math.toRadians(-90))
                                    .lineToY(-38)
                                    .setTangent(Math.toRadians(Math.toRadians(0)))
                                    .splineTo(new Vector2d(60,-60),Math.toRadians(270))
                            .build());
                    meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                            .setDarkMode(true)
                            .setBackgroundAlpha(0.95f)
                            .addEntity(myBot)
                            .start();
                } else if(blahblah.equals("redauto2")){
                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(12,-64,Math.toRadians(270)))
                                    //drive to submer
                                    .setTangent(Math.toRadians(90))
                                    .splineTo(new Vector2d(6,-38),Math.toRadians(90))
                                    //drive from sub to spike
                                    .setTangent(Math.toRadians(Math.toRadians(0)))
                                    .lineToXSplineHeading(-48,Math.toRadians(270))
                                    //drive to basket
                                    .setTangent(Math.toRadians(-90))
                                    .splineTo(new Vector2d(-56,-56),Math.toRadians(225))
                                    .setTangent(Math.toRadians(90))
                                    .lineToYSplineHeading(-38,Math.toRadians(-90))
                                    .lineToYSplineHeading(-56,Math.toRadians(225))
                            .build());
                    meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                            .setDarkMode(true)
                            .setBackgroundAlpha(0.95f)
                            .addEntity(myBot)
                            .start();
                } else if(blahblah.equals("redauto3")){
                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12,-64,Math.toRadians(270)))
                            .setTangent(Math.toRadians(90))
                            .splineTo(new Vector2d(-6,-33),Math.toRadians(90))
                            .setTangent(Math.toRadians(-90))
                            .lineToY(-38)
                            .setTangent(Math.toRadians(Math.toRadians(0)))
                            .splineTo(new Vector2d(60,-60),Math.toRadians(270))
                            .build());
                    meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                            .setDarkMode(true)
                            .setBackgroundAlpha(0.95f)
                            .addEntity(myBot)
                            .start();
                } else if(blahblah.equals("test")){
                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12,64, Math.toRadians(270)))
                                    .strafeTo(new Vector2d(-6,33))
                                    .setTangent(Math.toRadians(135))
                                    .splineToConstantHeading(new Vector2d(-35,30),Math.toRadians(270))
                                    .setTangent(Math.toRadians(180))
                                    .lineToX(-52)
                                    .setTangent(0)
                                    .splineToSplineHeading(new Pose2d(-46,22,Math.toRadians(180)),Math.toRadians(270))
                                    .setTangent(Math.toRadians(270))
                                    .splineToConstantHeading(new Vector2d(-52,10),Math.toRadians(180))
                                    .lineToX(-60)
                                    .setTangent(Math.toRadians(90))
                                    .lineToY(56)
                                    .build());
                    meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                            .setDarkMode(true)
                            .setBackgroundAlpha(0.95f)
                            .addEntity(myBot)
                            .start();

                }
    }
}