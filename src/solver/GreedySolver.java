package solver;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;                           
import util.ElementSet;
import model.SCPModel;

/** This is the main method that all solvers inherit from.  It is important
 *  to note how this solver calls nextBestSet() polymorphically!  Subclasses
 *  should *not* override solver(), they need only override nextBestSet().
 * 
 *  We'll assume models are well-defined here and do not specify Exceptions
 *  to throw.
 * 
 * @author ssanner@mie.utoronto.ca
 *
 */
public abstract class GreedySolver {
	
	protected String _name;		  // name of algorithm type
	protected double _alpha;          // minimum required coverage level in range [0,1]
	protected SCPModel _model;        // the SCP model we're currently operating on
	protected double _objFn;          // objective function value (*total cost sum* of all sets used)
	protected double _coverage;       // actual coverage fraction achieved
	protected long _compTime;         // computation time (ms)
	protected SCPModel _solnSets;     // sets selected for current model 
        protected SCPModel _unusedSets;   // sets left unselected
        
	// Basic setter (only one needed)
	public void setMinCoverage(double alpha) { _alpha = alpha; }
	public void setModel(SCPModel model) { _model = model; }
	
	// Basic getters
	public double getMinCoverage() { return _alpha; }
	public double getObjFn() { return _objFn; }
	public double getCoverage() { return _coverage; }
	public long getCompTime() { return _compTime; }
	public String getName() { return _name; }
		
	// TODO: Add any helper methods you need
	
	/** Run the simple greedy heuristic -- add the next best set until either
	 *  (1) The coverage level is reached, or 
	 *  (2) There is no set that can increase the coverage.
	 */
	public void solve() {
		
		// Reset the solver
		_coverage = 0;
                _objFn = 0;
                _compTime = 0;
                
		//Preliminary initializations
                _unusedSets = new SCPModel(_model); // copy of working SCP model 
                _solnSets = new SCPModel(); // empty model to store selected sets

		// Begin the greedy selection loop
		long start = System.currentTimeMillis();
		System.out.println("Running '" + getName() + "'...");
                
		// while (set elements remaining not covered > max num that can be left uncovered
		//        AND all sets have not been selected)
		while((_coverage < _alpha) && (!(_unusedSets._model.isEmpty())) ){
                    if (nextBestSet() != null){
                        ElementSet best = nextBestSet();
                        //change best ElementSet._treeset into a List
                        List<Integer> list = new ArrayList<Integer> (best.getTS());
                        _solnSets.addSetToCover(best.getId(), best.getCost(), list); //case TS into type List
                        System.out.println("- Selected: " + best);
                                
                        _unusedSets._model.remove(best);
  
                        _coverage = ((double)(_solnSets.modelElementNum()))/(this._model.modelElementNum());

                    }
                    else{
                        break; // no more possible best sets
                    }
                }

		//      Call nextBestSet() to get the next best ElementSet to add (if there is one)
		// 		Update solution and local members
		
                //sum the costs of sets in _solnSets
                _objFn = 0;
                for(ElementSet es : _solnSets._model){
                    _objFn += es.getCost();
                }
                
		// Record final set coverage, compTime and print warning if applicable
		 _coverage = ((double)(_solnSets.modelElementNum()))/(this._model.modelElementNum());
                _compTime = System.currentTimeMillis() - start;
		if (_coverage < _alpha)
			System.out.format("\nWARNING: Impossible to reach %.2f%% coverage level.\n", 100*_alpha);
		System.out.println("Done.");
	}
	
	/** Returns the next best set to add to the solution according to the heuristic being used.
	 * 
	 *  NOTE 1: This is the **only** method to be implemented in child classes.
	 *  
	 *  NOTE 2: If no set can improve the solution, returns null to allow the greedy algorithm to terminate.
	 *  
	 *  NOTE 3: This references an ElementSet class which is a tuple of (Set ID, Cost, Integer elements to cover)
	 *          which you must define.
	 * 
	 * @return
	 */
	public abstract ElementSet nextBestSet(); // Abstract b/c it must be implemented by subclasses
	
	/** Print the solution
	 * 
	 */
	public void print() {
		System.out.println("\n'" + getName() + "' results:");
		System.out.format("'" + getName() + "'   Time to solve: %dms\n", _compTime);
		System.out.format("'" + getName() + "'   Objective function value: %.2f\n", _objFn);
		System.out.format("'" + getName() + "'   Coverage level: %.2f%% (%.2f%% minimum)\n", 100*_coverage, 100*_alpha);
		System.out.format("'" + getName() + "'   Number of sets selected: %d\n", _solnSets.size());
		System.out.format("'" + getName() + "'   Sets selected: ");
		for (ElementSet s : _solnSets._model) { //edited: accessed the _model in _solnSets
			System.out.print(s.getId() + " ");
		}
		System.out.println("\n");
	}
	
	/** Print the solution performance metrics as a row
	 * 
	 */
	public void printRowMetrics() {
		System.out.format("%-25s%12d%15.4f%17.2f\n", getName(), _compTime, _objFn, 100*_coverage);
	}	
}
