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
public class ChvatalSolver extends GreedySolver{
    
    /**get the name
     * 
     * @return 
     */
    @Override
    public String getName(){
        return("Chvatal");
    }
    
     /**Find and return the set with the smallest cost per uncovered element
     * (lowest cost-coverage ratio)     * 
     * 
     *@return bestSet
     */
    @Override
    public ElementSet nextBestSet(){
        if(!(_unusedSets._model.isEmpty())){
            ElementSet bestSet = new ElementSet();
            int count = 0;
            boolean contain = false;
            double ratio = 0;
            double lowRatio = Double.MAX_VALUE;
            
            //find the elementSet in unusedSets with the greatest number of integers not contained in the solution set
            for (ElementSet us : _unusedSets._model) {
                
                //number of uncovered integers in this ElementSet
                for (int i : us.getTS()) {
                    for (ElementSet ss : _solnSets._model) {
                        if ((ss.getTS().contains(i))) {
                            contain = true;
                            break; //integer already in _solnSets
                        }
                    }
                    if(!(contain))//count only goes up if integer is uncovered in all of _solnSets
                        count++;
                    contain = false;
                }
                
                if(count != 0){
                    ratio = (us.getCost())/((double) count);
                    //update bestSet if this elementSet has lower cost ratio
                    if(ratio < lowRatio){
                        lowRatio = ratio;
                        bestSet = us;
                    }
                    count = 0;
                }
                
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
