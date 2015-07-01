/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package policynegotiation;

/**
 *
 * @author jose
 */

import java.util.Random;
import java.io.PrintStream;
import java.util.LinkedList;

public class Util {
    public static Random random = new Random();
	
	public static void initRandom(){
		random.setSeed(System.currentTimeMillis());
	}
	
	public static double[] generateRandomDoubles(int size){
		double[] prob = new double[size];
		
		
		for(int i=0;i<size;i++){
			prob[i]=random.nextDouble();
		}
		
		return prob;
	}
        
        
        
       public static int partition(double[] arr, int left, int right) {

        int i = left, j = right;

        double tmp;

        double pivot = arr[(left + right) / 2];

     

        while (i <= j) {

             while (arr[i] < pivot)

                  i++;

             while (arr[j] > pivot)

                  j--;

            if (i <= j) {

                  tmp = arr[i];

                  arr[i] = arr[j];

                  arr[j] = tmp;

                  i++;

                  j--;

            }

      }

     

      return i;

}

       
   public static int partition(Friend[] f, int left, int right) {

        int i = left, j = right;

        Friend tmp;

        double pivot = f[(left + right) / 2].intimacy;

     

        while (i <= j) {

             while (f[i].intimacy < pivot)

                  i++;

             while (f[j].intimacy > pivot)

                  j--;

            if (i <= j) {

                  tmp = f[i];

                  f[i] = f[j];

                  f[j] = tmp;

                  i++;

                  j--;

            }

      }

     

      return i;

}
   

public static void quickSort(double[] arr, int left, int right) {

      int index = partition(arr, left, right);

      if (left < index - 1)

            quickSort(arr, left, index - 1);

      if (index < right)

            quickSort(arr, index, right);

}

public static void quickSort(Friend[] f, int left, int right) {

      int index = partition(f, left, right);

      if (left < index - 1)

            quickSort(f, left, index - 1);

      if (index < right)

            quickSort(f, index, right);

}

public static void printPolicy(int[] pol, int max){
    for(int j=0;j<max;j++){
            System.out.print(pol[j]);
            
        }
        System.out.println();
}

public static void printPolicy(int[] pol, int max, PrintStream s){
    for(int j=0;j<max;j++){
            s.print(pol[j]);
            
        }
        s.println(" ");
}

public static int addListOrdered(LinkedList<BnBnode> l,BnBnode node){
            int pos=0;
            if(l.isEmpty()){l.add(node);
            }
            else{
                if(!l.contains(node)){   
                    for(int i=0;i<l.size();i++){
                        if(node.utility>=l.get(i).utility){
                           pos=i;
                           break;
                        }
                    }
                    l.add(pos, node);
                }
                else pos=-1;
            }
            
            return pos;
}

public static int addKnodesListOrdered(LinkedList<BnBnode> l,int k, BnBnode node){
            int pos=0;
            int minSize=Math.min(l.size(), k);
            
            if(l.isEmpty())
            {
                l.add(node);
            }
            else{
                if(!l.contains(node)){   
                    
                    for(int i=0;i<minSize;i++){
                        if(node.utility>=l.get(i).utility){
                           pos=i;
                           break;
                        }
                    }
                    l.add(pos, node);
                    if(l.size()>k){
                        l.removeLast();
                    }
                   
                    
                }
                else {pos=-1;}
            }
            
            return pos;
}

public static void pruneList(LinkedList<BnBnode> l,int pos){
    int nRemove=l.size()-(pos+1);
    for(int i=0;i<nRemove;i++)
    {l.removeLast();}     
}

public static void pruneList(LinkedList<BnBnode> l, double utility1, double utility2){
    double difference=utility1-utility2;
    double bound=utility1-(difference/2.0);
    int pos=0;
    int size=l.size();
    
    for(int i=0;i<l.size();i++){
        if(l.get(i).utility<bound){
                          pos=i;
                          break;
          }
    }
    
    for(int i=pos;i<size;i++){
        l.remove(pos);
    }
    
        
}

public static void pruneList(LinkedList<BnBnode> l, double utility){
    
    
    int pos=0;
    int size=l.size();
    
    for(int i=0;i<l.size();i++){
        if(l.get(i).utility<utility){
                          pos=i;
                          break;
          }
    }
    
    for(int i=pos;i<size;i++){
        l.remove(pos);
    }
    
        
}

public static boolean isComplete(int[] av, int size){
    boolean iscomplete=true;
    
    for(int i=0;i<size;i++){
        if(av[i]==-1){iscomplete=false;break;}
    }
    return iscomplete;
}

public static double greedyBounding(int[] auxVectorOrig, int nagents, Agent a1,Agent a2){
    double mostPromisingUT=0.0;
    int mostPromisingAgent;
    int mostPromisingDecision;
    double auxUT=0.0;
    int numberConflicts=0;
    int[] auxVector= new int[nagents];
    
    System.arraycopy(auxVectorOrig, 0, auxVector, 0, nagents);
    
    for(int i=0;i<nagents;i++){
        if(auxVector[i]==-1){numberConflicts++;}
    }
    
    
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
    
    
    
    return mostPromisingUT;
}

public static double greedySolution(int[] auxVectorOrig,int[] auxVector, int[] ntried, int nagents, Agent a1,Agent a2){
    double mostPromisingUT=0.0;
    int mostPromisingAgent;
    int mostPromisingDecision;
    double auxUT=0.0;
    int numberConflicts=0;
    
    
        ntried[0]=0;

    System.arraycopy(auxVectorOrig, 0, auxVector, 0, nagents);
    
    for(int i=0;i<nagents;i++){
        if(auxVector[i]==-1){numberConflicts++;}
    }
    
    
    for(int currentConflict=0;currentConflict<numberConflicts;currentConflict++){
        mostPromisingUT=0.0;
        mostPromisingAgent=-1;
        mostPromisingDecision=-1;
        
        for(int j=0;j<nagents;j++){
            if(auxVector[j]==-1){
                                ntried[0]++;       
       
                auxVector[j]=0;
                auxUT=a1.utility4(auxVector)*a2.utility4(auxVector);
                
                if(auxUT>mostPromisingUT){
                    mostPromisingUT=auxUT;
                    mostPromisingAgent=j;
                    mostPromisingDecision=0;
                }
                                ntried[0]++;       

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
    
    
    
    return mostPromisingUT;
}

public static double greedyIndividualSolution(int[] auxVectorOrig,int[] auxVector, int[] ntried, int nagents, Agent[] agents, int nagent){
    double mostPromisingUT=0.0;
    double mostPromisingIndUT=0.0;
    int mostPromisingAgent;
    int mostPromisingDecision;
    double auxUT=0.0;
    int numberConflicts=0;
    
    
        ntried[0]=0;

    System.arraycopy(auxVectorOrig, 0, auxVector, 0, nagents);
    
    for(int i=0;i<nagents;i++){
        if(auxVector[i]==-1){numberConflicts++;}
    }
    
    
    for(int currentConflict=0;currentConflict<numberConflicts;currentConflict++){
        mostPromisingUT=0.0;
        mostPromisingIndUT=0.0;
        mostPromisingAgent=-1;
        mostPromisingDecision=-1;
        
        for(int j=0;j<nagents;j++){
            if(auxVector[j]==-1){
                                ntried[0]++;       
       
                auxVector[j]=0;
                auxUT=agents[0].utility4(auxVector)*agents[1].utility4(auxVector);
                
                if(auxUT>mostPromisingUT){
                    mostPromisingUT=auxUT;
                    mostPromisingIndUT=agents[nagent].utility4(auxVector);
                    mostPromisingAgent=j;
                    mostPromisingDecision=0;
                }
                
                if(auxUT==mostPromisingUT && agents[nagent].utility4(auxVector)>mostPromisingIndUT){
                    mostPromisingIndUT=agents[nagent].utility4(auxVector);
                    mostPromisingAgent=j;
                    mostPromisingDecision=0;
                }
                
                                ntried[0]++;       

                auxVector[j]=1;
                auxUT=agents[0].utility4(auxVector)*agents[1].utility4(auxVector);
                if(auxUT>mostPromisingUT){
                    mostPromisingUT=auxUT;
                    mostPromisingIndUT=agents[nagent].utility4(auxVector);
                    mostPromisingAgent=j;
                    mostPromisingDecision=1;
                }
                if(auxUT==mostPromisingUT && agents[nagent].utility4(auxVector)>mostPromisingIndUT){
                    mostPromisingIndUT=agents[nagent].utility4(auxVector);
                    mostPromisingAgent=j;
                    mostPromisingDecision=1;
                }
                
                auxVector[j]=-1;
                
            }
                
        }
        
        
        auxVector[mostPromisingAgent]=mostPromisingDecision;
        
    }
    
    
    
    return mostPromisingUT;
}

public static double getMinimumUtility(int[] auxVector, int nagents, Agent[] agents){
     double minUT=agents[0].utility4(auxVector);
     double ut=0.0;
     
     for(int numberAgent=1;numberAgent<nagents;numberAgent++){
                ut=agents[numberAgent].utility4(auxVector);
         if(ut<minUT){
               minUT=ut;
                }
      }
     
     return minUT;
}

public static double getMinimumUtility3(int[] auxVector, int nagents, Agent[] agents){
     double minUT=agents[0].utility3(auxVector);
     double ut;
     
     for(int numberAgent=1;numberAgent<nagents;numberAgent++){
                ut=agents[numberAgent].utility3(auxVector);
         if(ut<minUT){
               minUT=ut;
                }
      }
     
     return minUT;
}

public static double greedyEgalitarianSolution3(int[] auxVectorOrig,int[] auxVector, int[] ntried, int nagents, int nnegotiating, Agent[] agents){
    double mostPromisingUT=0.0;
    
    int mostPromisingAgent;
    int mostPromisingDecision;
    double auxUT=0.0;
    int numberConflicts=0;
    
    
        ntried[0]=0;

    System.arraycopy(auxVectorOrig, 0, auxVector, 0, nagents);
    
    for(int i=0;i<nagents;i++){
        if(auxVector[i]==-1){numberConflicts++;}
    }
    
    
    for(int currentConflict=0;currentConflict<numberConflicts;currentConflict++){
        mostPromisingUT=0.0;
        
        mostPromisingAgent=-1;
        mostPromisingDecision=-1;
        
        for(int j=0;j<nagents;j++){
            if(auxVector[j]==-1){
                                ntried[0]++;       
       
                auxVector[j]=0;
                auxUT=getMinimumUtility3(auxVector,nnegotiating,agents);
                
                if(auxUT>mostPromisingUT){
                    mostPromisingUT=auxUT;
                    
                    mostPromisingAgent=j;
                    mostPromisingDecision=0;
                }
                
                
                
                                ntried[0]++;       

                auxVector[j]=1;
                auxUT=getMinimumUtility3(auxVector,nnegotiating,agents);
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
    
    
    
    return mostPromisingUT;
}

public static double greedyEgalitarianSolution(int[] auxVectorOrig,int[] auxVector, int[] ntried, int nagents, int nnegotiating, Agent[] agents){
    double mostPromisingUT=0.0;
    
    int mostPromisingAgent;
    int mostPromisingDecision;
    double auxUT=0.0;
    int numberConflicts=0;
    
    
        ntried[0]=0;

    System.arraycopy(auxVectorOrig, 0, auxVector, 0, nagents);
    
    for(int i=0;i<nagents;i++){
        if(auxVector[i]==-1){numberConflicts++;}
    }
    
    
    for(int currentConflict=0;currentConflict<numberConflicts;currentConflict++){
        mostPromisingUT=0.0;
        
        mostPromisingAgent=-1;
        mostPromisingDecision=-1;
        
        for(int j=0;j<nagents;j++){
            if(auxVector[j]==-1){
                                ntried[0]++;       
       
                auxVector[j]=0;
                auxUT=getMinimumUtility(auxVector,nnegotiating,agents);
                
                if(auxUT>mostPromisingUT){
                    mostPromisingUT=auxUT;
                    
                    mostPromisingAgent=j;
                    mostPromisingDecision=0;
                }
                
                
                
                                ntried[0]++;       

                auxVector[j]=1;
                auxUT=getMinimumUtility(auxVector,nnegotiating,agents);
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
    
    
    
    return mostPromisingUT;
}

public static int getNumberConflicts(int[] auxVector,int nagents){
   int numberConflicts=0;
   
    for(int i=0;i<nagents;i++){
        if(auxVector[i]==-1){numberConflicts++;}
    }
    
    return numberConflicts;
}

public static double greedySolution(int[] auxVectorOrig,int[] auxVector, int[] ntried, int nagents, Agent a1,Agent a2, Agent a3){
    double mostPromisingUT=0.0;
    int mostPromisingAgent;
    int mostPromisingDecision;
    double auxUT=0.0;
    int numberConflicts=0;
    
    
        ntried[0]=0;

    System.arraycopy(auxVectorOrig, 0, auxVector, 0, nagents);
    
    for(int i=0;i<nagents;i++){
        if(auxVector[i]==-1){numberConflicts++;}
    }
    
    
    for(int currentConflict=0;currentConflict<numberConflicts;currentConflict++){
        mostPromisingUT=0.0;
        mostPromisingAgent=-1;
        mostPromisingDecision=-1;
        
        for(int j=0;j<nagents;j++){
            if(auxVector[j]==-1){
                                ntried[0]++;       
       
                auxVector[j]=0;
                auxUT=a1.utility4(auxVector)*a2.utility4(auxVector)*a3.utility4(auxVector);
                
                if(auxUT>mostPromisingUT){
                    mostPromisingUT=auxUT;
                    mostPromisingAgent=j;
                    mostPromisingDecision=0;
                }
                                ntried[0]++;       

                auxVector[j]=1;
                auxUT=a1.utility4(auxVector)*a2.utility4(auxVector)*a3.utility4(auxVector);
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
    
    
    
    return mostPromisingUT;
}

}

