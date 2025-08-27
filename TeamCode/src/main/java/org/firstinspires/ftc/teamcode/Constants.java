package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Subsystems.BarrierFiringPin.targets;

import org.firstinspires.ftc.teamcode.utils.PIDFCoefficients;

public class Constants {
    public static final String ROBOT_NAME = "MentaV2.0";

    // ----------------------------- Drivebase Motors Configuration ----------------------------- //
    public static final String LEFT_MOTOR_NAME = "lm";
    public static final String RIGHT_MOTOR_NAME = "rm";

    // -------------------------- DriveBase Constants Configuration ----------------------------- //
    public static final double KS_theta = 0.1; // Static gain for turning
    public static final double DEFAULT_POWER = 0.55; // Default power for driving
    public static final double MAX_POWER = 1.0; // Maximum power for driving

    public enum Motor {
        LEFT(0),
        RIGHT(1);

        // Feedforward constants
        // KS: is the static gain -> for Static Friction
        // KV: is the velocity gain -> Fixes Motor Inaccuracies Linearly
        public static final double[] KS = {0.07, 0.05};
        public static final double[] KV = {1.07, 1.0};

        private final int idx;

        Motor(int idx) {
            this.idx = idx;
        }

        public double getKS() {
            return KS[idx];
        }
        public double getKV() {
            return KV[idx];
        }
    }

    //// ------------------------------------- Mechanisms ------------------------------------- ////
    // ----------------------------------------- Intake ----------------------------------------- //
    public static final String INTAKE_MOTOR_NAME = "intake";
    public static final double INTAKE_MAX_POWER = 1.0; // Maximum power for intake motor
    public enum IntakeState {
        STOPPED(0),
        FORWARD(1),
        REVERSE(2);

        private final int idx;
        public static final double[] VELOCITIES = {0.0, 1.0, -1.0};

        IntakeState(int idx) {
            this.idx = idx;
        }

        public double getVelocity() {
            return VELOCITIES[idx];
        }
    }

    //----------------------------------------- Barrier------------------------------------------ //
    public static final String BARRIER_MOTOR_NAME = "barrier";

    public enum BarrierState {
        DOWN(0),
        UP(1);

        public final int idx;
        BarrierState(int idx) {
            this.idx = idx;
        }

        int[] pos = {-10, 70};

        public int getPos() {
            return targets[idx];
        }
    }

    public static final PIDFCoefficients barrier_pid_coeffs = new PIDFCoefficients(
            0.09,
            0.0,
            0.0,
            0.0,
            0.0,
            5,
            20,
            0.7
    );
    public static final int[] barrier_targets = {-10, 70}; // Raised, Lowered Position
    public static final double barrier_Kg = 0.1, barrier_max_vel = 1.0;


    // -------------------------------------- Accelerator --------------------------------------- //
    public static final String ACCELERATOR_MOTOR_NAME = "accel";
    public enum AcceleratorState {
        STOPPED(0),
        RUNNING(1);

        private final int idx;
        public static final double[] VELOCITIES = {0.0, 1.0};

        AcceleratorState(int idx) {
            this.idx = idx;
        }

        public double getVelocity() {
            return VELOCITIES[idx];
        }
    }
    // ----------------------------------------- Ascent ----------------------------------------- //
    public static final String ASCENT_MOTOR_NAME = "ascent";
    public static double ACCEL_ENABLE_TIMESTAMP = 10.0;
    public enum AscentState {
        STOPPED(0),
        ASCENDING(1),
        DESCENDING(2);

        private final int idx;
        public static final double[] VELOCITIES = {0.0, 1.0, -1.0};

        AscentState(int idx) {
            this.idx = idx;
        }

        public double getVelocity() {
            return VELOCITIES[idx];
        }
    }
    // --------------------------------------- Latch ---------------------------------------- //
    public static final String LATCH1_NAME = "latch1";
    public static final String LATCH2_NAME = "latch2";

    public static final double LATCH_RELEASE_TIME = 1.0; // sec

    public enum Latch {
        LATCH1(0),
        LATCH2(1);

        private final int idx;

        Latch(int idx) {
            this.idx = idx;
        }

        public int getIdx() {
            return idx;
        }
    }

    public enum LatchState {
        OPEN(0),
        CLOSED(1);

        private final int idx;
        public static final double[][] POSITIONS = {
                {0.0, 1.0},
                {0.0, 1.0}
        };

        LatchState(int idx) {
            this.idx = idx;
        }

        public double getPosition(Latch latch) {
            return POSITIONS[latch.getIdx()][idx];
        }
    }
    // ---------------------------------------- Fingers ----------------------------------------- //
    public static final String LEFT_FINGER_NAME = "left_finger";
    public static final String RIGHT_FINGER_NAME = "right_finger";

    public enum FINGER {
        LEFT(0),
        RIGHT(1);

        public final int idx;
        FINGER(int idx) {
            this.idx = idx;
        }

        public int getIdx() {
            return idx;
        }
    }

    public enum FingerState {
        HIDE(0),
        GUIDE(1),
        BLOCK(2);

        public final int idx;
        FingerState(int idx) {
            this.idx = idx;
        }

        public static double[][] positions = {{0.0, 0.56, 0.93}, {1.0, 0.4, 0.02}};

        public double getPosition(FINGER finger) {
            return positions[finger.getIdx()][idx];
        }
    }
}
