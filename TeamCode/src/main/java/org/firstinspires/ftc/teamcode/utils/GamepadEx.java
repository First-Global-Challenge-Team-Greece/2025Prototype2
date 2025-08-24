package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.Gamepad;

// UTIL FILE: This file extends the Gamepad class to provide additional functionality for button
// presses and joystick inputs. (Rising Edge Detection, Falling Edge Detection, Correct Numerical,
// signs on joysticks)

public class GamepadEx {
    private Gamepad controller, prevController;

    public enum Button { // Enum for button mapping
        A, B, X, Y, DPAD_UP, DPAD_DOWN, DPAD_LEFT, DPAD_RIGHT,
        LEFT_BUMPER, RIGHT_BUMPER, LEFT_STICK_BUTTON, RIGHT_STICK_BUTTON
    }

    public GamepadEx(Gamepad gamepad) {
        this.controller = gamepad;
        this.prevController = new Gamepad();
    }

    public void update(Gamepad gamepad) {
        prevController.copy(controller);
        controller.copy(gamepad);
    }

    // ---------------------------------- Rising Edge Detection --------------------------------- //
    public boolean justPressed(Button button) {
        switch (button) {
            case A: return controller.a && !prevController.a;
            case B: return controller.b && !prevController.b;
            case X: return controller.x && !prevController.x;
            case Y: return controller.y && !prevController.y;
            case DPAD_UP: return controller.dpad_up && !prevController.dpad_up;
            case DPAD_DOWN: return controller.dpad_down && !prevController.dpad_down;
            case DPAD_LEFT: return controller.dpad_left && !prevController.dpad_left;
            case DPAD_RIGHT: return controller.dpad_right && !prevController.dpad_right;
            case LEFT_BUMPER: return controller.left_bumper && !prevController.left_bumper;
            case RIGHT_BUMPER: return controller.right_bumper && !prevController.right_bumper;
            case LEFT_STICK_BUTTON: return controller.left_stick_button && !prevController.left_stick_button;
            case RIGHT_STICK_BUTTON: return controller.right_stick_button && !prevController.right_stick_button;
        }
        return false;
    }

    // --------------------------------- Falling Edge Detection --------------------------------- //
    public boolean justReleased(Button button) {
        switch (button) {
            case A: return !controller.a && prevController.a;
            case B: return !controller.b && prevController.b;
            case X: return !controller.x && prevController.x;
            case Y: return !controller.y && prevController.y;
            case DPAD_UP: return !controller.dpad_up && prevController.dpad_up;
            case DPAD_DOWN: return !controller.dpad_down && prevController.dpad_down;
            case DPAD_LEFT: return !controller.dpad_left && prevController.dpad_left;
            case DPAD_RIGHT: return !controller.dpad_right && prevController.dpad_right;
            case LEFT_BUMPER: return !controller.left_bumper && prevController.left_bumper;
            case RIGHT_BUMPER: return !controller.right_bumper && prevController.right_bumper;
            case LEFT_STICK_BUTTON: return !controller.left_stick_button && prevController.left_stick_button;
            case RIGHT_STICK_BUTTON: return !controller.right_stick_button && prevController.right_stick_button;
        }
        return false;
    }

    // ------------------------------------- Button States -------------------------------------- //
    public boolean isDown(Button button) {
        switch (button) {
            case A: return controller.a;
            case B: return controller.b;
            case X: return controller.x;
            case Y: return controller.y;
            case DPAD_UP: return controller.dpad_up;
            case DPAD_DOWN: return controller.dpad_down;
            case DPAD_LEFT: return controller.dpad_left;
            case DPAD_RIGHT: return controller.dpad_right;
            case LEFT_BUMPER: return controller.left_bumper;
            case RIGHT_BUMPER: return controller.right_bumper;
            case LEFT_STICK_BUTTON: return controller.left_stick_button;
            case RIGHT_STICK_BUTTON: return controller.right_stick_button;
        }
        return false;
    }

    public boolean isUp(Button button) {
        switch (button) {
            case A: return !controller.a;
            case B: return !controller.b;
            case X: return !controller.x;
            case Y: return !controller.y;
            case DPAD_UP: return !controller.dpad_up;
            case DPAD_DOWN: return !controller.dpad_down;
            case DPAD_LEFT: return !controller.dpad_left;
            case DPAD_RIGHT: return !controller.dpad_right;
            case LEFT_BUMPER: return !controller.left_bumper;
            case RIGHT_BUMPER: return !controller.right_bumper;
            case LEFT_STICK_BUTTON: return !controller.left_stick_button;
            case RIGHT_STICK_BUTTON: return !controller.right_stick_button;
        }
        return false;
    }

    // ----------------------------------------- Linear ----------------------------------------- //
    public double getLeftStickX() {
        return controller.left_stick_x;
    }

    public double getLeftStickY() {
        return -controller.left_stick_y; // Minus to fix the incorrect joystick sign (+ -> up)
    }

    public double getRightStickX() {
        return controller.right_stick_x;
    }

    public double getRightStickY() {
        return -controller.right_stick_y; // Minus to fix the incorrect joystick sign (+ -> up)
    }

    public double getLeftTrigger() {
        return controller.left_trigger;
    }

    public double getRightTrigger() {
        return controller.right_trigger;
    }
}
