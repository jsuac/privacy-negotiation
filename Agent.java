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

public class Agent {
    protected double[] intimacy=null;
    protected int nagents;
    protected int nrts;
    protected int[] relationshipType;
    protected int nagent;
    protected Policy desired;
    
    protected int right;
    protected int left;
    protected double acceptableLeft,acceptableRight;
    protected int maxExceptions=-1;
    
    protected double maxDistance4=0.0;
    protected double maxDistance5=0.0;
    
        
    
    
    
    
    
    public Agent(int nagents,int nRelationshipTypes, int nagent){
        
        
        this.intimacy= Util.generateRandomDoubles(nagents);
        this.nagents=nagents;
        this.nagent=nagent;
        this.right=nagents-1;
        this.left=0;
        
        
        
        this.nrts=nRelationshipTypes;
        this.relationshipType=new int[this.nagents];
        for(int i=0;i<this.nagents;i++){
            this.relationshipType[i]=Util.random.nextInt(this.nrts);
           
        }
        
        maxDistance5=Math.sqrt(this.nagents*100);
        maxDistance4=Math.sqrt(this.nrts);
        
        
    }
    
    public double getIntimacy(int ag){
        return intimacy[ag];
    }
    
    public int getRelationshipType(int ag){
        return this.relationshipType[ag];
    }
    
    public void printIntimacy(){
        System.out.println("---------------------------");
        System.out.println("Intimacies for Agent "+nagent);
       
        for(int i=0;i<nagents;i++)
            System.out.print(intimacy[i]+" ");
        System.out.println();
        System.out.println("---------------------------");
    }
    
     public void printIntimacy(PrintStream s){
        s.println("-------");
        s.println("Intimacies for Agent "+nagent);
       
        for(int i=0;i<nagents;i++)
            s.print(intimacy[i]+" ");
        s.println();
        s.println("-------");
    }
    
    public double getDesiredPolicy(int ag){
        return desired.getThreshold(getRelationshipType(ag));
    }
    
    
    
    
    public void createDesiredPolicy(){
        this.desired= new Policy(this.nrts);
        for(int i=0;i<nrts;i++){
            desired.setThreshold(i, Util.random.nextDouble());
        }
    }
    
    public double getAcceptableLeft(){
        return acceptableLeft;
    }
    
    public double getAcceptableRight(){
        return acceptableRight;
    }
    
    
    public double policyIntimacy(int[] policy){
        double min=1.0;
        for(int i=0;i<nagents;i++){
            if(policy[i]==1 && intimacy[i]<min)
            {min=intimacy[i];}
        }
        return min;
    }
    
    public boolean acceptableOrdered(int[] policy){
        boolean acceptable=true;
        Friend[] friendList= new Friend[nagents];
        
        for(int i=0;i<nagents;i++){
            if(policy[i]==1 && intimacy[i]< acceptableLeft){
                acceptable=false;
                break;
            }
            
            if(policy[i]==0 && intimacy[i]>= acceptableRight){
                acceptable=false;
                break;
            }
            
            friendList[i]= new Friend();
            friendList[i].intimacy=intimacy[i];
            friendList[i].access=policy[i];
        }
        
        if(!acceptable) {return acceptable;}
        
        
        
        Util.quickSort(friendList, 0, nagents-1);
        
               
        int bound=-1;
        for(int i=0;i<nagents;i++){
            
            if(friendList[i].access==1 && bound==-1)
                bound=i;
            if(friendList[i].access==0 && bound!=-1){
                acceptable=false;
                break;
            }
        }
        
        return acceptable;
    }
    
    
     public double utility3(int[] policy){
        
        
        
        
        
        
        
        double maxD=this.nagents*10;
        
        
        double ut=0.0;
        int decision;
        double desired=0.0;
        double intimacy=0.0;
        for(int i=0;i<nagents;i++){
            desired=this.getDesiredPolicy(i);
            intimacy=this.getIntimacy(i);
            
            if(intimacy>=desired){
                decision=1;
            }
            else{
                decision=0;
            }
            
            if(policy[i]!=decision){
                
                ut=ut + 10*Math.abs(desired-intimacy);
          
            }
            
        }
        
        
        ut=(maxD - ut)/maxD;
        
        return ut;
    }
     
    public double utility4(int[] actionVector){
        
        
        
        
        
        
        Policy p=getMinimumExceptPolicy(actionVector);
        
        
        double ut=maxDistance4 - Policy.distance(desired, p);
        
        
        ut=ut*10.0;
        
        
        int nexcept=getExceptions(p,actionVector);
        
        ut=ut*(1-(nexcept*1.0)/(this.nagents*1.0));
        
        
           
        
        
        return ut;
    }
    
         
    public double utility5(int[] actionVector){
        
        double dt=0.0;
        double currentIntimacy;
        double currentThreshold;
        
        
        
        
        
        
        for(int i=0;i<this.nagents;i++)
        {
            currentIntimacy=this.intimacy[i];
            currentThreshold=this.getDesiredPolicy(i);
            
            if(actionVector[i]==0 && currentIntimacy>=currentThreshold){
                
                dt=dt+Math.pow((currentIntimacy-currentThreshold)*10.0, 2);
            }
            else{
                
                if(actionVector[i]==1 && currentIntimacy<currentThreshold){
                    
                    dt=dt+Math.pow((currentIntimacy-currentThreshold)*10.0, 2);
                }
                else{
                    
                    dt=dt+0.0;
                }
            }
        
        }
        
        
        
        
        double ut=maxDistance5 - Math.sqrt(dt);
        
        
        
        
        
        
        
        
        
        
        
           
        
        
        
        
        
        return ut;
    }
    
    public Policy getMinimumExceptPolicy(int[] actionVector){
        Policy p= new Policy(this.nrts);
        
        
        
        for(int rt=0;rt<this.nrts;rt++){
            p.setThreshold(rt, 1.0);
        }
        
        
        for(int ag=0;ag<this.nagents;ag++){
            if(actionVector[ag]==1 && intimacy[ag] < p.getThreshold(getRelationshipType(ag)) ){
                p.setThreshold(getRelationshipType(ag), intimacy[ag]);
            }
        }
        
       
        int[] nsi=new int[this.nrts];
        int[] nno=new int[this.nrts];
        double[] maxsi=new double[this.nrts];
        double[] maxno=new double[this.nrts];
        java.util.Arrays.fill(nsi, 0);
        java.util.Arrays.fill(nno, 0);
        java.util.Arrays.fill(maxsi, 0.0);
        java.util.Arrays.fill(maxno, 0.0);
        int rt=0;
        for(int ag=0;ag<this.nagents;ag++){
            rt=getRelationshipType(ag);
            
            if(actionVector[ag]==0 && intimacy[ag] >= p.getThreshold(rt) ){
                nno[rt]++;
                if(intimacy[ag]>maxno[rt]){
                    maxno[rt]=intimacy[ag];
                }
            }
            
            if(actionVector[ag]==1 && intimacy[ag] >= p.getThreshold(rt) ){
                nsi[rt]++;
                if(intimacy[ag]>maxsi[rt]){
                    maxsi[rt]=intimacy[ag];
                }
            }
        }
        
        for(int r=0;r<this.nrts;r++){
            if(nno[r]>nsi[r]){
             p.setThreshold(r, maxno[r]+0.00000001);   
            }
        }
        
        if(!Util.isComplete(actionVector, this.nagents)){
       
            
           
            for(int r=0;r<this.nrts;r++){
            for(int ag=0;ag<this.nagents;ag++){
                
                
               if( actionVector[ag]==1 && intimacy[ag] == p.getThreshold(rt) && intimacy[ag]>this.getDesiredPolicy(ag) && nno[rt]==0){
                   p.setThreshold(rt, this.getDesiredPolicy(ag));
               }
            }
            
   
            }
        }
            return p;
        
    }
    
    public Policy getMinimumExceptPolicy2(int[] actionVector){
        Policy p= new Policy(this.nrts);
        
        
        
        for(int rt=0;rt<this.nrts;rt++){
            p.setThreshold(rt, 1.0);
        }
        
        
        for(int ag=0;ag<this.nagents;ag++){
            if(actionVector[ag]==1 && intimacy[ag] < p.getThreshold(getRelationshipType(ag)) ){
                p.setThreshold(getRelationshipType(ag), intimacy[ag]);
            }
        }
        
       
        int[] nsi=new int[this.nrts];
        int[] nno=new int[this.nrts];
        double[] maxsi=new double[this.nrts];
        double[] maxno=new double[this.nrts];
        java.util.Arrays.fill(nsi, 0);
        java.util.Arrays.fill(nno, 0);
        java.util.Arrays.fill(maxsi, 0.0);
        java.util.Arrays.fill(maxno, 0.0);
        int rt=0;
        
        for(int ag=0;ag<this.nagents;ag++){
            rt=getRelationshipType(ag);
            
            if(actionVector[ag]==0 && intimacy[ag] >= p.getThreshold(rt) ){
                nno[rt]++;
                if(intimacy[ag]>maxno[rt]){
                    maxno[rt]=intimacy[ag];
                }
            }
            
        }
        
        for(int ag=0;ag<this.nagents;ag++){
            rt=getRelationshipType(ag);
            
            if(actionVector[ag]==1 && intimacy[ag] >= p.getThreshold(rt) && intimacy[ag]<maxno[rt]){
                nsi[rt]++;
                if(intimacy[ag]>maxsi[rt]){
                    maxsi[rt]=intimacy[ag];
                }
            }
            
        }
        
        
        for(int r=0;r<this.nrts;r++){
            if(nno[r]>nsi[r]){
             p.setThreshold(r, maxno[r]+0.00000001);   
            }
        }
        
        
       
            
           
            for(int r=0;r<this.nrts;r++){
            for(int ag=0;ag<this.nagents;ag++){
                
                
               if( actionVector[ag]==1 && intimacy[ag] == p.getThreshold(rt) && intimacy[ag]>this.getDesiredPolicy(ag)){
                   int maxDenied=-1;
                   double maxIntDenied=0.0;
                   for(int ag2=0;ag2<this.nagents;ag2++){
                       if(actionVector[ag2]==0 && intimacy[ag2] < intimacy[ag] && intimacy[ag2]>maxIntDenied){
                           maxDenied=ag2;
                           maxIntDenied=intimacy[ag2];
                       }
                   }
                   if(this.getDesiredPolicy(ag)>maxIntDenied)
                   {
                   p.setThreshold(rt, this.getDesiredPolicy(ag));
                  
                   }
                   else{
                       p.setThreshold(rt, maxIntDenied+0.00000001);
                   }
               }
            }
            }
            
   
            
        
            return p;
        
    }
    
    public Policy getMinimumExceptPolicy3(int[] actionVector){
        Policy p= new Policy(this.nrts);
        
        
        int[] minExceptions=new int[this.nrts];
        
        
        
        
        java.util.Arrays.fill(minExceptions, this.nagents);
        
        
        
        
        int rt=0,ne=0;
        double ct=0.0;
        
        for(int ag=0;ag<this.nagents;ag++){
                if(actionVector[ag]==1)
                {
                ne=0;
                rt=getRelationshipType(ag);
                ct=intimacy[ag];
                
                for(int ag2=0;ag2<this.nagents;ag2++){
                    if(ag!=ag2){ 
                        if(getRelationshipType(ag2)==rt){ 
                            if(actionVector[ag2]==1 && intimacy[ag2] < ct) {ne++;}
                            if(actionVector[ag2]==0 && intimacy[ag2] >= ct) {ne++;}
                            }
                        }
                    }
                }
                
                if(ne<minExceptions[rt]){
                    p.setThreshold(rt,ct);
                    minExceptions[rt]=ne;
                }
         }
        
            return p;
        
    }
     
     
    public double distance(double policy, double agent){
        return Math.abs(policy-agent);
    }
    
    
        
    
     
     
 
     public void setMaxExceptions(int max){
         maxExceptions=max;
     }
     
     public boolean policyComplyExceptions(int[] policy){
         if(getExceptions(policy)>maxExceptions)
         {return false;}
         else
         {return true;}
     }
     
     public int getExceptions(int[] actionVector){
         
         
         
         
         
         
         
         
        
         
         
        return getExceptions(this.getMinimumExceptPolicy(actionVector),actionVector);
        
     }
     
     public int getExceptions(Policy policy, int[] actionVector){
         int nexceptions=0;
         
         
         for(int cont=0;cont<nagents;cont++){
             if(getIntimacy(cont)>=policy.getThreshold(getRelationshipType(cont)) && actionVector[cont]==0) {
                 nexceptions++;
             }
             
        }
        return nexceptions;
     }
     
     public int getExceptions2(Policy policy, int[] actionVector){
         int nexceptions=0;
         
         
         for(int cont=0;cont<nagents;cont++){
             if(getIntimacy(cont)>=policy.getThreshold(getRelationshipType(cont)) && actionVector[cont]==0) {
                 nexceptions++;
             }
             
              if(getIntimacy(cont)<policy.getThreshold(getRelationshipType(cont)) && actionVector[cont]==1) {
                 nexceptions++;
             }
             
        }
        return nexceptions;
     }
     
      public int getMaxPossibleExceptions(){
         int nexceptions=0;
         
         
         for(int cont=0;cont<nagents;cont++){
             
             
             if(getIntimacy(cont)>=acceptableLeft && getIntimacy(cont)<=acceptableRight){
                 nexceptions++;
             }
             }  
        
        return nexceptions;
     }
}
