package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.RobotContainer;

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