package com.application.components.controller;

public class Physic {
    private static double staticFrictionCoefficent = 0.2;

    public static double calAcc() {
        double totalForce = getTotalForce();
        double normalForce = Controller.getObj().getMass() * 10;

        if (Math.abs(totalForce) > normalForce * staticFrictionCoefficent) {
            return totalForce / Controller.getObj().getMass();
        } else {
            return 0;
        }
    }

    public static double getTotalForce() {
        double totalForce = 0.0d;
        totalForce += Controller.getObj().getActor1();
        totalForce += Controller.getObj().getActor2();
        if (totalForce > 0) {
            totalForce -= getFrictionForce();
        } else {
            totalForce += getFrictionForce();
        }
        return totalForce;
    }

    public static double getFrictionForce() {
        return Controller.getObj().getMass() * 10 * Math.abs(Double.parseDouble(UserInput.get("Friction")));
    }
}
