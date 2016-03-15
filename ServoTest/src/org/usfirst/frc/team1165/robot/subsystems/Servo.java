package org.usfirst.frc.team1165.robot.subsystems;

import java.util.ArrayList;

import org.usfirst.frc.team1165.robot.RobotMap;
import org.usfirst.frc.team1165.robot.commands.ReportServo;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.PowerJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Servo extends Subsystem
{
	//private edu.wpi.first.wpilibj.Servo servo = new FirgelliL12(RobotMap.servoChannel);
	private edu.wpi.first.wpilibj.Servo servo = new edu.wpi.first.wpilibj.Servo(RobotMap.servoChannel);
	
	private double maxCurrent;
	private double totalCurrent;
	private double meanCurrent;
	private double medianCurrent;
	private ArrayList<Double> currents = new ArrayList<Double>();

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
	
	public synchronized void report()
	{
		SmartDashboard.putNumber("Servo position",  getPosition());
		SmartDashboard.putNumber("Servo angle",  getAngle());
		double current = PowerJNI.getUserCurrent6V();
		SmartDashboard.putNumber("6v current", current);
		
		// We ignore small current values because they indicate the servo is not moving.
		if (current >= .05)
		{
			// Update 6v current draw statistics:
			totalCurrent += current;
			maxCurrent = Math.max(current,  maxCurrent);
			
			// Maintain a sorted list of current values for calculating the median current:
			int i = 0;
			while (i < currents.size())
			{
				if (current < currents.get(i))
					break;
				i++;
			}
			currents.add(i, current);
			
			// Update mean (average) and median values:
			meanCurrent = totalCurrent / currents.size();
			int midIndex = currents.size() / 2;
			medianCurrent = currents.size() % 2 == 0
				? (currents.get(midIndex) + currents.get(midIndex - 1)) / 2.0
				: currents.get(midIndex);
		}
		
		// Put statistical info on smart dashboard:
		SmartDashboard.putNumber("6v current max", maxCurrent);
		SmartDashboard.putNumber("6v current mean", meanCurrent);
		SmartDashboard.putNumber("6v current median", medianCurrent);
		SmartDashboard.putNumber("6v current faults", PowerJNI.getUserCurrentFaults6V());
	}

	public synchronized void setAngle(double angle)
	{
		resetCurrentStats();
		servo.setAngle(angle);
	}
	
	public synchronized void setPosition(double position)
	{
		resetCurrentStats();
		servo.setPosition(position);
	}
	
	private void resetCurrentStats()
	{
		maxCurrent = 0;
		totalCurrent = 0;
		meanCurrent = 0;
		medianCurrent = 0;
		currents.clear();
	}
}
