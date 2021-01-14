package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

@TeleOp(name = "Distance Motor", group = "sensors")

public class DistanceMotor extends LinearOpMode {
  private DistanceSensor distanceSensor;
  private DcMotor rightFront;

  @Override

  public void runOpMode() {
    double distance;
    
    distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");
    rightFront = hardwareMap.dcMotor.get("rightFront");
    
    waitForStart();
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        distance = distanceSensor.getDistance(DistanceUnit.INCH);
        
        if (distance >= 9) {
          rightFront.setPower(1);
        } else {
          rightFront.setPower(0);
        }
        telemetry.addData("Distance: ", distance);
        telemetry.update();
      }
    }
  
  }
}
