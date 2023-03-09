package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.Robot;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commandgroups.DeliveryArmTransfer;
import frc.robot.commands.DeliveryArmCommand;
import frc.robot.commands.ExtendDeliveryArmCommand;
import frc.robot.commands.PauseCommand;
import frc.robot.subsystems.DeliveryArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.FlipperSubsystem;

import frc.robot.commands.DriveCommand;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import java.util.function.DoubleSupplier;



public class DeployGamepieceAndLeaveAndBalanceAuto extends SequentialCommandGroup{

    DoubleSupplier moveForward = () -> 0.2;

    DoubleSupplier x = () -> 0;
    DoubleSupplier y = () -> -0.2;
    DoubleSupplier rot = () -> 0;



    public DeployGamepieceAndLeaveAndBalanceAuto(double deployPosition, double endPosition){
        
            addCommands(
                new DeliveryArmTransfer(),
                new PauseCommand(0.9),
                new ExtendDeliveryArmCommand(deployPosition),
                
                new ParallelDeadlineGroup(
                    new PauseCommand(1),
                    new DriveCommand(RobotContainer.m_drivetrainSubsystem, moveForward, x, rot)
                ),

                new InstantCommand(() -> RobotContainer.m_deliverySubsystem.OpenDeliveryArmClamp()),
                new PauseCommand(0.5),
                new InstantCommand(() -> RobotContainer.m_flipperSubsystem.CloseFlipperClamp()),
                new ExtendDeliveryArmCommand(endPosition),
                
                new ParallelDeadlineGroup(
                    new PauseCommand(2.5), 
                    new DriveCommand(RobotContainer.m_drivetrainSubsystem, y, x, rot))
                
                );

    
    }

}