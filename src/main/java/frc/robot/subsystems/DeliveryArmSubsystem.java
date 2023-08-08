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

    private DoubleSolenoid DeliveryArmClampSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 6, 7);

    private int DeliveryArmClampOpen; // 1=true, 0=false, 2=off

    private DoubleSolenoid StopSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 4, 5);
    private int stopped; // 1=true, 0=false, 2=off

    private TalonSRX extendingArmMotor = new TalonSRX(41);
    //private boolean deliveryArmExtended = false;

    
    public DeliveryArmSubsystem() {
        Stop();

        extendingArmMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    }


    public void OpenDeliveryArmClamp(){
        DeliveryArmClampSolenoid.set(Value.kForward);
        // DeliveryArmClampOpen = 1;
    }


    public void CloseDeliveryArmClamp(){
        DeliveryArmClampSolenoid.set(Value.kReverse);
        // DeliveryArmClampOpen = 0;
    } 

    
    public void ToggleDeliveryArmClamp(){

        // if(DeliveryArmClampOpen == 1){
        //     DeliveryArmClampOpen = 0;

        // }

        // else if(DeliveryArmClampOpen == 0){
        //     DeliveryArmClampOpen = 1;
        // }

        if (DeliveryArmClampOpen == 1 || DeliveryArmClampOpen == 0){
            DeliveryArmClampSolenoid.toggle();
        }
        else{
            OpenDeliveryArmClamp();            
        }


    }


    public void Stop(){
        // stopped = 1;
        StopSolenoid.set(Value.kReverse);
    }


    public void Release(){
        // stopped = 0;
        StopSolenoid.set(Value.kForward);
    }

    public void ToggleStop(){
        // if(stopped == 1){
        //     stopped = 0;
        // }

        // else if(stopped == 0){
        //     stopped = 1;
        // }
        StopSolenoid.toggle();

    }


    public double GetDeliveryArmPosition(){
        return extendingArmMotor.getSelectedSensorPosition();
    }
    
    
    public void SetDeliveryArmSpeed(double speed){
        extendingArmMotor.set(ControlMode.PercentOutput, speed);
        // something is needed to tell the motor to stop
    }

    public void ZeroEncoder(){
        extendingArmMotor.setSelectedSensorPosition(0);
    }

    @Override
    public void periodic() {

        if (StopSolenoid.get() == Value.kReverse){
            stopped = 1;
        }
        else if(StopSolenoid.get() == Value.kForward){
            stopped = 0;
        }
        else if(StopSolenoid.get() == Value.kOff){
            stopped = 2;
        }

        if (DeliveryArmClampSolenoid.get() == Value.kReverse){
            DeliveryArmClampOpen = 0;
        }
        else if(DeliveryArmClampSolenoid.get() == Value.kForward){
            DeliveryArmClampOpen = 1;
        }
        else if (DeliveryArmClampSolenoid.get() == Value.kOff){
            DeliveryArmClampOpen = 2;

        }

        // System.out.println(extendingArmMotor.getSelectedSensorPosition());
        
        // if(GetDeliveryArmPosition() < Constants.deliveryArmFullyExtended && 
        // GetDeliveryArmPosition() > Constants.deliveryArmFullyRetracted){
        //     if (Math.abs(RobotContainer.m_secondCodriverController.getY()) > .1){
        //         Release();
        //         extendingArmMotor.set(ControlMode.PercentOutput, -RobotContainer.m_secondCodriverController.getY());
        //     }
        //     else{
        //         extendingArmMotor.set(ControlMode.PercentOutput, 0);
        //         Stop();
        //     }
        //     System.out.println("In bounds");
        // }
        // else if(GetDeliveryArmPosition() > Constants.deliveryArmFullyExtended && -RobotContainer.m_codriverController.getY() < -0.1){
        //     // Release();
        //     extendingArmMotor.set(ControlMode.PercentOutput, -RobotContainer.m_secondCodriverController.getY());
        //     System.out.println("Extended Past trying to return");
        // }
        // else if(GetDeliveryArmPosition() < Constants.deliveryArmFullyRetracted && -RobotContainer.m_secondCodriverController.getY() > .1){
        //     // Release();
        //     extendingArmMotor.set(ControlMode.PercentOutput, -RobotContainer.m_secondCodriverController.getY());
        //     System.out.println("Retracted Past Trying to Return");
        // }
        // else{
        //     extendingArmMotor.set(ControlMode.PercentOutput, 0);
        //     System.out.println("trying to do bad things");
        //     // Stop();
        // }
        
        // if(GetDeliveryArmPosition() < Constants.deliveryArmFullyExtended
        //  && GetDeliveryArmPosition() > Constants.deliveryArmFullyRetracted){
        //     if((Math.abs(RobotContainer.m_secondCodriverController.getY())) > .1){
        //         Release();
        //     extendingArmMotor.set(ControlMode.PercentOutput, -RobotContainer.m_secondCodriverController.getY());
        //     // System.out.println("Within Bounds");
        //     }
        //     else{
        //         extendingArmMotor.set(ControlMode.PercentOutput, 0);
        //         Stop();
        //     }
        //  }

        // else if(GetDeliveryArmPosition() >= Constants.deliveryArmFullyExtended && -RobotContainer.m_secondCodriverController.getY() < -0.1){
        //     Release();
        //     extendingArmMotor.set(ControlMode.PercentOutput, -RobotContainer.m_secondCodriverController.getY());
        //     // System.out.println("Too far coming back");
        // }

        // else if(GetDeliveryArmPosition() <= Constants.deliveryArmFullyRetracted && -RobotContainer.m_secondCodriverController.getY() > 0.1){
        //     Release();
        //     extendingArmMotor.set(ControlMode.PercentOutput, -RobotContainer.m_secondCodriverController.getY());
        //     // System.out.println("Too short coming back");
        // }

        // else{
        //     extendingArmMotor.set(ControlMode.PercentOutput, 0);
        //     Stop();
        //     // System.out.println("Out of bounds");
        // }

    }
}
