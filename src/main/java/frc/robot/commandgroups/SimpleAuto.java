package frc.robot.commandgroups;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.Robot;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import frc.robot.commands.DriveCommand;
import frc.robot.commands.MoveFlipperCommand;

import frc.robot.subsystems.FlipperSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;

import frc.robot.commands.PauseCommand;


public class SimpleAuto extends SequentialCommandGroup{

    DoubleSupplier x = () -> 0;
    DoubleSupplier y = () -> .3;
    DoubleSupplier rot = () -> 0;


    public SimpleAuto(){
        

        addCommands(
            new MoveFlipperCommand(0)
        
        );
        
        


    }

}