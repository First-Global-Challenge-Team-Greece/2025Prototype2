package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;

@Config
public class Intake {
    private DcMotorEx intakeMotor;
    public static double INTAKE_MAX_POWER = 0.7;
    private BooleanSupplier forwardButton, reverseButton;

    public enum State {
        STOPPED,
        FORWARD,
        REVERSE
    }

    private State state = State.STOPPED;

    private Telemetry telemetry;

    public Intake(HardwareMap hm,
                  Telemetry telemetry,
                  BooleanSupplier forwardButton,
                  BooleanSupplier reverseButton) {
        intakeMotor = hm.get(DcMotorEx.class, "intake");
        intakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intakeMotor.setDirection(DcMotorEx.Direction.REVERSE);

        this.forwardButton = forwardButton;
        this.reverseButton = reverseButton;

        this.telemetry = telemetry;
    }

    public void update() {
        double power = 0.0;

        if (forwardButton.getAsBoolean()) {
            power = INTAKE_MAX_POWER;
            state = State.FORWARD;
        } else if (reverseButton.getAsBoolean()) {
            power = -INTAKE_MAX_POWER;
            state = State.REVERSE;
        } else {
            state = State.STOPPED;
        }

        intakeMotor.setPower(power);

        telemetry.addData("Intake State: ", getState());
    }

    public State getState() {
        return state;
    }
}
