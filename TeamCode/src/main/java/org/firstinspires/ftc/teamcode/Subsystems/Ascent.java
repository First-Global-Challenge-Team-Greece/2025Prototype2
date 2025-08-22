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
    private BooleanSupplier button;

    public Ascent(HardwareMap hm,
                  BooleanSupplier button) {
        ascentMotor = hm.get(DcMotorEx.class, "ascent");
        ascentMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.button = button;
    }

    public void update() {
        ascentMotor.setPower(button.getAsBoolean() ? ASCENT_MAX_POWER : 0);
    }
}
