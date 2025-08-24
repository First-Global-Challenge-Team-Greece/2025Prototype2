package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.function.BooleanSupplier;

@Config
public class Ascent {
    // ---------------------------------------- Hardware ---------------------------------------- //
    private DcMotorEx ascentMotor;
    private BooleanSupplier raiseBtn, lowerBtn;

    private double start_time = 0.0; // seconds
    private boolean firct_update_cycle = false;

    private Constants.AscentState state = Constants.AscentState.STOPPED;

    private Telemetry telemetry;

    // ------------------------------------------------------------------------------------------ //

    public Ascent(HardwareMap hm,
                  Telemetry telemetry,
                  BooleanSupplier raiseBtn,
                  BooleanSupplier lowerBtn
                  ) {
        ascentMotor = hm.get(DcMotorEx.class, Constants.ASCENT_MOTOR_NAME);
        ascentMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ascentMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.raiseBtn = raiseBtn;
        this.lowerBtn = lowerBtn;

        this.telemetry = telemetry;
    }

    public void update() {
        if (!firct_update_cycle) { // First Cycle ++ Capture starting time
            firct_update_cycle = true;
            start_time = System.currentTimeMillis() / 1000.0;
        }

        // time < thresh: do not allow ascent SAFETY FEATURE
//        if((System.currentTimeMillis() / 1000.0 - start_time) < Constants.ACCEL_ENABLE_TIMESTAMP) return;

//         If no button is pressed, do not set power (Less Variable Writing Optimization)
        if(!raiseBtn.getAsBoolean() && !lowerBtn.getAsBoolean()) return;

        if(raiseBtn.getAsBoolean()) {
            if(state != Constants.AscentState.ASCENDING) {
                setState(Constants.AscentState.ASCENDING);
            } else {
                setState(Constants.AscentState.STOPPED);
            }
        }

        // ------------------------------------- Telemetry -------------------------------------- //
        telemetry.addData("[Ascent] State: ", state);
    }

    public void setState(Constants.AscentState state) {
        if(this.state == state) return; // No Change, (Less Variable Writing Optimization)
        this.state = state;
        ascentMotor.setPower(state.getVelocity());
    }

    public Constants.AscentState getState() {
        return state;
    }
}
