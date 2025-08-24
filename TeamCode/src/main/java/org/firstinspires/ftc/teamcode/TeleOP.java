package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Accelerator;
import org.firstinspires.ftc.teamcode.Subsystems.Ascent;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.utils.GamepadEx;

@TeleOp(name="TeleOP", group="")
public class TeleOP extends LinearOpMode {
    // --------------------------------------- Subsystems --------------------------------------- //
    private GamepadEx controller;
    private Drivetrain drivetrain;
    private Intake intake;
    private Accelerator accelerator;
    private Ascent ascent;
//    private Barrier barrier;

    private boolean robotHasInit;

    // ------------------------------------------------------------------------------------------ //

    @Override
    public void runOpMode() {
        // ------------------------------ Subsystem Initialization ------------------------------ //
        controller = new GamepadEx(gamepad1);

        drivetrain = new Drivetrain(
                hardwareMap,
                telemetry,
                () -> controller.getLeftStickY(),
                () -> controller.getRightStickX(),
                () -> controller.getRightTrigger(),
//                () -> barrier.getState() != Barrier.State.HIDE // TODO: Implement mode switch
                () -> false
        );
        intake = new Intake(
                hardwareMap,
                telemetry,
                () -> controller.justPressed(GamepadEx.Button.DPAD_UP),
                () -> controller.justPressed(GamepadEx.Button.DPAD_DOWN)
        );
        accelerator = new Accelerator(
                hardwareMap,
                telemetry,
                () -> controller.justPressed(GamepadEx.Button.A)
        );
        ascent = new Ascent(
                hardwareMap,
                telemetry,
                () -> controller.justPressed(GamepadEx.Button.RIGHT_BUMPER),
                () -> controller.justPressed(GamepadEx.Button.LEFT_BUMPER)
        );

//        barrier = new Barrier(
//                hardwareMap,
//                telemetry,
//                () -> controller.justPressed(GamepadEx.Button.Y),
//                () -> controller.justPressed(GamepadEx.Button.X),
//                () -> robotHasInit,
//                () -> accelerator.getState() == Accelerator.State.RUNNING
//        );

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            controller.update();

            // Wait for the robot to Move a little to init the subsystems -> avoid penalties b4
            // match timer starts
            if (!robotHasInit) {
                if(Math.abs(controller.getRightStickX()) > 0.1
                        || Math.abs(controller.getLeftStickY()) > 0.1) {
                    robotHasInit = true;
                }
                continue;
            }

            // ------------------------------- Subsystem Updates -------------------------------- //
            drivetrain.update();
            intake.update();
            accelerator.update();
            ascent.update();
//            barrier.update();

            telemetry.update();
        }
    }
}
