package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.SimpleExamples.EOCVPipelines.ColorConvertPipeline;
import org.firstinspires.ftc.teamcode.SimpleExamples.EOCVPipelines.krish;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

public class KrishWebcamOpMode extends OpMode {

    OpenCvWebcam KrishCam;
    krish krishpipeline;
    @Override
    public void init() {

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        KrishCam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "KRISHCAM"), cameraMonitorViewId);
        FtcDashboard.getInstance().startCameraStream(KrishCam, 0);

        krishpipeline = new krish(telemetry);
        KrishCam.setPipeline(krishpipeline);
        KrishCam.setMillisecondsPermissionTimeout(5000);
        KrishCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                KrishCam.startStreaming(640,480, OpenCvCameraRotation.SIDEWAYS_LEFT);

            }

            @Override
            public void onError(int errorCode) {

            }
        });

    }

    @Override
    public void loop() {

    }
}
