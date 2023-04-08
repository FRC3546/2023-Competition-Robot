package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.RobotContainer;

public class ResetGyroResetEncoders extends SequentialCommandGroup{

    public ResetGyroResetEncoders(){
        

        addCommands(
        new InstantCommand(() -> RobotContainer.m_drivetrainSubsystem.zeroGyroscope()));
        
        


    }

}