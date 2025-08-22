package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.DoubleSupplier;

@Config
public class Drivetrain {
    private DcMotorEx leftMotor, rightMotor;
    private DoubleSupplier forwardPower, turnPower, accelPower;
    public static double leftKS = 0.19, leftKV = 1.0, rightKS = 0.12, rightKV = 1.1;

    private Telemetry telemetry;

    public Drivetrain(
            HardwareMap hm,
            Telemetry telemetry,
            DoubleSupplier forwardPower,
            DoubleSupplier turnPower,
            DoubleSupplier accelPower
    ) {
        leftMotor = hm.get(DcMotorEx.class, "left_motor");
        rightMotor = hm.get(DcMotorEx.class, "right_motor");
        rightMotor.setDirection(DcMotorEx.Direction.REVERSE);
        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        this.forwardPower = forwardPower;
        this.turnPower = turnPower;
        this.accelPower = accelPower;

        this.telemetry = telemetry;
    }

    public double feedforward(double input, boolean isRight) {
        if (isRight) return rightKS * Math.signum(input) + rightKV * input;

        return leftKS * Math.signum(input) + leftKV * input;
    }

    public void update() {
        double max_mapping_power = Range.scale(accelPower.getAsDouble(), 0, 1, 0.35, 1);
        double leftPower = Range.scale(
                forwardPower.getAsDouble() + turnPower.getAsDouble(),
                0.0,
                1.0,
                0.0,
                max_mapping_power
        );
        double rightPower = Range.scale(
                forwardPower.getAsDouble() - turnPower.getAsDouble(),
                0.0,
                1.0,
                0.0,
                max_mapping_power
        );

        leftMotor.setPower(feedforward(leftPower, false));
        rightMotor.setPower(feedforward(rightPower, true));

        telemetry.addData("Forward Power: ", forwardPower.getAsDouble());
        telemetry.addData("Turn Power: ", turnPower.getAsDouble());
    }
}
