package com.application.components.controller;

import com.application.components.object.CubeObject;
import com.application.components.object.CylinderObject;

public class Physic {

    public static double calAcc() {
        return getTotalForce() / Controller.getObj().getMass();
    }

    public static double getTotalForce() {
        double totalForce = Controller.getObj().getActor1() + Controller.getObj().getActor2();
        if (totalForce > 0) {
            totalForce -= getFrictionForce();
        } else if (totalForce < 0) {
            totalForce += getFrictionForce();
        } else {
            totalForce = 0.0d;
        }
        return totalForce;
    }

    public static double getFrictionForce() {
        double frictionForce = 0.0d;
        double totalForce = Controller.getObj().getActor1() + Controller.getObj().getActor2();
        double normalForce = Controller.getObj().getMass() * 10.0d;
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
        return frictionForce;
    }

    public static double getKF() {
        return UserInput.get("Kinetic Friction");
    }

    public static double getSF() {
        return UserInput.get("Static Friction");
    }
}
