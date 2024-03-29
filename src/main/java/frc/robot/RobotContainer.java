// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.sql.Driver;
import java.util.HashMap;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import com.fasterxml.jackson.databind.JavaType;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import com.swervedrivespecialties.swervelib.SwerveModule;
import frc.robot.Constants.OperatorConstants;
import frc.robot.autos.DeployGamepieceAndBalanceAuto;
import frc.robot.autos.DeployGamepieceAndLeaveAndBalanceAuto;
import frc.robot.autos.DeployGamepieceAndLeaveAuto;
import frc.robot.autos.DeployGamepieceAuto;
import frc.robot.commands.Autos;
import frc.robot.commands.DaSoundOfDaPolice;
import frc.robot.commands.DeliveryArmCommand;
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
import edu.wpi.first.wpilibj.XboxController.Button;

// command groups
import frc.robot.commandgroups.LowerOpenFlipperCommand;
import frc.robot.commandgroups.ResetGyroResetEncoders;
// import frc.robot.commandgroups.ReturnToHomeCommandgroup;
// commands
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExtendDeliveryArmCommand;
import frc.robot.commands.MoveFlipperCommand;
import frc.robot.commands.RotateToAngleCommand;
import frc.robot.commands.JoystickExtendDeliveryArmCommand;
import frc.robot.commands.JoystickMoveFlipperCommand;
import frc.robot.commands.FlipperArmCommand;
import frc.robot.commands.LockWheelsCommand;

// subsystems
import frc.robot.subsystems.DeliveryArmSubsystem;
import frc.robot.subsystems.FlipperSubsystem;
import frc.robot.subsystems.LightEmUpSubsystem;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.Joystick;



public class RobotContainer {

  SendableChooser<List<PathPlannerTrajectory>> autoChooser = new SendableChooser<>();
  SwerveAutoBuilder autoBuilder;
  List<PathPlannerTrajectory> selectedAuto;
  SendableChooser<String> AllianceChooser = new SendableChooser<>();



  
  SendableChooser<Command> autos = new SendableChooser<>();
  SendableChooser<Double> placePosition = new SendableChooser<>();

  HashMap<String, Command> pathPlannerEventMap = new HashMap<>();

  // subsystem declarations
  public static final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  public static final FlipperSubsystem m_flipperSubsystem = new FlipperSubsystem();
  public static final DeliveryArmSubsystem m_deliverySubsystem = new DeliveryArmSubsystem();
  public static final LightEmUpSubsystem m_ledSubsystem = new LightEmUpSubsystem();

  // controllers
  public final XboxController m_driverController = new XboxController(OperatorConstants.kDriverControllerPort);
  public static final Joystick m_armController = new Joystick(OperatorConstants.kArmControllerPort);
  public static final Joystick m_flipperController = new Joystick(OperatorConstants.kFlipperControllerPort);


  public RobotContainer() {

    // m_drivetrainSubsystem.setGyroOffset(-90);

    m_deliverySubsystem.ZeroEncoder();

    m_drivetrainSubsystem.register();
    m_flipperSubsystem.register();
    

    // Teleop drivetrain movement
    m_drivetrainSubsystem.setDefaultCommand(new DriveCommand(
            m_drivetrainSubsystem,
            () -> -modifyAxis(-m_driverController.getRawAxis(1)) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(-m_driverController.getRawAxis(0)) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(-m_driverController.getRawAxis(2)) * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND
    ));

    m_deliverySubsystem.setDefaultCommand(new DeliveryArmCommand(() -> m_armController.getRawAxis(1), true));

    m_flipperSubsystem.setDefaultCommand(new FlipperArmCommand(() -> m_flipperController.getRawAxis(1)));


    configureBindings();

    placePosition.setDefaultOption("High Cube", Constants.cubeHigh);
    placePosition.addOption("High Cone", Constants.coneHigh);

    placePosition.addOption("Middle Cube", Constants.cubeMiddle);
    placePosition.addOption("Middle Cone", Constants.cubeMiddle);

    placePosition.addOption("Low Cube", Constants.cubeLow);
    placePosition.addOption("Low Cone", Constants.coneLow);

    autos.addOption("Score Cube", new DeployGamepieceAuto(placePosition.getSelected().doubleValue(), 100));
    autos.addOption("Score Cone", new DeployGamepieceAuto(placePosition.getSelected().doubleValue(), 100));

    autos.addOption("Score Cube and Leave", new DeployGamepieceAndLeaveAuto(placePosition.getSelected().doubleValue(), 100));
    autos.addOption("Score Cone and Leave", new DeployGamepieceAndLeaveAuto(placePosition.getSelected().doubleValue(), 100));

    autos.addOption("Score Cube and Balance", new DeployGamepieceAndBalanceAuto(placePosition.getSelected().doubleValue(), 100));
    autos.addOption("Score Cone and Balance", new DeployGamepieceAndBalanceAuto(placePosition.getSelected().doubleValue(), 100));

    autos.addOption("Score Cube and Leave and Balance", new DeployGamepieceAndLeaveAndBalanceAuto(placePosition.getSelected().doubleValue(), 100));
    autos.addOption("Score Cone and Leave and Balance", new DeployGamepieceAndLeaveAndBalanceAuto(placePosition.getSelected().doubleValue(), 100));


    SmartDashboard.putData("Deploy Position", placePosition);
    SmartDashboard.putData("Autonomous", autos);

    //SmartDashboard.putData("Auto Chooser", autoChooser);

    createAutoBuilder();
    generatePathPlannerGroups();
  }

 
  private void configureBindings() {


    new JoystickButton(m_driverController, 1)
      .whileTrue(new LockWheelsCommand());

    // new JoystickButton(m_driverController, 2)
    //   .whileTrue(new RotateToAngleCommand(180, 1));


    // driver drivetrain buttons
    

      new JoystickButton(m_flipperController, 1)
      .onTrue(new InstantCommand(() -> m_flipperSubsystem.OpenFlipperClamp()))
      .onFalse(new InstantCommand(() -> m_flipperSubsystem.CloseFlipperClamp()));

    new JoystickButton(m_flipperController, 2)
      .onTrue(new DeliveryArmTransfer());


    // new JoystickButton(m_flipperController, 10)
    //   .onTrue(new InstantCommand(() -> m_ledSubsystem.LEDPurple()));

    // new JoystickButton(m_flipperController, 9)
    //   .onTrue(new InstantCommand(() -> m_ledSubsystem.LEDYellow()));

    // new JoystickButton(m_flipperController, 8)
    //   .onTrue(new InstantCommand(() -> m_ledSubsystem.LEDRed()));

    // new JoystickButton(m_flipperController, 7)
    //   .onTrue(new InstantCommand(() -> m_ledSubsystem.LEDBlue()));

    new POVButton(m_flipperController, 180)
      .onTrue(new InstantCommand(() -> m_ledSubsystem.LEDPurple()));

    new POVButton(m_flipperController, 0)
      .onTrue(new InstantCommand(() -> m_ledSubsystem.LEDYellow()));

    new POVButton(m_flipperController, 90)
      .onTrue(new InstantCommand(() -> m_ledSubsystem.LEDRed()));

    new POVButton(m_flipperController, 270)
      .onTrue(new InstantCommand(() -> m_ledSubsystem.LEDBlue()));

    // new JoystickButton(m_flipperController, 7)
    //   .onTrue(new DaSoundOfDaPolice(1, 0, 0, 0, 0, 1, .5));


    new JoystickButton(m_flipperController, 4)
      .onTrue(new InstantCommand(() -> m_flipperSubsystem.CloseFlipperClamp()));

    new JoystickButton(m_flipperController, 6)
      .onTrue(new InstantCommand(() -> m_flipperSubsystem.OpenFlipperClamp()));


    

      new JoystickButton(m_flipperController, 11)
      .onTrue(new InstantCommand(() -> m_drivetrainSubsystem.zeroGyroscope()));

    // // delivery arm clamp
    // new JoystickButton(m_flipperController, 5)
    //   .onTrue(new InstantCommand(() -> m_deliverySubsystem.OpenDeliveryArmClamp()));
    
    // new JoystickButton(m_flipperController, 3)
    //   .onTrue(new InstantCommand(() -> m_deliverySubsystem.CloseDeliveryArmClamp()));


    
    // new JoystickButton(m_flipperController, 9)
    //   .onTrue(new MoveFlipperCommand(Constants.flipperArmDown));

    // new JoystickButton(m_flipperController, 10)
    //   .onTrue(new MoveFlipperCommand(Constants.flipperArmUp));





    new JoystickButton(m_flipperController, 1)
      .onTrue(new InstantCommand(() -> m_flipperSubsystem.OpenFlipperClamp()))
      .onFalse(new InstantCommand(() -> m_flipperSubsystem.CloseFlipperClamp()));

    new JoystickButton(m_flipperController, 2)
      .onTrue(new DeliveryArmTransfer());




    new JoystickButton(m_armController, 1)
      .onTrue(new InstantCommand(() -> m_deliverySubsystem.ToggleDeliveryArmClamp()));

    new JoystickButton(m_armController, 2)
      .onTrue(new InstantCommand(() -> m_deliverySubsystem.OpenDeliveryArmClamp()));
    
    new JoystickButton(m_armController, 3)
      .onTrue(new InstantCommand(() -> m_deliverySubsystem.CloseDeliveryArmClamp()));
    
    // new JoystickButton(m_armController, 4)
    //   .onTrue(new ExtendDeliveryArmCommand(20000.0));
    
    new JoystickButton(m_armController, 10)
      .onTrue(new DeliveryArmCommand(() -> m_armController.getRawAxis(1), false))
      .onFalse(new DeliveryArmCommand(() -> m_armController.getRawAxis(1), true))
      .onFalse(new InstantCommand(() -> m_deliverySubsystem.ZeroEncoder()));

    new JoystickButton(m_flipperController, 5)
      .onTrue(new MoveFlipperCommand(0.493));

    new JoystickButton(m_flipperController, 3)
      .onTrue(new MoveFlipperCommand(Constants.flipperHybrid));

    // new JoystickButton(m_flipperController, 7)
    //   .onTrue(new ReturnToHomeCommandgroup());
    
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


    return autos.getSelected();
    //return Autos.moveAuto();
    //return generateAutoWithPathPlanner();
  }

  private void generatePathPlannerGroups(){
    
    List<PathPlannerTrajectory> testDrive = PathPlanner.loadPathGroup("LeaveCommunity", new PathConstraints(DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND, 2));

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