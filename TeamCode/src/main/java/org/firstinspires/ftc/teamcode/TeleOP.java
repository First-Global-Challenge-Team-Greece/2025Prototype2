package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Accelerator;
import org.firstinspires.ftc.teamcode.Subsystems.Ascent;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

@TeleOp(name="TeleOP", group="")
public class TeleOP extends LinearOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Accelerator accelerator;
    private Ascent ascent;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(
                hardwareMap,
                telemetry,
                () -> -gamepad1.left_stick_y,
                () -> -gamepad1.right_stick_x,
                () -> gamepad1.right_trigger
        );
        intake = new Intake(
                hardwareMap,
                telemetry,
                () -> gamepad1.dpad_up,
                () -> gamepad1.dpad_down
        );
        accelerator = new Accelerator(
                hardwareMap,
                telemetry,
                () -> gamepad1.a,
                () -> gamepad1.right_bumper,
                () -> gamepad1.left_bumper
        );
        ascent = new Ascent(hardwareMap, () -> gamepad1.x);


        waitForStart();

        while (opModeIsActive()) {
            drivetrain.update();
            intake.update();
            accelerator.update();
            ascent.update();

            telemetry.update();
        }
    }
}
