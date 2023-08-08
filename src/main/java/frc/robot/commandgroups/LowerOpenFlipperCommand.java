package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Constants;
import frc.robot.RobotContainer;

import frc.robot.commands.MoveFlipperCommand;


// The flipper begins to open 
// The flipper moves to the down position and the flipper should be open

public class LowerOpenFlipperCommand extends SequentialCommandGroup{

    public LowerOpenFlipperCommand(){
        

        addCommands(
            new InstantCommand(() -> RobotContainer.m_flipperSubsystem.OpenFlipperClamp()), // Opens flipper
            new MoveFlipperCommand(Constants.flipperArmDown) // Lowers flipper
        );
        


    }

}