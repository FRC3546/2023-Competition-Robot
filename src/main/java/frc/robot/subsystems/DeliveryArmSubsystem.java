// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DeliveryArmSubsystem extends SubsystemBase{

    private DoubleSolenoid DeliveryArmClampSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 6, 7);

    private boolean DeliveryArmClampOpen = false;

    private DoubleSolenoid StopSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 4, 5);
    private boolean stopped;

    private TalonSRX extendingArmMotor = new TalonSRX(1);
    //private boolean deliveryArmExtended = false;

    
    public DeliveryArmSubsystem() {
        extendingArmMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    }


    public void OpenDeliveryArmClamp(){
        DeliveryArmClampSolenoid.set(Value.kForward);
        DeliveryArmClampOpen = true;
    }


    public void CloseDeliveryArmClamp(){
        DeliveryArmClampSolenoid.set(Value.kReverse);
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
        StopSolenoid.set(Value.kReverse);
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

    @Override
    public void periodic() {

        if(GetDeliveryArmPosition() < Constants.deliveryArmFullyExtended && 
        GetDeliveryArmPosition() > Constants.deliveryArmFullyRetracted){
            extendingArmMotor.set(ControlMode.PercentOutput, RobotContainer.m_codriverController.getY());
            System.out.println("In bounds");
        }
        else if(GetDeliveryArmPosition() > Constants.deliveryArmFullyRetracted && RobotContainer.m_codriverController.getY() > 0.1){
            extendingArmMotor.set(ControlMode.PercentOutput, RobotContainer.m_codriverController.getY());
            System.out.println("");
        }
        else if(GetDeliveryArmPosition() < Constants.deliveryArmFullyExtended && RobotContainer.m_secondCodriverController.getY() < -.1){
            extendingArmMotor.set(ControlMode.PercentOutput, RobotContainer.m_codriverController.getY());
            System.out.println("");
        }
        else{
            extendingArmMotor.set(ControlMode.PercentOutput, RobotContainer.m_codriverController.getY());
            System.out.println("trying to do bad things");
            //stop();
        }
        
        

    }
}
