package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;


import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

import frc.robot.subsystems.FlipperSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.math.kinematics.ChassisSpeeds;


import java.util.function.DoubleSupplier;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class FlipperArmCommand extends CommandBase{
    
        private final DoubleSupplier flipperMotorValue;
        private FlipperSubsystem m_flipperSubsystem = RobotContainer.m_flipperSubsystem;

    public FlipperArmCommand(DoubleSupplier flipperMotorValue){
        this.flipperMotorValue = flipperMotorValue;
        addRequirements(m_flipperSubsystem);
    }

    @Override
    public void execute() {
            if((m_flipperSubsystem.GetFlipperPosition() < Constants.flipperArmDown && 
            m_flipperSubsystem.GetFlipperPosition() > Constants.flipperArmUp)){
                m_flipperSubsystem.SetFlipperSpeed(flipperMotorValue.getAsDouble());
                // System.out.println("In bounds");
            }
            else if(m_flipperSubsystem.GetFlipperPosition() > Constants.flipperArmDown && RobotContainer.m_flipperController.getY() > 0.1){
                m_flipperSubsystem.SetFlipperSpeed(flipperMotorValue.getAsDouble());
                // System.out.println("Too Low out of bounds Increasing");
            }
            else if(m_flipperSubsystem.GetFlipperPosition() < Constants.flipperArmUp && RobotContainer.m_flipperController.getY() < -.1){
                m_flipperSubsystem.SetFlipperSpeed(flipperMotorValue.getAsDouble());
                // System.out.println("Too High out of bounds Increasing");
            }
            else{
                m_flipperSubsystem.SetFlipperSpeed(0);
                // System.out.println("trying to do bad things");
            }
    }
}