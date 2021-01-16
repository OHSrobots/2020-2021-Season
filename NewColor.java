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
//Declaring Motors
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor leftBack;
    private DcMotor ringLaunch2;
    
//Declaring Color Sensor and its Variables
    NormalizedColorSensor colorSensor;
    boolean foundRed = false;
    boolean foundWhite = false;
    int count = 0;
    boolean countBool = false;
    
//Declaring the IMU and its Variables
    BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;

    @Override

    public void runOpMode() {
    //Initializing Motors
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        ringLaunch2 = hardwareMap.dcMotor.get("ringLaunch2");

    //Changing Left-Side Motor Directions
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);     //Reverse
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);      //Reerse
        
    //Initializing Color Sensor & Turning the Light on
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensorColor");
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }
        
    //Initializing & Configuring the IMU
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        
        waitForStart();
        
    //Main Robot Code
        while (opModeIsActive()) {
            imu.initialize(parameters);
            goToWhite();

            imu.initialize(parameters);
            sleep(100);
            goToRed();

            //Setting Variable False so the Loop can Run for the Second Red Line
            foundRed = false;

            moveRobot(0.5, 0.5, 0.5, 0.5, 75);
            stopRobot();

            imu.initialize(parameters);
            sleep(100);
            goToRed();

            stopRobot();
        }
    }
    

//IMU Configuration Class
    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }


//IMU Configuration Class
    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    } 
    

//Class to Halt Robot Movement
    public void stopRobot() {
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
    }
    

//Class to Move Robot @ Designated Speed & Duration
    public void moveRobot(double rf, double lf, double rb, double lb, long dur) {
        rightFront.setPower(rf);
        leftFront.setPower(lf);
        rightBack.setPower(lb);
        leftBack.setPower(lb);
        sleep(dur);
    }
    

//Class to Find & Move to the White Line
    public void goToWhite() {
    //Needed (non-changing) Variables
        final float[] hsvValues = new float[3];
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        
    //If the "foundWhite" Boolean is False, Run Loop
        while (!foundWhite && opModeIsActive()) {
        //Needed (updating) Variables
            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            Color.colorToHSV(colors.toColor(), hsvValues);
            double heading = Double.parseDouble(formatAngle(angles.angleUnit, angles.firstAngle));
            
        //If-Else-If Statment to Drive Forward in a Straight Line
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
            
        //If Statement to Detect the White Line and Break the Loop
            if (colors.alpha > 0.5) {
                stopRobot();
                foundWhite = true;
            }
        }
    }
    

//Class to find & Move to the Red line
    public void goToRed() {
    //Needed (non-changing) Variables
        final float[] hsvValues = new float[3];
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        
    //If the "foundRed" Boolean is False, Run Loop
        while (!foundRed && opModeIsActive()) {
        //Needed (updating) Variables
            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            Color.colorToHSV(colors.toColor(), hsvValues);
            double heading = Double.parseDouble(formatAngle(angles.angleUnit, angles.firstAngle));
            
        //If-Else-If Statement to Drive Forward in a Straight Line
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
            
        //If Statement to Detect the Red Line and Break the Loop
            if (colors.alpha < 0.2) {
                stopRobot();
                foundRed = true;
            }
        }
    }
}
