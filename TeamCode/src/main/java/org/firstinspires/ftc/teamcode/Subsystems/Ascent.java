package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.function.BooleanSupplier;

@Config
public class Ascent {
    private DcMotorEx ascentMotor;
    public static double ASCENT_MAX_POWER = 1.0;
    private BooleanSupplier raiseBtn, lowerBtn;

    public Ascent(HardwareMap hm,
                  BooleanSupplier raiseBtn,
                  BooleanSupplier lowerBtn) {
        ascentMotor = hm.get(DcMotorEx.class, "ascent");
        ascentMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.raiseBtn = raiseBtn;
        this.lowerBtn = lowerBtn;
    }

    public void update() {
        if(!raiseBtn.getAsBoolean() && !lowerBtn.getAsBoolean()) return;

        ascentMotor.setPower(raiseBtn.getAsBoolean() ? ASCENT_MAX_POWER : 0);
        ascentMotor.setPower(lowerBtn.getAsBoolean() ? -ASCENT_MAX_POWER : 0);
    }
}
