package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.function.BooleanSupplier;

@Config
public class Barrier {
    private ServoImplEx leftArm, rightArm;
    private BooleanSupplier toggleButton, hideButton, robotMoved, accelRunning;
    private boolean prev_toggle_btn_state, prev_hide_btn_state, mechanism_has_init = false;

    public enum State {
        PUSH,
        GRAB,
        GUIDE,
        HIDE
    }

    private State state = State.HIDE;

    private HashMap<State, Double> leftArmPos = new HashMap<State, Double>() {{
        put(State.PUSH, 0.0);
        put(State.GRAB, 0.05);
        put(State.GUIDE, 0.1);
        put(State.HIDE, 0.7);
    }};

    private HashMap<State, Double> rightArmPos = new HashMap<State, Double>() {{
        put(State.PUSH, 0.74);
        put(State.GRAB, 0.7);
        put(State.GUIDE, 0.6);
        put(State.HIDE, 0.0);
    }};

    private Telemetry telemetry;

    public Barrier(HardwareMap hm,
                   Telemetry telemetry,
                   BooleanSupplier toggleButton,
                   BooleanSupplier hideButton,
                   BooleanSupplier robotMoved,
                   BooleanSupplier accelRunning) {
        leftArm = hm.get(ServoImplEx.class, "left_arm");
        rightArm = hm.get(ServoImplEx.class, "right_arm");
        leftArm.setPosition(leftArmPos.get(State.HIDE));
        rightArm.setPosition(rightArmPos.get(State.HIDE));

        this.toggleButton = toggleButton;
        this.hideButton = hideButton;
        this.robotMoved = robotMoved;
        this.accelRunning = accelRunning;

        this.telemetry = telemetry;
    }

    public void update() {
        if(!mechanism_has_init) {
            if(robotMoved.getAsBoolean()) {
                mechanism_has_init = true;
                setState(State.GUIDE);
            } else return;
        }

        if(!accelRunning.getAsBoolean()) {
            if(hideButton.getAsBoolean() && !prev_hide_btn_state) {
                setState(State.HIDE);
            }

            if(toggleButton.getAsBoolean() && !prev_toggle_btn_state) {
                if(state == State.PUSH) setState(State.GUIDE);
                else setState(State.PUSH);
            }
        } else {
            setState(State.HIDE);
        }


        telemetry.addData("Barrier Arms State: ", getState());
        prev_toggle_btn_state = toggleButton.getAsBoolean();
    }

    public void setState(State state) {
        if (this.state == state) return; // No change
        this.state = state;
        leftArm.setPosition(leftArmPos.get(state));
        rightArm.setPosition(rightArmPos.get(state));
    }

    public State getState() {
        return state;
    }
}
