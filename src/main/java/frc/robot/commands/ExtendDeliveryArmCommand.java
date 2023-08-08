// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DeliveryArmSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

public class ExtendDeliveryArmCommand extends CommandBase{
    
  private final DeliveryArmSubsystem m_deliveryArmSubsystem = RobotContainer.m_deliverySubsystem;

  private double endPosition;
  private boolean isLower;

  private final Timer time = new Timer();

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ExtendDeliveryArmCommand(double endPosition) {
    System.out.println("Created extend delivery arm command");  
    this.endPosition = endPosition;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.m_deliverySubsystem);
  }

  @Override
  public void initialize() {
    System.out.println("initializing Extend Delivery Arm Command");
    m_deliveryArmSubsystem.Release();

    if (m_deliveryArmSubsystem.GetDeliveryArmPosition() < endPosition){
        isLower = true;
        System.out.println("set speed .5");
        m_deliveryArmSubsystem.SetDeliveryArmSpeed(1);
    }
    else if (m_deliveryArmSubsystem.GetDeliveryArmPosition() > endPosition){
      isLower = false;
      System.out.println("set speed -.5");
      m_deliveryArmSubsystem.SetDeliveryArmSpeed(-1);
    }
    else{
      System.out.println("Error in ExtendDeliveryArmCommand initialize()");
    }

    time.start();
  }

  @Override
  public void execute() {
    if (Math.abs(m_deliveryArmSubsystem.GetDeliveryArmPosition() - endPosition) > 500)
      time.reset();


  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("Extend Delviery arm ended");
    m_deliveryArmSubsystem.SetDeliveryArmSpeed(0);
    m_deliveryArmSubsystem.Stop();
  }

  @Override
  public boolean isFinished() {
    
    return ((isLower && m_deliveryArmSubsystem.GetDeliveryArmPosition() > endPosition) || 
    (!isLower && m_deliveryArmSubsystem.GetDeliveryArmPosition() < endPosition) || 
    (time.get() > 2));
  }
}
