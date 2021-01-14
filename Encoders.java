package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;

@Autonomous

public class Encoders extends LinearOpMode {

  private DcMotor ringLaunch1;
  private DcMotor ringLaunch2;
  
    @Override
    
    public void runOpMode() {
      

      ringLaunch1 = hardwareMap.dcMotor.get("ringLaunch1");
      ringLaunch2 = hardwareMap.dcMotor.get("ringLaunch2");
      

      ringLaunch2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      telemetry.addData("Status: ", ringLaunch2.getCurrentPosition());

            waitForStart();

      int newTarget = 300;//ringLaunch2.getTargetPosition();
            

      ringLaunch2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

      ringLaunch2.setTargetPosition(300);
      ringLaunch2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      ringLaunch2.setPower(0.1);


      
      while (ringLaunch2.isBusy()) {
        telemetry.addData("Status: ", ringLaunch2.getCurrentPosition());
        
        telemetry.update();
      }
            while (opModeIsActive()) {
        telemetry.addData("direction",ringLaunch2.getDirection());
        telemetry.addData("position",ringLaunch2.getCurrentPosition());
        telemetry.addData("is at target",!ringLaunch2.isBusy());

      ringLaunch2.setPower(0);
      
      
    }
}}
