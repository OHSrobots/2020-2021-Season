package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;

  @Autonomous

  public class Encoders extends LinearOpMode {

    private DcMotorEx leftFront;
    private DcMotorEx rightFront;
    private DcMotorEx leftBack;
    private DcMotorEx rightBack;

    @Override
    public void runOpMode() {
      

      rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
      leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
      rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
      leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
  
      leftFront.setDirection(DcMotorSimple.Direction.REVERSE);    //Reverse
      leftBack.setDirection(DcMotorSimple.Direction.REVERSE);     //Reverse
      
      leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
      waitForStart();
      double length = 36;
      double calcPosition = length * (100* 280/(16.9646003294*4 *8.8 * 1.0555555556));
      int setPosition = (int) Math.round(calcPosition);
      
      leftFront.setTargetPosition(setPosition);
      rightFront.setTargetPosition(-setPosition);
      leftBack.setTargetPosition(-setPosition);
      rightBack.setTargetPosition(setPosition);
      
      leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      
      leftFront.setVelocity(500);
      rightFront.setVelocity(500);
      leftBack.setVelocity(500);
      rightBack.setVelocity(500);
      
      while (opModeIsActive()) {
        telemetry.addData("position", leftFront.getCurrentPosition());
        telemetry.addData("is at target", !leftFront.isBusy());
        telemetry.update();
      }
      
      leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    } 
}
