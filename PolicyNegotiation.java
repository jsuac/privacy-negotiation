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

public class PolicyNegotiation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        
        
        int maxdistances=1;
        int NumberOfRelTypes=1;
        
        double[] distances={5};

        for(int ndist=0;ndist<maxdistances;ndist++){
            double minDistance=distances[ndist];
            PrintStream resfile=null;
            try{
                resfile = new PrintStream("../result-100rep-noheuristica-except2-RT"+NumberOfRelTypes);
            } catch(FileNotFoundException e){
           }
           resfile.println("# 200 agents (1000 repetitions,no heuristica)");
           resfile.println("# Nagents %Agreements AvgProdUt AvgMinUt AvgExcept AvgTried");
        
        for(int nagents=10;nagents<80;nagents=nagents+10)
        {
        
        
        
        
        int[] policyAgreed= new int[nagents];
        int[] noconflict= new int[nagents];
        int maxRepetitions=100;
        Agent a1 = null;
        Agent a2 = null;
        
        
              
        
        Util.initRandom();
        
        
        PrintStream detailedLog=null;
        try{
            detailedLog = new PrintStream("../detailedLog-noHeuristica-except2-RT"+NumberOfRelTypes+"-"+nagents);
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
        
    
    
    int i;
    double maxUtility=0.0;
    
    
    int[] policy= new int[nagents];
    for(int j=0;j<nagents;j++){
            
            if(noconflict[j]!=-1)
                policy[j]=noconflict[j];
            else
            
                policy[j]=0;
        }
    
    
    int policyNumber=1;
    while(policy != null)
    {
        
        
        for(i=0;i<nagents;i++){
            if(noconflict[i]==-1)
            {
                 if (policy[i]==0){
                    policy[i]=1;
                    break;
                }
                else{
                    policy[i]=0;
                }
            }
   
        }
        
        
        
        
        
                
            
            
            
            
            
            double product = a1.utility4(policy)*a2.utility4(policy);
            
            if (product>maxUtility){
            maxUtility=product;
            System.arraycopy(policy, 0, policyAgreed, 0, nagents);
            }
       
    
    
     
        
        if (i==nagents) policy=null;
        
        policyNumber++;
    } 
    
    
    
    
    detailedLog.println("--- Negotiation Results  ");
    detailedLog.println("The agreed Policy is: ");
    Util.printPolicy(policyAgreed,nagents,detailedLog);
    
    if(maxUtility>0.0){
        detailedLog.println("Product of Utilities: "+maxUtility);
        double a1Utility=a1.utility4(policyAgreed);
        double a2Utility=a2.utility4(policyAgreed);
        detailedLog.println("Utility for Agent 1: "+a1Utility);
        detailedLog.println("Utility for Agent 2: "+a2Utility);
        detailedLog.println("----------------------");
    
    
        agreements++;
        sumUtilities=sumUtilities+maxUtility;
        if(a1Utility>=a2Utility)minUtility=minUtility+a2Utility;
        else minUtility= minUtility+a1Utility;
    }
    
    
    
    
    
    int a1Nexceptions=a1.getExceptions(policyAgreed);
    int a2Nexceptions=a2.getExceptions(policyAgreed);
    detailedLog.println("Number of Exceptions for Agent 1: "+a1Nexceptions);
    detailedLog.println("Number of Exceptions for Agent 2: "+a2Nexceptions);
    
    nexceptions=nexceptions+a1Nexceptions+a2Nexceptions;
    ntried=ntried+numberPoliciesToTry;
    feasibleagreements++;
    }
    else
        detailedLog.println("No possible Agreement");
    
    } 
    
        
        
        
        
        
        
        
        resfile.println(nagents+" "+((agreements*1.0)/(feasibleagreements*1.0))*100.0+" "+sumUtilities/agreements*1.0+" "+minUtility/agreements*1.0+" "+(nexceptions*1.0)/(agreements*2.0)+" "+(ntried)/(agreements));
        
}
    }
    }

}
