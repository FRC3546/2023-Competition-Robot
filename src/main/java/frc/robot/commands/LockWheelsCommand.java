// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.wpilibj.Timer;


public class LockWheelsCommand extends CommandBase {
  DrivetrainSubsystem m_drive = RobotContainer.m_drivetrainSubsystem;
  private final Timer timer = new Timer();

  /** Creates a new sturdyBaseCommand. */
  public LockWheelsCommand() {

    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.backLeftModule.set(0, -45);
    m_drive.backRightModule.set(0, 45);
    m_drive.frontRightModule.set(0, -45);
    m_drive.frontLeftModule.set(0, 45);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    timer.reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    // return timer.get() >= 1;
  }
}