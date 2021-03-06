// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2876.PowerUp2018;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc2876.PowerUp2018.commands.ArmDown;
import org.usfirst.frc2876.PowerUp2018.commands.ArmStop;
import org.usfirst.frc2876.PowerUp2018.commands.ArmUp;
import org.usfirst.frc2876.PowerUp2018.commands.ElevatorGoToPosition;
import org.usfirst.frc2876.PowerUp2018.commands.IntakeBackward;
import org.usfirst.frc2876.PowerUp2018.commands.IntakeForward;
import org.usfirst.frc2876.PowerUp2018.commands.ToggleSensitiveDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public Joystick xboxController;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    JoystickButton rightTrigger;
    JoystickButton leftTrigger;
    Button leftBumper;
    Button rightBumper;
    Button aButton;
    Button bButton;
    Button xButton;
    Button yButton;
    Button startButton;
    Button rJoyButton;
    Button lJoyButton;
    
    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        xboxController = new Joystick(0);
        


        // SmartDashboard Buttons
        //SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
//        SmartDashboard.putData("IntakeStop", new IntakeStop());
        SmartDashboard.putData("IntakeBackward", new IntakeBackward());
        SmartDashboard.putData("IntakeForward", new IntakeForward());
        SmartDashboard.putData("armUp", new ArmUp());
        SmartDashboard.putData("armDown", new ArmDown());
        SmartDashboard.putData("armStop", new ArmStop());
//        SmartDashboard.putData("ElevatorUp", new ElevatorUp());
//        SmartDashboard.putData("ElevatorDown", new ElevatorDown());
//        SmartDashboard.putData("ElevatorStop", new ElevatorStop());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
//        SmartDashboard.putData("XboxDrive", new XboxDrive());
//        SmartDashboard.putData("AutoDriveDistance", new AutoDriveDistance(100));
//        SmartDashboard.putData("AutoDriveTurn", new AutoDriveTurn(90));
//        SmartDashboard.putData("ElevatorPosition", new ElevatorPosition(80));
//        SmartDashboard.putData("ElevatorGoToPosition", new ElevatorGoToPosition(-5000));
//        SmartDashboard.putData("AutoDriveStraightDistance", new AutoDriveStraightDistance(100));
//        SmartDashboard.putData("Make a Box", new AutoCGBox());
//        SmartDashboard.putData("CGAcquireCube", new CGAcquireCube());
        
//        SmartDashboard.putData("ElevatorMatchStart", new ElevatorMatchStart());

        createSmartDashboardNumber(RobotMap.SD_KEY_AUTO_DELAY, 0);
   
        createSmartDashboardNumber(RobotMap.SD_KEY_ELEVATOR_POSITION_SETPOINT, 80);
        
        createSmartDashboardNumber("kDistanceTolerance", 2.0);
        createSmartDashboardString("DriveTalonMode", ControlMode.PercentOutput.name());
 	
        aButton = new JoystickButton(xboxController, A_BUTTON);
        aButton.whenPressed(new ElevatorGoToPosition(RobotMap.ELEVATOR_POSITION_ACQUIRE_CUBE));
        
        bButton = new JoystickButton(xboxController, B_BUTTON);
        bButton.whenPressed(new ElevatorGoToPosition(RobotMap.ELEVATOR_POSITION_SWITCH_CUBE));
        
        xButton = new JoystickButton(xboxController, X_BUTTON);
        xButton.whenPressed(new ElevatorGoToPosition(RobotMap.ELEVATOR_POSITION_DRIVE_CUBE));
        
        yButton = new JoystickButton(xboxController, Y_BUTTON);
        yButton.whenPressed(new ElevatorGoToPosition(RobotMap.ELEVATOR_POSITION_SCALE_CUBE));
        
        startButton = new JoystickButton(xboxController, START_BUTTON);
        startButton.whenPressed(new ToggleSensitiveDrive());
        
        leftBumper = new JoystickButton(xboxController, LEFT_BUMPER);
        leftBumper.whileHeld(new IntakeBackward());
        
        rightBumper = new JoystickButton(xboxController, RIGHT_BUMPER);
        rightBumper.whileHeld(new IntakeForward());
        
        lJoyButton = new JoystickButton(xboxController, LJOY_BUTTON);
        lJoyButton.whileHeld(new ArmDown());
        
        rJoyButton = new JoystickButton(xboxController, RJOY_BUTTON);
        rJoyButton.whileHeld(new ArmUp());

//        rightTrigger = new JoystickButton(xboxController, RIGHT_TRIGGER);
//        rightTrigger.whileHeld(new ElevatorDown());
//        leftTrigger = new JoystickButton(xboxController, LEFT_TRIGGER);
//        rightTrigger.whileHeld(new ElevatorUp());
        
    }
   

    public double getkDistanceTolerance() {
    	   return SmartDashboard.getNumber("kDistanceTolerance", 2.0);
    }
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getXboxController() {
        return xboxController;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
	public double getLeftX() {
		return xboxController.getRawAxis(LEFT_X_AXIS);
	}

	public double getLeftY() {
		return xboxController.getRawAxis(LEFT_Y_AXIS);
	}

	public double getRightX() {
		return xboxController.getRawAxis(RIGHT_X_AXIS);
	}

	public double getRightY() {
		return xboxController.getRawAxis(RIGHT_Y_AXIS);
	}

	public double getLeftTrigger() {
		return xboxController.getRawAxis(LEFT_TRIGGER);
	}

	public double getRightTrigger() {
		return xboxController.getRawAxis(RIGHT_TRIGGER);
	}

	public static final int LEFT_X_AXIS = 0, LEFT_Y_AXIS = 1, LEFT_TRIGGER = 2, RIGHT_TRIGGER = 3, RIGHT_X_AXIS = 4,
			RIGHT_Y_AXIS = 5, DPAD_X_AXIS = 6, DPAD_Y_AXIS = 7;
	
	public static final int LEFT_BUMPER = 5,
			RIGHT_BUMPER = 6,
			A_BUTTON=1,
			B_BUTTON=2,
			X_BUTTON=3,
			Y_BUTTON=4,
			START_BUTTON=8,
			LJOY_BUTTON=9,
			RJOY_BUTTON=10;

	/**
	 * Initialize value on SmartDashboard for user input, but leave old value if already present.
	 *
	 * @param key The SmartDashboard key to associate with the value.
	 * @param defValue The default value to assign if not already on dashboard.
	 *
	 * @return The current value that appears on the dashboard.
	 */
	public static double createSmartDashboardNumber(String key, double defValue) {

	  // See if already on dashboard, and if so, fetch current value
	  double value = SmartDashboard.getNumber(key, defValue);

	  // Make sure value is on dashboard, puts back current value if already set
	  // otherwise puts back default value
	  SmartDashboard.putNumber(key, value);

	  return value;
	}
	
	/**
	 * Initialize value on SmartDashboard for user input, but leave old value if already present.
	 *
	 * @param key The SmartDashboard key to associate with the value.
	 * @param defValue The default value to assign if not already on dashboard.
	 *
	 * @return The current value that appears on the dashboard.
	 */
	public static String createSmartDashboardString(String key, String defValue) {

	  // See if already on dashboard, and if so, fetch current value
	  String value = SmartDashboard.getString(key, defValue);

	  // Make sure value is on dashboard, puts back current value if already set
	  // otherwise puts back default value
	  SmartDashboard.putString(key, value);

	  return value;
	}
	
	public void putSmartDashboardDriveCommand(Command c){
		SmartDashboard.putString("DriveTrain Current Command", c.toString());
		SmartDashboard.putString("DriveTrain Current Command Group", c.getGroup().toString());
	}
}

