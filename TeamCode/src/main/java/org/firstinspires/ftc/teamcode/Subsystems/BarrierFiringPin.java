package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.utils.PIDFCoefficients;
import org.firstinspires.ftc.teamcode.utils.PIDFControllerEx;

import java.util.function.BooleanSupplier;

@Config
public class BarrierFiringPin {
    // ----------------------------------------- Hardware --------------------------------------- //
    private DcMotorEx pusherMotor;
    private BooleanSupplier activatePusherBtn, hanging;
    public static double vel = 1.0;
    private final PIDFCoefficients coeffs = new PIDFCoefficients(
            0.08,
            0.0,
            0.0,
            0.0,
            0.0,
            5,
            20,
            0.7
    );
    public static int[] targets = {0, 380};
    private int setPoint = targets[0], count = 0;
    public static int reps = 4, threshold = 5;
    private PIDFControllerEx controller;
    private boolean push_proc_running = false, extending = false, disabled = false;

    // ------------------------------------------------------------------------------------------ //
    private Telemetry telemetry;

    public BarrierFiringPin(HardwareMap hm,
                            Telemetry telemetry,
                            BooleanSupplier activatePusherBtn,
                            BooleanSupplier hanging) {
        pusherMotor = hm.get(DcMotorEx.class, Constants.BARRIER_MOTOR_NAME);
        pusherMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pusherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.controller = new PIDFControllerEx(coeffs);

        this.activatePusherBtn = activatePusherBtn;
        this.hanging = hanging;

        this.telemetry = telemetry;
    }

    public void update() {
        disabled = hanging.getAsBoolean() | disabled;

        if(hanging.getAsBoolean()) pusherMotor.setMotorDisable();

        if (disabled) return;
//        if (activatePusherBtn.getAsBoolean()) {
//            push_proc_running = true;
//            extending = true;
//            count = 0;
//        }
//
//        controller.setSetPoint(targets[extending ? 1 : 0]);
//        pusherMotor.setPower(
//                Range.clip(controller.calculate(pusherMotor.getCurrentPosition()), -vel, vel)
//        );
//
//        if (push_proc_running) {
//            if(Math.abs(controller.getPositionError()) < threshold) {
//                extending = !extending; // Switch direction
//                if (!extending) count++;
//                if (count >= reps) push_proc_running = false;
//            }
//        }

        if (activatePusherBtn.getAsBoolean()) extending = !extending;

        controller.setCoefficients(coeffs); // In case we change them via dashboard

        controller.setSetPoint(targets[extending ? 1 : 0]);
        pusherMotor.setPower(
                Range.clip(controller.calculate(pusherMotor.getCurrentPosition()), -vel, vel)
        );

        // -------------------------------------- Telemetry ------------------------------------- //
        telemetry.addData("[Barrier] State: ", 0);
        telemetry.addData("[Barrier] Target: ", controller.getSetPoint());
        telemetry.addData("[Barrier] Position: ", pusherMotor.getCurrentPosition());
        telemetry.addData("[Barrier] Error: ", controller.getPositionError());
        telemetry.addData("[Barrier] Power: ", pusherMotor.getPower());
        telemetry.addData("[Barrier] PID: ", controller.calculate(pusherMotor.getCurrentPosition()));
    }
}
