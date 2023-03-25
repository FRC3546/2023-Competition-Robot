package frc.robot.commandgroups;

import java.time.Instant;

import javax.naming.PartialResultException;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.Robot;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.ExtendDeliveryArmCommand;
import frc.robot.commands.MoveFlipperCommand;
import frc.robot.commands.PauseCommand;



public class VictoryLEDCommandgroup extends SequentialCommandGroup{


    public VictoryLEDCommandgroup(){
        
            addCommands(
                new InstantCommand(() -> RobotContainer.m_ledSubsystem.LEDBlue()),
                new PauseCommand(0)
            );
    
    }

}