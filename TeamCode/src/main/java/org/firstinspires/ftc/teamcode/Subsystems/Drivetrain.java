package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

@Config
public class Drivetrain {
    // ---------------------------------------- Hardware ---------------------------------------- //
    private DcMotorEx leftMotor, rightMotor;
    private DoubleSupplier forwardPower, turnPower, accelPower;

    private BooleanSupplier reversedHeading, hanging; // True Sets the heading to rear
    private boolean disabled;

    private Telemetry telemetry;

    // ------------------------------------------------------------------------------------------ //
    private double fwd_factor, max_mapping_power, left_power, right_power, max_raw_power,
            leftPower_map, rightPower_map;

    // ------------------------------------------------------------------------------------------ //

    public Drivetrain(
            HardwareMap hm,
            Telemetry telemetry,
            DoubleSupplier forwardPower,
            DoubleSupplier turnPower,
            DoubleSupplier accelPower,
            BooleanSupplier reversedHeading,
            BooleanSupplier hanging
    ) {
        leftMotor = hm.get(DcMotorEx.class, Constants.LEFT_MOTOR_NAME);
        rightMotor = hm.get(DcMotorEx.class, Constants.RIGHT_MOTOR_NAME);
        rightMotor.setDirection(DcMotorEx.Direction.REVERSE);
        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        this.forwardPower = forwardPower;
        this.turnPower = turnPower;
        this.accelPower = accelPower;

        this.reversedHeading = reversedHeading;
        this.hanging = hanging;

        this.telemetry = telemetry;
    }

    public double feedforward(double input, Constants.Motor motor) {
        // Formula: output = Math.signum(input) * Ks + input * kV
        return motor.getKS() * Math.signum(input) + motor.getKV() * input;
    }

    public void update() {
        disabled = hanging.getAsBoolean() | disabled;

        if(hanging.getAsBoolean()) {
            leftMotor.setMotorDisable();
            rightMotor.setMotorDisable();
        }

        if (disabled) return;

        // ------------------------------------ Calculations ------------------------------------ //
        fwd_factor = !reversedHeading.getAsBoolean() ? 1 : -1; // Reversed Heading Handling
        max_mapping_power = Range.scale(
                accelPower.getAsDouble(),
                0,
                1,
                Constants.DEFAULT_POWER,
                Constants.MAX_POWER
        ); // Map the Max Power Based on Trigger

        left_power = forwardPower.getAsDouble() * fwd_factor + turnPower.getAsDouble();
        right_power = forwardPower.getAsDouble() * fwd_factor - turnPower.getAsDouble();
        max_raw_power = Math.max(Math.abs(left_power), Math.abs(right_power));

        // Per Side/Motor Power Calculation
        leftPower_map = Range.scale(
                left_power/max_raw_power,
                0.0,
                1.0,
                0.0,
                max_mapping_power
        ) + Constants.KS_theta * Math.signum(turnPower.getAsDouble()); // KS_theta robot turn -> Smoothness
        rightPower_map = Range.scale(
                right_power/max_raw_power,
                0.0,
                1.0,
                0.0,
                max_mapping_power
        ) - Constants.KS_theta * Math.signum(turnPower.getAsDouble()); // KS_theta robot turn -> Smoothness

        // Set the Power to Motors (power calculated above -> Feedforward(L, R) -> Power Setting)
        leftMotor.setPower(feedforward(leftPower_map, Constants.Motor.LEFT));
        rightMotor.setPower(feedforward(rightPower_map, Constants.Motor.RIGHT));

        // --------------------------------------- Telemetry -------------------------------------- //
        telemetry.addData("[Drivetrain] Forward Power: ", forwardPower.getAsDouble());
        telemetry.addData("[Drivetrain] Turn Power: ", turnPower.getAsDouble());
    }
}
