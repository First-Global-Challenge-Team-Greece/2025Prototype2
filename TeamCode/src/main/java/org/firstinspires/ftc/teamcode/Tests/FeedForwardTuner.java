package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Accelerator;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.TeleOP;

@Config
@TeleOp(name="12", group="")
public class FeedForwardTuner extends LinearOpMode {
    private DcMotorEx leftMotor, rightMotor;
    public static double KS = 0.0, KV = 1.0;

    private Telemetry dash_tele;

    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.get(DcMotorEx.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotorEx.class, "right_motor");
        dash_tele = FtcDashboard.getInstance().getTelemetry();

        waitForStart();

        while (opModeIsActive()) {
            leftMotor.setPower(KS * Math.signum(gamepad1.left_stick_y) + KV * gamepad1.left_stick_y);
            rightMotor.setPower(KS * Math.signum(gamepad1.left_stick_y) + KV * gamepad1.left_stick_y);

            dash_tele.addData("Target Vel: ", gamepad1.left_stick_y*(28*6000));
            dash_tele.addData("Left Actual Vel: ", leftMotor.getVelocity());
            dash_tele.addData("Right Actual Vel: ", rightMotor.getVelocity());
        }
    }
}
