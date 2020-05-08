/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aiproject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author panch
 */
public class sampling {
    Random rand = new Random();    
    int SamplingInitialSize = 15;
    ArrayList<Integer> samplesnmbers = new ArrayList<>();  
    List<Movie> samples;
    List<Feature> Features;
    double totalLikeliness;
    double totalUnlikeliness;
    
    public sampling(){        
        Random rand = new Random();
        while(samplesnmbers.size() < SamplingInitialSize){
            int n = rand.nextInt(4916);
            if(!samplesnmbers.contains(n)){
                samplesnmbers.add(n);
            }
        }
        samples = new ArrayList<Movie>();       
    }
    
    public void Sampling(ArrayList<Movie> movies){        
        for(int i = 0; i < samplesnmbers.size(); i++){
            Movie sampleMovie = new Movie(movies.get(samplesnmbers.get(i)));
            samples.add(sampleMovie);
        }        
        for(Movie movie : samples){
            Random rnd = new Random();
            double p1 = rnd.nextDouble();
            movie.setUnlikeliness(p1);            
            movie.setLikeliness(1-p1);
            movie.priorLikeliness = Math.log(movie.getLikeliness());
            movie.priorUnlikeliness = Math.log(movie.getUnlikeliness());
        }
        FeatureProbabilities();        
    }
    
   private void FeatureProbabilities(){
       Features = new ArrayList<>();
        List<Movie> likedSamples =  this.samples.stream().filter(x -> x.getLikeliness() > x.getUnlikeliness()).collect(Collectors.toList());
        List<Movie> unlikedSamples = this.samples.stream().filter(x -> x.getUnlikeliness() > x.getLikeliness()).collect(Collectors.toList());                        
        totalLikeliness = (double)likedSamples.size()/this.samples.size();
        totalUnlikeliness = (double)unlikedSamples.size() / this.samples.size();                
        //EachFeature probability likeliness p(feature| likeliness or unlikeliness)
        for(int i = 0; i< this.samples.size();i++){
            Movie movie = this.samples.get(i);
            int cFeatureActor1 = (int) Features.stream().filter(x -> x.value.equals(movie.Actor1) && x.category.equals("Actor1")).count();
            if(cFeatureActor1 == 0){
                int countFeatureActor1 = (int) likedSamples.stream().filter( x -> x.Actor1.equals(movie.Actor1)).count();
                double pthisActor1 = (double) countFeatureActor1/likedSamples.size();
                Feature nFeature = new Feature("Actor1",movie.Actor1);
                nFeature.setPlikeliness(pthisActor1);
                //punlikeliness
                countFeatureActor1 = (int) unlikedSamples.stream().filter( x -> x.Actor1.equals(movie.Actor1)).count();
                pthisActor1 = (double) countFeatureActor1/unlikedSamples.size();
                nFeature.setPunlikeliness(pthisActor1);
                Features.add(nFeature);
            }            
            int cFeatureActor2 = (int) Features.stream().filter(x -> x.value.equals(movie.Actor2) && x.category.equals("Actor2")).count();
            if(cFeatureActor2 == 0){
                int countFeatureActor2 = (int) likedSamples.stream().filter( x -> x.Actor2.equals(movie.Actor2)).count();
                double pthisActor2 = (double) countFeatureActor2/likedSamples.size();
                Feature nFeature = new Feature("Actor2",movie.Actor2);
                nFeature.setPlikeliness(pthisActor2);
                //punlikeliness
                countFeatureActor2 = (int) unlikedSamples.stream().filter( x -> x.Actor2.equals(movie.Actor2)).count();
                pthisActor2 = (double) countFeatureActor2/unlikedSamples.size();
                nFeature.setPunlikeliness(pthisActor2);
                Features.add(nFeature);
            }
            int cFeaturedirector = (int) Features.stream().filter(x -> x.value.equals(movie.director) && x.category.equals("director")).count();
            if(cFeaturedirector == 0){
                int countFeatureDirector = (int) likedSamples.stream().filter( x -> x.director.equals(movie.director)).count();
                double pthisDirector = (double) countFeatureDirector/likedSamples.size();
                Feature nFeature = new Feature("director",movie.director);
                nFeature.setPlikeliness(pthisDirector);
                //unlikeliness
                countFeatureDirector = (int) unlikedSamples.stream().filter( x -> x.director.equals(movie.director)).count();
                pthisDirector = (double) countFeatureDirector/unlikedSamples.size();
                nFeature.setPunlikeliness(pthisDirector);
                Features.add(nFeature);                
            }
            for(String genre: movie.genres){
                int cFeaturegenre = (int) Features.stream().filter(x -> x.value.equals(genre) && x.category.equals("genre")).count();
                if(cFeaturegenre == 0){
                    int countFeatureGenre = (int) likedSamples.stream().filter(x -> x.genres.contains(genre)).count();
                    double pthisGenre = (double) countFeatureGenre / likedSamples.size();
                    Feature nFeature = new Feature("genre",genre);
                    nFeature.setPlikeliness(pthisGenre);
                    //unlikeliness
                    countFeatureGenre = (int) unlikedSamples.stream().filter(x -> x.genres.contains(genre)).count();
                    pthisGenre = (double) countFeatureGenre/unlikedSamples.size();
                    nFeature.setPunlikeliness(pthisGenre);
                    Features.add(nFeature);
                }
                
            }
        }        
    }
   
   public List<Movie> sSorted(){
       return this.samples.stream().sorted(Comparator.comparing(Movie::getLikeliness).reversed()).collect(Collectors.toList());
   }      
   public ArrayList<Movie> CalculatePMovies(ArrayList<Movie> movies){
       ArrayList<Movie> finalMovies = new ArrayList<>();
       for(int i = 0; i<movies.size(); i++){           
           List<Feature> FeatureList = new ArrayList<Feature>();
           Movie movie = movies.get(i);
            Feature feature1 = this.Features.stream().filter(x -> x.value.equals(movie.Actor1) && x.category.equals("Actor1")).findAny().orElse(new Feature("Actor1",movie.Actor1));
            FeatureList.add(new Feature(feature1));
            Feature feature2 = this.Features.stream().filter(x -> x.value.equals(movie.Actor2) && x.category.equals("Actor2")).findAny().orElse(new Feature("Actor2",movie.Actor2));
            FeatureList.add(new Feature(feature2));
            Feature feature3 = this.Features.stream().filter(x -> x.value.equals(movie.director) && x.category.equals("director")).findAny().orElse(new Feature("director",movie.director));
            FeatureList.add( new Feature(feature3));
            for(String genre : movie.genres){
                Feature feature = this.Features.stream().filter(x -> x.value.equals(genre) && x.category.equals("genre")).findAny().orElse(new Feature("genre",genre));    
                FeatureList.add(feature);
            }
            var finalMovie = PCalculation(FeatureList,movie);            
            finalMovies.add(finalMovie);
       }
       
       return finalMovies;
   }   
   private Movie PCalculation(List<Feature> features, Movie movie){
       int countFeaturespLikeliness = (int) features.stream().filter(x -> x.getPlikeliness() == 0.0).count();
       int countFeaturespUnlikeliness = (int) features.stream().filter(x -> x.getPunlikeliness() == 0.0).count();
       if(countFeaturespLikeliness > 0|| countFeaturespUnlikeliness > 0){
           features = LaplaceSmoothing(features);
       }
       double pMovieLikeliness = 1.0;
       double pMovieUnlikeness = 1.0;       
       for(int i = 0; i<features.size();i++){
           pMovieLikeliness = pMovieLikeliness*features.get(i).getPlikeliness();
           pMovieUnlikeness = pMovieUnlikeness*features.get(i).getPunlikeliness();
       }       
       pMovieLikeliness = (this.totalLikeliness*pMovieLikeliness)/(this.totalLikeliness*pMovieLikeliness + this.totalUnlikeliness*pMovieUnlikeness);
       movie.setLikeliness(pMovieLikeliness);
       movie.setUnlikeliness(1-movie.getLikeliness());
       
       return movie;
   }   
   private List<Feature> LaplaceSmoothing(List<Feature> features){
       List<Feature> nFeatures = new ArrayList<>();
       for(int i = 0; i<features.size(); i++){
           var f = features.get(i);
           double newpLikeliness;
           double newpUnlikeliness;           
           if(f.getPlikeliness() == 0.0){
               int likedSample = (int)samples.stream().filter(x -> x.getLikeliness() > x.getUnlikeliness()).count();
               newpLikeliness = (double) 1/(likedSample + samples.size());
               f.setPlikeliness(newpLikeliness);
           }else{
               var likedSamples = samples.stream().filter(x -> x.getLikeliness() > x.getUnlikeliness()).collect(Collectors.toList());               
               int apperances;
               switch (i) {
                   case 0:
                       apperances = (int)likedSamples.stream().filter( x -> x.Actor1.equals(f.value)).count();
                       newpLikeliness = (double) (apperances+1)/((int)samples.stream().filter(x -> x.getLikeliness() > x.getUnlikeliness()).count()+ samples.size());
                       f.setPlikeliness(newpLikeliness);
                       break;
                   case 1:
                       apperances = (int)likedSamples.stream().filter( x -> x.Actor2.equals(f.value)).count();
                       newpLikeliness = (double) (apperances+1)/((int)samples.stream().filter(x -> x.getLikeliness() > x.getUnlikeliness()).count()+ samples.size());
                       f.setPlikeliness(newpLikeliness);
                       break;
                   default:
                       apperances = (int)likedSamples.stream().filter( x -> x.director.equals(f.value)).count();
                       newpLikeliness = (double) (apperances+1)/((int)samples.stream().filter(x -> x.getLikeliness() > x.getUnlikeliness()).count()+ samples.size());
                       f.setPlikeliness(newpLikeliness);
                       break;
               }                              
           }
            if(f.getPunlikeliness() == 0.0){
               newpUnlikeliness = (double) 1/((int)samples.stream().filter(x -> x.getUnlikeliness() > x.getLikeliness()).count()+ samples.size());
               f.setPunlikeliness(newpUnlikeliness);
           }else{
               var unlikedSamples = samples.stream().filter(x -> x.getUnlikeliness() > x.getLikeliness()).collect(Collectors.toList());
               int apperances;
               switch (i) {
                   case 0:
                       apperances = (int)unlikedSamples.stream().filter( x -> x.Actor1.equals(f.value)).count();
                       newpUnlikeliness = (double) (apperances+1)/((int)samples.stream().filter(x -> x.getUnlikeliness() > x.getLikeliness()).count()+ samples.size());
                       f.setPunlikeliness(newpUnlikeliness);
                       break;
                   case 1:
                       apperances = (int)unlikedSamples.stream().filter( x -> x.Actor2.equals(f.value)).count();
                       newpUnlikeliness = (double) (apperances+1)/((int)samples.stream().filter(x -> x.getUnlikeliness() > x.getLikeliness()).count()+ samples.size());
                       f.setPunlikeliness(newpUnlikeliness);
                       break;
                   default:
                       apperances = (int)unlikedSamples.stream().filter( x -> x.director.equals(f.value)).count();
                       newpUnlikeliness = (double) (apperances+1)/((int)samples.stream().filter(x -> x.getUnlikeliness() > x.getLikeliness()).count()+ samples.size());
                       f.setPunlikeliness(newpUnlikeliness);
                       break;
               }                              
           }           
           nFeatures.add(f);
       }
       return nFeatures;
   }   
   public void Training(Movie selected){
       Movie nsMovie = new Movie(selected);
       String tag = selected.User_veredict;
       double nsample = 0.0;
       do{
             nsample = rand.nextDouble();
       }while(nsample <= 0.5);
       if(tag.equals("liked")){
           if(nsMovie.getLikeliness() < nsample){
               nsMovie.setLikeliness(nsample);
               nsMovie.setUnlikeliness(1-nsample);
           }
           
       }
       if(tag.equals("disliked")){
           if(nsMovie.getUnlikeliness() < nsample){
                nsMovie.setUnlikeliness(nsample);
                nsMovie.setLikeliness(1-nsample);
           }

       }
       int countInsample = (int) this.samples.stream().filter(x -> x.title.equals(nsMovie.title)).count();
       if(countInsample > 0){           
           boolean done = this.samples.removeIf( x -> x.title.equals(nsMovie.title));           
       }
       this.samples.add(nsMovie);
       FeatureProbabilities();
   }                                    
}
