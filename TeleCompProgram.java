/*   Coded for 2020-2021 Season | Ultimate Goal
 *   Team: AllChargedUp#9189
 *   Configuration Name: AutoSetup
 *   
 *   Contributors: Tate Schneider, Eric Thorndyke
 *   
 *   Connected Devices Port Info ~
 *   CH: Control Hub | EH: Expansion Hub
 *   
 *      Control Hub     CH      Hub 2
 *      Expansion Hub   EH      Hub 3
 *      Webcam          CH      USB
 *      
 *      rightFront      CH      Motor Port 0
 *      rightBack       CH      Motor Port 2
 *      leftFront       CH      Motor Port 1
 *      leftBack        CH      Motor Port 3
 *      ringScoop       EH      Motor Port 2
 *      ringLaunch1     EH      Motor Port 0
 *      ringLaunch2     EH      Motor Port 1
 *      armString       EH      Motor Port 3
 *      
 *      imu             CH      I2C Port 0
 *      distance1       CH      I2C Port 1
 *      distance2       CH      I2C Port 2
 *      distance3       EH      I2C Port 1
 *      distance4       EH      I2C Port 2
 *      colorSens       CH      I2C Port 3
 * 
 *      ringFling       EH      Servor Port 0
 *      topServo        CH      Servo Port 0
 *      bottomServo     CH      Servo Port 1
 *      
 *   Comments added to the best of the contributors ability to help
 *   understandement of code. Make sure all devices are in correct
 *   ports for the entire program or example snippets to work (as
 *   stated above). Configuration name for the phone is also stated.
 */
 
package org.firstinspires.ftc.teamcode.competition;

//Importing Needed Classes & Methods
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.hardware.DcMotorSimple;
    import com.qualcomm.robotcore.hardware.Servo;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name = "TeleOp Comp", group = "comp")

/*******************************************************************************
*                              COMP PROGRAM: START                             *
*******************************************************************************/

// INITILIZATION

public class TeleCompProgram extends LinearOpMode {
//Decalring Devices 
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

    @Override

    public void runOpMode() {
    //Mapping Devices
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
        
    //Changing Drving Motor Directions
        lf.setDirection(DcMotorSimple.Direction.REVERSE);   //Reverse
        lb.setDirection(DcMotorSimple.Direction.REVERSE);   //Reverse
        
    //Telemetry Data
        telemetry.addData("RAO", "Initializing...");
        telemetry.update();
        sleep(1250);
        telemetry.addData("RAO", "Press Play :)");
        telemetry.update();
        
    //Waiting for Start via Player
        waitForStart();
        
    //Initializing Servos
        bs.setPosition(0);
        ts.setPosition(1);
        _rf.setPosition(0.8);
        
    //More telemetry data
        telemetry.addData("RAO", "Running Code!");
        telemetry.addData("Status", "Player Controlled Driving");
        telemetry.update();
        
    //Begin loop for program
        while (opModeIsActive()) {
                
        //If-Then Logic for gamepad1
              
                //Forward & Backward
            if(gamepad1.right_stick_y != 0) {
                rf.setPower(-gamepad1.right_stick_y);
                lf.setPower(-gamepad1.right_stick_y);
                rb.setPower(-gamepad1.right_stick_y);
                lb.setPower(-gamepad1.right_stick_y);
            } 
                //Turn Right
            else if (gamepad1.right_bumper) {
                rf.setPower(1);
                rb.setPower(1);
            } 
                //Turn left
            else if (gamepad1.left_bumper) {
                lf.setPower(1);
                lb.setPower(1);
            } 
                //Turn round right
            else if (gamepad1.right_trigger > 0) {
                lf.setPower(gamepad1.right_trigger);
                lb.setPower(gamepad1.right_trigger);
                rb.setPower(-gamepad1.right_trigger);
                rf.setPower(-gamepad1.right_trigger);
            } 
                //Turn round left 
            else if (gamepad1.left_trigger > 0) {
                rf.setPower(gamepad1.left_trigger);
                rb.setPower(gamepad1.left_trigger);
                lb.setPower(-gamepad1.left_trigger);
                lf.setPower(-gamepad1.left_trigger);
            } 
                //Set all motors to 0
            else {                              
                rf.setPower(0);
                lf.setPower(0);
                rb.setPower(0);
                lb.setPower(0);
            }
            
            //If-Then logic for gamepad2
            
                //Arm Raise and Lower
            if (gamepad2.left_stick_y != 0) {
                as.setPower(-gamepad2.left_stick_y);
            }
                //Claw Release
            else if (gamepad2.dpad_down) {
                
            }
                //Hand turn towards bot
            else if (gamepad2.right_trigger != 0) {
                bs.setPosition(1);
            }
                //Hand turn away from bot
            else if (gamepad2.left_trigger != 0) {
                
            }
                //Activate Launch
            else if (gamepad2.a) {
            //Putting in loop so servo can continuously move back and forth
                while (gamepad2.a) {
                    rl1.setPower(-1);
                    rl2.setPower(-1);
                    
                //Moving ring flicker back and forth
                    _rf.setPosition(0.1);
                    sleep(500);
                    _rf.setPosition(0.8);
                    sleep(500);
                }
            }
                //Activate Intake
            else if (gamepad2.b) {
                rs.setPower(1);
            }
                //Total Shooter (15 Seconds) + Override
            else if (gamepad2.y) {
                
            //Creating variables for loop
                long time = System.currentTimeMillis();
                long time15 = time + 15000;
                
            //Loop runs the entire shooter for 10 seconds
                while(System.currentTimeMillis() < time15) {
                //Activating intake + launcher
                    rl1.setPower(-0.1);
                    rl2.setPower(-0.1);
                    rs.setPower(1);
                    
                //Activating ring flick
                    _rf.setPosition(0.4);
                    sleep(550);
                    _rf.setPosition(0.8);
                    sleep(550);
                    
                //Override stop for shooter
                    if (gamepad2.x) {
                        rs.setPower(0);
                        rl1.setPower(0);
                        rl2.setPower(0);
                        break;
                    }
                }
            }
            
            else {
                as.setPower(0);
                rl1.setPower(0);
                rl2.setPower(0);
                rs.setPower(0);
            }
        }
    }
}
