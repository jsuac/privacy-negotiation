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
import java.util.LinkedList;
public class PolicyNegotiationBnBGreedyTime {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        
        
        int maxdistances=1;
        int NumberOfRelTypes=1;
        
        
        long t1,t2,tot=0;

        for(int ndist=0;ndist<maxdistances;ndist++){
            
            PrintStream resfile=null;
            try{
                resfile = new PrintStream("../result-1000rep-BnBGreedy-TIME-RT"+NumberOfRelTypes);
            } catch(FileNotFoundException e){
           }
           resfile.println("# 200 agents (1000 repetitions)-utility [0,1]");
           resfile.println("# Nagents %Agreements AvgProdUt AvgMinUt AvgTried Time(ms)");
        
        for(int nagents=10;nagents<210;nagents=nagents+10)
        {
        
        
        
        
        int[] policyAgreed= new int[nagents];
        int[] noconflict= new int[nagents];
        int maxRepetitions=100;
        Agent a1 = null;
        Agent a2 = null;
        
        
        
              
        
        Util.initRandom();
        
        
        
        
        
        
        
        
        int agreements=0;
        int feasibleagreements=0;
        double sumUtilities=0.0;
        double minUtility=0.0;
        int nexceptions=0;
        double ntried=0.0;
        double numberTried;
        tot=0;
        
        
        
        
            
        for(int repetition=0;repetition<maxRepetitions;repetition++){
        
        
        t1 = System.currentTimeMillis();
        numberTried=0;
        
        
        
           
        
        
        
        
        
        
        
        
        
        
        int numberConflicts=0;
        int numberUniqueAcceptableOption=0;
        int numberHeuristic=0;
        boolean feasibleAgreement=true;
        boolean aDecision;
        boolean bDecision;
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
                    
                        
                        
                        
                        
                        
                            
                
                        

                   
                    
                    
                }
                 
            }
            
            
            tries++;
            if(tries==maxTries){break;}

        }while(numberConflicts==0);
        
        
        
        if(tries==maxTries) {continue;}
        
        
        
        
        
        
        
        
        
        
        
                

        
    if(feasibleAgreement){   
        
    
    
    int i;
    double maxUtility;
    int[] auxVector= new int[nagents];
    System.arraycopy(noconflict, 0, auxVector, 0, nagents);
    
    
    
    
    double auxUT;
    
    
    
    double mostPromisingUT=0.0;
    int mostPromisingAgent;
    int mostPromisingDecision;
    
    
    for(int currentConflict=0;currentConflict<numberConflicts;currentConflict++){
        mostPromisingUT=0.0;
        mostPromisingAgent=-1;
        mostPromisingDecision=-1;
        
        for(int j=0;j<nagents;j++){
            if(auxVector[j]==-1){
                       
                auxVector[j]=0;
                auxUT=a1.utility4(auxVector)*a2.utility4(auxVector);
                
                if(auxUT>mostPromisingUT){
                    mostPromisingUT=auxUT;
                    mostPromisingAgent=j;
                    mostPromisingDecision=0;
                }
                
                auxVector[j]=1;
                auxUT=a1.utility4(auxVector)*a2.utility4(auxVector);
                if(auxUT>mostPromisingUT){
                    mostPromisingUT=auxUT;
                    mostPromisingAgent=j;
                    mostPromisingDecision=1;
                }
                
                auxVector[j]=-1;
                
            }
                
        }
        
        
        auxVector[mostPromisingAgent]=mostPromisingDecision;
        
    }
    auxUT=0;
    
    int[] solution= new int[nagents];
    double maxSolution=mostPromisingUT;
    
    System.arraycopy(auxVector, 0, solution, 0, nagents);
    
    
    
    
    
    LinkedList<BnBnode> list= new LinkedList();
    System.arraycopy(noconflict, 0, auxVector, 0, nagents);
    
    
    BnBnode node;
    int pos;
    int[] auxSolution= new int[nagents];
    int[] auxNumberTried= new int[1];
    
    do{
        
        java.util.Arrays.fill(auxSolution, 0);
        pos=0;
        node=null;
        auxNumberTried[0]=0;
        
        
        if(!list.isEmpty()){
            
            BnBnode mostPromising=list.remove();
            System.arraycopy(mostPromising.actionVector, 0, auxVector, 0, nagents);
            
            
            
            
            
            if(mostPromising.utility>maxSolution){
                        
                        System.arraycopy(mostPromising.solution, 0, solution, 0, nagents);
                        maxSolution=mostPromising.utility;
                        
                        
                        Util.pruneList(list,maxSolution);
              
                    }
            
        }
        
        
        
        
        for(int j=0;j<nagents;j++){
            if(auxVector[j]==-1){
                       
                numberTried++;
                auxVector[j]=0;
                
                
                
                
                auxUT= Util.greedySolution(auxVector, auxSolution, auxNumberTried, nagents, a1, a2); 
                numberTried=numberTried+auxNumberTried[0];
                
                
                if(auxUT>maxSolution){     
                    node= new BnBnode(auxVector, auxSolution, nagents,auxUT);
                    pos=Util.addListOrdered(list, node);
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                }
               
                
                
                
                numberTried++;
                auxVector[j]=1;
                
                
                
                auxUT= Util.greedySolution(auxVector, auxSolution, auxNumberTried, nagents, a1, a2);
                numberTried=numberTried+auxNumberTried[0];
                
                if(auxUT>maxSolution){
                    node= new BnBnode(auxVector, auxSolution, nagents,auxUT);
                    pos=Util.addListOrdered(list, node);
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                }
                
                auxVector[j]=-1;
                
            }
        }
        
        
    }while(!list.isEmpty());
    
    

    
    System.arraycopy(solution, 0, policyAgreed, 0, nagents);
    maxUtility=a1.utility4(policyAgreed)*a2.utility4(policyAgreed);
    
    
        
        
        
        
        
        
                
            
            
            
            
            
    
    
     
        
      
        
       
    
    
    
    
    
    
    if(maxUtility>0.0){
        double a1Utility=a1.utility4(policyAgreed);
        double a2Utility=a2.utility4(policyAgreed);
    
    
        agreements++;
        sumUtilities=sumUtilities+maxUtility;
        if(a1Utility>=a2Utility){
            minUtility=minUtility+a2Utility;
        }
        else {
            minUtility= minUtility+a1Utility;
        }
    }
    
    
    
    
    
    ntried=ntried+numberTried;
    feasibleagreements++;
    }
    else{
    }
    
    t2 = System.currentTimeMillis();
    
    tot=tot+(t2-t1);
    
    } 
    
        
        
        
        
        
        
        
        
        resfile.println(nagents+" "+((agreements*1.0)/(feasibleagreements*1.0))*100.0+" "+sumUtilities/agreements*1.0+" "+minUtility/(agreements*1.0)+" "+(ntried)/agreements+" "+(tot*1.0)/(maxRepetitions*1.0));
        
        
}
    }
    }

}
