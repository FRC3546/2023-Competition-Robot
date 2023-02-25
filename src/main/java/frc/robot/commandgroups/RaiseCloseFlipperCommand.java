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

// 1. The delivery arm needs to be open so the flipper doesn't hit it on the way up when holding gamepiece
// 2. The flipper closes and is holding the gamepiece
// 3. There is a pause so we know the gamepiece is indeed contained and will not fall out
// 4. The flipper raises to the up position

public class RaiseCloseFlipperCommand extends SequentialCommandGroup{

    public RaiseCloseFlipperCommand(){
        

        addCommands(
            new InstantCommand(() -> RobotContainer.m_deliverySubsystem.OpenDeliveryArmClamp()),
            new InstantCommand(() -> RobotContainer.m_flipperSubsystem.CloseFlipperClamp()), // closes flipper
            new PauseCommand(0.5),
            new MoveFlipperCommand(Constants.flipperArmUp) // raises flipper
        );
        


    }

}