package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Subsystems.Accelerator;
import org.firstinspires.ftc.teamcode.Subsystems.Ascent;
import org.firstinspires.ftc.teamcode.Subsystems.Barrier;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

@Config
@TeleOp(name="BarrierArmsV2", group="")
public class BarrierArmsV2 extends LinearOpMode {
    private DcMotorEx leftMotor, rightMotor;
    private ServoImplEx leftArm, rightArm;

    private double posLeft[] = {0.62, 0.54, 0.26}, posRight[] = {0.36, 0.44, 0.8};
    public static double[] KS = {0.19, 0.12};
    public static double[] KV = {1.0, 1.1};

    private int idx = 0;
    private boolean prev_btn = false;

    public double feedforward(double input, int motor) {
        return KS[motor] * Math.signum(input) + KV[motor] * input;
    }

    @Override
    public void runOpMode() {
        leftArm = hardwareMap.get(ServoImplEx.class, "left_arm");
        rightArm = hardwareMap.get(ServoImplEx.class, "right_arm");
        leftMotor = hardwareMap.get(DcMotorEx.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotorEx.class, "right_motor");
        rightMotor.setDirection(DcMotorEx.Direction.REVERSE);
        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            double max_mapping_power = Range.scale(gamepad1.right_trigger, 0, 1, 0.55, 1);
            double leftPower = Range.scale(
                    gamepad1.left_stick_y - gamepad1.right_stick_x,
                    0.0,
                    1.0,
                    0.0,
                    max_mapping_power
            ) - 0.1 * Math.signum(gamepad1.right_stick_x);
            double rightPower = Range.scale(
                    gamepad1.left_stick_y + gamepad1.right_stick_x,
                    0.0,
                    1.0,
                    0.0,
                    max_mapping_power
            ) + 0.1 * Math.signum(gamepad1.right_stick_x);

            leftMotor.setPower(feedforward(leftPower, 0));
            rightMotor.setPower(feedforward(rightPower, 1));


            if(gamepad1.a && !prev_btn) {
                idx++;
                if (idx > posLeft.length-1) idx = 0;
                leftArm.setPosition(posLeft[idx]);
                rightArm.setPosition(posRight[idx]);
            }
            prev_btn = gamepad1.a;
        }
    }
}
