/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aiproject;

/**
 *
 * @author panch
 */
public class Feature{

    /**
     * @return the plikeliness
     */
    public double getPlikeliness() {
        return plikeliness;
    }

    /**
     * @param plikeliness the plikeliness to set
     */
    public void setPlikeliness(double plikeliness) {
        this.plikeliness = plikeliness;
    }

    /**
     * @return the punlikeliness
     */
    public double getPunlikeliness() {
        return punlikeliness;
    }

    /**
     * @param punlikeliness the punlikeliness to set
     */
    public void setPunlikeliness(double punlikeliness) {
        this.punlikeliness = punlikeliness;
    }
    String value;
    private double plikeliness;
    private double punlikeliness;
    
   Feature(String value){
       this.value = value;
       this.plikeliness = 0.0;
       this.punlikeliness = 0.0;
   }
}
