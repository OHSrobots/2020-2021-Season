package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

@Autonomous

public class DIstanceSens extends LinearOpMode {
  private DistanceSensor sensorRange;
  private DcMotor ringScoop;
  
  private DcMotor leftFront;
  private DcMotor rightFront;
  private DcMotor leftBack;
  private DcMotor rightBack;
  
  private double distanceReading;
  

  @Override

  public void runOpMode() {
  
    
    sensorRange = hardwareMap.get(DistanceSensor.class, "sensorRange");
    ringScoop = hardwareMap.dcMotor.get("ringScoop");
    
    leftFront = hardwareMap.dcMotor.get("leftFront");
    rightFront = hardwareMap.dcMotor.get("rightFront");
    leftBack = hardwareMap.dcMotor.get("leftBack");
    rightBack = hardwareMap.dcMotor.get("rightBack");
    
    leftFront.setDirection(DcMotorSimple.Direction.REVERSE);   //Reverse
    leftBack.setDirection(DcMotorSimple.Direction.REVERSE);   //Reverse
        
    waitForStart();
      while (opModeIsActive()) {
        /*if (sensorRange.getDistance(DistanceUnit.CM)>= 9) {
          ringScoop.setPower(1);
        } else {
          ringScoop.setPower(0);
        }
        telemetry.addData("Distance: ", sensorRange.getDistance(DistanceUnit.CM));
        telemetry.update(); */
        distanceReading = sensorRange.getDistance(DistanceUnit.CM);
        
        /*leftFront.setPower((distanceReading/2) - 0.25);
        rightFront.setPower(-(distanceReading/2) + 2.25);
        leftBack.setPower((distanceReading/2) - 0.25);
        rightBack.setPower(-(distanceReading/2) + 2.25);*/
        
        /*leftFront.setPower((distanceReading/2) - 2.25);
        rightFront.setPower(-(distanceReading/2) + 2.75);
        leftBack.setPower((distanceReading/2) - 2.25);
        rightBack.setPower(-(distanceReading/2) + 2.75); */
        
        leftFront.setPower((distanceReading/4) - 1.00);
        rightFront.setPower(-(distanceReading/4) + 1.50);
        leftBack.setPower((distanceReading/4) - 1.00);
        rightBack.setPower((-distanceReading/4) + 1.50); 
        
        
        
      }
  }
}
