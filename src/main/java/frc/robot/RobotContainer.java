// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.sql.Driver;
import java.util.HashMap;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import com.swervedrivespecialties.swervelib.SwerveModule;

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
import frc.robot.commandgroups.DeliveryArmTransfer;
// command groups
import frc.robot.commandgroups.LowerOpenFlipperCommand;
import frc.robot.commandgroups.ResetGyroResetEncoders;

// commands
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExtendDeliveryArmCommand;
import frc.robot.commands.MoveFlipperCommand;
import frc.robot.commands.JoystickExtendDeliveryArmCommand;
import frc.robot.commands.JoystickMoveFlipperCommand;

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

  SendableChooser<List<PathPlannerTrajectory>> autoChooser = new SendableChooser<>();
  SwerveAutoBuilder autoBuilder;
  List<PathPlannerTrajectory> selectedAuto;
  SendableChooser<String> AllianceChooser = new SendableChooser<>();

  HashMap<String, Command> pathPlannerEventMap = new HashMap<>();

  // subsystem declarations
  public static final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  public static final FlipperSubsystem m_flipperSubsystem = new FlipperSubsystem();
  public static final DeliveryArmSubsystem m_deliverySubsystem = new DeliveryArmSubsystem();

  // controllers
  public final XboxController m_driverController = new XboxController(OperatorConstants.kDriverControllerPort);
  public static final Joystick m_codriverController = new Joystick(OperatorConstants.kcodriverControllerPort);
  public static final Joystick m_secondCodriverController = new Joystick(OperatorConstants.ksecondCodriverControllerPort);

  public RobotContainer() {

    m_drivetrainSubsystem.setGyroOffset(0);

    m_deliverySubsystem.ZeroEncoder();

    m_drivetrainSubsystem.register();
    m_flipperSubsystem.register();
    

    // Teleop drivetrain movement
    m_drivetrainSubsystem.setDefaultCommand(new DriveCommand(
            m_drivetrainSubsystem,
            () -> -modifyAxis(m_driverController.getRawAxis(0)) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(m_driverController.getRawAxis(1)) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(-m_driverController.getRawAxis(2)) * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND
    ));


    configureBindings();

    SmartDashboard.putData("Auto Chooser", autoChooser);

    createAutoBuilder();
    generatePathPlannerGroups();
  }

 
  private void configureBindings() {

    // driver drivetrain buttons
    new JoystickButton(m_codriverController, 11)
      .onTrue(new ResetGyroResetEncoders());



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

    new JoystickButton(m_codriverController, 2)
      .onTrue(new DeliveryArmTransfer());




    new JoystickButton(m_secondCodriverController, 1)
      .onTrue(new InstantCommand(() -> m_deliverySubsystem.ToggleDeliveryArmClamp()));

    new JoystickButton(m_secondCodriverController, 2)
      .onTrue(new InstantCommand(() -> m_deliverySubsystem.OpenDeliveryArmClamp()));
    
    new JoystickButton(m_secondCodriverController, 3)
      .onTrue(new InstantCommand(() -> m_deliverySubsystem.CloseDeliveryArmClamp()));
    
    new JoystickButton(m_secondCodriverController, 4)
      .onTrue(new ExtendDeliveryArmCommand(20000.0));


  }

  private Command generateAutoWithPathPlanner() {
    return autoBuilder.fullAuto(autoChooser.getSelected());
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    
    //return Autos.moveAuto();
    return generateAutoWithPathPlanner();
  }

  private void generatePathPlannerGroups(){
    
    List<PathPlannerTrajectory> testDrive = PathPlanner.loadPathGroup("PickAndPlace", new PathConstraints(DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND, 2));

    autoChooser.setDefaultOption("test path", testDrive);
  }

  private void createAutoBuilder() {

    pathPlannerEventMap = new HashMap<>();

    autoBuilder = new SwerveAutoBuilder(

        m_drivetrainSubsystem::getPose, // Pose2d supplier
        m_drivetrainSubsystem::resetOdometry, // Pose2d consumer, used to reset odometry at the beginning of auto
        m_drivetrainSubsystem.getKinematics(), // SwerveDriveKinematics
        new PIDConstants(0.0, 0.0, 0.0), // PID constants to correct for translation error (used to
                                                              // create the X and Y PID controllers)
        new PIDConstants(0.0, 0.0, 0.0), // PID constants to correct for rotation error (used to
                                                                 // create the rotation controller)
        m_drivetrainSubsystem::drive, // Module states consumer used to output to the drive subsystem
        pathPlannerEventMap,
        true, // Should the path be automatically mirrored depending on alliance color.
              // Optional, defaults to true
        m_drivetrainSubsystem // The drive subsystem. Used to properly set the requirements of path following
                              // commands
    );
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