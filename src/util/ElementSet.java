/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;
import java.util.List;
import java.util.TreeSet;
/**
 *
 * @author zhan3312
 */

public class ElementSet implements Comparable {
    
    // Element set represents the tuple (Set ID, Cost, Integer elements to cover)
    protected int _id; // Set ID
    protected double _cost; // Cost of element set
    protected TreeSet<Integer> _ts; // Element IDs
    
    /**Convert a list into a TreeSet
     * 
     * @param list
     * @return ts
     */
    public static TreeSet<Integer> convertTS(List list){
        TreeSet<Integer> ts = new TreeSet<Integer>(list);
        return ts;
    }
    
    /** Constructor for empty ElementSet
     * 
     */
    public ElementSet(){
        _id = 0;
        _cost = 0.0;
        _ts = new TreeSet<Integer>();
    }
    
    /** Constructor for empty ElementSet
     * 
     * @param id, cost, elements
     */
    public ElementSet(int id, double cost, List<Integer> elements){
        _id = id;
        _cost = cost;
        _ts = convertTS(elements);
    }

    /** Override the comparableTo method so it can be used in a sorted set
     * 
     *@param o
     *@return -1, 0, 1
     */
    @Override
    public int compareTo(Object o){
        if (o instanceof ElementSet){
            ElementSet e = (ElementSet) o;
            if(this._id < e._id)
                return -1;
            else if(this._id == e._id)
                return 0;
            else
                return 1;
        }
        else
            return -1;
    }  

    //GETTERS
    
    /**Get the ID 
     * 
     * @return id
     */
    public int getId(){
        return _id;
    }
    
    /**Get the cost
     * 
     * @return cost
     */
    public double getCost(){
        return _cost;
    }

    /**Get the address for the element TreeSet
     *  
     * @return ts
     */
    public TreeSet<Integer> getTS(){
        return _ts;
    }
    
    /**Get the number of integer elements in the treeset //NO LONGER USED
     * 
     * @return num
     */
    public int elementNum(){
        int num  = _ts.size();
        return num;
    }
    
    
    /** Check if ElementSet is empty // is this even needed
     * 
     * @return empty
     */
    public boolean isEmpty(){
        if(this._ts.isEmpty())
            return true;
        else
            return false;
    }
 
    /**Produce a re-parseable representation of this ElementSet as a String
     * 
     * @return sb.toString()
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Set ID: %3d   Cost: %6.2f   Element IDs: %s", _id, _cost, _ts));
        return sb.toString();
    }
}
