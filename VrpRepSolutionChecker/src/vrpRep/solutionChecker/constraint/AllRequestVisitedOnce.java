/**
 * 
 */
package vrpRep.solutionChecker.constraint;

import java.util.ArrayList;
import java.util.List;

import vrpRep.solutionChecker.ConstraintEvaluation;
import vrpRep.solutionChecker.IConstraint;
import vrpRep.structure.solution.Request;
import vrpRep.structure.solution.Route;
import vrpRep.structure.solution.Solution;

/**
 * Class checking that all request are only visited once
 * @author Maxim HOSKINS, Romain LIENARD, Raphael MOLY and Alexandre RENAUD
 *
 */
public class AllRequestVisitedOnce implements IConstraint {

	private ConstraintEvaluation cEval;
	/* (non-Javadoc)
	 * @see vrpRep.solChecker.IConstraint#checkConstraint()
	 */
	@Override
	public ConstraintEvaluation checkConstraint() {
		cEval = new ConstraintEvaluation();
		evaluateRequest();
		return cEval;
	}
	/**
	 * Method evaluating the constraint
	 */
	private void evaluateRequest() {
		List<Integer> listSolRequest = new ArrayList<Integer>();
		List<Integer> doublon = new ArrayList<Integer>();
		//each route
		for(Route r : Solution.getRoutes()){
			//each request
			for(Request req : r.getRequests()){
				if(!listSolRequest.contains(req.getId())){
					listSolRequest.add(req.getId());
				}
				else{
					if(!doublon.contains(req.getId())){
						cEval.addMessage("AllRequestVisitedOnce|The request : "+req.getId()+" is visited more than once");
						doublon.add(req.getId());
					}
				}
			}
		}
	}

}
