package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PauseCommand extends CommandBase{
    
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final Timer timer = new Timer();
    private double pauseTime;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public PauseCommand(double pauseTime){
    this.pauseTime = pauseTime;
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {}
  

  @Override
  public void end(boolean interrupted) {
    timer.stop();
    timer.reset();
  }

  @Override
  public boolean isFinished() {
    
    return timer.get() >= pauseTime;

  }
}

