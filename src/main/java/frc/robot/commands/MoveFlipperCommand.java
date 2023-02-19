// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.FlipperSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveFlipperCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  private final FlipperSubsystem m_flipperSubsystem;
  
  // private final boolean isRaised;
  private final double position;
  private  boolean lowering;

  /**   *
   * @param subsystem The subsystem used by this command.
   */
  public MoveFlipperCommand(double position) {
    
    this.m_flipperSubsystem = RobotContainer.m_flipperSubsystem;
    this.position = position;


    addRequirements(m_flipperSubsystem);
  }

  @Override
  public void initialize() {
    if (position > m_flipperSubsystem.GetFlipperPosition()){
        lowering = true;
        m_flipperSubsystem.SetFlipperSpeed(-0.5);
    }
    else if (position < m_flipperSubsystem.GetFlipperPosition()){
      lowering = false;
      m_flipperSubsystem.SetFlipperSpeed(0.5);
    }

    else{
      System.out.println("Error in MoveFlipperCommand initialize()");
    }
  }
 
  @Override
  public void execute() {
    
  }

  @Override
  public void end(boolean interrupted) {
    m_flipperSubsystem.SetFlipperSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return (lowering && m_flipperSubsystem.GetFlipperPosition() >= position) || 
    (!lowering && m_flipperSubsystem.GetFlipperPosition() <= position );
  }
}