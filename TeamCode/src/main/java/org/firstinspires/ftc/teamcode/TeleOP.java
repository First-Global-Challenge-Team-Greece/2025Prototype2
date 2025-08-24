package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Accelerator;
import org.firstinspires.ftc.teamcode.Subsystems.Ascent;
import org.firstinspires.ftc.teamcode.Subsystems.Barrier;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

@TeleOp(name="TeleOP", group="")
public class TeleOP extends LinearOpMode {
    private Drivetrain drivetrain;
    private Intake intake;
    private Accelerator accelerator;
    private Ascent ascent;
//    private Barrier barrier;

    private boolean robotHasInit;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(
                hardwareMap,
                telemetry,
                () -> -gamepad1.left_stick_y,
                () -> gamepad1.right_stick_x,
                () -> gamepad1.right_trigger,
//                () -> barrier.getState() != Barrier.State.HIDE // TODO: Implement mode switch
                () -> false
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
                () -> gamepad1.a
        );
        ascent = new Ascent(hardwareMap, () -> gamepad1.right_bumper, () -> gamepad1.left_bumper);

//        barrier = new Barrier(
//                hardwareMap,
//                telemetry,
//                ()->gamepad1.y,
//                ()->gamepad1.x,
//                () -> robotHasInit,
//                () -> accelerator.getState() == Accelerator.State.RUNNING
//        );

        waitForStart();

        while (opModeIsActive()) {
            if (!robotHasInit) {
                if(Math.abs(gamepad1.right_stick_x) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1) {
                    robotHasInit = true;
                }
                continue;
            }

            drivetrain.update();
            intake.update();
            accelerator.update();
            ascent.update();
//            barrier.update();

            telemetry.update();
        }
    }
}
