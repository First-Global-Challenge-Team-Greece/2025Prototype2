package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.Constants;

public class Compartment {
    private ServoImplEx latch1, latch2;
    private Constants.LatchState state = Constants.LatchState.CLOSED;
    private double initialTime = 0;
    private boolean firstTime = true, finishedAction = false;

    public Compartment(HardwareMap hm) {
        this.latch1 = hm.get(ServoImplEx.class, Constants.LATCH1_NAME);
        this.latch2 = hm.get(ServoImplEx.class, Constants.LATCH2_NAME);
    }

    public void update() {
        if (finishedAction) return; // This subsystem is need only at the first seconds of the game

        if(firstTime) {
            // First Update Loop
            initialTime = System.currentTimeMillis() / 1000.0;
            firstTime = false;
            setState(Constants.LatchState.OPEN);
        }

        if(System.currentTimeMillis() / 1000.0 - initialTime >= Constants.LATCH_RELEASE_TIME) {
            // Action Finished
            finishedAction = true;
            setState(Constants.LatchState.CLOSED);

            // Disable PWM for less power consumption
            latch1.setPwmDisable();
            latch2.setPwmDisable();
        }
    }

    public void setState(Constants.LatchState state) {
        if(this.state == state) return; // No Change, (Less Variable Writing Optimization)

        this.state = state;
        latch1.setPosition(state.getPosition(Constants.Latch.LATCH1));
        latch2.setPosition(state.getPosition(Constants.Latch.LATCH2));
    }
}
