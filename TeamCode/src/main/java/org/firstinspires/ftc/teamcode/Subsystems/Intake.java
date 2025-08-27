package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.function.BooleanSupplier;

@Config
public class Intake {
    // ---------------------------------------- Hardware ---------------------------------------- //
    private DcMotorEx intakeMotor;
    private BooleanSupplier forwardButton, stopButton, reverseButton, hanging;
    private boolean disabled;

    // ------------------------------------ State Management ------------------------------------ //
    private Constants.IntakeState state = Constants.IntakeState.STOPPED;

    private Telemetry telemetry;

    // ------------------------------------------------------------------------------------------ //

    public Intake(HardwareMap hm,
                  Telemetry telemetry,
                  BooleanSupplier forwardButton,
                  BooleanSupplier stopButton,
                  BooleanSupplier reverseButton,
                  BooleanSupplier hanging
    ) {
        intakeMotor = hm.get(DcMotorEx.class, Constants.INTAKE_MOTOR_NAME);
        intakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.forwardButton = forwardButton;
        this.stopButton = stopButton;
        this.reverseButton = reverseButton;
        this.hanging  = hanging;

        this.telemetry = telemetry;
    }

    public void update() {
        disabled = hanging.getAsBoolean() | disabled;

        if(hanging.getAsBoolean()) intakeMotor.setMotorDisable();

        if (disabled) return;

        // Set Intake State according to button presses
        if(forwardButton.getAsBoolean()) setState(Constants.IntakeState.FORWARD);
        if(stopButton.getAsBoolean()) setState(Constants.IntakeState.STOPPED);
        if(reverseButton.getAsBoolean()) setState(Constants.IntakeState.REVERSE);

        // Set motor power based on state
        intakeMotor.setPower(Range.clip(
                state.getVelocity(),
                -Constants.INTAKE_MAX_POWER,
                Constants.INTAKE_MAX_POWER
        ));

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
