package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DeliveryArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;

import java.util.function.DoubleSupplier;

import frc.robot.RobotContainer;
import frc.robot.Constants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
    

public class DeliveryArmCommand extends CommandBase{
    private final DoubleSupplier armMotorValue;
    private DeliveryArmSubsystem m_deliveryArmSubsystem = RobotContainer.m_deliverySubsystem;

    public DeliveryArmCommand(DoubleSupplier armMotorValue) {
        this.armMotorValue = armMotorValue;
        addRequirements(m_deliveryArmSubsystem);
    }

    public void execute(){
        if(m_deliveryArmSubsystem.GetDeliveryArmPosition() < Constants.deliveryArmFullyExtended
         && m_deliveryArmSubsystem.GetDeliveryArmPosition() > Constants.deliveryArmFullyRetracted){
            if((Math.abs(RobotContainer.m_armController.getY())) > .1){
                m_deliveryArmSubsystem.Release();
                m_deliveryArmSubsystem.SetDeliveryArmSpeed(-armMotorValue.getAsDouble());
            // System.out.println("Within Bounds");
            }
            else{
                m_deliveryArmSubsystem.SetDeliveryArmSpeed(0);
                m_deliveryArmSubsystem.Stop();
            }
         }

        else if(m_deliveryArmSubsystem.GetDeliveryArmPosition() >= Constants.deliveryArmFullyExtended && -RobotContainer.m_armController.getY() < -0.1){
            m_deliveryArmSubsystem.Release();
            m_deliveryArmSubsystem.SetDeliveryArmSpeed(-armMotorValue.getAsDouble());
            // System.out.println("Too far coming back");
        }

        else if(m_deliveryArmSubsystem.GetDeliveryArmPosition() <= Constants.deliveryArmFullyRetracted && -RobotContainer.m_armController.getY() > 0.1){
            m_deliveryArmSubsystem.Release();
            m_deliveryArmSubsystem.SetDeliveryArmSpeed(-armMotorValue.getAsDouble());
            // System.out.println("Too short coming back");
        }

        else{
            m_deliveryArmSubsystem.SetDeliveryArmSpeed(0);
            m_deliveryArmSubsystem.Stop();
            // System.out.println("Out of bounds");
        }
    }

}


