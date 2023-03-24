// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.subsystems.DeliveryArmSubsystem;
import frc.robot.Constants;
import frc.robot.autos.DeployGamepieceAndBalanceAuto;
import frc.robot.autos.DeployGamepieceAndLeaveAuto;
import frc.robot.autos.DeployGamepieceAuto;
// import frc.robot.commandgroups.BalanceAndLockCommandgroup;
import frc.robot.commands.BalanceCommand;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static CommandBase exampleAuto(DeliveryArmSubsystem subsystem) {
    return Commands.sequence(new ExtendDeliveryArmCommand(100));
  }

  public static CommandBase moveAuto(){
    return new DeployGamepieceAndLeaveAuto(Constants.coneHigh, 100);
    //return new RotateToAngleCommand(180, 15);
    //return new RotateToAngleCommand(179, 15);
    // return new balancecommandgroup();
    //return new ParallelDeadlineGroup(new PauseCommand(1), new DriveCommand(null, () -> -0.1, null, null));
    //return new BalanceAndLockCommandgroup();
    // return new DeployGamepieceAndLeaveAndBalanceAuto(Constants.coneHigh, 100);
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
