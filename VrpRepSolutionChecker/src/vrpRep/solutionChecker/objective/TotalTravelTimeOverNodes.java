/**
 * 
 */
package vrpRep.solutionChecker.objective;

import vrpRep.solutionChecker.IObjectiveFunction;
import vrpRep.solutionChecker.constraint.DeterministicMaxWorkTimeNodes;
import vrpRep.structure.instance.DoubleValue;
import vrpRep.structure.instance.Instance;
import vrpRep.structure.solution.Request;
import vrpRep.structure.solution.Route;
import vrpRep.structure.solution.Solution;
import vrpRep.utilities.DistanceCalculator;

/**
 * Calculates vehicle travel time when times are based on node separations and vehicle speed and service times for requests are NOT taken into account
 * @author Maxim HOSKINS, Romain LIENARD, Raphael MOLY and Alexandre RENAUD
 *
 */
public class TotalTravelTimeOverNodes implements IObjectiveFunction{

	@Override
	public double evaluate() {
		DeterministicMaxWorkTimeNodes wton = new DeterministicMaxWorkTimeNodes();

		int vehiType = -1;
		int departureNode, arrivalNode;
		double timeSpent = 0, speed;
		Request req;

		//each route
		for(Route r : Solution.getRoutes()){
			vehiType = (r.isHasType()?r.getType():-1);	
			if(Instance.getRequest(r.getRequests().get(0).getId()).getAttribute("serviceTime") != null)
				timeSpent += ((DoubleValue)Instance.getRequest(r.getRequests().get(0).getId()).getAttribute("serviceTime").get(0)).getValue();

			
			for(int reqIndex = 1; reqIndex < r.getRequests().size(); reqIndex++){
				req = r.getRequests().get(reqIndex);
				arrivalNode = req.getNodeId();
				departureNode = r.getRequests().get(reqIndex-1).getNodeId();

				if(vehiType != -1)
					speed = wton.getSpeed(Instance.getVehicle(vehiType).getAttribute("speedProfile"), timeSpent);
				else
					speed = wton.getSpeed(Instance.getVehicle(vehiType).getAttribute("speedProfile"), timeSpent);
				timeSpent += DistanceCalculator.calculateDistance(departureNode, arrivalNode)/speed;	
				if(Instance.getRequest(req.getId()).getAttribute("serviceTime") != null)
					timeSpent += ((DoubleValue)Instance.getRequest(req.getId()).getAttribute("serviceTime").get(0)).getValue();
			}
		}

		return timeSpent;
	}
}