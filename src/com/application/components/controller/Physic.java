package com.application.components.controller;

import com.application.components.object.CubeObject;
import com.application.components.object.CylinderObject;

public class Physic {

    public static double calAcc() {
        return getTotalForce() / Controller.getObj().getMass();
    }

    public static double getTotalForce() {
        double totalForce = 0.0d;
        double normalForce = Controller.getObj().getMass() * 10;
        totalForce += Controller.getObj().getActor1();
        totalForce += Controller.getObj().getActor2();

        if (Controller.getObj() instanceof CylinderObject) {
            if (Math.abs(totalForce) < getSF() * normalForce) {
                return 0;
            } else {
                if (totalForce > 0) {
                    totalForce -= getKF();
                } else {
                    totalForce += getKF();
                }
            }
        } else if (Controller.getObj() instanceof CubeObject) {
            totalForce /= 6;
        }

        return totalForce;
    }

    public static double getKF() {
        return Controller.getObj().getMass() * 10 * Math.abs(Double.parseDouble(UserInput.get("Kinetic Friction")));
    }

    public static double getSF() {
        return Controller.getObj().getMass() * 10 * Math.abs(Double.parseDouble(UserInput.get("Static Friction")));
    }
}
