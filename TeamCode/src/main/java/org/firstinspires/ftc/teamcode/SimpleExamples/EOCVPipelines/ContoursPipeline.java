package org.firstinspires.ftc.teamcode.SimpleExamples.EOCVPipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

/***
 * This example shows how to use an OpenCvPipeline to process an image to draw a contour around an object.  It works by doing the following steps.
 * 0. Establish variables to hold onto a series of images (Mats) at different stages of analysis.
 * 1. Establish Scalar variables to establish upper and lower values for a range mask
 * 2. Make erode and dilate elements for the erosion and dilation of an image (to blur it to remove artifacts).
 *
 * PROCESSFRAME
 * 3. Make an ArrayList to hold onto all contours
 * 4. Take input image and convert it into a different color space.  HSV color space is often easier for some colors.
 * 5. Make an image that is a mask of the converted image.  Each pixel is either 255 or 0 depending on if it is in range, isolating important parts from background.
 * 6. Erodes an image thereby reducing noise, but shrinks any edges down
 * 7. Dilates an image which blows the edges back up to the previous size.
 * 8. Finds contours around the mask.
 * 9. Copies the original input image to a new place to later draw on top of it for viewing.
 * 10. Calculates the "moments" of the contours and uses them to find the center of the object.
 * 11. Calculates a bounding box around the contours.
 * 12. Draws the bounding box and outputs data about where the center is.
 */

public class ContoursPipeline extends OpenCvPipeline {
    Telemetry telemetry;
    public ContoursPipeline(Telemetry telemetry){this.telemetry = telemetry;}

    //imgs
    Mat clrConvertedMat = new Mat(); //mat after color conversion
    Mat thresholdMat = new Mat(); //mask -- y pixels are part of thing or n they are not
    Mat morphedThreshold = new Mat();  //denoise
    Mat contoursOnPlainImageMat = new Mat(); //copy of original image to vandalize with contours

    //ArrayList to hold the contours
    //ArrayList<MatOfPoint> contoursList = new ArrayList<>();

    //upper and lower Scalar values for changing the bounding range for the mask
    //Only values in color space between these values will show up on the mask.
    //For now we will keep them wide to test in EOCV Sim.  We can then update these values to make them permanent.
    public Scalar lower = new Scalar(0,0,0);
    public Scalar upper = new Scalar(255,255,255);

    //sizes to adjust for the erosion and dilation of the mask
    //Erosion shrinks an image, and dilation expands an image.  Useful for reducing noise (and detail).
    public int size = 3;
    public int mult = 2;

    Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(size, size));
    Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(mult*size, mult*size));

    Point center = new Point(0,0); //variable to hold the center of object (for later)
    @Override
    public Mat processFrame(Mat input) {
        // Executed every time a new frame is dispatched
        ArrayList<MatOfPoint> contoursList = new ArrayList<>();
        //convert color space to change how the data is displayed.
        //Imgproc.cvtColor(input, clrConvertedMat, Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(input, clrConvertedMat, Imgproc.COLOR_RGB2YCrCb);
        //Make a mask using a range.  Includes only the values within that range
        Core.inRange(clrConvertedMat,lower,upper,thresholdMat);

        //erode the image - reducing the noisy pixels from the mask
        //by taking the lowest value in a 3x3 box (erodeElement)
        //and replacing that 3x3 pixel block with 1 pixel.
        Imgproc.erode(thresholdMat, morphedThreshold, erodeElement);
        Imgproc.erode(morphedThreshold, morphedThreshold, erodeElement);    //Performed twice to reduce noise further.  Now image is 1/27th the size.

        //expands the edges of the mask out again (needed because erosion reduces both noise and signal
        //and this boosts the signal of the edges)
        Imgproc.dilate(morphedThreshold, morphedThreshold, dilateElement);
        Imgproc.dilate(morphedThreshold, morphedThreshold, dilateElement);

        //use function to find contours of each object and put them into contoursList.
        //find contours on the mask of all of the edges of the same value that make a closed shape.
        //These contours are points indicated on an image of their own, stored in contoursList.
        //Each object that is detected is stored as a separate object in contoursList.
        //We want to reduce this down to 1 object ideally so we can make a simple decision.

        //                  Mask Image        ListToStore  Optional   Mode                  Method
        //Mode and method are standard in example code.  Play around with other options
        Imgproc.findContours(morphedThreshold,contoursList, new Mat(),Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_NONE);

        //copy "input" image to draw contours on for display purposes.  This just copies input to that place.
        input.copyTo(contoursOnPlainImageMat);

        //draw contours using the contours List
        //                  Image to draw on        contours2draw           ?       Color           Thickness
        Imgproc.drawContours(contoursOnPlainImageMat,contoursList,-1,new Scalar(0,0,255),2);


        //ArrayList<Moments> moments = new ArrayList<>();
        //ArrayList<Point> points = new ArrayList<>();

        Moments moment = new Moments();
        center = new Point();

        //Sometimes images have no contours!  The list is empty, so if you try to grab an object from it
        //It yells at you.  This makes sure the list isn't empty before you access it!
        if (!contoursList.isEmpty()){
            //Now we know we have 1 object at least detected.  We should likely try to find the biggest contour or one that is at least above a certain size.
            //The example though will just grab the first one to demonstrate how to get info from a contour.

            //this grabs the first contour and finds the moments of it.
            moment = Imgproc.moments(contoursList.get(0));

            //This calculations the center of mass using m10/m00, m01/m00 and stores them
            //as a point in "center"
            //the (int) is to cast the double to an integer to cut off the decimals...they were annoying
            center = new Point((int)(moment.m10/moment.m00),(int)(moment.m01/moment.m00));

            //This draws a circle at that point on the image so we can see what the image tracks.
            Imgproc.circle(contoursOnPlainImageMat,center,3,new Scalar(0,0,255),3);

            //This calculates and draws a bounding rectangle.
            //Bounding rectangles are nice because they have an inherent width that can be measured
            Rect boundingRectangle = Imgproc.boundingRect(contoursList.get(0));

            //At this point, we could try to estimate the distance of the box by a ratio of apparent size to expected.

            //this draws a box around the image to show you where the computer things the object is based on "center"
            Imgproc.rectangle(contoursOnPlainImageMat,boundingRectangle.tl(),boundingRectangle.br(),new Scalar(255,0,0),2);
        }

        telemetry.addData("Number of objects found", contoursList.size());
        telemetry.addData("Center of first object", center.toString());
        telemetry.update();


        //this returns the image with the contours
        return contoursOnPlainImageMat; // Return the image that will be displayed in the viewport

        //uncomment these to see what they look like
        //return input;
        //return thresholdMat;
        //return morphedThreshold;

    }




    @Override
    public void onViewportTapped() {
        // Executed when the image display is clicked by the mouse or tapped
        // This method is executed from the UI thread, so be careful to not
        // perform any sort heavy processing here! Your app might hang otherwise
    }

}

