/**
 * 
 */
package vrpRep.solutionChecker.constraint;

import vrpRep.solutionChecker.ConstraintEvaluation;
import vrpRep.solutionChecker.IConstraint;
import vrpRep.structure.instance.DoubleValue;
import vrpRep.structure.instance.Instance;
import vrpRep.structure.instance.IntValue;
import vrpRep.structure.solution.Route;
import vrpRep.structure.solution.Solution;
import vrpRep.utilities.DistanceCalculator;

/**
 * Verifies that all maximum vehicle traveling distance constraints are respected when only one 
 * vehicle type exists.
 * 
 * @author Maxim HOSKINS, Romain LIENARD, Raphael MOLY and Alexandre RENAUD
 * 
 */
public class MaxTravelDistanceNoType implements IConstraint {
	/**
	 * Evaluation result
	 */
	private ConstraintEvaluation cEval;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * vrpRep.solutionChecker.constraint.IConstraint#evaluate(vrpRep.solutionChecker
	 * .instance.DefaultInstance,
	 * vrpRep.solutionChecker.solution.DefaultSolution)
	 */
	@Override
	public ConstraintEvaluation checkConstraint() {
		cEval = new ConstraintEvaluation();
		evaluateMtd();
		
		return cEval;
	}

	/**
	 * Evaluate constraint with only one type of vehicle
	 * 
	 * @return result
	 */
	private void evaluateMtd() {
		double travelDist;
		int nodeId1, nodeId2;

		for (Route r : Solution.getRoutes()) { // for each route
			travelDist = 0;
			// for each pair of node
			for (int j = 0; j < r.getRequests().size() - 1; j++) {
				try {
					nodeId1 = ((IntValue) Instance.getRequest(
							r.getRequests().get(j).getId())
							.getAttribute("node").get(0)).getValue();
					nodeId2 = ((IntValue) Instance.getRequest(
							r.getRequests().get(j + 1).getId()).getAttribute(
									"node").get(0)).getValue();
					travelDist += DistanceCalculator.calculateDistance(nodeId2,
							nodeId1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			double maxTravelDist = ((DoubleValue) (Instance.getFleet().get(0)
					.getAttribute("maxTravelDistance").get(0))).getValue();
			if (travelDist > maxTravelDist) {
				cEval.addMessage("MaxTravelDistanceNoType|On route :"+r.getId()+" distance travelled :"+travelDist+" greater than "+maxTravelDist);
			}
		}
	}

}