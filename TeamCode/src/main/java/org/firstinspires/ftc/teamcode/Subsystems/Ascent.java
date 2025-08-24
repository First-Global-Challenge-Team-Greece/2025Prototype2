package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.function.BooleanSupplier;

@Config
public class Ascent {
    // ---------------------------------------- Hardware ---------------------------------------- //
    private DcMotorEx ascentMotor;
    public static double ASCENT_MAX_POWER = 1.0;
    private BooleanSupplier raiseBtn, lowerBtn;

    private double enable_timestamp = 110, start_time = 0.0; // seconds
    private boolean firct_update_cycle = false;

    // ------------------------------------------------------------------------------------------ //

    public Ascent(HardwareMap hm,
                  BooleanSupplier raiseBtn,
                  BooleanSupplier lowerBtn
                  ) {
        ascentMotor = hm.get(DcMotorEx.class, "ascent");
        ascentMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.raiseBtn = raiseBtn;
        this.lowerBtn = lowerBtn;
    }

    public void update() {
        if (!firct_update_cycle) { // First Cycle ++ Capture starting time
            firct_update_cycle = true;
            start_time = System.currentTimeMillis() / 1000.0;
        }

        // time < thresh: do not allow ascent SAFETY FEATURE
        if((System.currentTimeMillis() / 1000.0 - start_time) < enable_timestamp) return;

        // If no button is pressed, do not set power (Less Variable Writing Optimization)
        if(!raiseBtn.getAsBoolean() && !lowerBtn.getAsBoolean()) return;

        // Set power based on button presses
        ascentMotor.setPower(raiseBtn.getAsBoolean() ? ASCENT_MAX_POWER : 0);
        ascentMotor.setPower(lowerBtn.getAsBoolean() ? -ASCENT_MAX_POWER : 0);

        // -------------------------------------- Telemetry ------------------------------------- //
    }
}
