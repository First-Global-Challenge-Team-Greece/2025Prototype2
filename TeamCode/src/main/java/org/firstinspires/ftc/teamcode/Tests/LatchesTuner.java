package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ServoImplEx;

// This TEST FILE: tunes the servo positions of the arms (++ FTCDashboard Config Variables)

//@Disabled // TEST FILE: It is Disabled the OpMode so it doesnt show up in the driver station
@Config
@TeleOp(name="ArmsTuner", group="Tests")
public class LatchesTuner extends LinearOpMode {
    public static double latch1_pos, latch2_pos;
    private ServoImplEx latch1, latch2;

    @Override
    public void runOpMode() {
        latch1 = hardwareMap.get(ServoImplEx.class, "latch1");
        latch2 = hardwareMap.get(ServoImplEx.class, "latch2");

        waitForStart();

        while (opModeIsActive()) {
            latch1.setPosition(latch1_pos);
            latch2.setPosition(latch2_pos);
        }
    }
}
