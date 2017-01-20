package com.gaproject.jturner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by jefft on 12/28/2016.
 */
public class Population {

    private static int populationSize = 3000;
    private static int expectedFitness = 742;
    private ArrayList<Chromosome> population = new ArrayList<>();
    private ArrayList<Gene> allPlayerList = new ArrayList<>();
    private ArrayList<Chromosome> tempPop = new ArrayList<>();
    Random rand = new Random();

    public void buildPopulation(ArrayList<Gene> apl){
        for (int i = 0; i < populationSize; i++){
            this.allPlayerList = apl;
            Chromosome destro = new Chromosome();
            destro.createRandomChromosome(apl);
            population.add(destro);
        }
    }

    //Testing function to print the names of the players in the first 20 chromosomes of the population
    public void testPopulationPrint(){
        for(int i = 0; i < 20; i++){
            System.out.println("*_*_*_*_*_*_*_*_*");
            Chromosome printingDestro = population.get(i);
            ArrayList<Gene> printingGeneArray = printingDestro.getChromosomePlayers();
            for(int j =0; j < 9; j++){
                Gene printingGene = printingGeneArray.get(j);
                System.out.println(j);
                System.out.println(printingGene.getName());
            }
            System.out.println("*_*_*_*_*_*_*_*_*");
        }
    }

    public void evolvePopulation(){

        int totalFitness = 0;
        Chromosome parentOne = new Chromosome();
        Chromosome parentTwo = new Chromosome();

        tempPop.clear();
        /*get the fitness of all the chromosomes in the population
        * and sort the arraylist.  Save the top 5%.
        * */

        for(int i = 0; i < population.size(); i++){
            int newAge = 0;
            population.get(i).calculateFitness();
            totalFitness = totalFitness + population.get(i).getFitness();

            newAge = population.get(i).getAge() + 1;
            population.get(i).setAge(newAge);
        }
        Collections.sort(population);

        for (int i = 0; i < (int)((population.size()) * .05); i++){   //(int)((population.size()) * .05)
            tempPop.add(population.get(i));
        }

        /*
        Assign each remaining chromosome a probability of being chosen:
        = fitness/sum of all fitness (absolute values)
        and make a random roll to choose survivors
        clear the rest of the chromosomes
         */
        for (int i = (int)(population.size() * .05); i < population.size(); i++){
            if(population.get(i).getFitness() > 0 && totalFitness > 0) {
                population.get(i).setProbabilityofLife((float) ((population.get(i).getFitness()) / totalFitness));
                int n = rand.nextInt(100) + 1;
                System.out.println("ProbabilityofLife: " + i + " " + population.get(i).getProbabilityofLife());
                if ((population.get(i).getProbabilityofLife() * 100) > n) {
                    tempPop.add(population.get(i));
                }
            }
        }


        /*
        Crossover: get two random parents from the survivors
            for each spot in the chromosome
            choose randomly from one of the parents
            fill the population up to 800
         */
        int roomLeft = (int) ((populationSize) * .4 - tempPop.size());

        for (int i = 0; i < roomLeft; i++) {
            int n = rand.nextInt((int)(populationSize * 0.69));
            int o = rand.nextInt((int)(populationSize * 0.69));
            tempPop.add(crossover(population.get(n), population.get(o)));
        }

        /*
        fill the last 250 with random chromosomes
         */
        roomLeft = populationSize - tempPop.size();

        for (int i = 0; i < roomLeft; i++){
            Chromosome tempC = new Chromosome();
            tempC.createRandomChromosome(allPlayerList);
            tempPop.add(tempC);
        }

        population.clear();
        for (int i = 0; i < populationSize; i++){
            population.add(tempPop.get(i));
        }
        /*
        check all chromosomes for mutation
         */
        for(int i = 0; i < population.size(); i++) {
            population.get(i).calculateFitness();
        }

        for(int i = 0; i < population.size(); i++){
            if (population.get(i).getAge() > 500){
                population.remove(i);
            }
        }

        for (int i = 0; i < (populationSize - population.size()); i++){
            Chromosome tempD = new Chromosome();
            tempD.createRandomChromosome(allPlayerList);
            population.add(tempD);
        }

        Collections.sort(population);

        tempPop.clear();

    }

    private Chromosome crossover(Chromosome p1, Chromosome p2){
        Chromosome parentOne = p1;
        Chromosome parentTwo = p2;
        Chromosome baby = new Chromosome();

        for (int i = 0; i < 9; i++){
           int j = rand.nextInt(10) + 1;
           if (j > 5){
               baby.addGenetoDestro(parentOne.getGeneFromDestro(i));
           }else{
               baby.addGenetoDestro(parentTwo.getGeneFromDestro(i));
           }
        }
        return baby;
    }

    public Boolean converganceCheck(){
        if(population.get(0).getFitness() >= expectedFitness){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Chromosome> getSolution(){
        return population;
    }

    public ArrayList<Chromosome> getPopulation(){
        return population;
    }


}
