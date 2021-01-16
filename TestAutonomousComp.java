package org.firstinspires.ftc.teamcode.competition;

import java.util.List;
import java.util.Stack;
import android.view.View;
import android.graphics.Color;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.Func;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;


@Autonomous(name = "TEST Autonomous Comp", group = "comp")


/*******************************************************************************
*                              COMP PROGRAM: START                             *
*******************************************************************************/

// INITILIZATION
public class TestAutonomousComp extends LinearOpMode {

// Declaring Motors
    private     DcMotor     rf;         //port 0
    private     DcMotor     lf;         //port 1
    private     DcMotor     rb;         //port 2
    private     DcMotor     lb;         //port 3
    private     DcMotor     rs;         //port 0
    private     DcMotor     rl1;        //port 1
    private     DcMotor     rl2;        //port 2
    private     DcMotor     as;         //port 3

    private     Servo       _rf;        //port 0
    private     Servo       ts;         //port 0
    private     Servo       bs;         //port 1
    
    NormalizedColorSensor colorSensor;
    View relativeLayout;

    
//Declaring Camera Variables 
    private TFObjectDetector tfod;
    private VuforiaLocalizer vuforia;
    private static final String LABEL_FIRST_ELEMENT = "4 Stack";
    private static final String LABEL_SECOND_ELEMENT = "0 Stack";
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String VUFORIA_KEY = "ARxaOAX/////AAABmR91q9ci+kNYqGb/NElhuhBQa5klidYZ5jKk5hFYJ6qAQOtCGKSEZXn1qYawipXKEEpJh+vP3GNnOUvabO2blz4vkymDnu8LUocLc6/rMpQdLwBt80JVdgWWkd/4j1DmwDdRRP4f/jP78furjgexjT7HgmC37xLP+msr78zAeWwkrsT2X1yjnL6nyiGcRKlBw6+EcUIcZYiiuXwbILds8rl4Fu7AuecLaygDft6XIUFg/qQm51UF45l5pYT8AoNTUhP9GTksKkmHgde7iGlo3CfIYu9QanjPHreT/+JZLJWG22jWC7Nnzch/1HC6s3s2jzkrFV6sRVA4lL9COLIonjRBYPhbxCF06c5fUMy9sj/e";

    @Override
    
    public void runOpMode() {
        // Mapping Devices
        rf  =   hardwareMap.dcMotor.get("rightFront");
        lf  =   hardwareMap.dcMotor.get("leftFront");
        rb  =   hardwareMap.dcMotor.get("rightBack");
        lb  =   hardwareMap.dcMotor.get("leftBack");
        
        rs  =   hardwareMap.dcMotor.get("ringScoop");
        rl1 =   hardwareMap.dcMotor.get("ringLaunch1");
        rl2 =   hardwareMap.dcMotor.get("ringLaunch2");
        as  =   hardwareMap.dcMotor.get("armString");
        
        _rf =   hardwareMap.servo.get("ringFling");
        ts  =   hardwareMap.servo.get("topServo");
        bs  =   hardwareMap.servo.get("bottomServo");
        
        // Extra Motor Steps
        lf.setDirection(DcMotorSimple.Direction.REVERSE);    //Reverse
        lb.setDirection(DcMotorSimple.Direction.REVERSE);    //Reverse
        
        //Initialize Camera
        initVuforia();
        initTfod();
        if (tfod != null) {
            tfod.activate();
        }
        
        //Telemetry Data
        telemetry.addData("Stat", "Initializing...");
        telemetry.update();
        sleep(900);
        telemetry.addData("Stat", "Start Program");
        telemetry.update();
        
        //Camera Detection
        ts.setPosition(0.92);
        boolean singleStack = false;
        boolean quadStack = false;
        if (!opModeIsActive()) {
            while (!opModeIsActive()) {
                if (tfod != null) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                      telemetry.addData("# of Rings", updatedRecognitions.size()); //# Object Detected
                      int i = 0;
                      for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        if(recognition.getLabel() == LABEL_SECOND_ELEMENT) {
                            singleStack = true;
                        } else if(recognition.getLabel() == LABEL_FIRST_ELEMENT) {
                            quadStack = true;
                        }
                      }
                      telemetry.update();
                    }
                }
            }
        }
        
    //Waiting for start via Player
        waitForStart();
        
    //Initializing Servos
        bs.setPosition(0);
        _rf.setPosition(1);
        
    //More telemetry data
        telemetry.addData("RAO", "Running Code!");
        telemetry.addData("Status", "Pre-Coded Robot Driving");
        telemetry.update();
        
    //Beginning Loop for Program
        if (opModeIsActive()) {
            
            //as.setPower(1);
            //sleep(350);
            
            shootRing(10);
            
            if(quadStack) {
                wheelSpeed(1,1,1,1);
                sleep(1550);
                
                wheelSpeed(0.5, -0.5, 0.5, -0.5);
                sleep(980);
                
                wheelSpeed(-0.5, -0.5, -0.5, -0.5);
                sleep(1150);
                
                sleep(100);
                
                bs.setPosition(0.4);
                sleep(100);
                
                sleep(100);
                
                ts.setPosition(0.5);
                sleep(25);
                
                sleep(100);
                
                wheelSpeed(0.5, -0.5, 0.5, -0.5);
                sleep(400);
                
                wheelSpeed(1, 1, 1, 1);
                sleep(250);
                
            } else if(singleStack) {
                testForColor();
                
                /* wheelSpeed(1, 1, 1, 1);
                sleep(1500);
                
                wheelSpeed(0.5, -0.5, 0.5, -0.5);
                sleep(883);
                
                wheelSpeed(-0.5, -0.5, -0.5, -0.5);
                sleep(40);
                
                sleep(100);
                
                bs.setPosition(0.9);
                sleep(100);
                ts.setPosition(0.5);
                sleep(25);
                
                wheelSpeed(1, 1, 1, 1);
                sleep(90);
                
                wheelSpeed(0.5, -0.5, 0.5, -0.5);
                sleep(250);
                
                wheelSpeed(1, 1, 1, 1);
                sleep(6); */
                
            } else {
                wheelSpeed(1, 1, 1, 1);
                sleep(1178);
            
                wheelSpeed(0.5, -0.5, 0.5, -0.5);
                sleep(870);
            
                wheelSpeed(-0.5, -0.5, -0.5, -0.5);
                sleep(1425);
            
                bs.setPosition(0.9);
                sleep(100);
                ts.setPosition(0.5);
                sleep(25);
            
                wheelSpeed(0.75, 0.75, 0.75, 0.75);
                sleep(90); 
            }
            wheelSpeed(0, 0, 0, 0);
        }
    }

//Vufoira Method
    private void initVuforia() {
        
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    
//Tfod Method
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
       tfodParameters.minResultConfidence = 0.8f;
       tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
       tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }


//Method to Completely Stop ALL Robot Movement
    private void forceStop() {
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
        as.setPower(0);
        rs.setPower(0);
        rl1.setPower(0);
        rl2.setPower(0);
        bs.setPosition(0);
        _rf.setPosition(0.8);
    }


//Method to Halt Robot Movement
    public void stopRobot() {
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
    }
    

//Method to Move Robot @ Designated Speed & Duration
    public void moveRobot(double rf, double lf, double rb, double lb, long dur) {
        rightFront.setPower(rf);
        leftFront.setPower(lf);
        rightBack.setPower(lb);
        leftBack.setPower(lb);
        sleep(dur);
    }

//Method to Shoot Rings for Designated Time (seconds)
    public void shootRing(int wantedTime) {
        long time = Sytem.currentTimeMillis();
        long _time = time + (wantedTime * 1000);

        while (System.currentTimeMillis() < _time) {
            rl1.setPower(-0.8460);
            rl2.setPower(-0.8460);
            
            _rf.setPosition(0.4);
            sleep(300);
            _rf.setPosition(0.8);
            sleep(300); 
        
            rs.setPower(1);
        }
    }
}
