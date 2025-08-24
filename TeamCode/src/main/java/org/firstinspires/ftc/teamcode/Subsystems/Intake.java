package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.function.BooleanSupplier;

@Config
public class Intake {
    // ---------------------------------------- Hardware ---------------------------------------- //
    private DcMotorEx intakeMotor;
    private BooleanSupplier forwardButton, reverseButton;

    // ------------------------------------ State Management ------------------------------------ //

    public enum State {
        STOPPED,
        FORWARD,
        REVERSE
    }

    private State state = State.STOPPED;

    private HashMap<State, Double> velocities = new HashMap<Intake.State, Double>() {{
        put(State.FORWARD, 1.0);
        put(State.REVERSE, -1.0);
        put(State.STOPPED, 0.0);
    }};

    private Telemetry telemetry;

    // ------------------------------------------------------------------------------------------ //

    public Intake(HardwareMap hm,
                  Telemetry telemetry,
                  BooleanSupplier forwardButton,
                  BooleanSupplier reverseButton) {
        intakeMotor = hm.get(DcMotorEx.class, "intake");
        intakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.forwardButton = forwardButton;
        this.reverseButton = reverseButton;

        this.telemetry = telemetry;
    }

    public void update() {
        // Move Intake according to button presses
        if(forwardButton.getAsBoolean()) {
            state = state != State.FORWARD ? State.FORWARD : State.STOPPED;
        }

        if(reverseButton.getAsBoolean()) {
            state = state != State.REVERSE ? State.REVERSE : State.STOPPED;
        }

        // Set motor power based on state
        intakeMotor.setPower(velocities.get(state));

        // -------------------------------------- Telemetry ------------------------------------- //
        telemetry.addData("Intake State: ", getState());
    }

    public void setState(State state) {
        if(this.state == state) return; // No Change, (Less Variable Writing Optimization)
        this.state = state;
//        intakeMotor.setPower(velocities.get(state));
    }

    public State getState() {
        return state;
    }
}
