/*   Coded for 2020-2021 Season | Ultimate Goal
 *   Team: AllChargedUp#9189
 *   Configuration Name: AutoSetup
 *   
 *   Contributors: Micah Knox, Eric Thorndyke
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
 *      Arm             EH      Motor Port 0 
 *      spinner         EH      Motor Port 1
 *      
 *      imu             CH      I2C Port 0
 *      distance1       CH      I2C Port 1
 *      distance2       CH      I2C Port 2
 *      distance3       EH      I2C Port 1
 *      distance4       EH      I2C Port 2
 *      colorSens       CH      I2C Port 3
 * 
 *      arm             CH      Servor Port 0
 *      wrist           CH      Servor Port 1
 *      
 *   Comments added to the best of the contributors ability to help
 *   understandement of code. Make sure all devices are in correct
 *   ports for the entire program or example snippets to work (as
 *   stated above). Configuration name for the phone is also stated.
 */
 
package org.firstinspires.ftc.teamcode.competition;

//Importing Needed Classes & Methods
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
    import com.qualcomm.robotcore.hardware.DistanceSensor;
    import com.qualcomm.robotcore.hardware.DcMotorSimple;
    import com.qualcomm.robotcore.hardware.Servo;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name = "FinalTeleOp", group = "comp")

/*******************************************************************************
*                              COMP PROGRAM: START                             *
*******************************************************************************/

// INITILIZATION

public class FinalTeleOp extends LinearOpMode {
//Decalring Devices 
    private     DcMotor     rf;         //port 0
    private     DcMotor     lf;         //port 1
    private     DcMotor     rb;         //port 2
    private     DcMotor     lb;         //port 3
    private     DcMotor     Arm;        //port 0
    private     DcMotor     spinner;     //port 1

    private     Servo       arm;        //port 0
    private     Servo       wrist;        //port 0

    DigitalChannel magSensor;
    
    @Override

    public void runOpMode() {
    //Mapping Devices
        rf  =   hardwareMap.dcMotor.get("rightFront");
        lf  =   hardwareMap.dcMotor.get("leftFront");
        rb  =   hardwareMap.dcMotor.get("rightBack");
        lb  =   hardwareMap.dcMotor.get("leftBack");
        
        Arm  =   hardwareMap.dcMotor.get("Arm");
        spinner  =   hardwareMap.dcMotor.get("spinner");

        
        arm =   hardwareMap.servo.get("arm");
        wrist =   hardwareMap.servo.get("wrist");

       
        magSensor = hardwareMap.get(DigitalChannel.class, "magSensor");
        magSensor.setMode(DigitalChannel.Mode.INPUT);
        
    //Changing Drving Motor Directions
        lf.setDirection(DcMotorSimple.Direction.REVERSE);   //Reverse
        lb.setDirection(DcMotorSimple.Direction.REVERSE);   //Reverse
        
    //Telemetry Data
 telemetry.addData("RAO", "Initializing...");
        telemetry.update();
        sleep(1250);
        telemetry.addData("RAO", "Press Play :)");
        telemetry.update();
        double w = -.75;
        double r = 0.01;

    //Waiting for Start via Player
        waitForStart();
         
    //Initializing Servos
        
    //More telemetry data
        telemetry.addData("RAO", "Running Code!");
        telemetry.addData("Status", "Player Controlled Driving");
        telemetry.update();

    //Begin loop for program
    
        while (opModeIsActive()) {
            //expand
        //If-Then Logic for gamepad1
              
                //Forward & Backward
                                     //Arm Raise and Lower
            if (gamepad2.right_stick_y != 0 && gamepad1.left_stick_y != 0) {
                while ((magSensor.getState() == false) && gamepad2.right_stick_y > 0) {
                    Arm.setPower(0);
                }
                while (Math.abs(gamepad2.right_stick_y) > 0){
                    Arm.setPower(-gamepad2.right_stick_y);
                                    
                rf.setPower(-gamepad1.left_stick_y);
                lf.setPower(-gamepad1.left_stick_y);
                rb.setPower(-gamepad1.left_stick_y);
                lb.setPower(-gamepad1.left_stick_y);
                }
                stopRobot();
            }           
            
            else if(gamepad1.left_stick_y != 0) {
                rf.setPower(-gamepad1.left_stick_y);
                lf.setPower(-gamepad1.left_stick_y);
                rb.setPower(-gamepad1.left_stick_y);
                lb.setPower(-gamepad1.left_stick_y);
            }
            
            /* Eric:
            else if (gamepad1.x && gamepad1.rightstick_y != 0)
                rf.setPower(gamepad1.right_stick_y);
                lf.setPower(gamepad1.right_stick_y);
                rb.setPower(gamepad1.right_stick_y);
                lb.setPower(gamepad1.right_stick_y);
            */
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
            else if (gamepad1.right_stick_x != 0) {
                lf.setPower(gamepad1.right_stick_x);
                lb.setPower(gamepad1.right_stick_x);
                rb.setPower(-gamepad1.right_stick_x);
                rf.setPower(-gamepad1.right_stick_x);
            } 
            else stopRobot();
            
                //Turn round left 
                //Set all motors to 0

            
            
            //If-Then logic for gamepad2
            

            //set wrist to start position
            if (gamepad1.a) {
                wrist.setPosition(w);
            }


                                    //Arm Raise and Lower
            if (gamepad2.right_stick_y != 0) {
                while ((magSensor.getState() == false) && gamepad2.right_stick_y > 0) {
                    Arm.setPower(0);
                }
                while (Math.abs(gamepad2.right_stick_y) > 0){
                    Arm.setPower(-gamepad2.right_stick_y);
                }
                Arm.setPower(0);
            }
            //move spinner for red side
            else if (gamepad2.a) {
                
                while (gamepad2.a == true){

                    spinner.setPower(-0.5);
                }
                spinner.setPower(0);
            }
            //move spinner for blue side
             else if (gamepad2.b) {
                
                while (gamepad2.b == true){

                    spinner.setPower(0.5);
                }
                spinner.setPower(0);
             }
             //set arm positions
            else if (gamepad2.dpad_up) {

                arm.setPosition(.5);
            }
            else if (gamepad2.dpad_down) {
                arm.setPosition(0.1);
            }
                
            
             
            
                
                //wrist turn towards bot
            else if (gamepad2.left_bumper) {
                    wrist.setPosition(.25);
                    
                
               
            }
            //wrist turns to middle of bot
            else if (gamepad2.right_bumper) {
            wrist.setPosition(-.25);
            }   
            
            else stopRobot();
            
    }}
        public void stopRobot(){
                rf.setPower(0);
                lf.setPower(0);
                rb.setPower(0);
                lb.setPower(0);
                Arm.setPower(0);
        }}
