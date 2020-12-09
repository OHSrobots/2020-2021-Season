package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import java.util.Stack;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

@Autonomous(name = "CamComp", group = "comp")
/*******************************************************************************
*                              COMP PROGRAM: START                             *
*******************************************************************************/

// INITILIZATION
public class AutonomousCompWithCam extends LinearOpMode {
    
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
    
//Declaring Camera Variables 
    private TFObjectDetector tfod;
    private VuforiaLocalizer vuforia;
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
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
        lb.setDirection(DcMotorSimple.Direction.REVERSE);     //Reverse
        
        //Initialize Camera
        initVuforia();
        initTfod();
        if (tfod != null) {
            tfod.activate();
        }
        
        //Telemetry Data
        telemetry.addData("RAO", "Initializing...");
        telemetry.update();
        sleep(1200);
        telemetry.addData("RAO", "Press Play :)");
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
                      telemetry.addData("# Object Detected", updatedRecognitions.size());
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
            
            long time = System.currentTimeMillis();
            long time1 = time + 10000;
            
            /* rl1.setPower(-0.8425);
            rl2.setPower(-0.8425);
            sleep(2300);*/
            
            /* while (System.currentTimeMillis() < time1) {
                rl1.setPower(-0.8425);
                rl2.setPower(-0.8425);
                
                _rf.setPosition(0.4);
                sleep(450);
                _rf.setPosition(0.8);
                sleep(450); 
            
                rs.setPower(1);
            } */
            
            rl1.setPower(0);
            rl2.setPower(0);
            sleep(100);
            _rf.setPosition(0.8);
            
            if(quadStack) {
                rf.setPower(1);
                lf.setPower(1);
                rb.setPower(1);
                lb.setPower(1);
                sleep(1550);
            
                rf.setPower(0.5);
                lf.setPower(-0.5);
                rb.setPower(0.5);
                lb.setPower(-0.5);
                sleep(1180);
            
                rf.setPower(-0.5);
                lf.setPower(-0.5);
                rb.setPower(-0.5);
                lb.setPower(-0.5);
                sleep(1480);
                
                sleep(100);
                
                bs.setPosition(0.4);
                sleep(100);
                
                sleep(100);
                
                ts.setPosition(0.5);
                sleep(25);
                
                sleep(100);
                
                rf.setPower(0.5);
                lf.setPower(-0.5);
                rb.setPower(0.5);
                lb.setPower(-0.5);
                sleep(400);
                
                rf.setPower(1);
                lf.setPower(1);
                rb.setPower(1);
                lb.setPower(1);
                sleep(100);
                
                /*
                bs.setPosition(0.9);
                sleep(100);
                ts.setPosition(0.5);
                sleep(25);
            
                rf.setPower(0.75);
                rb.setPower(0.75);
                lf.setPower(0.75);
                lb.setPower(0.75);
                sleep(550);
                
                rf.setPower(0.5);
                lf.setPower(-0.5);
                rb.setPower(0.5);
                lb.setPower(-0.5);
                sleep(800);
                
                rf.setPower(0.5);
                rb.setPower(0.5);
                lf.setPower(0.5);
                lb.setPower(0.5);
                sleep(1000); */
                
            } else if(singleStack) {
                
                rf.setPower(1);
                lf.setPower(1);
                rb.setPower(1);
                lb.setPower(1);
                sleep(1500);
            
                rf.setPower(-0.5);
                lf.setPower(0.5);
                rb.setPower(-0.5);
                lb.setPower(0.5);
                sleep(850);
            
                rf.setPower(1);
                lf.setPower(1);
                rb.setPower(1);
                lb.setPower(1);
                sleep(475);
            
                rf.setPower(-1);
                lf.setPower(1);     //360
                rb.setPower(-1);
                lb.setPower(1);
                sleep(400);
            
                rf.setPower(0.75);
                rb.setPower(0.75);
                lf.setPower(0.75);
                lb.setPower(0.75);
                sleep(70);
            
                bs.setPosition(0.4);
                sleep(100);
                ts.setPosition(0.5);
                sleep(25);
                
                sleep(300);
                
                rf.setPower(0.75);
                rb.setPower(0.75);
                lf.setPower(0.75);
                lb.setPower(0.75);
                sleep(8);
                
            } else {
                rf.setPower(1);
                lf.setPower(1);
                rb.setPower(1);
                lb.setPower(1);
                sleep(1200);
            
                rf.setPower(0.5);
                lf.setPower(-0.5);
                rb.setPower(0.5);
                lb.setPower(-0.5);
                sleep(975);
            
                rf.setPower(-0.5);
                rb.setPower(-0.5);
                lf.setPower(-0.5);
                lb.setPower(-0.5);
                sleep(1500);
            
                bs.setPosition(0.9);
                sleep(100);
                ts.setPosition(0.5);
                sleep(25);
            
                rf.setPower(0.75);
                rb.setPower(0.75);
                lf.setPower(0.75);
                lb.setPower(0.75);
                sleep(50); 
            }
        }
    }
    
    private void initVuforia() {
        
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
    
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
       tfodParameters.minResultConfidence = 0.8f;
       tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
       tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
