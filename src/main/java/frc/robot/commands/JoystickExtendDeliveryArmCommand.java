// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.DeliveryArmSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class JoystickExtendDeliveryArmCommand extends CommandBase{
    
  private final DeliveryArmSubsystem m_deliveryArmSubsystem;

  private double motorSpeed;
  private boolean run;

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public JoystickExtendDeliveryArmCommand(double motorSpeed) {
    m_deliveryArmSubsystem = RobotContainer.m_deliverySubsystem;    
    this.motorSpeed = motorSpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_deliveryArmSubsystem);
  }

  @Override
  public void initialize() {

    run = false;

    if (OperatorConstants.kFlipperIsManual != true){
        run = false;
        m_deliveryArmSubsystem.Release();
    }
    else{
        run = true;
    }
  }

  @Override
  public void execute() {

    if(motorSpeed != 0){
        m_deliveryArmSubsystem.SetDeliveryArmSpeed(motorSpeed);
    }
    else{
        m_deliveryArmSubsystem.Stop();
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_deliveryArmSubsystem.SetDeliveryArmSpeed(0);
    m_deliveryArmSubsystem.Stop();
  }

  @Override
  public boolean isFinished() {
    
    return run;
  }
}
