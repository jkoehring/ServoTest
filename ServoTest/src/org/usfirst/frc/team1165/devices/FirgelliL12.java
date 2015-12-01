package org.usfirst.frc.team1165.devices;

import edu.wpi.first.wpilibj.Servo;

public class FirgelliL12 extends Servo
{
	private double minServoPWM = .95;
	private double maxServoPWM = 2.04;
	
	public FirgelliL12(int channel)
	{
		super(channel);
        setBounds(maxServoPWM, 0, 0, 0, minServoPWM);
	}

}
