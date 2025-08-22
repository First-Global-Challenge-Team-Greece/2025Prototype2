package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;

@Config
public class Accelerator {
    private DcMotorEx accelMotor;
    public static double ACCEL_MAX_POWER = 1.0;
    private BooleanSupplier activationButton, forwardButton, reverseButton;
    private boolean prev_btn_state, accelEnabled = false;

    public enum State {
        STOPPED,
        RUNNING
    }

    private State state = State.STOPPED;

    private Telemetry telemetry;

    public Accelerator(HardwareMap hm,
                       Telemetry telemetry,
                       BooleanSupplier activationButton,
                       BooleanSupplier forwardButton,
                       BooleanSupplier reverseButton) {
        accelMotor = hm.get(DcMotorEx.class, "accel");
        accelMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        this.activationButton = activationButton;
        this.forwardButton = forwardButton;
        this.reverseButton = reverseButton;

        this.telemetry = telemetry;
    }

    public void update() {
        if(forwardButton.getAsBoolean()) accelMotor.setPower(ACCEL_MAX_POWER*0.7);
        else accelMotor.setPower(0);
        if(reverseButton.getAsBoolean()) accelMotor.setPower(-ACCEL_MAX_POWER*0.7);
        else accelMotor.setPower(0);

//        if(activationButton.getAsBoolean() && !prev_btn_state) {
//            state = accelEnabled ? State.RUNNING : State.STOPPED;
//        }
//
//        accelMotor.setPower(state == State.RUNNING ? ACCEL_MAX_POWER : 0);
//
//        prev_btn_state = activationButton.getAsBoolean();

        telemetry.addData("Accelerator State: ", getState());
    }

    public State getState() {
        return state;
    }
}
