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
import edu.wpi.first.wpilibj2.command.button.Trigger;

// command groups
import frc.robot.commandgroups.LowerOpenFlipperCommand;
import frc.robot.commandgroups.ResetGyroResetEncoders;

// commands
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExtendDeliveryArmCommand;
import frc.robot.commands.MoveFlipperCommand;
import frc.robot.commands.JoystickExtendDeliveryArmCommand;
import frc.robot.commands.JoystickMoveFlipperCommand;
import frc.robot.commands.PrinterCommand;

// subsystems
import frc.robot.subsystems.DeliveryArmSubsystem;
import frc.robot.subsystems.FlipperSubsystem;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.Joystick;



public class RobotContainer {

  // subsystem declarations
  public static final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  public static final FlipperSubsystem m_flipperSubsystem = new FlipperSubsystem();
  public static final DeliveryArmSubsystem m_deliverySubsystem = new DeliveryArmSubsystem();

  // controllers
  public final XboxController m_driverController = new XboxController(OperatorConstants.kDriverControllerPort);
  public static final Joystick m_codriverController = new Joystick(OperatorConstants.kcodriverControllerPort);
  public static final Joystick m_secondCodriverController = new Joystick(OperatorConstants.ksecondCodriverControllerPort);

  public RobotContainer() {

    m_deliverySubsystem.ZeroEncoder();

    m_drivetrainSubsystem.register();
    m_flipperSubsystem.register();

    // Teleop drivetrain movement
    m_drivetrainSubsystem.setDefaultCommand(new DriveCommand(
            m_drivetrainSubsystem,
            () -> -modifyAxis(m_driverController.getRawAxis(1)) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(-m_driverController.getRawAxis(0)) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(-m_driverController.getRawAxis(2)) * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND
    ));

    //m_deliverySubsystem.setDefaultCommand(new JoystickExtendDeliveryArmCommand(m_codriverController.getY()));

    // m_flipperSubsystem.setDefaultCommand(new JoystickMoveFlipperCommand(-(m_codriverController.getY())));


    // if (OperatorConstants.kFlipperIsManual == true && OperatorConstants.kArmIsManual != true){
      
    //   OperatorConstants.kArmIsManual = false;
    // }
    // else{
      
    //   OperatorConstants.kFlipperIsManual = false;
    // }
    


    configureBindings();
  }

 
  private void configureBindings() {

    // driver drivetrain buttons
    new JoystickButton(m_codriverController, 11)
      .onTrue(new ResetGyroResetEncoders());


    // // codriver flipper buttons
    // new JoystickButton(m_codriverController, OperatorConstants.kFlipperUpButton)
    //   .onTrue(new InstantCommand(() -> m_flipperSubsystem.OpenFlipperClamp()));
    
    // new JoystickButton(m_codriverController, OperatorConstants.kFlipperDownButton)
    //   .onTrue(new InstantCommand(() -> m_flipperSubsystem.CloseFlipperClamp()));

    // new JoystickButton(m_codriverController, OperatorConstants.kFlipperToggleButton)
    //   .toggleOnTrue(new PickUpGamepieceCommand());

    // new JoystickButton(m_codriverController, OperatorConstants.kFlipperDisableSafety)
    //   .onTrue(new InstantCommand(() -> OperatorConstants.kFlipperIsManual = true));
    
    // new JoystickButton(m_codriverController, OperatorConstants.kFlipperEnableSafety)
    //   .onTrue(new InstantCommand(() -> OperatorConstants.kFlipperIsManual = false));

    // codriver arm buttons
    // new JoystickButton(m_codriverController, OperatorConstants.kArmDisableSafety)
    //   .onTrue(new InstantCommand(() -> OperatorConstants.kArmIsManual = true));

    // new JoystickButton(m_codriverController, 1)
    //   .onTrue(new InstantCommand(() -> m_deliverySubsystem.Stop()));

    // new JoystickButton(m_codriverController, 3)
    //   .onTrue(new InstantCommand(() -> m_flipperSubsystem.SetFlipperSpeed(0.3)));
    
    // new JoystickButton(m_codriverController, 5)
    //   .onTrue(new InstantCommand(() -> m_flipperSubsystem.SetFlipperSpeed(-0.3)));

    // flipper arm clamp
    new JoystickButton(m_codriverController, 6)
      .onTrue(new InstantCommand(() -> m_flipperSubsystem.OpenFlipperClamp()));

    new JoystickButton(m_codriverController, 4)
      .onTrue(new InstantCommand(() -> m_flipperSubsystem.CloseFlipperClamp()));

    // delivery arm clamp
    new JoystickButton(m_codriverController, 5)
      .onTrue(new InstantCommand(() -> m_deliverySubsystem.OpenDeliveryArmClamp()));
    
    new JoystickButton(m_codriverController, 3)
      .onTrue(new InstantCommand(() -> m_deliverySubsystem.CloseDeliveryArmClamp()));


    
    new JoystickButton(m_codriverController, 9)
      .onTrue(new MoveFlipperCommand(Constants.flipperArmDown));

    new JoystickButton(m_codriverController, 10)
      .onTrue(new MoveFlipperCommand(Constants.flipperArmUp));

    new JoystickButton(m_codriverController, 1)
      .onTrue(new InstantCommand(() -> m_flipperSubsystem.OpenFlipperClamp()))
      .onFalse(new InstantCommand(() -> m_flipperSubsystem.CloseFlipperClamp()));









    new JoystickButton(m_secondCodriverController, 1)
      .onTrue(new InstantCommand(() -> m_deliverySubsystem.ToggleDeliveryArmClamp()));

    new JoystickButton(m_secondCodriverController, 2)
      .onTrue(new InstantCommand(() -> m_deliverySubsystem.OpenDeliveryArmClamp()));
    
    new JoystickButton(m_secondCodriverController, 3)
      .onTrue(new InstantCommand(() -> m_deliverySubsystem.CloseDeliveryArmClamp()));
    
    new JoystickButton(m_secondCodriverController, 4)
      .onTrue(new ExtendDeliveryArmCommand(20000.0));

    new JoystickButton(m_secondCodriverController, 5).onTrue(new PrinterCommand());


  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    // An example command will be run in autonomous
    return Autos.moveAuto();
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