/**
 * 
 */
package vrpRep.solutionChecker.constraint;

import java.util.List;

import vrpRep.solutionChecker.ConstraintEvaluation;
import vrpRep.solutionChecker.IConstraint;
import vrpRep.structure.instance.DoubleValue;
import vrpRep.structure.instance.Instance;
import vrpRep.structure.instance.IntValue;
import vrpRep.structure.instance.Vehicle;
import vrpRep.structure.solution.Route;
import vrpRep.structure.solution.Solution;
import vrpRep.utilities.DistanceCalculator;

/**
 * Verifies that all maximum vehicle traveling distance constraints are respected when multiple 
 * vehicle types exist.
 * @author Maxim HOSKINS, Romain LIENARD, Raphael MOLY and Alexandre RENAUD
 * 
 */
public class MaxTravelDistanceWithType implements IConstraint {
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
		evaluateMtdWithTypes();

		return cEval;
	}

	/**
	 * Evaluate constraint with several types of vehicles
	 */
	private void evaluateMtdWithTypes() {
		List<Vehicle> fleet = Instance.getFleet();
		int currentType = 0, nodeId1, nodeId2;
		double travelDist;
		//each route
		for (Route r : Solution.getRoutes()) {
			travelDist = 0;
			//each pair of node
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
			//each vehicle
			for (int i = 0; i < fleet.size(); i++) {
				currentType = ((IntValue) (fleet.get(i).getAttribute("type")
						.get(0))).getValue();
				if (currentType == r.getType()) {
					double maxTravelDist=((DoubleValue) (Instance.getFleet().get(i)
							.getAttribute("maxTravelDistance").get(0)))
							.getValue();
					if (travelDist >maxTravelDist ) {
						cEval.addMessage("MaxTravelDistanceWithType|On route :"+r.getId()+"vehicle of type : "+currentType+" travelled a distance of:"+travelDist+" greater than "+maxTravelDist);
					}
				}
			}
		}
	}
	
}