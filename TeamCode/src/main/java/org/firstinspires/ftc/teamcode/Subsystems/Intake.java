package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.function.BooleanSupplier;

@Config
public class Intake {
    // ---------------------------------------- Hardware ---------------------------------------- //
    private DcMotorEx intakeMotor;
    private BooleanSupplier forwardButton, reverseButton;

    // ------------------------------------ State Management ------------------------------------ //
    private Constants.IntakeState state = Constants.IntakeState.STOPPED;

    private Telemetry telemetry;

    // ------------------------------------------------------------------------------------------ //

    public Intake(HardwareMap hm,
                  Telemetry telemetry,
                  BooleanSupplier forwardButton,
                  BooleanSupplier reverseButton) {
        intakeMotor = hm.get(DcMotorEx.class, Constants.INTAKE_MOTOR_NAME);
        intakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.forwardButton = forwardButton;
        this.reverseButton = reverseButton;

        this.telemetry = telemetry;
    }

    public void update() {
        // Move Intake according to button presses
        if(forwardButton.getAsBoolean()) {
            state = state != Constants.IntakeState.FORWARD ?
                    Constants.IntakeState.FORWARD :
                    Constants.IntakeState.STOPPED;
        }

        if(reverseButton.getAsBoolean()) {
            state = state != Constants.IntakeState.REVERSE ?
                    Constants.IntakeState.REVERSE :
                    Constants.IntakeState.STOPPED;
        }

        // Set motor power based on state
        intakeMotor.setPower(state.getVelocity());

        // -------------------------------------- Telemetry ------------------------------------- //
        telemetry.addData("[Intake] State: ", getState());
    }

    public void setState(Constants.IntakeState state) {
        if(this.state == state) return; // No Change, (Less Variable Writing Optimization)
        this.state = state;
    }

    public Constants.IntakeState getState() {
        return state;
    }
}
