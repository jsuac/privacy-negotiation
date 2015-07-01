/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package policynegotiation;


/**
 *
 * @author jose
 */


public class Policy {
     double[] thresholds;
    int nRTs; 
    
    public Policy (int nRelationshipTypes){
        nRTs=nRelationshipTypes;
        
        thresholds=new double[nRTs];
    }
    
    public int getnRTs(){
        return nRTs;
    }
    
    public void setThreshold(int rt, double thr){
        thresholds[rt]=thr;
    }
    
    
    
    public double getThreshold(int rt){
        return thresholds[rt];
    }
    
    public static double distance(Policy pol1, Policy pol2){
        double result=0.0;
        int NRTS=pol1.getnRTs();
        
        for(int i=0;i<NRTS;i++){
            result=result+Math.pow(pol1.getThreshold(i)-pol2.getThreshold(i), 2.0);
        }
        return Math.sqrt(result);
    }
}
