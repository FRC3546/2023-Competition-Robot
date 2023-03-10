package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;
import java.util.function.DoubleSupplier;

public class RotateToAngleCommand extends CommandBase {

    private final DoubleSupplier zero = () -> 0;

  private final DrivetrainSubsystem drivetrain = RobotContainer.m_drivetrainSubsystem;
  private boolean complete = false;
  private double angle;
  private Timer timer = new Timer();
  private double timeout;

  public RotateToAngleCommand(double degrees, double timeout) {
    angle = degrees;
    this.timeout = timeout;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    complete = false;
    System.out.println("rotate initialized");
  }

  @Override
  public void execute() {

    System.out.println("rotate executing");

    double gyroAngle = drivetrain.gyroscope.getYaw();

    final double kP = 0.2;

    if (angle > 180) {
      angle = -(360 - angle);
    } 
    
    else if (angle < -180) {
      angle = 360 + angle;
    }

    double err = angle - gyroAngle;
    DoubleSupplier speed = () ->
        MathUtil.clamp(
            err * kP,
            -DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_DEGREES_PER_SECOND * 0.5,
            DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_DEGREES_PER_SECOND * 0.5);

    if (Math.abs(err) > 2 && timer.get() < timeout) {
      new DriveCommand(drivetrain, zero, zero, () -> .3);
        System.out.println("Driving");
    } else {
        System.out.println("complete");
      complete = true;
    }
  }

  @Override
  public void end(boolean inturrupted) {
    new DriveCommand(drivetrain, zero, zero, zero);
    timer.stop();
  }

  @Override
  public boolean isFinished() {
    return complete;
  }
}
