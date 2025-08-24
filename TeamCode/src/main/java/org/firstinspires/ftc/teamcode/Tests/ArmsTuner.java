package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Barrier;

@Config
@TeleOp(name="ArmsTuner", group="Tests")
public class ArmsTuner extends LinearOpMode {
    public static double posL, posR;
    private ServoImplEx leftArm, rightArm;
    public static Barrier.State state = Barrier.State.HIDE;

    private Barrier barrier;

    @Override
    public void runOpMode() {
        leftArm = hardwareMap.get(ServoImplEx.class, "left_arm");
        rightArm = hardwareMap.get(ServoImplEx.class, "right_arm");
//        barrier = new Barrier(hardwareMap, telemetry, () -> gamepad1.y, () -> true);

        waitForStart();

        while (opModeIsActive()) {
            leftArm.setPosition(posL);
            rightArm.setPosition(posR);
//            barrier.update();
//            barrier.setState(state);
        }
    }
}
