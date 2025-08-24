package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;

@Config
public class Accelerator {
    // ---------------------------------------- Hardware ---------------------------------------- //
    private DcMotorEx accelMotor;
    public static double ACCEL_MAX_POWER = 1.0;
    private BooleanSupplier activationButton;

    // ------------------------------------ State Management ------------------------------------ //

    public enum State {
        STOPPED,
        RUNNING
    }

    private State state = State.STOPPED;

    private Telemetry telemetry;

    // ------------------------------------------------------------------------------------------ //

    public Accelerator(HardwareMap hm,
                       Telemetry telemetry,
                       BooleanSupplier activationButton) {
        accelMotor = hm.get(DcMotorEx.class, "accel");
        accelMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        this.activationButton = activationButton;

        this.telemetry = telemetry;
    }

    public void update() {
        if(activationButton.getAsBoolean()) { // Toggle state on button press
            state = (state == State.STOPPED) ? State.RUNNING : State.STOPPED;
        }

        // Set motor power based on state
        accelMotor.setPower(state == State.RUNNING ? ACCEL_MAX_POWER : 0);

        // -------------------------------------- Telemetry ------------------------------------- //
        telemetry.addData("Accelerator State: ", state);
    }

    public State getState() {
        return state;
    }
}
