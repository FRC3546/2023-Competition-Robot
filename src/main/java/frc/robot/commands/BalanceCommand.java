package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

import edu.wpi.first.math.geometry.Translation2d;


public class BalanceCommand extends CommandBase {

    public double forwardFast = -0.6;
    public double backwardsFast = 0.6;

    public double forwardSlow = -0.2;
    public double backwardsSlow = 0.2;

    public double zero = 0.0;


    boolean balanced = false;

    private DrivetrainSubsystem drivetrain = RobotContainer.m_drivetrainSubsystem;
  
    public BalanceCommand() {
    
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    System.out.println("Initialized");
    balanced = false;
  }

  @Override
  public void execute() {
   
    System.out.println("executing");
    System.out.println(balanced);

    if (Math.abs(drivetrain.getPitchRate()) > 10) {
      balanced = true;
    } 
    
    else if (drivetrain.gyroscope.getRoll() > 2) {
      drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(backwardsFast, zero, zero, drivetrain.getRotation()));
    } 
    
    else if (drivetrain.gyroscope.getRoll() < -2) {
      drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(forwardFast, zero, zero, drivetrain.getRotation()));
    } else {
      balanced = true;
    }
  }
    
  //   if(((drivetrain.gyroscope.getRoll() >= -6) && (drivetrain.gyroscope.getRoll() <= 6))){


  //     System.out.println("Going slow");

  //     // if(drivetrain.gyroscope.getRoll() > -13){
  //     //   balanced = true;
  //     // }

  //     if(drivetrain.gyroscope.getRoll() < -4){
      
  //       drivetrain.drive(
  //       ChassisSpeeds.fromFieldRelativeSpeeds(forwardSlow, zero, zero, drivetrain.getRotation()));
  //     }
  
  //     else if(drivetrain.gyroscope.getRoll() > 4){
  //       drivetrain.drive(
  //       ChassisSpeeds.fromFieldRelativeSpeeds(backwardsSlow, zero, zero, drivetrain.getRotation()));
  //     }

  //     else{
  //     drivetrain.drive(
  //       ChassisSpeeds.fromFieldRelativeSpeeds(zero, zero, zero, drivetrain.getRotation()));
  //       balanced = true;
  //     }

      
  // }

    // else if(!((drivetrain.gyroscope.getRoll() >= -10) && (drivetrain.gyroscope.getRoll() <= 10))){


    //   System.out.println("going fast");

    //   if(drivetrain.gyroscope.getRoll() <= -3){
        
    //     drivetrain.drive(
    //     ChassisSpeeds.fromFieldRelativeSpeeds(forwardFast, zero, zero, drivetrain.getRotation()));
    //   }

    //   else if(drivetrain.gyroscope.getRoll() >= 3){
    //     drivetrain.drive(
    //     ChassisSpeeds.fromFieldRelativeSpeeds(backwardsFast, zero, zero, drivetrain.getRotation()));
    //   }






      
    // }


  @Override
  public void end(boolean inturrupted) {

    ChassisSpeeds.fromFieldRelativeSpeeds(zero, zero, zero, drivetrain.getRotation());
  }

  @Override
  public boolean isFinished() {
    return balanced;
  }
}
