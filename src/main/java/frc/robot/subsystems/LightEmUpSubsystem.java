package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

//The LightDrive Library
import java.awt.Color; //Predefined colors and routines

public class LightEmUpSubsystem extends SubsystemBase{

    public AddressableLED m_led = new AddressableLED(1);
    public AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(60);

    public void LEDSetup(){

        m_led.setLength(m_ledBuffer.getLength());

        m_led.setData(m_ledBuffer);
        m_led.start();
    }

    public void LEDBlue(){
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            m_ledBuffer.setRGB(i, 255, 0, 0);
         }
         
         m_led.setData(m_ledBuffer);
         
    }
    

}

