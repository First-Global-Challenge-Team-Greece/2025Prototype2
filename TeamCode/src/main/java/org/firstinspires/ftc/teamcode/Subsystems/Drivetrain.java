package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

@Config
public class Drivetrain {
    private DcMotorEx leftMotor, rightMotor;
    private DoubleSupplier forwardPower, turnPower, accelPower;
    public static double[] KS = {0.0, 0.0};
    public static double[] KV = {1.0, 1.0};

    private BooleanSupplier reversedHeading;

    private Telemetry telemetry;

    public Drivetrain(
            HardwareMap hm,
            Telemetry telemetry,
            DoubleSupplier forwardPower,
            DoubleSupplier turnPower,
            DoubleSupplier accelPower,
            BooleanSupplier reversedHeading
    ) {
        leftMotor = hm.get(DcMotorEx.class, "lm");
        rightMotor = hm.get(DcMotorEx.class, "rm");
        rightMotor.setDirection(DcMotorEx.Direction.REVERSE);
        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        this.forwardPower = forwardPower;
        this.turnPower = turnPower;
        this.accelPower = accelPower;

        this.reversedHeading = reversedHeading;

        this.telemetry = telemetry;
    }

    public double feedforward(double input, int motor) {
        return KS[motor] * Math.signum(input) + KV[motor] * input;
    }

    public void update() {
        double fwd_factor = !reversedHeading.getAsBoolean() ? 1 : -1;
        double max_mapping_power = Range.scale(accelPower.getAsDouble(), 0, 1, 0.55, 1);
        double leftPower = Range.scale(
                forwardPower.getAsDouble() * fwd_factor + turnPower.getAsDouble(),
                0.0,
                1.0,
                0.0,
                max_mapping_power
        ) + 0.1 * Math.signum(turnPower.getAsDouble());
        double rightPower = Range.scale(
                forwardPower.getAsDouble() * fwd_factor - turnPower.getAsDouble(),
                0.0,
                1.0,
                0.0,
                max_mapping_power
        ) - 0.1 * Math.signum(turnPower.getAsDouble());

        leftMotor.setPower(feedforward(leftPower, 0));
        rightMotor.setPower(feedforward(rightPower, 1));

        telemetry.addData("Forward Power: ", forwardPower.getAsDouble());
        telemetry.addData("Turn Power: ", turnPower.getAsDouble());
    }
}
