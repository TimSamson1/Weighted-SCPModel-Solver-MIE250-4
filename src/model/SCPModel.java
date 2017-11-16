/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import util.ElementSet;
import java.util.List;
import java.util.TreeSet;
import solver.GreedySolver;

/**
 *
 * @author zhan3312
 */
public class SCPModel {
    
    
    public TreeSet<ElementSet> _model;
    
    
    /** Constructor for empty SCPModel
     * 
     */
    public SCPModel(){
        _model = new TreeSet<ElementSet>();
    }
    
     /** Constructor for copy of current SCPModel
     * 
     * @param model
     */
    public SCPModel(SCPModel model){
        _model = new TreeSet<ElementSet>();
        for(ElementSet es: model._model)
            _model.add(es); 
    }
    
    /**
     * Add the current element set to the cover model
     * 
     * @param id, cost, ts, elements
    */
    public void addSetToCover(int id, double cost, List<Integer> elements){
        ElementSet set = new ElementSet(id , cost, elements);
        _model.add(set);
    }
    
    /**Gets the total number of unique elements in model
     * 
     * @return num
     */
    public int modelElementNum(){
        TreeSet<Integer> e = new TreeSet<Integer>();
        for(ElementSet es : this._model){
            e.addAll(es.getTS());
        }
        return e.size();  
    }
    
    
    /**Gets the number of sets in a model
     * 
     * @return size
     */
    public int size(){
        int size = _model.size();
        return size;   
    }
    
    /**Gets the integer elements in the _ts from ElementSet in this._model
     * 
     * @return ts
     */
    public TreeSet<Integer> getIntElements(){
        TreeSet ts = new TreeSet<Integer>();
        for(ElementSet es : this._model){
            ts.addAll(es.getTS());
        }
        return ts;
    }
    
     /** Check if SCPModel is empty // is this even needed
     * 
     * @return empty
     */
    public boolean isEmpty(){
        boolean empty = true;
        for(ElementSet es : _model){
            if(!(es.isEmpty())){
                empty = false;
                break;
            }
        }
        return empty;
    }
    
    /** Produce a re-parseable representation of the SCPModel as a String
     * 
     * @return sb.toString()
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\nWeighted SCP model:\n");
        sb.append("---------------------\n");
        sb.append("Number of elements (n): " + this.modelElementNum() + "\n");
        sb.append("Number of sets (m): " + _model.size()  + "\n\n");
        sb.append("Set details:\n");
        sb.append("----------------------------------------------------------\n");
        
        for(ElementSet es : _model){
            sb.append(es + "\n");
        }
        return sb.toString();
    }
}
