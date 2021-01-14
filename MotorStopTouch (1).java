package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name = "Touch Sensor", group = "sensors")

public class MotorStopTouch extends LinearOpMode {
    private DcMotor rightFront;
    private DigitalChannel digitalTouch;
    
    boolean tStatus;
    
    @Override
    
    public void runOpMode() {
        rightFront = hardwareMap.dcMotor.get("rightFront");
        digitalTouch = hardwareMap.get(DigitalChannel.class, "touchSensor");
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);
        
        waitForStart();
        
        while (opModeIsActive()) {
            
            
            
            if (digitalTouch.getState() == true) {
                telemetry.addData("Touch Sensor", "Is Not Pressed");
                rightFront.setPower(1);
            } else if (digitalTouch.getState() == false) {
                telemetry.addData("Touch Sensor", "Is Pressed");
                rightFront.setPower(0);
            } 
            telemetry.update();
        }
    }
}
