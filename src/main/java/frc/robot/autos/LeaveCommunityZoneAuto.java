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
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;



public class LeaveCommunityZoneAuto extends SequentialCommandGroup{

    PathPlannerTrajectory traj2 = PathPlanner.generatePath(
    new PathConstraints(4, 3), 
    new PathPoint(new Translation2d(1.0, 1.0), Rotation2d.fromDegrees(0), Rotation2d.fromDegrees(0)), // position, heading(direction of travel), holonomic rotation
    new PathPoint(new Translation2d(3.0, 1.0), Rotation2d.fromDegrees(0), Rotation2d.fromDegrees(-90) // position, heading(direction of travel), holonomic rotation
    ));

    public LeaveCommunityZoneAuto(){
        
            addCommands(
                new InstantCommand(() -> 
                RobotContainer.m_drivetrainSubsystem.followTrajectoryCommand(traj2, isFinished()))
                );
    
    }

}