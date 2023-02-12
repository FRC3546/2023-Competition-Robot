package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.motorcontrol.Spark;

import edu.wpi.first.wpilibj.Encoder;

public class FlipperSubsystem extends SubsystemBase{
    
    private DoubleSolenoid flipperClampSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
    private boolean flipperClampOpen = false;

    private Spark raiseFlipperMotor = new Spark(0);
    private boolean flipperArmRaised = false;

    private Encoder flipperEncoder = new Encoder(8, 9);
      

    public static boolean motorFinished = false;

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
    // public void RaiseFlipperArm(){

    //     if (pot.get() <= upValue){
    //         raiseFlipperMotor.set(0.5);
    //         flipperArmRaised = true;
    //     }

    //     else{
    //         raiseFlipperMotor.set(0.0);
    //     }

    //     motorFinished = true;
    // }

    // public void LowerFlipperArm(){

    //     if(pot.get() >= downValue){        
    //         raiseFlipperMotor.set(-0.5);
    //         flipperArmRaised = false;
    //     }

    //     else{
    //         raiseFlipperMotor.set(0.0);
    //     }

    //     motorFinished = true;
    // }

    // public void toggleFlipperArm(){
        
    //     if(flipperArmRaised == true){
    //         flipperArmRaised = false;
    //         LowerFlipperArm();
    //     }

    //     if(flipperArmRaised == false){
    //         flipperArmRaised = true;
    //         RaiseFlipperArm();
    //     }
    // }

    public int GetFlipperPosition(){
        return flipperEncoder.get();
    }

    public void SetFlipperSpeed(double speed){
        raiseFlipperMotor.set(speed);
    }

}
