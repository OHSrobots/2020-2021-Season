package org.firstinspires.ftc.teamcode.sensors;

import java.util.List;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TfodSkyStone;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@TeleOp(name = "Webcam Sensor", group = "sensors")

public class JavaWebcam extends LinearOpMode {

  private VuforiaSkyStone vuforiaSkyStone;
  private TfodSkyStone tfodSkyStone;
  private DcMotor rightFront;
  Recognition recognition;
  long time = System.currentTimeMillis();
  long _time = time + 3000;

  @Override
  
  public void runOpMode() {

    List<Recognition> recognitions;
    double index;

    vuforiaSkyStone = new VuforiaSkyStone();
    tfodSkyStone = new TfodSkyStone();
    rightFront = hardwareMap.dcMotor.get("rightFront");

    vuforiaSkyStone.initialize(
      "",
      hardwareMap.get(WebcamName.class, "Webcam"),
      "",
      true,
      false,
      VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES,
      0, 
      0,
      0,
      0,
      0,
      0,
      true
    );
    
    tfodSkyStone.initialize(vuforiaSkyStone, 0.6F, true, true);
    rightFront.setPower(0);
    
    tfodSkyStone.activate();
    telemetry.addData("Squishey", "Initialized - Press Play");
    telemetry.update();
    
    waitForStart();
      
    while (opModeIsActive()) {
        
      recognitions = tfodSkyStone.getRecognitions();
      
    while (System.currentTimeMillis() < _time) {
      rightFront.setPower(1);
    }
        
      if (recognitions.size() == 0) {
        telemetry.addData("TFOD", "No items detected.");
      } else {
        index = 0;
        
        for (Recognition recognition : recognitions) {
          telemetry.addData("label " + index, recognition.getLabel());
          telemetry.addData("Left, Top " + index, recognition.getLeft() + ", " + recognition.getTop());
          telemetry.addData("Right, Bottom " + index, recognition.getRight() + ", " + recognition.getBottom());
          
          index = index + 1;
        }
      }
        
      telemetry.update();
    }
    
  tfodSkyStone.deactivate();

  vuforiaSkyStone.close();
  tfodSkyStone.close();
  }
}
