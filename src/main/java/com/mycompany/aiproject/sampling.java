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
    int SamplingSize = 357;
    ArrayList<Integer> samplesnmbers = new ArrayList<>();  
    List<Movie> samples;
    
    public sampling(){        
        Random rand = new Random();
        while(samplesnmbers.size() < SamplingSize){
            int n = rand.nextInt(5043);
            if(!samplesnmbers.contains(n)){
                samplesnmbers.add(n);
            }
        }        
    }
    
    public List<Movie> SamplingStart(ArrayList<Movie> movies){
        samples = new ArrayList<Movie>();
        for(int i = 0; i < samplesnmbers.size(); i++){
            samples.add(movies.get(samplesnmbers.get(i)));
        }        
        for(Movie movie : samples){
            Random rnd = new Random();
            double p1 = rnd.nextDouble();
            movie.setUnlikeliness(p1);            
            movie.setLikeliness(1-p1);
            movie.priorLikeliness = Math.log(movie.getLikeliness());
            movie.priorUnlikeliness = Math.log(movie.getUnlikeliness());
        }
        FeatureProbabilites(samples);
        return samples.stream().sorted(Comparator.comparing(Movie::getLikeliness).reversed()).collect(Collectors.toList());
    }
    
    private void FeatureProbabilites(List<Movie> sample){
        int likelinessCount = (int) sample.stream().filter(x -> x.getLikeliness() > x.getUnlikeliness()).count();
        int unlikeliness = sample.size()-likelinessCount;        
    }
    
    
}
