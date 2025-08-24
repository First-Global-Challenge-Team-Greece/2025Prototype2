package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.function.BooleanSupplier;

@Config
public class Accelerator {
    // ---------------------------------------- Hardware ---------------------------------------- //
    private DcMotorEx accelMotor;
    private BooleanSupplier activationButton;

    // ------------------------------------ State Management ------------------------------------ //

    private Constants.AcceleratorState state = Constants.AcceleratorState.STOPPED;

    private Telemetry telemetry;

    // ------------------------------------------------------------------------------------------ //

    public Accelerator(HardwareMap hm,
                       Telemetry telemetry,
                       BooleanSupplier activationButton) {
        accelMotor = hm.get(DcMotorEx.class, Constants.ACCELERATOR_MOTOR_NAME);
        accelMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        this.activationButton = activationButton;

        this.telemetry = telemetry;
    }

    public void update() {
        if(activationButton.getAsBoolean()) { // Toggle state on button press
            state = (state == Constants.AcceleratorState.STOPPED) ? Constants.AcceleratorState.RUNNING : Constants.AcceleratorState.STOPPED;
        }

        // Set motor power based on state
        accelMotor.setPower(state.getVelocity());

        // -------------------------------------- Telemetry ------------------------------------- //
        telemetry.addData("[Accelerator] State: ", state);
    }

    public Constants.AcceleratorState getState() {
        return state;
    }
}
