// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;

import com.revrobotics.CANSparkMax;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import com.swervedrivespecialties.swervelib.MkSwerveModuleBuilder;
import com.swervedrivespecialties.swervelib.MotorType;
import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import com.swervedrivespecialties.swervelib.SwerveModule;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


import edu.wpi.first.math.controller.PIDController;

import com.revrobotics.CANSparkMax.IdleMode;


public class DrivetrainSubsystem extends SubsystemBase {
    private static final double MAX_VOLTAGE = 12.0;
    public static final double MAX_VELOCITY_METERS_PER_SECOND = 4.14528;
    public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND /
            Math.hypot(Constants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0, Constants.DRIVETRAIN_WHEELBASE_METERS / 2.0);

    public static final double MAX_ANGULAR_VELOCITY_DEGREES_PER_SECOND = Math.toDegrees(MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND);
    
    public final SwerveModule frontLeftModule;
    public final SwerveModule frontRightModule;
    public final SwerveModule backLeftModule;
    public final SwerveModule backRightModule;

        //Drive motors variables that cast to in the declarator of the modules
    public final CANSparkMax frontLeftDriveMotor;
    public final CANSparkMax frontRightDriveMotor;
    public final CANSparkMax backLeftDriveMotor;
    public final CANSparkMax backRightDriveMotor;
        //Steering motor variables that we cast to in the declarator of the modules
    public final CANSparkMax frontLeftSteerMotor;
    public final CANSparkMax frontRightSteerMotor;
    public final CANSparkMax backLeftSteerMotor;
    public final CANSparkMax backRightSteerMotor;

    public double offset = -90;

    private SwerveModuleState[] states;


    public final AHRS gyroscope = new AHRS(SerialPort.Port.kUSB);
    

    private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            new Translation2d(Constants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0, Constants.DRIVETRAIN_WHEELBASE_METERS / 2.0),
            new Translation2d(Constants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -Constants.DRIVETRAIN_WHEELBASE_METERS / 2.0),
            new Translation2d(-Constants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0, Constants.DRIVETRAIN_WHEELBASE_METERS / 2.0),
            new Translation2d(-Constants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -Constants.DRIVETRAIN_WHEELBASE_METERS / 2.0)
    );
    private final SwerveDriveOdometry odometry;

    private ChassisSpeeds chassisSpeeds = new ChassisSpeeds(0.0, 0.0, 0.0);

     {

        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("Drivetrain");

        frontLeftModule = new MkSwerveModuleBuilder()
                .withLayout(shuffleboardTab.getLayout("Front Left Module", BuiltInLayouts.kList)
                        .withSize(2, 4)
                        .withPosition(0, 0))
                .withGearRatio(SdsModuleConfigurations.MK4I_L2)
                .withDriveMotor(MotorType.NEO, Constants.FRONT_LEFT_MODULE_DRIVE_MOTOR)
                .withSteerMotor(MotorType.NEO, Constants.FRONT_LEFT_MODULE_STEER_MOTOR)
                .withSteerEncoderPort(Constants.FRONT_LEFT_MODULE_STEER_ENCODER)
                .withSteerOffset(Constants.FRONT_LEFT_MODULE_STEER_OFFSET)
                .build();

        frontRightModule = new MkSwerveModuleBuilder()
                .withLayout(shuffleboardTab.getLayout("Front Right Module", BuiltInLayouts.kList)
                        .withSize(2, 4)
                        .withPosition(2, 0))
                .withGearRatio(SdsModuleConfigurations.MK4I_L2)
                .withDriveMotor(MotorType.NEO, Constants.FRONT_RIGHT_MODULE_DRIVE_MOTOR)
                .withSteerMotor(MotorType.NEO, Constants.FRONT_RIGHT_MODULE_STEER_MOTOR)
                .withSteerEncoderPort(Constants.FRONT_RIGHT_MODULE_STEER_ENCODER)
                .withSteerOffset(Constants.FRONT_RIGHT_MODULE_STEER_OFFSET)
                .build();

        backLeftModule = new MkSwerveModuleBuilder()
                .withLayout(shuffleboardTab.getLayout("Back Left Module", BuiltInLayouts.kList)
                        .withSize(2, 4)
                        .withPosition(4, 0))
                .withGearRatio(SdsModuleConfigurations.MK4I_L2)
                .withDriveMotor(MotorType.NEO, Constants.BACK_LEFT_MODULE_DRIVE_MOTOR)
                .withSteerMotor(MotorType.NEO, Constants.BACK_LEFT_MODULE_STEER_MOTOR)
                .withSteerEncoderPort(Constants.BACK_LEFT_MODULE_STEER_ENCODER)
                .withSteerOffset(Constants.BACK_LEFT_MODULE_STEER_OFFSET)
                .build();

        backRightModule = new MkSwerveModuleBuilder()
                .withLayout(shuffleboardTab.getLayout("Back Right Module", BuiltInLayouts.kList)
                        .withSize(2, 4)
                        .withPosition(6, 0))
                .withGearRatio(SdsModuleConfigurations.MK4I_L2)
                .withDriveMotor(MotorType.NEO, Constants.BACK_RIGHT_MODULE_DRIVE_MOTOR)
                .withSteerMotor(MotorType.NEO, Constants.BACK_RIGHT_MODULE_STEER_MOTOR)
                .withSteerEncoderPort(Constants.BACK_RIGHT_MODULE_STEER_ENCODER)
                .withSteerOffset(Constants.BACK_RIGHT_MODULE_STEER_OFFSET)
                .build();

                odometry = new SwerveDriveOdometry(
                        kinematics,
                        Rotation2d.fromDegrees(gyroscope.getYaw()),
                        new SwerveModulePosition[]{ frontLeftModule.getPosition(), frontRightModule.getPosition(), backLeftModule.getPosition(), backRightModule.getPosition() }
                );


        shuffleboardTab.addNumber("Gyroscope Angle", () -> getRotation().getDegrees());
        shuffleboardTab.addNumber("Pose X", () -> odometry.getPoseMeters().getX());
        shuffleboardTab.addNumber("Pose Y", () -> odometry.getPoseMeters().getY());

        frontLeftDriveMotor = (CANSparkMax) frontLeftModule.getDriveMotor();
        frontRightDriveMotor = (CANSparkMax) frontRightModule.getDriveMotor();
        backLeftDriveMotor = (CANSparkMax) backLeftModule.getDriveMotor();
        backRightDriveMotor = (CANSparkMax) backRightModule.getDriveMotor();

        frontLeftSteerMotor = (CANSparkMax) frontLeftModule.getSteerMotor();
        frontRightSteerMotor = (CANSparkMax) frontRightModule.getSteerMotor();
        backLeftSteerMotor = (CANSparkMax) backLeftModule.getSteerMotor();
        backRightSteerMotor = (CANSparkMax) backRightModule.getSteerMotor();
    }

    

//     public void zeroGyroscope() {
//         odometry.resetPosition(
//                 Rotation2d.fromDegrees(gyroscope.getFusedHeading()),
//                 new SwerveModulePosition[]{ frontLeftModule.getPosition(), frontRightModule.getPosition(), backLeftModule.getPosition(), backRightModule.getPosition() },
//                 new Pose2d(odometry.getPoseMeters().getTranslation(), Rotation2d.fromDegrees(0.0))
//         );
//     }

private SwerveModulePosition[] getPositions() {
        SwerveModulePosition[] swervePositions = {
                frontLeftModule.getPosition(),
                frontRightModule.getPosition(),
                backLeftModule.getPosition(),
                backRightModule.getPosition()
        };
        return swervePositions;
    }

    public Rotation2d getRotation() {
        return odometry.getPoseMeters().getRotation();
        //return Rotation2d.fromDegrees(gyroscope.getYaw());
    }

    public void drive(ChassisSpeeds chassisSpeeds) {
        this.chassisSpeeds = chassisSpeeds;

        states = kinematics.toSwerveModuleStates(chassisSpeeds);

        SwerveDriveKinematics.desaturateWheelSpeeds(states, MAX_VELOCITY_METERS_PER_SECOND);

        frontLeftModule.set(states[0].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
                states[0].angle.getRadians());
        frontRightModule.set(states[1].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
                states[1].angle.getRadians());
        backLeftModule.set(states[2].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
                states[2].angle.getRadians());
        backRightModule.set(states[3].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
                states[3].angle.getRadians());
    }

    public void drive(SwerveModuleState[] states) {
     
        //this.states = states;
        SwerveDriveKinematics.desaturateWheelSpeeds(states, MAX_VELOCITY_METERS_PER_SECOND);

        frontLeftModule.set(states[0].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
                states[0].angle.getRadians());
        frontRightModule.set(states[1].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
                states[1].angle.getRadians());
        backLeftModule.set(states[2].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
                states[2].angle.getRadians());
        backRightModule.set(states[3].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
                states[3].angle.getRadians());
    }


    public void zeroGyroscope(){
        gyroscope.zeroYaw();
    }

    public void setGyroOffset(double offset){
        this.offset = offset;
    }

    public double getPitch(){
        return gyroscope.getRoll();
    }

    public double getGyroFusedOffset(){
        return gyroscope.getFusedHeading() + offset;
    }

    public double getGyroYaw(){
        return gyroscope.getYaw();
    }

    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(pose.getRotation(),
                getPositions(),
                pose);
    }

    public double getPitchRate() {
        return gyroscope.getRawGyroY();
      }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public SwerveDriveKinematics getKinematics() {
        return kinematics;
    }

    public void setSteerMotorBrake(boolean x){
        
        if (x == true){
            frontLeftSteerMotor.setIdleMode(IdleMode.kBrake);
            frontRightSteerMotor.setIdleMode(IdleMode.kBrake);
            backLeftSteerMotor.setIdleMode(IdleMode.kBrake);
            backRightSteerMotor.setIdleMode(IdleMode.kBrake);
        }

        if (x == false){
            frontLeftSteerMotor.setIdleMode(IdleMode.kCoast);
            frontRightSteerMotor.setIdleMode(IdleMode.kCoast);
            backLeftSteerMotor.setIdleMode(IdleMode.kCoast);
            backRightSteerMotor.setIdleMode(IdleMode.kCoast);    
        }

        else{
            System.out.println("setSteerMotorMode value wrong");
        }
    }

    public void setDriveMotorBrake(boolean x){
        
        if (x == true){
            frontLeftDriveMotor.setIdleMode(IdleMode.kBrake);
            frontRightDriveMotor.setIdleMode(IdleMode.kBrake);
            backLeftDriveMotor.setIdleMode(IdleMode.kBrake);
            backRightDriveMotor.setIdleMode(IdleMode.kBrake);
        }
        else{
            frontLeftDriveMotor.setIdleMode(IdleMode.kCoast);
            frontRightDriveMotor.setIdleMode(IdleMode.kCoast);
            backLeftDriveMotor.setIdleMode(IdleMode.kCoast);
            backRightDriveMotor.setIdleMode(IdleMode.kCoast);
        }
    }
    
    
    @Override
    public void periodic() {
        odometry.update(
                Rotation2d.fromDegrees(-gyroscope.getYaw()),//might be negative
                new SwerveModulePosition[]{ frontLeftModule.getPosition(), frontRightModule.getPosition(), backLeftModule.getPosition(), backRightModule.getPosition() }
        );
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);

        frontLeftModule.set(states[0].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE, states[0].angle.getRadians());
        frontRightModule.set(states[1].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE, states[1].angle.getRadians());
        backLeftModule.set(states[2].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE, states[2].angle.getRadians());
        backRightModule.set(states[3].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE, states[3].angle.getRadians());
    }

    // Assuming this method is part of a drivetrain subsystem that provides the necessary methods
public Command followTrajectoryCommand(PathPlannerTrajectory traj, boolean isFirstPath) {
        return new SequentialCommandGroup(
             new InstantCommand(() -> {
               // Reset odometry for the first path you run during auto
               if(isFirstPath){
                   this.resetOdometry(traj.getInitialHolonomicPose());
               }
             }),
             new PPSwerveControllerCommand(
                        traj,
                        this::getPose, // Pose supplier
                        this.kinematics, // SwerveDriveKinematics
                        new PIDController(0, 0, 0), // X controller. Tune these values for your
                                                                          // robot. Leaving them 0 will only use
                                                                          // feedforwards.
                        new PIDController(0, 0, 0), // Y controller (usually the same values as X
                                                                           // controller)
                        new PIDController(0, 0, 0), // Rotation controller. Tune these values
                                                                              // for your robot. Leaving them 0 will
                                                                              // only use feedforwards.
                        this::drive, // Module states consumer
                        true, // Should the path be automatically mirrored depending on alliance color.
                              // Optional, defaults to true
                        this // Requires this drive subsystem
                ));
        }
}