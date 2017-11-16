import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import model.SCPModel;
import solver.ChvatalSolver;
import solver.GreedyCoverageSolver;
import solver.GreedySolver;
import solver.GreedyCostSolver;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeSet;

/** Example testing class, identical to TestSCPSoln except for classes used.
 * 
 * @author ssanner@mie.utoronto.ca
 *
 */
public class TestSCP {
	
	public static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
	
        
	public static void main(String[] args) throws IOException {
		
		SCPModel model = new SCPModel();
                
		// Create a weighted SCP with
		//   Set 1: weight 3.0, elements { 1, 3, 5, 7, 9 }
		//   Set 2: weight 2.0, elements { 1, 5, 9 }
		//   Set 3: weight 2.0, elements { 5, 7, 9 }
		//   Set 4: weight 5.0, elements { 2, 4, 6, 8, 10 }
		//   Set 5: weight 2.0, elements { 2, 6, 10 }
		//   Set 6: weight 2.0, elements { 4, 8 }
		model.addSetToCover(6, 2.0, Arrays.asList(new Integer[] {4,8}));
		model.addSetToCover(5, 2.0, Arrays.asList(new Integer[] {2,6,10}));
		model.addSetToCover(4, 5.0, Arrays.asList(new Integer[] {2,4,6,8,10}));
		model.addSetToCover(3, 2.0, Arrays.asList(new Integer[] {5,7,9}));
		model.addSetToCover(2, 2.0, Arrays.asList(new Integer[] {1,5,9}));
		model.addSetToCover(1, 3.0, Arrays.asList(new Integer[] {1,3,5,7,9}));
                
		GreedyCoverageSolver CoverageMethod = new GreedyCoverageSolver();
                GreedyCostSolver CostMethod = new GreedyCostSolver();
		ChvatalSolver ChvatalMethod = new ChvatalSolver();
		
                List<GreedySolver> solvers = Arrays.asList(new GreedySolver[] {CoverageMethod, CostMethod, ChvatalMethod});
                
		printComparison(solvers, model, 0.9);
                
                //MY TEST CASES -----------------------------------------------
                
                //Model 2
                SCPModel model2 = new SCPModel();
                
		model2.addSetToCover(6, 2.0, Arrays.asList(new Integer[] {1,2,3,4,8}));
		model2.addSetToCover(5, 2.8, Arrays.asList(new Integer[] {2,5,10}));
		model2.addSetToCover(4, 5.0, Arrays.asList(new Integer[] {2,4,5,9,10,12,15,19}));
		model2.addSetToCover(3, 2.0, Arrays.asList(new Integer[] {5,7,8,9}));
		model2.addSetToCover(2, 2.5, Arrays.asList(new Integer[] {1,5,9}));
		model2.addSetToCover(1, 4.9, Arrays.asList(new Integer[] {2,3,6,8,9,10,30}));
                
                printComparison(solvers, model2, 0.95);
                
                //Model 3
                SCPModel model3 = new SCPModel();
                
		model3.addSetToCover(6, 29.0, Arrays.asList(new Integer[] {4,8,33,55,65,76,77,87,90,100,103,104,140}));
		model3.addSetToCover(5, 69.0, Arrays.asList(new Integer[] {2,6,10,11,12,13,14,55}));
		model3.addSetToCover(4, 57.0, Arrays.asList(new Integer[] {1,2,5,8,10,44,55,93}));
		model3.addSetToCover(3, 28.0, Arrays.asList(new Integer[] {5,7,9}));
		model3.addSetToCover(2, 29.0, Arrays.asList(new Integer[] {1,5,9,10,12,34}));
		model3.addSetToCover(1, 37.0, Arrays.asList(new Integer[] {1,5,7,9,98}));
                
                printComparison(solvers, model3, 0.8);
                
                //Model 4
                SCPModel model4 = new SCPModel();
                
		model4.addSetToCover(6, 9.9, Arrays.asList(new Integer[] {4,5,6,8}));
		model4.addSetToCover(1, 22.0, Arrays.asList(new Integer[] {2,6,7,9,10,11,12,15}));
		model4.addSetToCover(5, 8.0, Arrays.asList(new Integer[] {2,4,6,8,10,11,14,19,20}));
		model4.addSetToCover(3, 20.0, Arrays.asList(new Integer[] {3,5,7,9,10,11,18}));
		model4.addSetToCover(4, 18.0, Arrays.asList(new Integer[] {1,2,5,9}));
		model4.addSetToCover(2, 13.4, Arrays.asList(new Integer[] {10,12,15,24,35,36,37}));
	
                printComparison(solvers, model4, 0.9);
        }
		
	// set minimum coverage level for solution methods
	public static void printComparison(List<GreedySolver> solvers, SCPModel model, double alpha) {
			
		// Show the model
		System.out.println(model);
		
		// Run all solvers and record winners
		GreedySolver timeWinner = null;
		long minTime = Long.MAX_VALUE;
		
		GreedySolver objWinner = null;
		double minObj = Double.MAX_VALUE;
		
		GreedySolver covWinner = null;
		double maxCov = -Double.MAX_VALUE;
		
		for (GreedySolver s : solvers) {
			s.setMinCoverage(alpha);
			s.setModel(model);
			s.solve();
			s.print();
			s.printRowMetrics();
			
			if (minTime > s.getCompTime()) {
				minTime = s.getCompTime();
				timeWinner = s;
			}
			
			if (minObj > s.getObjFn()) {
				minObj = s.getObjFn();
				objWinner = s;
			}
			
			if (maxCov < s.getCoverage()) {
				maxCov = s.getCoverage();
				covWinner = s;
			}
		}

		System.out.format("\nAlpha: %.2f%%\n\n", 100*alpha);
		System.out.println("Algorithm                   Time (ms)     Obj Fn Val     Coverage (%)");
		System.out.println("---------------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------------");
		for (GreedySolver s : solvers)
			s.printRowMetrics();
		System.out.format("%-25s%12s%15s%17s\n", "Category winner", timeWinner.getName(), objWinner.getName(), covWinner.getName());
		System.out.println("---------------------------------------------------------------------\n");
		
		String overall = "Unclear";
		if (timeWinner.getName().equals(objWinner.getName()) && 
			objWinner.getName().equals(covWinner.getName()))
			overall = timeWinner.getName();
		
		System.out.println("Overall winner: " + overall + "\n");
	}
	
}
