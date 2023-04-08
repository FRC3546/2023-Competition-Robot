package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class BalanceCommand extends CommandBase {

    // public double forwardFast = -0.3;
    // public double backwardsFast = 0.3;

    public double forwardSlow = 0.3;
    public double backwardSlow = -0.3;

    public double time1 = Double.MIN_VALUE;
    public double lastAngle;

    public double zero = 0.0;

    private final Timer time = new Timer();

    boolean balanced = false;

    private DrivetrainSubsystem drivetrain = RobotContainer.m_drivetrainSubsystem;
  
    public BalanceCommand() {
    
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    time.start();
    System.out.println("Initialized");
    balanced = false;
  }

  @Override
  public void execute() {
   
    // System.out.println("executing");
    // System.out.println(balanced);

    // if (Math.abs(drivetrain.getPitchRate()) > 10) {
    //   balanced = true;
    // } 
    
    // else if (drivetrain.gyroscope.getRoll() > 2) {
    //   drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(backwardsFast, zero, zero, drivetrain.getRotation()));
    // } 
    
    // else if (drivetrain.gyroscope.getRoll() < -2) {
    //   drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(forwardFast, zero, zero, drivetrain.getRotation()));
    // } else {
    //   balanced = true;
    // }
  
    
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

    // if((drivetrain.gyroscope.getRoll() >= 5) || (drivetrain.gyroscope.getRoll() <= -5)){

    //   System.out.println("Fast speeds");

    //   if(drivetrain.gyroscope.getRoll() < 0){

    //     drivetrain.drive(
    //       ChassisSpeeds.fromFieldRelativeSpeeds(forwardFast, zero, zero, drivetrain.getRotation())
    //     );

    //   }

    //   else if(drivetrain.gyroscope.getRoll() > 0){
        
    //     drivetrain.drive(
    //       ChassisSpeeds.fromFieldRelativeSpeeds(backwardsFast, zero, zero, drivetrain.getRotation())
    //     );

    //   }


    // }

    // else{

    //   System.out.println("Slow speeds");

    //   if(drivetrain.gyroscope.getRoll() < -2){

    //     drivetrain.drive(
    //       ChassisSpeeds.fromFieldRelativeSpeeds(backwardsSlow, zero, zero, drivetrain.getRotation())
    //     );

    //   }


    //   else if(drivetrain.gyroscope.getRoll() > 2){

    //     drivetrain.drive(
    //       ChassisSpeeds.fromFieldRelativeSpeeds(backwardsSlow, zero, zero, drivetrain.getRotation())
    //     );

    //   }

    //   else{

    //     drivetrain.drive(
    //       ChassisSpeeds.fromFieldRelativeSpeeds(forwardSlow, zero, zero, drivetrain.getRotation())
    //     );

    //     balanced = true;

    //   }

    // }
    

    // if(drivetrain.gyroscope.getRoll() > 2){
    //   time.reset();
    //   drivetrain.drive(
    //     ChassisSpeeds.fromFieldRelativeSpeeds(backwardSlow, zero, zero, drivetrain.getRotation())
    //   );
    //   System.out.println("Moving backward into range");
    //   balanced = false;
    // }

    // else if(drivetrain.gyroscope.getRoll() < -6){
    //   time.reset();
    //   drivetrain.drive(
    //     ChassisSpeeds.fromFieldRelativeSpeeds(forwardSlow, zero, zero, drivetrain.getRotation())
        
    //   );
    //   System.out.println("moving forward into range");
    //   balanced = false;
    // }

    // else{
      if(time1 == Double.MIN_VALUE) {
      time1 = Timer.getFPGATimestamp();
      lastAngle = drivetrain.gyroscope.getRoll();  
      }
      time.reset();

      double duration = Timer.getFPGATimestamp() - time1;
      
      double rateOfChange = (lastAngle - drivetrain.gyroscope.getRoll())/ duration;

      SmartDashboard.putNumber("RateOfChange", rateOfChange);
      time1 = Timer.getFPGATimestamp();
      lastAngle = drivetrain.gyroscope.getRoll();
      
      //-3
      if(rateOfChange < -50) {
        balanced = true;
      } 
      else {
    drivetrain.drive(
        ChassisSpeeds.fromFieldRelativeSpeeds(forwardSlow, zero, zero, drivetrain.getRotation())
        
      );
  
      }
     
      
      // We are balanced
      // if((time.get() > 3 && drivetrain.gyroscope.getRoll() < 7 && (drivetrain.gyroscope.getRoll() > -11)) || time.getMatchTime() < 0.2){
      //   balanced = true;
      // }

      // else{
      //   if(drivetrain.gyroscope.getRoll() > 7){
      //      time.reset();
      //      drivetrain.drive(
      //        ChassisSpeeds.fromFieldRelativeSpeeds(backwardSlow, zero, zero, drivetrain.getRotation())
      //     );
      //     System.out.println("Moving backward into range");
      //     balanced = false;
      //   }
    
      //   else if(drivetrain.gyroscope.getRoll() < -11){
      //     time.reset();
      //     drivetrain.drive(
      //       ChassisSpeeds.fromFieldRelativeSpeeds(forwardSlow, zero, zero, drivetrain.getRotation())
            
      //     );
      //     System.out.println("moving forward into range");
      //     balanced = false;
      //   }
      // }

    // }


  }


  @Override
  public void end(boolean inturrupted) {

    System.out.println("Finished");
    ChassisSpeeds.fromFieldRelativeSpeeds(zero, zero, zero, drivetrain.getRotation());
  }

  @Override
  public boolean isFinished() {
    return balanced;
  }
}
