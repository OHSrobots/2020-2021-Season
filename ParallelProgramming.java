package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/* @TeleOp(name = "ParallelProgramming (Blocks to Java)", group = "")
public class ParallelProgramming extends LinearOpMode {

  DcMotor rightFront;
  DcMotor leftFront;
  DcMotor rightBack;
  DcMotor leftBack;

  
  @Override
  public void runOpMode() {
    
    rightFront  =   hardwareMap.dcMotor.get("rightFront");
    leftFront  =   hardwareMap.dcMotor.get("leftFront");
    rightBack  =   hardwareMap.dcMotor.get("rightBack");
    leftBack  =   hardwareMap.dcMotor.get("leftBack");
    
    waitForStart();
    
    while (opModeIsActive()) {
      try {
        Task taskRunner = new Task();
      taskRunner.start();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
    class Task extends Thread {
      
      public void run(String[] args) throws Exception {
        int x = 0;
        while (x<10) {
            Telemetry.addData("Number: ", x);
            Telemetry.update();
            x++;
        }
      }
    } */
