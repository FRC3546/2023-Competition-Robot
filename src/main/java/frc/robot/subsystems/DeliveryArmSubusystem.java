package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

public class DeliveryArmSubusystem extends SubsystemBase{

    private DoubleSolenoid frontDeliveryArmClampSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 4, 5);
    private boolean frontDeliveryArmClampOpen = false;

    private DoubleSolenoid rearDeliveryArmClampSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 6, 7);
    private boolean rearDeliveryArmClampOpen = false;

    private VictorSP extendingArmMotor = new VictorSP(1);
    //private boolean deliveryArmExtended = false;

    

    public void OpenFrontDeliveryArmClamp(){
        frontDeliveryArmClampSolenoid.set(Value.kReverse);
        frontDeliveryArmClampOpen = true;
    }

    public void OpenRearDeliveryArmClamp(){
        rearDeliveryArmClampSolenoid.set(Value.kReverse);
        rearDeliveryArmClampOpen = true;
    }



    public void CloseFrontDeliveryArmClamp(){
        frontDeliveryArmClampSolenoid.set(Value.kForward);
        frontDeliveryArmClampOpen = false;
    } 

    public void CloseRearDeliveryArmClamp(){
        rearDeliveryArmClampSolenoid.set(Value.kForward);
        rearDeliveryArmClampOpen = false;
    }



    public void ToggleFrontDeliveryArmClamp(){

        if(frontDeliveryArmClampOpen == true){
            frontDeliveryArmClampOpen = false;
        }
        else if(frontDeliveryArmClampOpen == false){
            frontDeliveryArmClampOpen = true;
        }

        frontDeliveryArmClampSolenoid.toggle();
    }

    public void ToggleRearDeliveryArmClamp(){

        if(rearDeliveryArmClampOpen == true){
            rearDeliveryArmClampOpen = false;
        }
        else if(rearDeliveryArmClampOpen == false){
            rearDeliveryArmClampOpen = true;
        }

        rearDeliveryArmClampSolenoid.toggle();
    }



    public void OpenDeliveryArmClamp(){
        
        OpenFrontDeliveryArmClamp();
        OpenRearDeliveryArmClamp();
    }

    public void CloseDeliveryArmClamp(){

        CloseFrontDeliveryArmClamp();
        CloseRearDeliveryArmClamp();
    }

    public void ToggleDeliveryArmClamp(){

        if(frontDeliveryArmClampOpen == true && rearDeliveryArmClampOpen == true){
            
            frontDeliveryArmClampOpen = false;
            rearDeliveryArmClampOpen = false;
        }
        else if(frontDeliveryArmClampOpen == false && rearDeliveryArmClampOpen == false){
            frontDeliveryArmClampOpen = true;
            rearDeliveryArmClampOpen = true;
        }

        ToggleFrontDeliveryArmClamp();
        ToggleRearDeliveryArmClamp();
    }








    //------------------------------------------------------------------------------------------------

    // public void OpenDeliveryArmClamp(){
    //     frontDeliveryArmClampSolenoid.set(Value.kReverse);
    //     frontDeliveryArmClampOpen = true;
    // }

    // public void CloseDeliveryArmClamp(){
    //     frontDeliveryArmClampSolenoid.set(Value.kForward);
    //     frontDeliveryArmClampOpen = false;
    // } 

    // public void ToggleDeliveryArmClamp(){

    //     if(frontDeliveryArmClampOpen == true){
    //         frontDeliveryArmClampOpen = false;
    //     }
    //     else if(frontDeliveryArmClampOpen == false){
    //         frontDeliveryArmClampOpen = true;
    //     }

    //     frontDeliveryArmClampSolenoid.toggle();
    // }
    
    public void ExtendDeliveryArm(double speed){
        extendingArmMotor.set(speed);
        // something is needed to tell the motor to stop
    }

    public void SetExtendDeliveryArm(double position){
        extendingArmMotor.set(0.5);
        // something is needed to tell the motor to stop at a certain possition
        // this would be used for certain heights of the nodes were we want to
        // deliver at the 2nd or 3rd nodes without manuel tuning.
    }
}
