/**
 * 
 */
package vrpRep.solutionChecker.constraint;

import vrpRep.solutionChecker.ConstraintEvaluation;
import vrpRep.solutionChecker.IConstraint;
import vrpRep.structure.instance.Instance;
import vrpRep.structure.instance.IntValue;
import vrpRep.structure.instance.Vehicle;
import vrpRep.structure.solution.Route;
import vrpRep.structure.solution.Solution;

/**
 * Evaluates the vehicles start and finish at the correct nodes
 * @author Maxim HOSKINS, Romain LIENARD, Raphael MOLY and Alexandre RENAUD
 * 
 */
public class DepartureArrivalNode implements IConstraint {
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
		check();
		
		return cEval;
	}
	
	/**
	 * Method checking the validity of the constraint Departure - Arrival Node.
	 */
	private void check() {
		//each route
		for (Route r : Solution.getRoutes()) {
			int type = r.getType();

			if (r.getRequests().get(0).getNodeId() != -1) {
				int routeNodeStart = r.getRequests().get(0).getNodeId();
				int routeNodeArrival = r.getRequests().get(r.getRequests().size()-1)
						.getNodeId();
				//each vehicle
				for (Vehicle v : Instance.getFleet()) {
					if (v.getAttribute("type").get(0) != null) {
						int vehicleType = ((IntValue)v.getAttribute("type").get(0)).getValue();
						if (vehicleType == type) {
							int vehicleNodeArrival = ((IntValue) v.getAttribute("arrivalNode").get(0)).getValue();
							int vehicleNodeStart = ((IntValue) v.getAttribute("departureNode").get(0)).getValue();
							if (vehicleNodeArrival != routeNodeArrival|| vehicleNodeStart != routeNodeStart){
								cEval.addMessage("DepartureArrivalNode|On Route ID : "+r.getId()+
										", Departure Node : "+routeNodeStart+
										", Arrival Node : "+routeNodeArrival+
										" but Vehicle of type "+vehicleType+
										" Departure Node is "+vehicleNodeStart+
										" and Arrival Node is "+vehicleNodeArrival);
							}

						}
					} else {
						int vehicleNodeArrival = ((IntValue) v.getAttribute("arrivalNode").get(0)).getValue();
						int vehicleNodeStart = ((IntValue) v.getAttribute("departureNode").get(0)).getValue();
						if (((IntValue) v.getAttribute("arrivalNode").get(0)).getValue() != 
								routeNodeArrival|| ((IntValue) v.getAttribute("departureNode").get(0)).getValue() != routeNodeStart){
							cEval.addMessage("DepartureArrivalNode|On Route ID : "+r.getId()+
									", Departure Node : "+routeNodeStart+
									", Arrival Node : "+routeNodeArrival+
									" but Vehicle "+
									" Departure Node is "+vehicleNodeStart+
									" and Arrival Node is "+vehicleNodeArrival);
						}
					}
				}
			} 
		}
	}
	
}