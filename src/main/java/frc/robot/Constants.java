// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kArmControllerPort = 1;
    public static final int kFlipperControllerPort = 2;

    // driver buttons
    public static final int kResetGyroEncoderButton = 1;

    // codriver buttons

    // flipper buttons
    public static final int kFlipperToggleButton = 2;
    public static final int kFlipperDownButton = 6;
    public static final int kFlipperUpButton = 4;

    // flipper SAFETY
    public static final int kFlipperEnableSafety = 3;
    public static final int kFlipperDisableSafety = 5;
    public static boolean kFlipperIsManual;

    // extension arm clamp button
    public static final int kArmClampButton = 1;

    // extension arm SAFETY
    public static final int kArmDisableSafety = 5;
    public static boolean kArmIsManual;

    // extension arm buttons for the CONE
    public static final int kArmLowCone = 8;
    public static final int kArmMidCone = 10;
    public static final int kArmHighCone = 12;

    // extension arm buttons for the CUBE
    public static final int kArmLowCube = 7;
    public static final int kArmMidCube = 9;
    public static final int lArmHighCube = 11;


  }

  public static final double DRIVETRAIN_TRACKWIDTH_METERS =  0.55245;
  /**
   * The front-to-back distance between the drivetrain wheels.
   *
   * Should be measured from center to center.
   */
  public static final double DRIVETRAIN_WHEELBASE_METERS = 0.67945;

  public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 20;
  public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 21;
  public static final int FRONT_LEFT_MODULE_STEER_ENCODER = 0;
  public static final double FRONT_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(79.8046);
  
  public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 22;
  public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 23;
  public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 1;
  public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(233.4375);

  public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 24;
  public static final int BACK_LEFT_MODULE_STEER_MOTOR = 25;
  public static final int BACK_LEFT_MODULE_STEER_ENCODER = 2;
  public static final double BACK_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(60.5566);

  public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 26;
  public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 27;
  public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 3;
  public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(199.7539);

  public static final double deliveryArmFullyExtended = 67000;
  public static final double deliveryArmFullyRetracted = 0;


  public static final double coneHigh = 55500;
  public static final double coneMiddle = 37228;
  public static final double coneLow = 10000;

  public static final double cubeHigh = 55500;
  public static final double cubeMiddle = 37228;
  public static final double cubeLow = 10000;


  public static final double flipperArmUp = .325;
  public static final double flipperArmDown = 0.55;
}
