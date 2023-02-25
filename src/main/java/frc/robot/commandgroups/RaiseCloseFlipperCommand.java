package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.Robot;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import frc.robot.commands.DriveCommand;
import frc.robot.commands.MoveFlipperCommand;
import frc.robot.commands.PauseCommand;
import frc.robot.subsystems.FlipperSubsystem;


public class RaiseCloseFlipperCommand extends SequentialCommandGroup{

    public RaiseCloseFlipperCommand(){
        

        addCommands(
            new InstantCommand(() -> RobotContainer.m_flipperSubsystem.CloseFlipperClamp()), // closes flipper
            new PauseCommand(0.5),
            new MoveFlipperCommand(Constants.flipperArmUp) // raises flipper
        );
        


    }

}