// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DeliveryArmSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PrinterCommand extends CommandBase{
    


  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public PrinterCommand() {
    ;
  }

  @Override
  public void initialize() {
    System.out.println("initialized");
  }

  @Override
  public void execute() {
    System.out.println("executing");
  }

  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    
    return false;
  }
}
