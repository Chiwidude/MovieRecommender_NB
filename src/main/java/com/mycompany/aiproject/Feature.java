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
    String category;
    private double plikeliness;
    private double punlikeliness;
       
   Feature(String category, String value){
       this.category = category;
       this.value = value;
       this.plikeliness = 0.0;
       this.punlikeliness = 0.0;
   }
  public Feature(Feature data){
      this.category = data.category;
      this.value = data.value;
      this.plikeliness = data.plikeliness;
      this.punlikeliness = data.punlikeliness;
  }
}
