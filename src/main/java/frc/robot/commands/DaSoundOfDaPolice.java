package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.LightEmUpSubsystem;
import edu.wpi.first.wpilibj.Timer;


public class DaSoundOfDaPolice extends CommandBase{
    private final static Timer time = new Timer();
    private LightEmUpSubsystem ledSubsystem = RobotContainer.m_ledSubsystem;
    private double period;
    private double r1;
    private double g1;
    private double b1;
    private double r2;
    private double g2;
    private double b2;
    private boolean first = true;

    public DaSoundOfDaPolice(double r1, double g1, double b1, double r2, double g2, double b2, double period) {
        this.period = period;
        this.r1 = r1;
        this.g1 = g1;
        this.b1 = b1;
        this.r2 = r2;
        this.g2 = g2;
        this.b2 = b2;
        addRequirements(ledSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("WHOOP WHOOOP");
        ledSubsystem.LEDSet(r1, g1, b1);
        time.start();
    }

    public void execute(){
        if (time.get() > period){
            if(first){
                ledSubsystem.LEDSet(r2, g2, b2);
            }
            else{
                ledSubsystem.LEDSet(r1,g1,b2);
            }
            time.restart();
        }

    }


}
