package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;
import java.util.Locale;
@Autonomous

public class RingEncoder extends LinearOpMode {

    private     DcMotorEx     rl1;        //port 1
    private     DcMotorEx     rl2;        //port 2
    
    @Override
    public void runOpMode() {
        
        rl1 =   hardwareMap.get(DcMotorEx.class, "ringLaunch1");
        rl2 =   hardwareMap.get(DcMotorEx.class, "ringLaunch2");
        
        rl1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rl2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            
        telemetry.addData("Stat", "Start Program");
        telemetry.update();
        
        waitForStart();
        
        double lengthUsingInches = 10;
            
        rl1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rl2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        double calcPosition = lengthUsingInches * (100* 280/(16.9646003294*4 *8.8 * 1.0555555556));
        int setPosition = (int) Math.round(calcPosition);

        rl1.setTargetPosition(setPosition);
        rl2.setTargetPosition(setPosition);

        rl1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rl2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rl1.setVelocity(500);
        rl2.setVelocity(500);

        while (opModeIsActive() && rl1.isBusy()) {
            telemetry.addData("position", rl1.getCurrentPosition());
            telemetry.addData("is at target", !rl1.isBusy());
            telemetry.update();
        }

        rl1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rl2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rl1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rl2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
  
     }
}
