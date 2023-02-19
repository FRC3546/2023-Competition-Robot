// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.FlipperSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class JoystickMoveFlipperCommand extends CommandBase{
    
  private final FlipperSubsystem m_flipperSubsystem;

  private double motorSpeed;

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public JoystickMoveFlipperCommand(double motorSpeed) {
    m_flipperSubsystem = RobotContainer.m_flipperSubsystem;    
    this.motorSpeed = motorSpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_flipperSubsystem);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {

    m_flipperSubsystem.SetFlipperSpeed(motorSpeed);

  }

  @Override
  public void end(boolean interrupted) {
    m_flipperSubsystem.SetFlipperSpeed(0);
  }

  @Override
  public boolean isFinished() {
    
    return false;
  }
}
