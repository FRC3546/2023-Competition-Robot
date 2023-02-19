package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.Robot;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExtendDeliveryArmCommand;
import frc.robot.commands.MoveFlipperCommand;

import frc.robot.subsystems.FlipperSubsystem;

import edu.wpi.first.wpilibj.Timer;



public class DeliveryArmTransfer extends SequentialCommandGroup{

    private final Timer m_timer = new Timer();

    public DeliveryArmTransfer(){
        
            new InstantCommand(() -> RobotContainer.m_deliverySubsystem.OpenDeliveryArmClamp());
            
            m_timer.reset();
            m_timer.start();

            if (m_timer.get() < 1){
                ;
            }
            else{
                new InstantCommand(() -> RobotContainer.m_deliverySubsystem.CloseDeliveryArmClamp());
                m_timer.stop();
            }
    
    }

}