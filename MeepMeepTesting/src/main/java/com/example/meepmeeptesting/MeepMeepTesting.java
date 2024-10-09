package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
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
                String blahblah = "idunno";
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
                } else if(blahblah.equals("idunno")) {
                    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(36, 65.25, Math.toRadians(270)))
                            .lineToY(59.25)
                            .splineTo(new Vector2d(6, 38), Math.toRadians(270))
                                    .setTangent(0)
                                    .lineToXSplineHeading(48,Math.toRadians(90))
                                    .setTangent(Math.toRadians(90))
                                            .splineTo(new Vector2d(56,56),Math.toRadians(45))
                            .setTangent(Math.toRadians(-90))
                            .lineToYSplineHeading(38,Math.toRadians(90))
                            .setTangent(Math.toRadians(90))
                            .lineToYSplineHeading(56,Math.toRadians(45))
                              /*      .setTangent(Math.toRadians(0))
                            .splineTo(new Vector2d(56, 56), Math.toRadians(135))
                                    .setTangent(Math.toRadians(-90))
                            .lineToYSplineHeading(38,Math.toRadians(-90))
                                    .setTangent(Math.toRadians(90))
                                    .lineToYSplineHeading(56,Math.toRadians(45))
                            */
                            .build());

                    meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                            .setDarkMode(true)
                            .setBackgroundAlpha(0.95f)
                            .addEntity(myBot)
                            .start();
                }
    }
}