// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.DeliveryArmSubsystem;
import frc.robot.Constants;
import frc.robot.autos.DeployGamepieceAuto;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static CommandBase exampleAuto(DeliveryArmSubsystem subsystem) {
    return Commands.sequence(new ExtendDeliveryArmCommand(100));
  }

  public static CommandBase moveAuto(){
    return new DeployGamepieceAuto(Constants.cubeMiddle, 100);
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
