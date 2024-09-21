package org.firstinspires.ftc.teamcode.SimpleExamples.EOCVPipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Scalar;

public class coolThing extends OpenCvPipeline {



    final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(100,240);
    final int REGION_WIDTH = 50;
    final int REGION_HEIGHT = 50;

    Point region1_pointA = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x,
            REGION1_TOPLEFT_ANCHOR_POINT.y);
    Point region1_pointB = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

    public Scalar lower = new Scalar(0, 0, 0);
    public Scalar upper = new Scalar(255, 255, 255);

    private Mat ycrcbMat       = new Mat();
    private Mat binaryMat      = new Mat();
    private Mat maskedInputMat = new Mat();

    Telemetry telemetry;
    public coolThing(Telemetry telemetry){this.telemetry = telemetry;}
    Mat coolGray = new Mat();
    Mat blahBlah = new Mat();
    Mat R = new Mat();
    Mat G = new Mat();
    Mat B = new Mat();
    int avgTest;
    Mat region1 = new Mat();
    Mat YCrCb;
    Mat Cb;

    void inputToCb(Mat input) {
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(YCrCb, Cb, 2);
    }
    @Override
    public void init(Mat firstFrame) {
        inputToCb(firstFrame);
        region1 = YCrCb.submat(new Rect(region1_pointA, region1_pointB));
    }

    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, ycrcbMat, Imgproc.COLOR_RGB2YCrCb);
        Core.inRange(ycrcbMat, lower, upper, binaryMat);
        maskedInputMat.release();
        Core.bitwise_and(input, input, maskedInputMat, binaryMat);

        Imgproc.cvtColor(input,coolGray,Imgproc.COLOR_RGB2GRAY);
        Imgproc.cvtColor(input,blahBlah,Imgproc.COLOR_RGB2HLS);

        Core.extractChannel(input,R,0);
        Core.extractChannel(input,G,1);
        Core.extractChannel(input,B,2);

        telemetry.addData("Mean R Value", Core.mean(R));
        telemetry.addData("Mean G Value", Core.mean(G));
        telemetry.addData("Mean B Value", Core.mean(B));
        telemetry.update();

        avgTest = (int) Core.mean(input).val[0];


        return null;
    }
    @Override
    public void onViewportTapped() {

    }

}

