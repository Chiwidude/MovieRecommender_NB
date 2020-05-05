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
public class Movie {
    
   Feature color;
   Feature title;
   FArray plot_keywords;
   FArray genres;
   Feature director;
   int critics_reviews;
   Feature Actor1;
   Feature Actor2;   
   double IMDB_Score;
   int facebook_likes;
   Feature year;
   //prior probability cold start
   double priorLikeliness = Math.log(0.5);
   double priorUnlikeliness = Math.log(0.5);
   //acumulated probability after cold start
   double likeliness = 0.0;
   double unlikeliness = 0.0;   
   
   public Movie(String[] data){
       
       this.color = new Feature(data[0].trim());
       this.director = new Feature(data[1].trim());
       this.critics_reviews = data[2].trim().equals("")? 0: Integer.parseInt(data[2].trim());
       this.Actor2 = new Feature(data[3].trim());
       this.Actor1 = new Feature(data[4].trim());
       this.genres = new FArray(data[5].split("_"));
       this.title = new Feature(data[6].trim());
       this.plot_keywords = new FArray(data[7].split("_"));
       this.year = new Feature(data[8].trim());
       this.IMDB_Score = data[9].trim().equals("")? 0.0: Double.parseDouble(data[9].trim());
       this.facebook_likes = data[10].trim().equals("")? 0: Integer.parseInt(data[10].trim());
   }    
   
   
  
}

class Feature{
    String value;
    double plikeliness;
    double punlikeliness;
    
   Feature(String value){
       this.value = value;
       this.plikeliness = 0.0;
       this.punlikeliness = 0.0;
   }
}
class FArray{
    Feature[] values;
    double plikeliness;
    double punlikeliness;
    
    FArray(String[] values){
        this.plikeliness = 0.0;
        this.punlikeliness = 0.0;
        this.values = new Feature[values.length];
        for (int i = 0; i< values.length; i++) {
            this.values[i] = new Feature(values[i].trim());
        }
    }
        
}
