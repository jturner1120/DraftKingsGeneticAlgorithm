package com.gaproject.jturner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jefft on 12/28/2016.
 */
public class CSVReader {

    //a List of the player objects (genes)
    private ArrayList<Gene> geneList = new ArrayList<>();
    private int numberofPlayers;


    //read in the file and put it in the list
    public void createPlayerPool(){
        BufferedReader dkstatsBuffer = null;
        String csvSplitter = ",";
        try{
            String dkstatsLine;
            String[] geneBuilder;
            Gene player;
            dkstatsBuffer = new BufferedReader(new FileReader("/Users/jefft/DKGA/DKSalaries.csv"));

            while ((dkstatsLine = dkstatsBuffer.readLine()) != null) {
                geneBuilder = dkstatsLine.split(csvSplitter);
                float f = Float.parseFloat(geneBuilder[4]);
                int i = Integer.parseInt(geneBuilder[2]);
                player = new Gene(geneBuilder[1], geneBuilder[0], f, i);
                geneList.add(player);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        this.numberofPlayers = geneList.size();
    }

    //return the list to callers
    public ArrayList<Gene> getPlayerPool(){
        return geneList;
    }

    public String getPoolStatus(){
        String status;

        if (geneList.size() > 0){
            status = "Player List Available";
        }else{
            status = "Player List Unavailable";
        }

        return status;
    }

    public void clearPlayerPool(){
        geneList.clear();
    }

    public int getNumberofPlayers(){
        return numberofPlayers;
    }

}
