package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
@TeleOp(name="FeedForwardTuner", group="Tests")
public class FeedForwardTuner extends LinearOpMode {
    private DcMotorEx leftMotor, rightMotor;
    public static double KSL = 0.19, KSR=0.12, KVL = 1.0, KVR = 1.1, power=0;
    private Telemetry dash_tele;

    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.get(DcMotorEx.class, "lm");
        rightMotor = hardwareMap.get(DcMotorEx.class, "rm");
        rightMotor.setDirection(DcMotorEx.Direction.REVERSE);
        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        dash_tele = FtcDashboard.getInstance().getTelemetry();

        waitForStart();

        while (opModeIsActive()) {
//            power = gamepad1.left_stick_y;
            leftMotor.setPower(KSL * Math.signum(power) + KVL * power);
            rightMotor.setPower(KSR * Math.signum(power) + KVR * power);

            dash_tele.addData("Target Vel: ", power*(28*500));
            dash_tele.addData("Left Actual Vel: ", leftMotor.getVelocity());
            dash_tele.addData("Right Actual Vel: ", rightMotor.getVelocity());
            dash_tele.update();
        }
    }
}
