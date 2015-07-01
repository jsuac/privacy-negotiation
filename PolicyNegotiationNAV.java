/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package policynegotiation;

/**
 *
 * @author jose
 */

import java.io.PrintStream;
import java.io.FileNotFoundException;

public class PolicyNegotiationNAV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        
        
        int maxdistances=1;
        int NumberOfRelTypes=5;
        
        double[] distances={5};

        for(int ndist=0;ndist<maxdistances;ndist++){
            double minDistance=distances[ndist];
            PrintStream resfile=null;
            try{
                resfile = new PrintStream("../result-1000rep-noheuristica-NAV-RT"+NumberOfRelTypes);
            } catch(FileNotFoundException e){
           }
           resfile.println("# 200 agents (1000 repetitions,no heuristica)");
           resfile.println("# Nagents %Agreements AvgProdUt AvgMinUt AvgExcept AvgTried");
        
        for(int nagents=10;nagents<210;nagents=nagents+10)
        {
        
        
        
        
        int[] policyAgreed= new int[nagents];
        int[] noconflict= new int[nagents];
        int maxRepetitions=1000;
        Agent a1 = null;
        Agent a2 = null;
        
        
              
        
        Util.initRandom();
        
        
        PrintStream detailedLog=null;
        try{
            detailedLog = new PrintStream("../detailedLog-noHeuristica-NAV-RT"+NumberOfRelTypes+"-"+nagents);
        } catch(FileNotFoundException e){
        }
        
        int agreements=0;
        int feasibleagreements=0;
        double sumUtilities=0.0;
        double minUtility=0.0;
        int nexceptions=0;
        double ntried=0.0;
        
        
        
        
        
            
        for(int repetition=0;repetition<maxRepetitions;repetition++){
        detailedLog.println("----------------------");
        detailedLog.println("Repetition "+repetition);
        
        
        
           
        
        
        
        
        
        
        
        
        
        
        int numberConflicts=0;
        int numberUniqueAcceptableOption=0;
        int numberHeuristic=0;
        boolean feasibleAgreement=true;
        boolean aDecision=false;
        boolean bDecision=false;
        int maxTries=1000;
        int tries=0;
        
        do{
            
            
            for(int j=0;j<nagents;j++){
                noconflict[j]=-1;
            }
        
            
            for(int j=0;j<nagents;j++){
                policyAgreed[j]=0;
            }
    
            
            a1 = new Agent(nagents,NumberOfRelTypes,1);
            a2 = new Agent(nagents,NumberOfRelTypes,2);
            
            
            a1.createDesiredPolicy();
            a2.createDesiredPolicy();
            
         
            
            
            
            
            
        
            
            
            
            
           
            
            
            
            aDecision=false;
            bDecision=false;
            numberConflicts=0;
            numberUniqueAcceptableOption=0;
            numberHeuristic=0;
            feasibleAgreement=true;
            
            for(int cont=0;cont<nagents;cont++)
            {
                double intimacyTo1=a1.getIntimacy(cont);
                double intimacyTo2=a2.getIntimacy(cont);
                aDecision=(a1.getDesiredPolicy(cont)<=intimacyTo1);
                bDecision=(a2.getDesiredPolicy(cont)<=intimacyTo2);
                
                
                
                if(aDecision==bDecision)
                {
                    if(aDecision)
                    {noconflict[cont]=1;}
                    else
                    {noconflict[cont]=0;}
                }
                else
                {
                    
                    numberConflicts++;
                    
                        
                        
                        
                        
                        
                            
                            if(noconflict[cont]==-1){
                               double a1distance = a1.distance(a1.getDesiredPolicy(cont), a1.getIntimacy(cont));
                               double a2distance = a2.distance(a2.getDesiredPolicy(cont), a2.getIntimacy(cont));
                                
                                if(Math.abs(a1distance-a2distance)>=minDistance){
                                    if(a1distance>a2distance){
                                        
                                        numberHeuristic++;
                                        if(aDecision) noconflict[cont]=1;
                                            else noconflict[cont]=0;
                                    }
                                    else{
                                    
                                        
                                        numberHeuristic++;
                                        if(bDecision) noconflict[cont]=1;
                                        else noconflict[cont]=0;
                                    }
                                }
                            }
                            
                
                        

                   
                    
                    
                }
                 
            }
            
            
            tries++;
            if(tries==maxTries)break;

        }while(numberConflicts==0);
        
        
        
        if(tries==maxTries) continue;
        
        
        
        
        
        
        a1.printIntimacy(detailedLog);
        a2.printIntimacy(detailedLog);
        
        
        detailedLog.println("---------- Base Policy ----------------");
        Util.printPolicy(noconflict, nagents, detailedLog);
        detailedLog.println("---------------------------------------");
        double numberPoliciesToTry=Math.pow(2, numberConflicts-numberUniqueAcceptableOption-numberHeuristic); 
        detailedLog.println("NumberConflicts: "+numberConflicts);
        detailedLog.println("numberUniqueAcceptableOption: "+numberUniqueAcceptableOption);
        detailedLog.println("numberHeuristic: "+numberHeuristic);
        detailedLog.println("NumberPoliciesToTry: "+numberPoliciesToTry);
        detailedLog.println("---------------------------------------");
        
                

        
    if(feasibleAgreement){   
        
    
    
    ntried=ntried+numberPoliciesToTry;
    feasibleagreements++;
    }
    else
        detailedLog.println("No possible Agreement");
    
    } 
    
        
        
        
        
        
        
        
resfile.println(nagents+" "+(ntried)/(feasibleagreements));        
}
    }
    }

}
