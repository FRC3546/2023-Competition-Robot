// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.FlipperSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeLinearActuatorCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  private final FlipperSubsystem m_armSubsystem;
  
  private final boolean isRaised;

  /**   *
   * @param subsystem The subsystem used by this command.
   */
  public IntakeLinearActuatorCommand(boolean isRaisedParameter) {
    
    m_armSubsystem = RobotContainer.m_flipperSubsystem;
    isRaised = isRaisedParameter;


    addRequirements(m_armSubsystem);
  }

  @Override
  public void initialize() {
    if (endPosition > m_deliveryArmSubsystem.GetDeliveryArmPosition()){
        isLower = true;
        m_deliveryArmSubsystem.SetDeliveryArmSpeed(.1);
    }
    else if (endPosition < m_deliveryArmSubsystem.GetDeliveryArmPosition()){
      isLower = false;
      m_deliveryArmSubsystem.SetDeliveryArmSpeed(-0.1);
    }

    else{
      System.out.println("Error in ExtendDeliveryArmCommand initialize()");
    }
  }
 
  @Override
  public void execute() {
    
    


  }

  @Override
  public void end(boolean interrupted) {
    ;
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}