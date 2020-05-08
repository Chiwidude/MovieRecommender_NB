/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aiproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author panch
 */
public class Movie {

    /**
     * @return the likeliness
     */
    public double getLikeliness() {
        return likeliness;
    }

    /**
     * @param likeliness the likeliness to set
     */
    public void setLikeliness(double likeliness) {
        this.likeliness = likeliness;
    }

    /**
     * @return the unlikeliness
     */
    public double getUnlikeliness() {
        return unlikeliness;
    }

    /**
     * @param unlikeliness the unlikeliness to set
     */
    public void setUnlikeliness(double unlikeliness) {
        this.unlikeliness = unlikeliness;
    }

    /**
     * @return the IMDB_Score
     */
    public double getIMDB_Score() {
        return IMDB_Score;
    }
    
   String color;
   String title;
   String[] plot_keywords;
   List<String> genres;
   String director;
   int critics_reviews;
   String Actor1;
   String Actor2;   
   private double IMDB_Score;
   int facebook_likes;
    String year;
   String User_veredict;
   //prior probability cold start¿?
   double priorLikeliness = Math.log(0.5);
   double priorUnlikeliness = Math.log(0.5);
   //acumulated probability after cold start
   private double likeliness = 0.0;
   private double unlikeliness = 0.0;   
   
   public Movie(String[] data){
       
       this.color = data[0].trim();
       this.director = data[1].trim();
       this.critics_reviews = data[2].trim().equals("")? 0: Integer.parseInt(data[2].trim());
       this.Actor2 = data[3].trim();
       this.Actor1 = data[4].trim();
       this.genres = new ArrayList<>();
       this.genres.addAll(Arrays.asList(data[5].split("_")));
       this.title = data[6].trim();
       this.plot_keywords = data[7].split("_");
       this.year =  data[8].trim();
       this.IMDB_Score = data[9].trim().equals("")? 0.0: Double.parseDouble(data[9].trim());
       this.facebook_likes = data[10].trim().equals("")? 0: Integer.parseInt(data[10].trim());
       this.User_veredict = "";
   }    
   
   @Override
   public String toString(){
       StringBuilder src = new StringBuilder();
       src.append("Title: ");
       src.append(this.title);
       src.append(System.lineSeparator());
       src.append("Director: ");
       src.append(this.director);
       src.append(System.lineSeparator());
       src.append("Year: ");
       src.append(this.year);
       src.append(System.lineSeparator());
       src.append("IMDB Score: ");
       src.append(String.valueOf(this.getIMDB_Score()));
       src.append(System.lineSeparator());
       src.append("Genres: ");
       for(String gen : genres){
           src.append(gen);
           src.append(" |");
       }
       
       return src.toString();
   }
   
   
   public Movie (Movie data){
       this.Actor1 = data.Actor1;
       this.Actor2 = data.Actor2;
       this.IMDB_Score = data.IMDB_Score;
       this.color = data.color;
       this.critics_reviews = data.critics_reviews;
       this.director = data.director;
       this.facebook_likes = data.facebook_likes;
       this.likeliness = data.likeliness;
       this.User_veredict = data.User_veredict;
       this.title = data.title;
       this.unlikeliness = data.unlikeliness;
       this.priorLikeliness = data.priorLikeliness;
       this.priorUnlikeliness = data.priorUnlikeliness;
       this.genres = data.genres;
       this.plot_keywords = data.plot_keywords;
       this.year = data.year;
   }
   
  
}
