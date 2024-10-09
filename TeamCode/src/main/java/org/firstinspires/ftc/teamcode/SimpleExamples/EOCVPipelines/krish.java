package org.firstinspires.ftc.teamcode.SimpleExamples.EOCVPipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class krish extends OpenCvPipeline
{

    Telemetry telemetry;
    public krish (Telemetry telemetry){this.telemetry = telemetry;}

    public Scalar lower = new Scalar(0, 0, 0);
    public Scalar upper = new Scalar(255, 255, 255);

    //variables
    Mat HSV = new Mat();
    Mat HSVInvert = new Mat();
    Mat GRAY = new Mat();
    Mat R = new Mat();
    Mat G = new Mat();
    Mat B = new Mat();
    Mat YCrCb = new Mat();
    Mat YCrCbInvert = new Mat();
    Mat Y = new Mat();
    Mat Cr = new Mat();
    Mat Cb = new Mat();
    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input,GRAY,Imgproc.COLOR_RGB2GRAY);
        Imgproc.cvtColor(input,HSV,Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(input,YCrCb,Imgproc.COLOR_RGB2GRAY);
        //Core.invert(HSV,HSVInvert);
        //Core.invert(YCrCb,YCrCbInvert);
        Core.extractChannel(YCrCb,Y,0);
        //Core.extractChannel(YCrCb,Cr,1);
        //Core.extractChannel(YCrCb,Cb,2);
        Core.extractChannel(input,R,0);
        Core.extractChannel(input,G,1);
        Core.extractChannel(input,B,2);


        /*telemetry.addData("Mean R Value", Core.mean(R));
        telemetry.addData("Mean G Value", Core.mean(G));
        telemetry.addData("Mean B Value", Core.mean(B));
        telemetry.addData("Mean Y Value:", Core.mean(Y));
        telemetry.addData("Mean Cr Value", Core.mean(Cr));
        telemetry.addData("Mean Cb Value", Core.mean(Cb));
        telemetry.update();*/



        return YCrCb;
    }
}
