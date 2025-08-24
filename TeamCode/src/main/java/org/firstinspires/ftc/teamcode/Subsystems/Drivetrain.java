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

    // Feedforward constants
    // KS: is the static gain -> for Static Friction
    // KV: is the velocity gain -> Fixes Motor Inaccuracies Linearly

    private BooleanSupplier reversedHeading; // True Sets the heading to rear

    private Telemetry telemetry;

    // ------------------------------------------------------------------------------------------ //

    public Drivetrain(
            HardwareMap hm,
            Telemetry telemetry,
            DoubleSupplier forwardPower,
            DoubleSupplier turnPower,
            DoubleSupplier accelPower,
            BooleanSupplier reversedHeading
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

        this.telemetry = telemetry;
    }

    public double feedforward(double input, Constants.Motor motor) {
        // Formula: output = Math.signum(input) * Ks + input * kV
        return motor.getKS() * Math.signum(input) + motor.getKV() * input;
    }

    public void update() {
        double fwd_factor = !reversedHeading.getAsBoolean() ? 1 : -1; // Reversed Heading Handling
        double max_mapping_power = Range.scale(
                accelPower.getAsDouble(),
                0,
                1,
                Constants.DEFAULT_POWER,
                Constants.MAX_POWER
        ); // Map the Max Power Based on Trigger

        // Per Side/Motor Power Calculation
        double leftPower = Range.scale(
                forwardPower.getAsDouble() * fwd_factor + turnPower.getAsDouble(),
                0.0,
                1.0,
                0.0,
                max_mapping_power
        ) + Constants.KS_theta * Math.signum(turnPower.getAsDouble()); // KS_theta robot turn -> Smoothness // TODO Na to alla 0.1 kai 0.2
        double rightPower = Range.scale(
                forwardPower.getAsDouble() * fwd_factor - turnPower.getAsDouble(),
                0.0,
                1.0,
                0.0,
                max_mapping_power
        ) - Constants.KS_theta * Math.signum(turnPower.getAsDouble()); // KS_theta robot turn -> Smoothness

        // Set the Power to Motors (power calculated above -> Feedforward(L, R) -> Power Setting)
        leftMotor.setPower(feedforward(leftPower, Constants.Motor.LEFT));
        rightMotor.setPower(feedforward(rightPower, Constants.Motor.RIGHT));

        // --------------------------------------- Telemetry -------------------------------------- //
        telemetry.addData("[Drivetrain] Forward Power: ", forwardPower.getAsDouble());
        telemetry.addData("[Drivetrain] Turn Power: ", turnPower.getAsDouble());
    }
}
