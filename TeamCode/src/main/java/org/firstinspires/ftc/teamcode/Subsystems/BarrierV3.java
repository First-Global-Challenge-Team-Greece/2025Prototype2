package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.utils.PIDFControllerEx;

import java.util.function.BooleanSupplier;

@Config
public class BarrierV3 {
    // ----------------------------------------- Hardware --------------------------------------- //
    private DcMotorEx motor;
    private BooleanSupplier toggleBtn;
    private PIDFControllerEx controller;

    private Constants.BarrierState state = Constants.BarrierState.DOWN;

    // ------------------------------------------------------------------------------------------ //
    private Telemetry telemetry;

    public BarrierV3(HardwareMap hm,
                     Telemetry telemetry,
                     BooleanSupplier toggleBtn) {
        motor = hm.get(DcMotorEx.class, Constants.BARRIER_MOTOR_NAME);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.controller = new PIDFControllerEx(Constants.barrier_pid_coeffs);

        this.toggleBtn = toggleBtn;

        this.telemetry = telemetry;
    }

    public void update() {
        // toggle barrier state
        if (toggleBtn.getAsBoolean()) state = (state == Constants.BarrierState.DOWN)
                ? Constants.BarrierState.UP
                :
                Constants.BarrierState.DOWN;

        // calculate motor power PID
        controller.setSetPoint(state.getPos());

        // Set power ++ Feedforward
        motor.setPower(
                Range.clip(
                        controller.calculate(
                                motor.getCurrentPosition()),
                        -Constants.barrier_max_vel,
                        Constants.barrier_max_vel
                ) - Constants.barrier_Kg * Math.cos(
                        Math.toRadians(Range.scale(motor.getCurrentPosition(),
                                0,
                                75,
                                90,
                                0
                        )))
        );

        // -------------------------------------- Telemetry ------------------------------------- //
        telemetry.addData("[Barrier] State: ", 0);
        telemetry.addData("[Barrier] Target: ", controller.getSetPoint());
        telemetry.addData("[Barrier] Position: ", motor.getCurrentPosition());
        telemetry.addData("[Barrier] Error: ", controller.getPositionError());
        telemetry.addData("[Barrier] Power: ", motor.getPower());
        telemetry.addData("[Barrier] PID: ", controller.calculate(motor.getCurrentPosition()));
    }
}
