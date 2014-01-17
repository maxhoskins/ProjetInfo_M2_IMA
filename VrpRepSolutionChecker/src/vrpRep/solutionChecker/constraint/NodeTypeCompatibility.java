/**
 * 
 */
package vrpRep.solutionChecker.constraint;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import vrpRep.fileReaders.InstanceTranslator;
import vrpRep.schema.instance.Instance;
import vrpRep.schema.instance.Instance.Fleet.Vehicle;
import vrpRep.schema.instance.Instance.Network.Nodes.Node;
import vrpRep.schema.solution.Solution.Routes.Route;
import vrpRep.solutionChecker.solution.DefaultSolution;

/**
 * @author Maxim HOSKINS, Romain LIENARD, Raphael MOLY and Alexandre RENAUD
 * 
 */
public class NodeTypeCompatibility implements IConstraint {

	private Instance		inst;
	private DefaultSolution	sol;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * vrpRep.solutionChecker.constraint.IConstraint#evaluate(vrpRep.solutionChecker
	 * .instance.DefaultInstance,
	 * vrpRep.solutionChecker.solution.DefaultSolution)
	 */
	@Override
	public void evaluate(InstanceTranslator inst, DefaultSolution sol) {
		this.inst = (Instance) inst.getInstance();
		this.sol = sol;

		List<List<BigInteger>> listCompatibilityInstance = vehicleNodeCompatibilityInstance();
		List<BigInteger> listNodeType = getListNodeType();
		boolean b = checkCompatibility(listCompatibilityInstance, listNodeType);

	}

	/**
	 * Check if every vehicle pass through a compatible node
	 * 
	 * @param listCompatibilityInstance
	 *            : contain all the node compatible for each vehicle
	 * @param listNodeType
	 *            : contain all type of each node
	 * @param sol
	 *            : Object used to store XML solution data
	 * @return true if the node/vehicle compatibility is respected
	 */
	private boolean checkCompatibility(
			List<List<BigInteger>> listCompatibilityInstance,
			List<BigInteger> listNodeType) {

		for (Route r : sol.getSolution().getRoutes().getRoute()) {
			// Type de v�hicle de la route
			BigInteger b = r.getType();
			for (vrpRep.schema.solution.Solution.Routes.Route.Node n : r
					.getNode()) {
				if (!listCompatibilityInstance.get(b.intValue()).contains(
						listNodeType.get(Integer.parseInt(n.getContent())))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * @param inst
	 *            : Object used to store XML instance data
	 * @return a list with all the node's type
	 */
	private List<BigInteger> getListNodeType() {
		List<BigInteger> list = new ArrayList<BigInteger>();
		for (Node n : inst.getNetwork().getNodes().getNode()) {
			list.add(n.getId().intValue(), n.getType());
		}
		return list;
	}

	/**
	 * 
	 * @param inst
	 *            : Object used to store XML instance data
	 * @return a list containing all the node compatible for each vehicle
	 */
	private List<List<BigInteger>> vehicleNodeCompatibilityInstance() {
		List<List<BigInteger>> list = new ArrayList<List<BigInteger>>();
		for (Vehicle v : inst.getFleet().getVehicle()) {
			list.add(v.getType().intValue(), v.getNodeTypesCompatible());
		}
		return list;
	}

}
