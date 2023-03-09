package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;

public class BalanceCommand extends CommandBase {

    DoubleSupplier forward = () -> 0.15;
    DoubleSupplier backwards = () -> -0.15;

    DoubleSupplier zero = () -> 0;

    boolean balanced = false;

    private DrivetrainSubsystem drivetrain = RobotContainer.m_drivetrainSubsystem;
  
    public BalanceCommand() {
    
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    balanced = false;
  }

  @Override
  public void execute() {
   
    double pitch = drivetrain.getPitch();

    if(pitch >= 2){
        new DriveCommand(drivetrain, forward, zero, zero);
    }

    if(pitch <= -2){
        new DriveCommand(drivetrain, backwards, zero, zero);
    }

    else{
        new DriveCommand(drivetrain, zero, zero, zero);
        balanced = true;
    }

  }

  @Override
  public void end(boolean inturrupted) {
    new DriveCommand(drivetrain, zero, zero, zero);
  }

  @Override
  public boolean isFinished() {
    return balanced;
  }
}
