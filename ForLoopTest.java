package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import java.util.Locale;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import android.graphics.Color;
import android.view.View;

@Autonomous

public class ForLoopTest extends LinearOpMode {
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;
    
    BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;
    
    NormalizedColorSensor colorSensor;
    View relativeLayout;
    
    boolean x;
    double y;
    int z;

    @Override

    public void runOpMode() {
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        
        waitForStart();
        
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        
        while (opModeIsActive()) {
            
            testForColor();
            
            /*angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double heading = Double.parseDouble(formatAngle(angles.angleUnit, angles.firstAngle));
            double speed = 0.35; 
            double proportionalTerm = 0.025;
            
            if (heading < -0.1  && heading > -90){
                leftFront.setPower(speed - (proportionalTerm * heading));
                leftBack.setPower(speed - (proportionalTerm * heading));
                rightFront.setPower(speed + (proportionalTerm * heading));
                rightBack.setPower(speed + (proportionalTerm * heading));       
            }else if (heading > 0.1 && heading < 90){
                leftFront.setPower(speed + (proportionalTerm * heading));
                leftBack.setPower(speed + (proportionalTerm * heading));
                rightFront.setPower(speed - (proportionalTerm * heading));
                rightBack.setPower(speed - (proportionalTerm * heading));    
            } else {
                leftFront.setPower(speed);
                leftBack.setPower(speed);
                rightFront.setPower(speed);
                rightBack.setPower(speed);
            } */
            
            /* y = Math.random()*10;
            z = (int) y;
            
            if (z > 8) {
                x = true;   
            } else {
                x = false;   
            }
            
            sleep(300);
            
            for (int i=0; i<100; i++) {
                
                if (x) {
                    rightFront.setPower(0.25);
                    leftFront.setPower(0.25);
                    rightBack.setPower(0.25);
                    leftBack.setPower(0.25);
                    
                    telemetry.addData("Moving", x);
                    telemetry.update();
                } else if (!x) {
                    rightFront.setPower(0);
                    leftFront.setPower(0);
                    rightBack.setPower(0);
                    leftBack.setPower(0);
                    
                    telemetry.addData("Not Moving", x);
                    telemetry.update();
                }
            }
            telemetry.addData("value", x);
            telemetry.update();*/
        } 
    }
        
    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
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
  //for(int i = 0; i < 2; i++) {
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
        sensedRed = true;
        sleep(1000);
        sensedRed = true;
        }
  }
  //sensedRed = false;
  //}
    }
}
