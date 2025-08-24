package org.firstinspires.ftc.teamcode;

public class Constants {
    public static final String ROBOT_NAME = "-";

    // ----------------------------- Drivebase Motors Configuration ----------------------------- //
    public static final String LEFT_MOTOR_NAME = "lm";
    public static final String RIGHT_MOTOR_NAME = "rm";

    // -------------------------- DriveBase Constants Configuration ----------------------------- //
    public static final double KS_theta = 0.1; // Static gain for turning
    public static final double DEFAULT_POWER = 0.55; // Default power for driving
    public static final double MAX_POWER = 1.0; // Maximum power for driving

    public static enum Motor {
        LEFT(0),
        RIGHT(1);

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
    public static enum IntakeState {
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

    // --------------------------------------- Accelerator --------------------------------------- //
    public static final String ACCELERATOR_MOTOR_NAME = "accel";
    public static enum AcceleratorState {
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
    public static double ACCEL_ENABLE_TIMESTAMP = 110;
    public static enum AscentState {
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
    // ------------------------------------------------------------------------------------------ //
}
