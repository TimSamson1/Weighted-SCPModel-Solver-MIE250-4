/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solver;
import java.util.TreeSet;
import util.ElementSet;
import model.SCPModel;

/**
 *
 * @author shere
 */

public class GreedyCostSolver extends GreedySolver{
    
    /**get the name
     * 
     * @return 
     */
    @Override
    public String getName(){
        return("Cost");
    }
    
     /** Find and return the set with the lowest cost and at least one uncovered element
     *
     * 
     *@return bestSet
     */
    @Override
    public ElementSet nextBestSet(){
        ElementSet bestSet = new ElementSet();
        if(!(_unusedSets._model.isEmpty())){ 
            boolean notContain = false;
            //set lowCost as the cost of the first element in the unused sets
            double lowCost = Double.MAX_VALUE;
            TreeSet<Integer> solTS = _solnSets.getIntElements();
            
            //find the elementSet in _unusedSets with the greatest number of integers not contained in the solution set
            for (ElementSet us : _unusedSets._model) {
                if(_solnSets._model.isEmpty()){
                    notContain = true;
                }
                else{//once found that elementSet contains one uncovered integer, break
                    for (int i : us.getTS()) {
                        if(!(solTS.contains(i))){
                            notContain = true;
                            break;
                        }
                    }
                }
                
                //update bestSet if this elementSet contains one uncovered int and has lower cost
                if(notContain){
                    if(us.getCost() < lowCost){
                        lowCost = us.getCost();
                        bestSet = us;
                    }
                }
                notContain = false;
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
