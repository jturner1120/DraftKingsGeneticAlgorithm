package com.gaproject.jturner;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.lang.Comparable;

/**
 * Created by jefft on 12/28/2016.
 */
public class Chromosome implements Comparable {

    private final int chromoSize = 9;
    private int fitness;
    private int age;
    private float probabilityofLife = 0;
    private ArrayList<Gene> chromosomePlayers = new ArrayList<>();
    private ArrayList<Gene> apl = new ArrayList<>();

    public Chromosome(){
        fitness = 0;
    }

    public void createRandomChromosome(ArrayList<Gene> passedApl){
        this.apl = passedApl;
        for(int i = 0; i < chromoSize; i++){
            int randomPlayer = getRandom();
            chromosomePlayers.add(apl.get(randomPlayer));
        }
    }

    public void setAge(int a){
        this.age = a;
    }

    public int getAge(){
        return this.age;
    }

    public void addGenetoDestro(Gene g){
        this.chromosomePlayers.add(g);
    }

    public Gene getGeneFromDestro (int index){
        return chromosomePlayers.get(index);
    }

    public void setProbabilityofLife(float pol){
        this.probabilityofLife = pol;
    }

    public float getProbabilityofLife(){
        return probabilityofLife;
    }

    public ArrayList<Gene> getChromosomePlayers(){
        return chromosomePlayers;
    }

    public int getFitness(){
        return fitness;
    }

    public void calculateFitness(){
        int sum = 0;
        int totalSalary = 0;
        int capDif = 0;
        int qbNum = 0;
        int wrNum = 0;
        int rbNum = 0;
        int dNum = 0;
        int teNum = 0;
        int posDif = 0;
        float totalPoints = 0;

        for(int i = 0; i < chromoSize; i++){
            Gene currentPlayer = chromosomePlayers.get(i);
            totalSalary = totalSalary + currentPlayer.getSalary();
            totalPoints = totalPoints + currentPlayer.getAvgPoints();

            if (Objects.equals(currentPlayer.getPosition(), "QB")){
                qbNum++;
            }else if (Objects.equals(currentPlayer.getPosition(), "RB")){
                rbNum++;
            }else if (Objects.equals(currentPlayer.getPosition(), "WR")){
                wrNum++;
            }else if (Objects.equals(currentPlayer.getPosition(), "TE")){
                teNum++;
            }else if (Objects.equals(currentPlayer.getPosition(), "DST")){
                dNum++;
            }
        }

        if (qbNum == 1 && dNum == 1 && teNum == 1){
            sum = sum + 300;
        }else{
            posDif = Math.abs(qbNum - 1) + Math.abs(dNum - 1) + Math.abs(teNum - 1);
            sum = sum - (100 * posDif);
        }

        if ((rbNum == 2 && wrNum == 4) ||(rbNum == 3 && wrNum == 3)){
            sum = sum + 200;
        }else{
            posDif = Math.abs(rbNum - 1) + Math.abs(wrNum - 1);
            sum = sum - (100 * posDif);
        }

        if (totalSalary <= 50000){
            capDif = 50000 - totalSalary;
        }

        if (totalSalary > 50000){
            sum = sum - 1000;
        }else if(capDif < 101){
            sum = sum + 100;
        }else if(capDif < 201){
            sum = sum + 80;
        }else if(capDif < 301){
            sum = sum + 60;
        }else if(capDif < 401){
            sum = sum + 40;
        }else if(capDif < 501){
            sum = sum + 20;
        }else{
            sum = sum - capDif;
        }

        sum = (int) (sum + totalPoints);
        //expected fitness 800
        this.fitness = sum;
    }

    public void printChromosome(){
        System.out.println("--------");
        for (int i = 0; i < chromoSize; i++){
            Gene tempGene = this.chromosomePlayers.get(i);
            System.out.println(i);
            System.out.println("Player: " + tempGene.getName());
            System.out.println("Cost: " + tempGene.getSalary());
            System.out.println("AvgPoints: " + tempGene.getAvgPoints());
            System.out.println("Position: " + tempGene.getPosition());
        }
        System.out.println("--------");
    }

    public void printChromosomeTotals(){
        float avgTotal = 0;
        int totalCost = 0;

        for (int i = 0; i < chromoSize; i++){
            Gene tempGene = this.chromosomePlayers.get(i);
            avgTotal = avgTotal + tempGene.getAvgPoints();
            totalCost = totalCost + tempGene.getSalary();
        }
        System.out.println("avgTotal: " + avgTotal);
        System.out.println("totalCost: " + totalCost);
    }


    private int getRandom(){
        Random rand = new Random();
        int n = rand.nextInt(apl.size());
        return n;
    }

    @Override
    public int compareTo(Object c) {
        int compareFit = ((Chromosome)c).getFitness();
        return compareFit-this.getFitness();
    }
}
