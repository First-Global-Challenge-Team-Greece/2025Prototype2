package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.Constants;

import java.util.function.BooleanSupplier;

@Config
public class Fingers {
    // --------------------------------------- Hardware ----------------------------------------- //
    private ServoImplEx leftFinger, rightFinger;

    private BooleanSupplier intaking, toggleBtn;

    private Constants.FingerState state;

    private boolean firstTime = true;

    public Fingers(HardwareMap hm, BooleanSupplier intaking, BooleanSupplier toggleBtn) {
        this.leftFinger = hm.get(ServoImplEx.class, Constants.LEFT_FINGER_NAME);
        this.rightFinger = hm.get(ServoImplEx.class, Constants.RIGHT_FINGER_NAME);
        setState(Constants.FingerState.BLOCK);
        this.intaking = intaking;
        this.toggleBtn = toggleBtn;
    }

    public void update() {
        if(firstTime) {
            setState(Constants.FingerState.HIDE);
            firstTime = false;
        }

        if(intaking.getAsBoolean()) setState(Constants.FingerState.HIDE);

        // Toggle
        if(toggleBtn.getAsBoolean()) {
            if(state == Constants.FingerState.HIDE) setState(Constants.FingerState.GUIDE);
            else setState(Constants.FingerState.HIDE);
        }
    }

    public void setState(Constants.FingerState state) {
        if(this.state == state) return; // No Change, (Less Variable Writing Optimization)
        this.state = state;
        leftFinger.setPosition(state.getPosition(Constants.FINGER.LEFT));
        rightFinger.setPosition(state.getPosition(Constants.FINGER.RIGHT));
    }
}
