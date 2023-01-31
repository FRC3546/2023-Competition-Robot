package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

public class DeliveryArmSubusystem extends SubsystemBase{

    private DoubleSolenoid deliveryArmClampSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 8, 9);
    private boolean deliveryArmClampOpen = false;

    private VictorSP extendingArmMotor = new VictorSP(1);
    //private boolean deliveryArmExtended = false;



    public void OpenDeliveryArmClamp(){
        deliveryArmClampSolenoid.set(Value.kReverse);
        deliveryArmClampOpen = true;
    }

    public void CloseDeliveryArmClamp(){
        deliveryArmClampSolenoid.set(Value.kForward);
        deliveryArmClampOpen = false;
    } 

    public void ToggleDeliveryArmClamp(){

        if(deliveryArmClampOpen == true){
            deliveryArmClampOpen = false;
        }
        else if(deliveryArmClampOpen == false){
            deliveryArmClampOpen = true;
        }

        deliveryArmClampSolenoid.toggle();
    }
    
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
