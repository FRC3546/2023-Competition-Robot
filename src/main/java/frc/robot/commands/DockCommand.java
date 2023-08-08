// package frc.robot.commands;

// import java.util.function.DoubleSupplier;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.RobotContainer;
// import frc.robot.subsystems.DrivetrainSubsystem;
// import edu.wpi.first.math.kinematics.ChassisSpeeds;



// public class BalanceCommand extends CommandBase {

//     public double forwardFast = -0.5;
//     public double backwardsFast = 0.5;

//     public double forwardSlow = -0.05;
//     public double backwardsSlow = 0.05;

//     public double zero = 0.0;

//     public int direction;

//     boolean balanced = false;

//     private DrivetrainSubsystem drivetrain = RobotContainer.m_drivetrainSubsystem;
  
//     public BalanceCommand(int direction) {
//     this
//     addRequirements(drivetrain);
//   }

//   @Override
//   public void initialize() {
//     System.out.println("Initialized");
//     balanced = false;
//   }

//   @Override
//   public void execute() {
   
    


//   }


//   @Override
//   public void end(boolean inturrupted) {

//     System.out.println("Finished");
//     ChassisSpeeds.fromFieldRelativeSpeeds(zero, zero, zero, drivetrain.getRotation());
//   }

//   @Override
//   public boolean isFinished() {
//     return balanced;
//   }
// }
