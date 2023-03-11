// package frc.robot.commandgroups;

// import javax.naming.PartialResultException;

// import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
// import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
// import frc.robot.Robot;
// import frc.robot.Constants;
// import frc.robot.RobotContainer;
// import frc.robot.commands.ExtendDeliveryArmCommand;
// import frc.robot.commands.MoveFlipperCommand;
// import frc.robot.commands.PauseCommand;

// // 1. Opens the delivery arm clamp
// // 2. Opens the flipper arm to transfer
// // 3. Pauses to allow everything to open
// // 4. closes the deviery arm clamp to pick up the gamepiece

// public class ReturnToHomeCommandgroup extends SequentialCommandGroup{


//     public ReturnToHomeCommandgroup(){
        
//             addCommands(
//                 new InstantCommand(() -> RobotContainer.m_flipperSubsystem.CloseFlipperClamp()),
//                 new InstantCommand(() -> RobotContainer.m_deliverySubsystem.OpenDeliveryArmClamp()),
//                 new PauseCommand(0.2),

//                 new ParallelDeadlineGroup(new ExtendDeliveryArmCommand(0), 
//                 new MoveFlipperCommand(Constants.flipperArmUp))
//             );
    
//     }

// }