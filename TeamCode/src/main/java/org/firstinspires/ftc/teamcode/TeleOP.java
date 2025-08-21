package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="12", group="")
public class TeleOP extends LinearOpMode {
    // Gamepads
    Gamepad driveOp = new Gamepad();
    Gamepad toolOp = new Gamepad(), prev_toolOp = new Gamepad();
    private DcMotorEx leftMotor, rightMotor, intakeMotor, accelMotor;
    private double forwardPower = 0.0, steeringPower = 0.0, maxPower = 1.0;

    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.get(DcMotorEx.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotorEx.class, "right_motor");
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        accelMotor = hardwareMap.get(DcMotorEx.class, "accel");
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            // Handle Gamepad States
            driveOp.copy(gamepad1);
            prev_toolOp.copy(toolOp);
            toolOp.copy(gamepad2);

            // Drivetrain Control
            forwardPower = driveOp.left_stick_y;
            steeringPower = -driveOp.right_stick_x;
//            maxPower = Range.scale(driveOp.right_trigger, 0, 1, 0.5, 1); // Boost

            leftMotor.setPower(Range.scale(forwardPower + steeringPower, 0, 1, 0, maxPower));
            rightMotor.setPower(Range.scale(forwardPower - steeringPower, 0, 1, 0, maxPower));

            intakeMotor.setPower(driveOp.right_trigger-driveOp.left_trigger);
            accelMotor.setPower(driveOp.a ? 1 : 0);
        }
    }
}
