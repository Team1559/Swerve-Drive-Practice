// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private DTXboxController controller;
    private SwerveDrive swerveDrive;
    private AHRS navX;
    private double swerveAngle = 0;

    /**
     * This function is run once when the robot is first started up and should
     * be used for any initialization code.
     */
    @Override
    public void robotInit() {
        this.controller = new DTXboxController(0);
        this.navX = new AHRS(SPI.Port.kMXP);
        this.swerveDrive = new SwerveDrive(this.navX);

        this.controller.setDeadBand(0.02);
        double[] offsets = this.swerveDrive.getCancoderOffsets();
        for (int i = 0; i < offsets.length; i++) {
            SmartDashboard.putNumber("Offset " + (i + 1), offsets[i]);
        }
    }

    /**
     * This function is called every robot packet, no matter the mode. This runs
     * after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        double[] cancoderPos = this.swerveDrive.getCancoderPositions();
        for (int i = 0; i < cancoderPos.length; i++) {
            SmartDashboard.putNumber("Wheel " + (i + 1), cancoderPos[i]);
        }

        SmartDashboard.putNumber("Yaw", this.navX.getYaw());
        SmartDashboard.putNumber("Roll", this.navX.getRoll());
        SmartDashboard.putNumber("Pitch", this.navX.getPitch());
    }

    /** This function is called once when autonomous is enabled. */
    @Override
    public void autonomousInit() {
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
    }

    /** This function is called once when teleop is enabled. */
    @Override
    public void teleopInit() {
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        double leftJoystickScaleFactor = 1;
        double rightJoystickScaleFactor = 1;
        if (controller.getLeftBumper()) {
            leftJoystickScaleFactor = 0.25;
            rightJoystickScaleFactor = 0.25;
        }
        this.swerveDrive.drive(
                this.controller.getLeftStickXSquared() * leftJoystickScaleFactor,
                this.controller.getLeftStickYSquared() * leftJoystickScaleFactor,
                this.controller.getRightStickXSquared() * rightJoystickScaleFactor);

        if (this.controller.getAButtonPressed()) {
            this.swerveDrive.zeroGyroscope();
        }
        // SmartDashboard.putNumber("Controller X",
        // this.controller.getLeftStickXSquared());
        // SmartDashboard.putNumber("Controller Y",
        // this.controller.getLeftStickYSquared());
        // SmartDashboard.putNumber("Controller R",
        // this.controller.getRightStickXSquared());
        // this.swerveDrive.drive(0, 0.3, 0);

        // double amount = 1;
        // if (this.controller.getLeftBumper()) {
        // amount = 0.1;
        // } else if (this.controller.getRightBumper()) {
        // amount = 10;
        // }
        // if (this.controller.getYButtonPressed()) {
        // swerveAngle += amount;
        // } else if (this.controller.getAButtonPressed()) {
        // swerveAngle -= amount;
        // }
        // this.swerveDrive.setAngles(swerveAngle);
    }

    /** This function is called once when the robot is disabled. */
    @Override
    public void disabledInit() {
    }

    /** This function is called periodically when disabled. */
    @Override
    public void disabledPeriodic() {
    }

    /** This function is called once when test mode is enabled. */
    @Override
    public void testInit() {
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {
    }
}
