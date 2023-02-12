// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

// Subsystems
import frc.robot.subsystems.FlipperSubsystem;
import frc.robot.subsystems.DeliveryArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;

// Commands
import frc.robot.commands.Autos;
import frc.robot.commands.DriveCommand;



public class RobotContainer {

  // Subsystem declarations
  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  public static final FlipperSubsystem m_flipperSubsystem = new FlipperSubsystem();
  public static final DeliveryArmSubsystem m_deliverySubsystem = new DeliveryArmSubsystem();

  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {

    // Teleop drivetrain movement
    m_drivetrainSubsystem.setDefaultCommand(new DriveCommand(
            m_drivetrainSubsystem,
            () -> -modifyAxis(m_driverController.getLeftX()) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(m_driverController.getLeftY()) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(m_driverController.getRightY()) * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND
    ));
    

    configureBindings();
  }

 
  private void configureBindings() {
   
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    // An example command will be run in autonomous
    return Autos.exampleAuto(m_deliverySubsystem);
  }

  private static double deadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  private static double modifyAxis(double value) {
    // Deadband
    value = deadband(value, 0.05);

    // Square the axis
    value = Math.copySign(value * value, value);

    return value;
  }
}