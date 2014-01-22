/**
 * 
 */
package vrpRep.solutionChecker.constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vrpRep.exceptions.MissingAttributeException;
import vrpRep.structure.instance.Instance;
import vrpRep.structure.instance.IntValue;
import vrpRep.structure.instance.Vehicle;
import vrpRep.structure.solution.Route;
import vrpRep.structure.solution.Solution;

/**
 * @author Maxim HOSKINS, Romain LIENARD, Raphael MOLY and Alexandre RENAUD
 * 
 */
public class NbVehicleAvailable implements IConstraint {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * vrpRep.solutionChecker.constraint.IConstraint#evaluate(vrpRep.solutionChecker
	 * .instance.DefaultInstance,
	 * vrpRep.solutionChecker.solution.DefaultSolution)
	 */

	private Instance	inst;
	private Solution	sol;

	public void evaluate(Instance instance, Solution solution) {

		this.inst = instance;
		this.sol = solution;
		List<Integer> nbVehicleTypeInstance;
		try {
			nbVehicleTypeInstance = getInstanceVehicle(inst);
			List<Integer> nbVehicleTypeSolution = getSolutionVehicle(sol);
			boolean b = compare(nbVehicleTypeInstance, nbVehicleTypeSolution);
			System.out.println(b);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Compare the number of vehicle used per type with the vehicle available in
	 * the instance
	 * 
	 * @param nbVehicleTypeInstance
	 *            is a list containing the number of vehicle AVAILABLE per type
	 * @param nbVehicleTypeSolution
	 *            is a list containing the number of vehicle USED per type
	 * @return true if the number of vehicles contraint is verified
	 */
	private boolean compare(List<Integer> nbVehicleTypeInstance,
			List<Integer> nbVehicleTypeSolution) {
		if (nbVehicleTypeInstance.size() == 1) {
			if (nbVehicleTypeInstance.get(0) != nbVehicleTypeSolution.get(0))
				return false;
		} else {
			for (int i = 0; i < nbVehicleTypeInstance.size(); i++) {
				if (nbVehicleTypeSolution.get(i).compareTo(
						nbVehicleTypeInstance.get(i)) > 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param inst2
	 *            : Object used to store XML solution data
	 * @return the list containing the number of vehicle AVAILABLE per type
	 * @throws MissingAttributeException
	 * @throws NumberFormatException
	 */
	private List<Integer> getInstanceVehicle(Instance inst) {
		List<Integer> nbVehicleType = new ArrayList<Integer>();
		try {
			if (inst.getFleet().size() == 1) {
				nbVehicleType.add(((IntValue) inst.getFleet().get(0)
						.getAttribute("number").get(0)).getValue());
			} else {
				for (Vehicle v : inst.getFleet()) {

					nbVehicleType.add(
							((IntValue) v.getAttribute("type").get(0))
									.getValue(),
							((IntValue) v.getAttribute("number").get(0))
									.getValue());
				}

			}
		} catch (MissingAttributeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nbVehicleType;
	}

	/**
	 * 
	 * @param sol
	 *            : Object used to store XML solution data
	 * @return the list containing the number of vehicle USED per type
	 */
	private List<Integer> getSolutionVehicle(Solution sol) {
		List<Integer> nbVehicleType = new ArrayList<Integer>(
				Collections.nCopies(sol.getRoutes().size(), 0));
		for (Route r : sol.getRoutes()) {
			// If there is no type of vehicle define in the instance
			if (r.isHasType() == false) {
				int b = nbVehicleType.get(0);
				nbVehicleType.set(0, nbVehicleType.get(b) + 1);
			} else
				nbVehicleType.set(r.getType(),
						nbVehicleType.get(r.getType()) + 1);
		}
		return nbVehicleType;
	}
}
