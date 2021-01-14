package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name = "Magnetic Sensor", group = "sensors")

public class MagTest extends LinearOpMode {

    private DigitalChannel magLimit;
    
    public void runOpMode() {
        magLimit = hardwareMap.get(DigitalChannel.class, "magLimit");
        magLimit.setMode(DigitalChannel.Mode.INPUT);
        
        waitForStart();
        
        while (opModeIsActive()) {
            if (magLimit.getState() == true) {
                telemetry.addData("RAO: ", "Pressed");
            } else if (magLimit.getState() == false) {
                telemetry.addData("RAO: ", "Not Pressed");
            }
            telemetry.update();
        }
    }
}
