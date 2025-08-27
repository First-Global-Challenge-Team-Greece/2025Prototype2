package org.firstinspires.ftc.teamcode.utils;

public class PIDFCoefficients {
    public double Kp, Ki, Kd, Kf, Alpha, deadzone, integralWorkingBounds, integralClippingBounds;

    public PIDFCoefficients(
            double Kp,
            double Ki,
            double Kd,
            double Kf,
            double Alpha,
            double deadzone,
            double integralWorkingBounds,
            double integralClippingBounds
    ) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.Kf = Kf;
        this.Alpha = Alpha;
        this.deadzone = deadzone;
        this.integralWorkingBounds = integralWorkingBounds;
        this.integralClippingBounds = integralClippingBounds;
    }

    public PIDFCoefficients() {
        this(0, 0, 0, 0, 0, 0,
                Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public PIDFCoefficients(PIDFCoefficients other) {
        this(
            other.Kp,
            other.Ki,
            other.Kd,
            other.Kf,
            other.Alpha,
            other.deadzone,
            other.integralWorkingBounds,
            other.integralClippingBounds
        );
    }

    public PIDFCoefficients(double Kp, double Ki, double Kd) {
        this(Kp, Ki, Kd, 0, 0, 0,
                Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public String toString() {
        return String.format("Kp: %.4f, Ki: %.4f, Kd: %.4f, Kf: %.4f, Alpha: %.4f, Deadzone: %.4f, Integral Working Bounds: %.4f, Integral Clipping Bounds: %.4f",
                Kp, Ki, Kd, Kf, Alpha, deadzone, integralWorkingBounds, integralClippingBounds);
    }

    public double getKp() { return Kp;}
    public double getKi() { return Ki;}
    public double getKd() { return Kd;}
    public double getKf() { return Kf;}
    public double getAlpha() { return Alpha;}
    public double getDeadzone() { return deadzone; }
    public double getIntegralWorkingBounds() { return integralWorkingBounds; }
    public double getIntegralClippingBounds() { return integralClippingBounds;}
}
