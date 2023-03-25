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


    

    // public void LEDSetup(){

    //     led.setLEDOutput(0, CANifier.LEDChannel.LEDChannelA); // Green
    //     led.setLEDOutput(0, CANifier.LEDChannel.LEDChannelB); // Red
    //     led.setLEDOutput(0, CANifier.LEDChannel.LEDChannelC); // Blue
        
    // }

    public void LEDSet(double r1, double g1, double b1){
        led.setLEDOutput(b1, CANifier.LEDChannel.LEDChannelA); // Blue
        led.setLEDOutput(r1, CANifier.LEDChannel.LEDChannelB); // Red
        led.setLEDOutput(g1, CANifier.LEDChannel.LEDChannelC); // Green
    }

    public void LEDYellow(){
        led.setLEDOutput(0, CANifier.LEDChannel.LEDChannelA); // Blue
        led.setLEDOutput(1, CANifier.LEDChannel.LEDChannelB); // Red
        led.setLEDOutput(0.25, CANifier.LEDChannel.LEDChannelC); // Green
         
    }

    public void LEDPurple(){
        led.setLEDOutput(1, CANifier.LEDChannel.LEDChannelA); // Blue
        led.setLEDOutput(1, CANifier.LEDChannel.LEDChannelB); // Red
        led.setLEDOutput(0, CANifier.LEDChannel.LEDChannelC); // Green
         
    }

    public void LEDBlue(){
        led.setLEDOutput(1, CANifier.LEDChannel.LEDChannelA); // Blue
        led.setLEDOutput(0, CANifier.LEDChannel.LEDChannelB); // Red
        led.setLEDOutput(0, CANifier.LEDChannel.LEDChannelC); // Green
         
    }

    public void LEDRed(){
        led.setLEDOutput(0, CANifier.LEDChannel.LEDChannelA); // Blue
        led.setLEDOutput(1, CANifier.LEDChannel.LEDChannelB); // Red
        led.setLEDOutput(0, CANifier.LEDChannel.LEDChannelC); // Green
    }
    

}

