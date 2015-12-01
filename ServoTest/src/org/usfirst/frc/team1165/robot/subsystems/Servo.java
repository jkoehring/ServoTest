package org.usfirst.frc.team1165.robot.subsystems;

import org.usfirst.frc.team1165.devices.FirgelliL12;
import org.usfirst.frc.team1165.robot.RobotMap;
import org.usfirst.frc.team1165.robot.commands.ReportServo;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Servo extends Subsystem
{
	private edu.wpi.first.wpilibj.Servo servo = new FirgelliL12(RobotMap.servoChannel);

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public double getAngle()
	{
		return servo.getAngle();
	}
	
	public double getPosition()
	{
		return servo.getPosition();
	}
	

	public void initDefaultCommand()
	{
		setDefaultCommand(new ReportServo());
	}
	
	public void report()
	{
		SmartDashboard.putNumber("Servo position",  getPosition());
		SmartDashboard.putNumber("Servo angle",  getAngle());
	}
	
	public void setAngle(double angle)
	{
		servo.setAngle(angle);
	}
	
	public void setPosition(double position)
	{
		servo.setPosition(position);
	}
}
