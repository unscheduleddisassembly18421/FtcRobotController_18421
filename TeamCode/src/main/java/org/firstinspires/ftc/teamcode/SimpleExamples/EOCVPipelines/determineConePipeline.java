package org.firstinspires.ftc.teamcode.SimpleExamples.EOCVPipelines;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.imgproc.Imgproc;


public class determineConePipeline extends OpenCvPipeline {


    final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(100,240);
    final int REGION_WIDTH = 50;
    final int REGION_HEIGHT = 50;

    Point region1_pointA = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x,
            REGION1_TOPLEFT_ANCHOR_POINT.y);
    Point region1_pointB = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

    Mat region1;
    Mat HSV = new Mat();
    Mat Cb = new Mat();
    int avg1;
    int avg2;
    public Scalar rectColor = new Scalar(255, 0, 0);


    Telemetry telemetry;
    public determineConePipeline(Telemetry telemetry){this.telemetry = telemetry;}

    void inputToCb(Mat input) {
        Imgproc.cvtColor(input, HSV, Imgproc.COLOR_RGB2HSV);
        Core.extractChannel(HSV, Cb, 2);
    }
    @Override
    public void init(Mat firstFrame) {
        inputToCb(firstFrame);
        region1 = HSV.submat(new Rect(region1_pointA, region1_pointB));
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, HSV, Imgproc.COLOR_RGB2HSV);
        Imgproc.rectangle(input, region1_pointA, region1_pointB, rectColor, 1);

        avg1 = (int) Core.mean(region1).val[0];
        telemetry.addData("average hue", avg1);
        avg2 = (int) Core.mean(region1).val[1];
        telemetry.addData("average saturation", avg2);

        if ((avg1 >= 110 && avg1 <= 120 ) && (avg2 >= 160)) {
            telemetry.addLine("color: blue");
        } else if (((avg1 >= 1 && avg1 <= 10) || (avg1 >= 170)) && (avg2 >= 160)) {
            telemetry.addLine("color: red");
        }
          else {
            telemetry.addLine("i dunno :(");

          }
        telemetry.update();
        return input;
    }

}
