package com.gaproject.jturner;

import java.util.ArrayList;

/**
 * Created by jefft on 12/28/2016.
 */
public class GeneticAlgorithm {

    private ArrayList<Chromosome> solution = new ArrayList<>();
    private CSVReader gaReader = new CSVReader();
    private Population pop = new Population();
    private ArrayList<Gene> allPlayersList;
    private ArrayList<ArrayList<Chromosome>> newPopulation = new ArrayList<>();
    private int totalNumberofPlayers;
    private Boolean keepGoing = true;
    private int testControl = 10000;
    private int counter = 0;

    private int generation = 0;
    /*
    Run Method:
    While there is no convergence,
        increment the generation.
        evolve the population.
        mutate the population.
        sort the list.
     */
    public ArrayList<Chromosome> run(){

        //initialize a new player pool
        gaReader.clearPlayerPool();
        gaReader.createPlayerPool();
        totalNumberofPlayers = gaReader.getNumberofPlayers();
        allPlayersList = gaReader.getPlayerPool();

        //create a population and fill it with chomosomes
        pop.buildPopulation(allPlayersList);
        while (keepGoing){  //true code: gaPop.convergenceCheck() != true
            counter++;
            if (counter > 100){
                System.out.println("Working");
                System.out.println("Fit@0: " + pop.getPopulation().get(0).getFitness() + "  Age: " + pop.getPopulation().get(0).getAge());
                System.out.println("Fit@500: " + pop.getPopulation().get(500).getFitness() + "  Age: " + pop.getPopulation().get(500).getAge());
                System.out.println("Fit@1250: " + pop.getPopulation().get(1250).getFitness() + "  Age: " + pop.getPopulation().get(1250).getAge());
                System.out.println("Fit@1999: " + pop.getPopulation().get(1999).getFitness() + "  Age: " + pop.getPopulation().get(1999).getAge());
                counter = 0;
            }
            generation++;
            pop.evolvePopulation();
            //System.out.println(pop.getSolution().get(0).getFitness());
            keepGoing = pop.converganceCheck();
        }

        solution = pop.getSolution();

        return solution;
    }

}
