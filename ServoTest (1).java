package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

//@Autonomous(name = "Sensor: ServoTest", group = "Sensor")
public class ServoTest extends LinearOpMode {
    private Servo topServo;
    private Servo bottomServo;
    private Servo ringFling;
    private DcMotor armString;
    
    @Override

    public void runOpMode() {
        topServo = hardwareMap.servo.get("topServo");
        bottomServo = hardwareMap.servo.get("bottomServo");
        ringFling = hardwareMap.servo.get("ringFling");
        armString = hardwareMap.dcMotor.get("armString");
        
        armString.setPower(0.5);
        sleep(750);
        
    }
}
