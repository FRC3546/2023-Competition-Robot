package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class FlipperSubsystem extends SubsystemBase{
    
    private DoubleSolenoid flipperClampSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
    private boolean flipperClampOpen = false;

    private VictorSP raiseFlipperMotor = new VictorSP(0);
    private boolean flipperArmRaised = false;

    private AnalogPotentiometer pot = new AnalogPotentiometer(0, 3600, 0);
    private int upValue = 1800;
    private int downValue = 0;    


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

        if (pot.get() <= upValue){
            raiseFlipperMotor.set(0.5);
            flipperArmRaised = true;
        }

        else{
            raiseFlipperMotor.set(0.0);
        }
    }

    public void LowerFlipperArm(){

        if(pot.get() >= downValue){        
            raiseFlipperMotor.set(-0.5);
            flipperArmRaised = false;
        }

        else{
            raiseFlipperMotor.set(0.0);
        }
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
