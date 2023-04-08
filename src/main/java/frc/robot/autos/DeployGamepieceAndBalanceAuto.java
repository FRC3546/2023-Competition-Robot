package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

import frc.robot.RobotContainer;
import frc.robot.commandgroups.DeliveryArmTransfer;
import frc.robot.commands.BalanceCommand;
import frc.robot.commands.DaSoundOfDaPolice;
import frc.robot.commands.ExtendDeliveryArmCommand;
import frc.robot.commands.LockWheelsCommand;
import frc.robot.commands.PauseCommand;

import frc.robot.commands.DriveCommand;



import java.util.function.DoubleSupplier;





public class DeployGamepieceAndBalanceAuto extends SequentialCommandGroup{

    DoubleSupplier moveForward = () -> -0.1;
    DoubleSupplier moveBackward = () -> 0.1;


    DoubleSupplier x = () -> 0;
    DoubleSupplier y = () -> 0.2;
    DoubleSupplier rot = () -> 0;

    DoubleSupplier balanceSpeed = () -> 0.1;



    public DeployGamepieceAndBalanceAuto(double deployPosition, double endPosition){
        
            addCommands(

                new DeliveryArmTransfer(),
                new PauseCommand(0.7),
                new ExtendDeliveryArmCommand(deployPosition),
                
                // new ParallelDeadlineGroup(
                //     new PauseCommand(0.5),
                //     new DriveCommand(RobotContainer.m_drivetrainSubsystem, moveForward, x, rot)
                // ),

                new InstantCommand(() -> RobotContainer.m_deliverySubsystem.OpenDeliveryArmClamp()),
                new PauseCommand(0.5),
                new InstantCommand(() -> RobotContainer.m_flipperSubsystem.CloseFlipperClamp()),
                

                // new ParallelDeadlineGroup(
                //     new PauseCommand(0.5),
                //     new DriveCommand(RobotContainer.m_drivetrainSubsystem, moveBackward, x, rot)
                // ),

                
                // new ParallelDeadlineGroup(new RotateToAngleCommand(180, 6),

                // new MoveFlipperCommand(0.493),

                

                

                

                
                // THIS IS OUR TIME BASESD
                new ParallelDeadlineGroup(new PauseCommand(1), new DriveCommand(RobotContainer.m_drivetrainSubsystem, balanceSpeed, x, rot)),
                
                // THIS IS OUR TIME BASESD
                new ParallelDeadlineGroup(new PauseCommand(3.2), new DriveCommand(RobotContainer.m_drivetrainSubsystem, balanceSpeed, x, rot),
                new ExtendDeliveryArmCommand(endPosition)),



                // THIS IS OUR BALANCE COMMAND THAT WE COMMENTED OUT BEFORE OUR LAST TEST

                // new ParallelDeadlineGroup(new PauseCommand(2.3), new DriveCommand(RobotContainer.m_drivetrainSubsystem, y, x, rot),
                // new ExtendDeliveryArmCommand(endPosition)),

                //new ParallelDeadlineGroup(new BalanceCommand(), new ExtendDeliveryArmCommand(endPosition)),



                
                // new InstantCommand(() -> RobotContainer.m_ledSubsystem.LEDSet(0, 1, 0)),
                new LockWheelsCommand()

                // new ParallelDeadlineGroup(
                //     new PauseCommand(2.5), 
                //     new DriveCommand(RobotContainer.m_drivetrainSubsystem, y, x, rot))

                
                // new BalanceCommand(),
                // new LockWheelsCommand()
                
                
                );

                



    
    }

}