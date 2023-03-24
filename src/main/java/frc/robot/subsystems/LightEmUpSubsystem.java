package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import com.ctre.phoenix.CANifier.LEDChannel;
import com.ctre.phoenix.CANifier;

public class LightEmUpSubsystem extends SubsystemBase{

    public CANifier led = new CANifier(9);


    

    public void LEDSetup(){

        led.setLEDOutput(0, CANifier.LEDChannel.LEDChannelA); // Green
        led.setLEDOutput(0, CANifier.LEDChannel.LEDChannelB); // Red
        led.setLEDOutput(100, CANifier.LEDChannel.LEDChannelC); // Blue
        
    }

    // public void LEDBlue(){
    //     for (var i = 0; i < m_ledBuffer.getLength(); i++) {
    //         // Sets the specified LED to the RGB values for red
    //         m_ledBuffer.setRGB(i, 255, 0, 0);
    //      }
         
    //      m_led.setData(m_ledBuffer);
         
    // }
    

}

