package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

public class FlipperSubsystem extends SubsystemBase{
    
    private DoubleSolenoid flipperClampSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
    private boolean flipperClampOpen = false;

    private VictorSP raiseFlipperMotor = new VictorSP(0);
    private boolean flipperArmRaised = false;
    


    public void OpenFlipperClamp(){
        flipperClampSolenoid.set(Value.kReverse);
        flipperClampOpen = true;
    }

    public void CloseFlipperClamp(){
        flipperClampSolenoid.set(Value.kForward);
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

    // NEEDS A SYSTEM TO STOP THE MOTOR!!! (limit switch or something)
    public void RaiseFlipperArm(){
        raiseFlipperMotor.set(0.5);
        flipperArmRaised = true;
    }

    public void LowerFlipperArm(){
        raiseFlipperMotor.set(-0.5);
        flipperArmRaised = false;
    }

    public void toggleFlipperArm(){
        
        if(flipperArmRaised == true){
            flipperArmRaised = false;
            LowerFlipperArm();
        }

        if(flipperArmRaised == false){
            flipperArmRaised = true;
            RaiseFlipperArm();
        }

    }

}
