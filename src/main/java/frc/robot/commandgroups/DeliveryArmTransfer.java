package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.Robot;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import frc.robot.commands.PauseCommand;

// 1. Opens the delivery arm clamp
// 2. Opens the flipper arm to transfer
// 3. Pauses to allow everything to open
// 4. closes the deviery arm clamp to pick up the gamepiece

public class DeliveryArmTransfer extends SequentialCommandGroup{


    public DeliveryArmTransfer(){
        
            addCommands(
                new InstantCommand(() -> RobotContainer.m_deliverySubsystem.CloseDeliveryArmClamp()),
                new InstantCommand(() -> RobotContainer.m_flipperSubsystem.OpenFlipperClamp())
            );
    
    }

}