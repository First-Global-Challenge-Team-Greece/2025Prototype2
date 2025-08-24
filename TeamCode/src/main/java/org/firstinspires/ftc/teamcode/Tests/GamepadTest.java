package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.GamepadEx;

// This TEST FILE: tests rising edge detection class for gamepad. For latency and logic bugs!

@Disabled // TEST FILE: It is Disabled the OpMode so it doesnt show up in the driver station
@TeleOp(name="GamepadTest", group="Tests")
public class GamepadTest extends LinearOpMode {
    public GamepadEx controller;

    private boolean cur_states[] = new boolean[GamepadEx.Button.values().length];

    @Override
    public void runOpMode() {
        controller = new GamepadEx(gamepad1);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("A : ", gamepad1.a);
            telemetry.addData("A2: ", controller.isDown(GamepadEx.Button.A));
            telemetry.addData("B : ", gamepad1.b);
            telemetry.addData("B2: ", controller.isDown(GamepadEx.Button.B));
            telemetry.addData("X : ", gamepad1.x);
            telemetry.addData("X2: ", controller.isDown(GamepadEx.Button.X));
            telemetry.addData("Y : ", gamepad1.y);
            telemetry.addData("Y2: ", controller.isDown(GamepadEx.Button.Y));
            telemetry.addData("DPad Up : ", gamepad1.dpad_up);
            telemetry.addData("DPad Up2: ", controller.isDown(GamepadEx.Button.DPAD_UP));
            telemetry.addData("DPad Down : ", gamepad1.dpad_down);
            telemetry.addData("DPad Down2: ", controller.isDown(GamepadEx.Button.DPAD_DOWN));
            telemetry.addData("DPad Left : ", gamepad1.dpad_left);
            telemetry.addData("DPad Left2: ", controller.isDown(GamepadEx.Button.DPAD_LEFT));
            telemetry.addData("DPad Right : ", gamepad1.dpad_right);
            telemetry.addData("DPad Right2: ", controller.isDown(GamepadEx.Button.DPAD_RIGHT));
            telemetry.addData("Left Stick X : ", gamepad1.left_stick_x);
            telemetry.addData("Left Stick X2: ", controller.getLeftStickX());
            telemetry.addData("Left Stick Y : ", gamepad1.left_stick_y);
            telemetry.addData("Left Stick Y2: ", controller.getLeftStickY());
            telemetry.addData("Right Stick X : ", gamepad1.right_stick_x);
            telemetry.addData("Right Stick X2: ", controller.getRightStickX());
            telemetry.addData("Right Stick Y : ", gamepad1.right_stick_y);
            telemetry.addData("Right Stick Y2: ", controller.getRightStickY());
            telemetry.addData("Left Trigger : ", gamepad1.left_trigger);
            telemetry.addData("Left Trigger2: ", controller.getLeftTrigger());
            telemetry.addData("Right Trigger : ", gamepad1.right_trigger);
            telemetry.addData("Right Trigger2: ", controller.getRightTrigger());
            telemetry.addData("Left Bumper : ", gamepad1.left_bumper);
            telemetry.addData("Left Bumper2: ", controller.isDown(GamepadEx.Button.LEFT_BUMPER));
            telemetry.addData("Right Bumper : ", gamepad1.right_bumper);
            telemetry.addData("Right Bumper2: ", controller.isDown(GamepadEx.Button.RIGHT_BUMPER));
            telemetry.addData("Just Pressed A: ", controller.justPressed(GamepadEx.Button.A));
            telemetry.addData("Just Pressed B: ", controller.justPressed(GamepadEx.Button.B));
            telemetry.addData("Just Pressed X: ", controller.justPressed(GamepadEx.Button.X));
            telemetry.addData("Just Pressed Y: ", controller.justPressed(GamepadEx.Button.Y));
            telemetry.addData("Just Pressed DPad Up: ", controller.justPressed(GamepadEx.Button.DPAD_UP));
            telemetry.addData("Just Pressed DPad Down: ", controller.justPressed(GamepadEx.Button.DPAD_DOWN));
            telemetry.addData("Just Pressed DPad Left: ", controller.justPressed(GamepadEx.Button.DPAD_LEFT));
            telemetry.addData("Just Pressed DPad Right: ", controller.justPressed(GamepadEx.Button.DPAD_RIGHT));
            telemetry.addData("Just Pressed Left Stick: ", controller.justPressed(GamepadEx.Button.LEFT_STICK_BUTTON));
            telemetry.addData("Just Pressed Right Stick: ", controller.justPressed(GamepadEx.Button.RIGHT_STICK_BUTTON));
            telemetry.addData("Just Pressed Left Bumper: ", controller.justPressed(GamepadEx.Button.LEFT_BUMPER));
            telemetry.addData("Just Pressed Right Bumper: ", controller.justPressed(GamepadEx.Button.RIGHT_BUMPER));
            controller.update();

            telemetry.update();
        }
    }
}
