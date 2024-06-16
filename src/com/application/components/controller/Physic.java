package com.application.components.controller;

import com.application.components.object.CubeObject;
import com.application.components.object.CylinderObject;

public class Physic {

    public static double calAcc() {
        return getTotalForce() / Controller.getObj().getMass();
    }

    public static double getTotalForce() {
        double totalForce = 0.0d;
        double normalForce = Controller.getObj().getMass() * 10.0d;
        totalForce += Controller.getObj().getActor1();
        totalForce += Controller.getObj().getActor2();
        double frictionForce = 0.0d;

        if (Controller.getObj() instanceof CylinderObject) {
            if (Math.abs(totalForce) - 3 * getSF() * normalForce <= 0.001d) {
                frictionForce = totalForce * 1 / 3;
            } else {
                frictionForce = getKF() * normalForce;
            }
        } else if (Controller.getObj() instanceof CubeObject) {
            if (Math.abs(totalForce) - getSF() * normalForce <= 0.001d) {
                frictionForce = totalForce;
            } else {
                frictionForce = getKF() * normalForce;
            }
        }
        if (totalForce > 0) {
            totalForce -= frictionForce;
        } else {
            totalForce += frictionForce;
        }
        return totalForce;
    }

    public static double getKF() {
        return Double.parseDouble(UserInput.get("Kinetic Friction"));
    }

    public static double getSF() {
        return Double.parseDouble(UserInput.get("Static Friction"));
    }
}
