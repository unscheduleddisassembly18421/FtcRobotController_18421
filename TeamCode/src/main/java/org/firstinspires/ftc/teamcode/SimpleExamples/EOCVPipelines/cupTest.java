package org.firstinspires.ftc.teamcode.SimpleExamples.EOCVPipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;


public class cupTest extends OpenCvPipeline {
    Telemetry telemetry;

    public cupTest(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public Scalar lower = new Scalar(0, 0, 0);
    public Scalar upper = new Scalar(255, 255, 255);
    public final Scalar BLUELOWER = new Scalar(110, 0, 0);
    public final Scalar REDLOWER = new Scalar(0, 0, 0);
   public final Scalar BLUEHIGHER = new Scalar(120, 255, 255);
    static final Scalar REDHIGHER = new Scalar(180, 255, 255);
    public Scalar rectColor = new Scalar(255,0,0);
    public Point rectp1 = new Point(100,50);
    public Point rectp2 = new Point(150,100);

    Mat HSV = new Mat();
    Mat REGION1 = new Mat();

    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(210,250);
    //static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(70,90);
    static final int REGION_WIDTH = 50;//240;
    static final int REGION_HEIGHT = 50;//340;

    Point region1_pointA = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x,
            REGION1_TOPLEFT_ANCHOR_POINT.y);
    Point region1_pointB = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
    public void init(Mat firstFrame){
        Imgproc.cvtColor(firstFrame,HSV,Imgproc.COLOR_RGB2HSV);
        REGION1=HSV.submat(new Rect(region1_pointA,region1_pointB));


    }

    public Mat processFrame(Mat input) {
        /*
        //TODO Figure out how to isolate red color with Hue Wrapping problem (160-180 is red and 0 to 20 is red)
        1.x Color Convert Image
        2.x Make Selected Area
        2.5 x Make a mask using the scalar values
        2.75 x Calculate the color in the area mean value
        3.x if color in selected area --> Output some telemetry
         */

        Imgproc.cvtColor(input, HSV, Imgproc.COLOR_RGB2HSV);
        Imgproc.rectangle(input, region1_pointA, region1_pointB, rectColor, 2);
        int avgHue;
        int avgValue;
        int avgSat;
        avgHue = (int) Core.mean(REGION1).val[0];
        avgSat = (int) Core.mean(REGION1).val[1];
        avgValue = (int) Core.mean(REGION1).val[2];

        telemetry.addData("Mean", avgHue);
        telemetry.addData("Saturation",avgSat);
        telemetry.addData("Value", avgValue);
        if (avgHue >= BLUELOWER.val[0] && avgHue <= BLUEHIGHER.val[0] && avgSat >=160) {
           telemetry.addData("Color","Blue");
        } else if(((avgHue >= 1) && (avgHue <= 10) && avgSat >=160) || ((avgHue >= 170) && (avgHue <= 180)) && avgSat >=160) {
           telemetry.addLine("color is red");
        }else {
            telemetry.addLine("unknown, not smart enough");
        }
        telemetry.update();
        return input;
        //(:
    }






}

