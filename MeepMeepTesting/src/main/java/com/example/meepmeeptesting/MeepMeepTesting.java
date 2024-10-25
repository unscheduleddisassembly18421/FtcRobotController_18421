package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
                String blahblah = "blueauto3";
                if(blahblah.equals("cool")) {

                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 0, 0))
                                    .setTangent(Math.toRadians(45))
                                    //.splineToSplineHeading(new Pose2d(48,48,Math.PI / 2),Math.toRadians(90))
                                    .lineToYSplineHeading(48,Math.toRadians(90))
                            .build());
                    meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                            .setDarkMode(true)
                            .setBackgroundAlpha(0.95f)
                            .addEntity(myBot)
                            .start();
                } else if(blahblah.equals("blueauto1")) {
                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(36, 65.25, Math.toRadians(270)))
                            .splineTo(new Vector2d(6, 38), Math.toRadians(270))
                            .setTangent(0)
                            .lineToXSplineHeading(48, Math.toRadians(90))
                            .setTangent(Math.toRadians(90))
                            .splineTo(new Vector2d(56, 56), Math.toRadians(45))
                            .setTangent(Math.toRadians(-90))
                            .lineToYSplineHeading(38, Math.toRadians(90))
                            .setTangent(Math.toRadians(90))
                            .lineToYSplineHeading(56, Math.toRadians(45))
                            .build());

                    meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                            .setDarkMode(true)
                            .setBackgroundAlpha(0.95f)
                            .addEntity(myBot)
                            .start();
                } else if(blahblah.equals("blueauto3")) {
                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12,65.25, Math.toRadians(270)))
                                    .splineTo(new Vector2d(-6,38),Math.toRadians(270))
                                    .setTangent(0)
                                    .lineToXSplineHeading(-48,Math.toRadians(90))
                                    .setTangent(Math.toRadians(90))
                                    .lineToY(56)
                                    .setTangent(Math.toRadians(245))
                                    .lineToY(38)
                                    .setTangent(Math.toRadians(90))
                                    .lineToY(56)
                            .build());
                            meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                                    .setDarkMode(true)
                                    .setBackgroundAlpha(0.95f)
                                    .addEntity(myBot)
                                    .start();
                } else if(blahblah.equals("redauto1")){
                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-36,-65.25,Math.toRadians(90)))
                                    .splineTo(new Vector2d(-6,-38),Math.toRadians(90))
                                    .setTangent(Math.toRadians(Math.toRadians(0)))
                                    .lineToXSplineHeading(-48,Math.toRadians(270))
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
                } else if(blahblah.equals("redauto2")){
                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12,-65.25,Math.toRadians(90)))
                                    //drive to submer
                                    .splineTo(new Vector2d(-6,-38),Math.toRadians(90))
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
                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(12,-65.25,Math.toRadians(90)))
                                    .splineTo(new Vector2d(6,-38),Math.toRadians(90))
                                    .setTangent(0)
                                    .lineToXSplineHeading(48,Math.toRadians(270))
                                    .setTangent(Math.toRadians(-90))
                                    .lineToY(-56)
                                    .setTangent(Math.toRadians(65))
                                    .lineToY(-38)
                                    .setTangent(Math.toRadians(90))
                                    .lineToY(-56)
                            .build());
                    meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                            .setDarkMode(true)
                            .setBackgroundAlpha(0.95f)
                            .addEntity(myBot)
                            .start();
                } else if(blahblah.equals("test")){
                    myBot.runAction(
                            new SequentialAction(
                                    myBot.getDrive().actionBuilder(new Pose2d(12,-65.25,Math.toRadians(90)))

                                            .build()
                            ));


//                            myBot.getDrive().actionBuilder(new Pose2d(12,-65.25,Math.toRadians(90)))
//                            .splineTo(new Vector2d(6,-38),Math.toRadians(90))
//                            .setTangent(0)
//                            .lineToXSplineHeading(48,Math.toRadians(270))
//                            .setTangent(Math.toRadians(-90))
//                            .lineToY(-56)
//                            .setTangent(Math.toRadians(65))
//                            .lineToY(-38)
//                            .setTangent(Math.toRadians(90))
//                            .lineToY(-56)
//                            .build());


                    meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                            .setDarkMode(true)
                            .setBackgroundAlpha(0.95f)
                            .addEntity(myBot)
                            .start();

                }
    }
}