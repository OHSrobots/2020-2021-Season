package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * This is an example LinearOpMode that shows how to use a color sensor in a generic
 * way, regardless of which particular make or model of color sensor is used. The Op Mode
 * assumes that the color sensor is configured with a name of "sensor_color".
 *
 * There will be some variation in the values measured depending on the specific sensor you are using.
 *
 * You can increase the gain (a multiplier to make the sensor report higher values) by holding down
 * the A button on the gamepad, and decrease the gain by holding down the B button on the gamepad.
 *
 * If the color sensor has a light which is controllable from software, you can use the X button on
 * the gamepad to toggle the light on and off. The REV sensors don't support this, but instead have
 * a physical switch on them to turn the light on and off, beginning with REV Color Sensor V2.
 *
 * If the color sensor also supports short-range distance measurements (usually via an infrared
 * proximity sensor), the reported distance will be written to telemetry. As of September 2020,
 * the only color sensors that support this are the ones from REV Robotics. These infrared proximity
 * sensor measurements are only useful at very small distances, and are sensitive to ambient light
 * and surface reflectivity. You should use a different sensor if you need precise distance measurements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this Op Mode to the Driver Station OpMode list
 */
@Autonomous

public class FindTheLine extends LinearOpMode {
  
  private DcMotor leftFront;
  private DcMotor rightFront;
  private DcMotor leftBack;
  private DcMotor rightBack;
  
  /** The colorSensor field will contain a reference to our color sensor hardware object */
  NormalizedColorSensor colorSensor;

  /** The relativeLayout field is used to aid in providing interesting visual feedback
   * in this sample application; you probably *don't* need this when you use a color sensor on your
   * robot. Note that you won't see anything change on the Driver Station, only on the Robot Controller. */
  View relativeLayout;

  /**
   * The runOpMode() method is the root of this Op Mode, as it is in all LinearOpModes.
   * Our implementation here, though is a bit unusual: we've decided to put all the actual work
   * in the runSample() method rather than directly in runOpMode() itself. The reason we do that is
   * that in this sample we're changing the background color of the robot controller screen as the
   * Op Mode runs, and we want to be able to *guarantee* that we restore it to something reasonable
   * and palatable when the Op Mode ends. The simplest way to do that is to use a try...finally
   * block around the main, core logic, and an easy way to make that all clear was to separate
   * the former from the latter in separate methods.
   */
  @Override public void runOpMode() {
    
    leftFront = hardwareMap.dcMotor.get("leftFront");
    rightFront = hardwareMap.dcMotor.get("rightFront");
    leftBack = hardwareMap.dcMotor.get("leftBack");
    rightBack = hardwareMap.dcMotor.get("rightBack");
    
    leftFront.setDirection(DcMotorSimple.Direction.REVERSE);   //Reverse
    leftBack.setDirection(DcMotorSimple.Direction.REVERSE);   //Reverse
    
    testForColor(); // actually execute the sample

  }


  protected void testForColor() {

  final float[] hsvValues = new float[3];
  colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensorColor");
    if (colorSensor instanceof SwitchableLight) {
      ((SwitchableLight)colorSensor).enableLight(true);
    }
    
    waitForStart();
    boolean sensedWhite = false;
    boolean sensedRed = false;
  while(!sensedWhite && opModeIsActive()) {
      NormalizedRGBA colors = colorSensor.getNormalizedColors();

      Color.colorToHSV(colors.toColor(), hsvValues);
      
      
      telemetry.addLine()
              .addData("Red", "%.3f", colors.red * 100)
              .addData("Green", "%.3f", colors.green * 100)
              .addData("Blue", "%.3f", colors.blue * 100);
      telemetry.addLine();
      telemetry.addData("Alpha", "%.3f", colors.alpha);

      
      
      telemetry.update();
      
        
        leftFront.setPower(0.25);
        rightFront.setPower(0.25);
        leftBack.setPower(0.25);
        rightBack.setPower(0.25);
        
        if (colors.alpha > 0.3){
        sensedWhite = true;
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        sleep(1000);
        }
        
  }
  for(int i = 0; i < 2; i++) {
  while(!sensedRed && opModeIsActive()) {
      NormalizedRGBA colors = colorSensor.getNormalizedColors();

      Color.colorToHSV(colors.toColor(), hsvValues);
      
      
      telemetry.addLine()
              .addData("Red", "%.3f", colors.red * 100)
              .addData("Green", "%.3f", colors.green * 100)
              .addData("Blue", "%.3f", colors.blue * 100);
      telemetry.addLine();
      telemetry.addData("Alpha", "%.3f", colors.alpha);

      
      
      telemetry.update();
      
        
        leftFront.setPower(0.25);
        rightFront.setPower(0.25);
        leftBack.setPower(0.25);
        rightBack.setPower(0.25);
        
        if (colors.alpha < 0.1){
        sensedRed = true;
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        sleep(1000);
        }
  }
  sensedRed = false;
  }
}
}
