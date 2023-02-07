// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.FlipperSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FlipperCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  private final FlipperSubsystem m_armSubsystem;
  
  private final boolean isRaised;
  private final boolean isOpen;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public FlipperCommand(boolean isRaisedParameter, boolean isOpenParameter) {
    
    m_armSubsystem = RobotContainer.m_flipperSubsystem;
    isRaised = isRaisedParameter;
    isOpen = isOpenParameter;


    addRequirements(m_armSubsystem);
  }

  @Override
  public void initialize() {
    System.out.println("Raise Lower Arm Command Initialized");
  }
 
  @Override
  public void execute() {
    
    if(isRaised == true){
        m_armSubsystem.RaiseFlipperArm();
    }

    if(isRaised == false){
        m_armSubsystem.LowerFlipperArm();
    }

    else{
        System.out.println("No value for FlipperArmCommands");
    }



    if(isOpen == true){
        m_armSubsystem.OpenFlipperClamp();
    }

    if(isOpen == false){
        m_armSubsystem.CloseFlipperClamp();
    }

    else{
        System.out.println("No value for the FlipperCommands");
    }

  }

  @Override
  public void end(boolean interrupted) {
    ;
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}