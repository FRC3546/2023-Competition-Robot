// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DutyCycleEncoder;


public class FlipperSubsystem extends SubsystemBase{
    
    private DoubleSolenoid flipperClampSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
    private boolean flipperClampOpen = false;

    private Spark raiseFlipperMotor = new Spark(0);
    private boolean flipperArmRaised = false;

    private DutyCycleEncoder flipperEncoder = new DutyCycleEncoder(9);

    public static boolean motorFinished = false;

    public void OpenFlipperClamp(){
        flipperClampSolenoid.set(Value.kForward);
        flipperClampOpen = true;
    }

    public void CloseFlipperClamp(){
        flipperClampSolenoid.set(Value.kReverse);
        flipperClampOpen = false;
    }

    public void ToggleFlipperClamp(){

        if(flipperClampOpen == true){
            flipperClampOpen = false;
        }
        else if(flipperClampOpen == false){
            flipperClampOpen = true;
        }

        flipperClampSolenoid.toggle();
    }

    public double GetFlipperPosition(){
        return flipperEncoder.getAbsolutePosition();
    }

    public double GetFlipperMotorSpeed(){
        return raiseFlipperMotor.get();
    }

    public void SetFlipperSpeed(double speed){
        raiseFlipperMotor.set(speed);
    }

    @Override
    public void periodic() {

        // if (((GetFlipperPosition()) < Constants.flipperArmDown || (RobotContainer.m_codriverController.getY() < -0.2))
        //  && (GetFlipperPosition() > Constants.flipperArmUp || (RobotContainer.m_codriverController.getY() > 0.2))){
        //     raiseFlipperMotor.set(RobotContainer.m_codriverController.getY());
        // }

        // else{
        //     System.out.println("Out of bounds");
        // }

            // if ((((GetFlipperPosition() < Constants.flipperArmDown) || (RobotContainer.m_codriverController.getY() > 0.2))) && 
            // (((GetFlipperPosition() > Constants.flipperArmUp) || (RobotContainer.m_codriverController.getY() < -0.2)))){
            //     raiseFlipperMotor.set(RobotContainer.m_codriverController.getY());
            //    System.out.println("Within bounds");
            // }
            // else{
            //     System.out.println("Out of bounds and can not go back");
            // }

            
            if((GetFlipperPosition() < Constants.flipperArmDown && 
            GetFlipperPosition() > Constants.flipperArmUp)){
                raiseFlipperMotor.set(RobotContainer.m_codriverController.getY());
                // System.out.println("In bounds");
            }
            else if(GetFlipperPosition() > Constants.flipperArmDown && RobotContainer.m_codriverController.getY() > 0.1){
                raiseFlipperMotor.set(RobotContainer.m_codriverController.getY());
                // System.out.println("Too Low out of bounds Increasing");
            }
            else if(GetFlipperPosition() < Constants.flipperArmUp && RobotContainer.m_codriverController.getY() < -.1){
                raiseFlipperMotor.set(RobotContainer.m_codriverController.getY());
                // System.out.println("Too High out of bounds Increasing");
            }
            else{
                raiseFlipperMotor.set(0);
                // System.out.println("trying to do bad things");
            }

  }

}
