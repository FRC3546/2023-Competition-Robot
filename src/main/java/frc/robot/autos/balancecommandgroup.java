package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.Robot;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commandgroups.DeliveryArmTransfer;
import frc.robot.commands.BalanceCommand;
import frc.robot.commands.DeliveryArmCommand;
import frc.robot.commands.ExtendDeliveryArmCommand;
import frc.robot.commands.LockWheelsCommand;
import frc.robot.commands.PauseCommand;
import frc.robot.commands.RotateToAngleCommand;
import frc.robot.subsystems.DeliveryArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.FlipperSubsystem;

import frc.robot.commands.DriveCommand;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import java.util.function.DoubleSupplier;

import frc.robot.RobotContainer;



public class balancecommandgroup extends SequentialCommandGroup{

    // DoubleSupplier moveForward = () -> 0.2;

    DoubleSupplier x = () -> 0;
    DoubleSupplier y = () -> -0.2;
    DoubleSupplier rot = () -> 0;



    public balancecommandgroup(){
        
            addCommands(
                
                new BalanceCommand(),
                new DriveCommand(RobotContainer.m_drivetrainSubsystem, y, x, rot)
            );

                



    
    }

}