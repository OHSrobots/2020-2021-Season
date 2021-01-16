package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import java.util.Locale;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import android.graphics.Color;

@Autonomous

public class NewColor extends LinearOpMode {
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;
    private DcMotor ringLaunch2;
    
    NormalizedColorSensor colorSensor;
    boolean foundRed = false;
    boolean foundWhite = false;
    int count = 0;
    boolean countBool = false;
    
    BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;

    @Override

    public void runOpMode() {
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        ringLaunch2 = hardwareMap.dcMotor.get("ringLaunch2");
        
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensorColor");
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }
        
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        
        waitForStart();
        
        while (opModeIsActive()) {
            imu.initialize(parameters);
            goToWhite();
            imu.initialize(parameters);
            sleep(100);
            goToRed();
            foundRed = false;
            moveRobot(0.5, 0.5, 0.5, 0.5, 75);
            stopRobot();
            imu.initialize(parameters);
            sleep(100);
            goToRed();
            stopRobot();
        }
    }
    
    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    } 
    
    public void stopRobot() {
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
    }
    
    public void moveRobot(double rf, double lf, double rb, double lb, long dur) {
        rightFront.setPower(rf);
        leftFront.setPower(lf);
        rightBack.setPower(lb);
        leftBack.setPower(lb);
        sleep(dur);
    }
    
    public void goToWhite() {
        final float[] hsvValues = new float[3];
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        
        while (!foundWhite && opModeIsActive()) {
            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            Color.colorToHSV(colors.toColor(), hsvValues);
            double heading = Double.parseDouble(formatAngle(angles.angleUnit, angles.firstAngle));
            
            if (heading < -0.1  && heading > -90){
                leftFront.setPower(0.35 - (0.025 * heading));
                leftBack.setPower(0325 - (0.025 * heading));
                rightFront.setPower(0.35 + (0.025 * heading));
                rightBack.setPower(0.35 + (0.025 * heading));       
            }else if (heading > 0.1 && heading < 90){
                leftFront.setPower(0.35 + (0.025 * heading));
                leftBack.setPower(0.35 + (0.025 * heading));
                rightFront.setPower(0.35 - (0.025 * heading));
                rightBack.setPower(0.35 - (0.025 * heading));    
            } else {
                leftFront.setPower(0.35);
                leftBack.setPower(0.35);
                rightFront.setPower(0.35);
                rightBack.setPower(0.35);
            }
            
            if (colors.alpha > 0.5) {
                stopRobot();
                foundWhite = true;
            }
        }
    }
    
    public void goToRed() {
        final float[] hsvValues = new float[3];
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        
        while (!foundRed && opModeIsActive()) {
            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            Color.colorToHSV(colors.toColor(), hsvValues);
            double heading = Double.parseDouble(formatAngle(angles.angleUnit, angles.firstAngle));
            
            if (heading < -0.1  && heading > -90){
                leftFront.setPower(0.25 - (0.025 * heading));
                leftBack.setPower(0.25 - (0.025 * heading));
                rightFront.setPower(0.25 + (0.025 * heading));
                rightBack.setPower(0.25 + (0.025 * heading));       
            }else if (heading > 0.1 && heading < 90){
                leftFront.setPower(0.25 + (0.025 * heading));
                leftBack.setPower(0.25 + (0.025 * heading));
                rightFront.setPower(0.25 - (0.025 * heading));
                rightBack.setPower(0.25 - (0.025 * heading));    
            } else {
                leftFront.setPower(0.25);
                leftBack.setPower(0.25);
                rightFront.setPower(0.25);
                rightBack.setPower(0.25);
            }
            
            if (colors.alpha < 0.2) {
                stopRobot();
                foundRed = true;
            }
        }
    }
}
