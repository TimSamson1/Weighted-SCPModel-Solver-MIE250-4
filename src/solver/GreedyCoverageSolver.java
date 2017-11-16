/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solver;
import util.ElementSet;

/**
 *
 * @author zhan3312
 */
public class GreedyCoverageSolver extends GreedySolver{
 
    /**get the name
     * 
     * @return 
     */
    @Override
    public String getName(){
        return("Coverage");
    }
    
    /**Find and return the set which will cover the most uncovered elements
     * 
     * 
     *@return bestSet
     */
    @Override
    public ElementSet nextBestSet(){
        if(!(_unusedSets._model.isEmpty())){
            ElementSet bestSet = new ElementSet();
            int count = 0;
            boolean contain = false;
            int topcount = Integer.MIN_VALUE;
            
            //find the elementSet in unusedSets with the greatest number of integers not contained in the solution set
            for (ElementSet us : _unusedSets._model) {
                
                //number of uncovered integers in this ElementSet
                for (int i : us.getTS()) {
                    for (ElementSet ss : _solnSets._model) {
                        if ((ss.getTS().contains(i))) {
                            contain = true;
                            break; //integer already in -solnSets
                        }
                    }
                    if(!(contain)) // count goes up only when integer is uncovered in solnSets
                        count++;
                    contain = false;
                }
                //update bestSet if this elementSet contains more uncovered integers
                if(count > topcount){
                    topcount = count;
                    bestSet = us;
                }
                count = 0;
            }
            if (bestSet.isEmpty()) //return null if there are no uncovered elements
                return null;
            
            return bestSet;
        }
        else{
            return null;
        }
    }
       
    
    
    
    
}
