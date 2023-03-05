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

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;


public class LeaveCommunityZoneAuto extends SequentialCommandGroup{

    PathPlannerTrajectory path = PathPlanner.loadPath("LeaveCommunity", new PathConstraints(DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND, 2));

    public LeaveCommunityZoneAuto(){
        
            addCommands(
                new InstantCommand(() -> 
                RobotContainer.m_drivetrainSubsystem.followTrajectoryCommand(path, isFinished()))
                );
    
    }

}