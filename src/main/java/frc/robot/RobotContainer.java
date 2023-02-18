// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;

import frc.robot.subsystems.DeliveryArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.FlipperSubsystem;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

// command groups
import frc.robot.commandgroups.PickUpGamepieceCommand;
import frc.robot.commandgroups.ResetGyroResetEncoders;

// commands
import frc.robot.commands.DriveCommand;
import frc.robot.commands.MoveFlipperCommand;

// subsystems
import frc.robot.subsystems.DeliveryArmSubsystem;
import frc.robot.subsystems.DeliveryArmSubsystem;
import frc.robot.subsystems.FlipperSubsystem;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;


import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.Joystick;



public class RobotContainer {

  // Subsystem declarations
  public static final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  public static final FlipperSubsystem m_flipperSubsystem = new FlipperSubsystem();
  public static final DeliveryArmSubsystem m_deliverySubsystem = new DeliveryArmSubsystem();

  // controllers
  private final XboxController m_driverController = new XboxController(OperatorConstants.kDriverControllerPort);
  private final Joystick m_codriverController = new Joystick(OperatorConstants.kcodriverControllerPort);

  public RobotContainer() {

    m_drivetrainSubsystem.register();

    // Teleop drivetrain movement
    m_drivetrainSubsystem.setDefaultCommand(new DriveCommand(
            m_drivetrainSubsystem,
            () -> -modifyAxis(m_driverController.getRawAxis(1)) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(-m_driverController.getRawAxis(0)) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(-m_driverController.getRawAxis(2)) * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND
    ));



    if (OperatorConstants.kFlipperIsManual == true){
      m_flipperSubsystem.setDefaultCommand(new MoveFlipperCommand(m_codriverController.getY()));
    }
    else{
      ;
    }
    


    configureBindings();
  }

 
  private void configureBindings() {

    // driver drivetrain buttons
    new JoystickButton(m_driverController, OperatorConstants.kResetGyroEncoderButton)
      .onTrue(new ResetGyroResetEncoders());


    // codriver flipper buttons
    new JoystickButton(m_codriverController, OperatorConstants.kFlipperUpButton)
      .onTrue(new InstantCommand(() -> m_flipperSubsystem.OpenFlipperClamp()));
    
    new JoystickButton(m_codriverController, OperatorConstants.kFlipperDownButton)
      .onTrue(new InstantCommand(() -> m_flipperSubsystem.CloseFlipperClamp()));

    new JoystickButton(m_codriverController, OperatorConstants.kFlipperToggleButton)
      .toggleOnTrue(new PickUpGamepieceCommand());

    new JoystickButton(m_codriverController, OperatorConstants.kFlipperDisableSafety)
      .onTrue(new InstantCommand(() -> OperatorConstants.kFlipperIsManual = true));
    
    new JoystickButton(m_codriverController, OperatorConstants.kFlipperEnableSafety)
      .onTrue(new InstantCommand(() -> OperatorConstants.kFlipperIsManual = false));

    
      
    
    


  }


  // update our smart dashboard
  public void UpdateSmartdashboard(){
    m_flipperSubsystem.UpdateSmartdashboard();
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