package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ParallelProgramming1 (Blocks to Java)", group = "")
public class ParallelProgramming1 extends LinearOpMode {

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    // Put initialization blocks here.
    waitForStart();
    while (opModeIsActive()) {
      FirstTask();
      Second_Task();
      Third_Task();
      telemetry.update();
    }
  }

  /**
   * Describe this function...
   */
  private void FirstTask() {
    if (opModeIsActive()) {
      // Check a sensor and/or activate a motor or servo.
      // Do not loop or use sleep blocks as they would
      // prevent the other tasks from running.
      // Instead, check elapsed time, a sensor or a motor
      // busy state and return so the other tasks can run.
    }
  }

  /**
   * Describe this function...
   */
  private void Second_Task() {
    if (opModeIsActive()) {
      // Check a sensor and/or activate a motor or servo.
      // Do not loop or use sleep blocks as they would
      // prevent the other tasks from running.
      // Instead, check elapsed time, a sensor or a motor
      // busy state and return so the other tasks can run.
    }
  }

  /**
   * Describe this function...
   */
  private void Third_Task() {
    if (opModeIsActive()) {
      // Check a sensor and/or activate a motor or servo.
      // Do not loop or use sleep blocks as they would
      // prevent the other tasks from running.
      // Instead, check elapsed time, a sensor or a motor
      // busy state and return so the other tasks can run.
    }
  }
}
