package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

import frc.robot.RobotContainer;
import frc.robot.commandgroups.DeliveryArmTransfer;
import frc.robot.commands.ExtendDeliveryArmCommand;
import frc.robot.commands.PauseCommand;

import java.util.function.DoubleSupplier;
import frc.robot.commands.DriveCommand;


public class DeployGamepieceAuto extends SequentialCommandGroup{


    DoubleSupplier moveForward = () -> -0.1;
    DoubleSupplier moveBackward = () -> 0.1;

    DoubleSupplier x = () -> 0;
    DoubleSupplier y = () -> 0;
    DoubleSupplier rot = () -> 0;

    public DeployGamepieceAuto(double deployPosition, double endPosition){
        

                addCommands(
                new DeliveryArmTransfer(),
                new PauseCommand(0.9),
                new ExtendDeliveryArmCommand(deployPosition),
                
                // new ParallelDeadlineGroup(
                //     new PauseCommand(1),
                //     new DriveCommand(RobotContainer.m_drivetrainSubsystem, moveForward, x, rot)
                // ),

                new InstantCommand(() -> RobotContainer.m_deliverySubsystem.OpenDeliveryArmClamp()),
                new PauseCommand(0.5),
                new InstantCommand(() -> RobotContainer.m_flipperSubsystem.CloseFlipperClamp()),
                new ExtendDeliveryArmCommand(endPosition)
                
                );
    
    }

}