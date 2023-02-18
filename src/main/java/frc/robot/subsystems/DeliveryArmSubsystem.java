// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DeliveryArmSubsystem extends SubsystemBase{

    private DoubleSolenoid DeliveryArmClampSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 4, 5);

    private boolean DeliveryArmClampOpen = false;

    private DoubleSolenoid StopSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
    private boolean stopped;

    private TalonSRX extendingArmMotor = new TalonSRX(1);
    //private boolean deliveryArmExtended = false;

    
    public DeliveryArmSubsystem() {
        extendingArmMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    }


    public void OpenDeliveryArmClamp(){
        DeliveryArmClampSolenoid.set(Value.kReverse);
        DeliveryArmClampOpen = true;
    }


    public void CloseDeliveryArmClamp(){
        DeliveryArmClampSolenoid.set(Value.kForward);
        DeliveryArmClampOpen = false;
    } 

    
    public void ToggleDeliveryArmClamp(){

        if(DeliveryArmClampOpen == true){
            DeliveryArmClampOpen = false;
        }

        else if(DeliveryArmClampOpen == false){
            DeliveryArmClampOpen = true;
        }

        DeliveryArmClampSolenoid.toggle();
    }


    public void Stop(){
        stopped = true;
        StopSolenoid.set(Value.kForward);
    }


    public void Release(){
        stopped = false;
        StopSolenoid.set(Value.kForward);
    }

    public void ToggleStop(){
        if(stopped == true){
            stopped = false;
        }

        else if(stopped == false){
            stopped = true;
        }

        DeliveryArmClampSolenoid.toggle();
    }


    public double GetDeliveryArmPosition(){
        return extendingArmMotor.getSelectedSensorPosition();
    }
    
    
    public void SetDeliveryArmSpeed(double speed){
        extendingArmMotor.set(ControlMode.PercentOutput, speed);
        // something is needed to tell the motor to stop
    }
}
