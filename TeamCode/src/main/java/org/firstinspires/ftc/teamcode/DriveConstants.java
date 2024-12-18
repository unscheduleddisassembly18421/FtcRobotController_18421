package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

@Config
public class DriveConstants {
    //arm claw
    public static double CLAW_CLOSED_POSITION = 0.08;
    public static double CLAW_OPEN_POSITION = 0.3;

    //flip intake
    public static double TRANSFER_POSITION = 0;
    public static double INTAKE_POSITION = 0.55;

    //intake claw
    public static double INTAKE_CLAW_OPEN = 0.3;
    public static double INTAKE_CLAW_CLOSE = 0.3;
    //intake
    public static double INTAKE_SPEED = 1;
    public static double INTAKE_TEST_FLIP = 0.6;
    public static double INTAKE_START = 0;

    //intake turn
    public static double NORMAL_POSITION_RIGHT = 0;
    public static double NORMAL_POSITION_LEFT = -0;
    public static double TURN_POSITION_RIGHT = 0.25;
    public static double TURN_POSITION_LEFT = 0.25;

    //extension
    public static double HORIZONTAL_EXTENSION_SPEED = 1;
    public static double HORIZONTAL_EXTENSION_SPEED_MULTIPLIER = 2;
    public static int EXTENSION_RECTRACT_POSITION = 0;
    public static double VERTICAL_EXTENSION_SPEED = 1;
    public static double VERTICAL_EXTENSION_SPEED_MULTIPLIER = 2;

    //arm
    public static double HORIZONTAL_POSITION = 0.07; // grab
    public static double VERTICAL_POSITION = 0.4;
    public static double ARM_TRANSFER_POSITION = 0.04;

    //transfer (vertical?)TODO IS THIS RIGHT?
    public static int LOW_POSITION = 0;
    public static int HIGH_POSITION = 600;

    public static int VERTICAL_WIGGLE_ROOM = 10;
    public static int HORIZONTAL_WIGGLE_ROOM = 10;

    public static double ARM_MOVE_DELAY = 500;
    public static double ARM_CLOSE_DELAY = 500;
    public static double INTAKE_OPEN_DELAY = 500;
    public static double ARM_MOVE_DELAY2 = 500;
    public static double INTAKE_MOVE_DELAY = 500;

    //stuff i dont know
    public static double START_POSITION = 0;
    public static double GRAB_AND_SCORE_POSITION = 1;




}

