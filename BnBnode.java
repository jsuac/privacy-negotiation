/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package policynegotiation;

/**
 *
 * @author jose
 */

public class BnBnode {
	public int[] actionVector;
        public int[] solution;
	public double utility;
        public int size;
	
	public BnBnode (int[] aV, int[] sol, int size, double utility){
		this.actionVector=new int[size];
                this.solution=new int[size];
                this.size=size;
		System.arraycopy(aV, 0, actionVector, 0, size);
                System.arraycopy(sol, 0, solution, 0, size);
		this.utility=utility;		
	}
        
        public BnBnode (int[] aV, int size, double utility){
		this.actionVector=new int[size];
                this.solution=new int[size];
                this.size=size;
		System.arraycopy(aV, 0, actionVector, 0, size);
                
		this.utility=utility;		
	}
        
        public boolean equals(BnBnode node){
            boolean isequal=true;
            
            for(int i=0;i<this.size;i++){
                if(this.actionVector[i]!=node.actionVector[i]){
                    isequal=false;
                    break;
                }
            }
            
            return isequal;
        }
        
}

