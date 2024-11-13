package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

@Config
public class DriveConstants {
    //claw
    public static double CLAW_CLOSED_POSITION = 0.1;
    public static double CLAW_OPEN_POSITION = 0.2;

    //flip intake
    public static double TRANSFER_POSITION = 0;
    public static double INTAKE_POSITION = 0.62;

    //intake
    public static double INTAKE_SPEED = 1;

    //extension
    public static int BASE_POSITION = 0;
    public static int HALF_POSITION = 100;
    public static int FULL_POSITION = 200;

    public static double EXTENSION_SPEED = 1;

    //arm
    public static double DOCK_POSITION = 0.31; // grab
    public static double VERTICAL_POSITION = 0.65; // dunking
    public static double ALIGN_POSITION = .72; // delete
    public static double DUNK_POSITION = 0; // start position

    public static double ARM_SPEED = 0.1;



}

