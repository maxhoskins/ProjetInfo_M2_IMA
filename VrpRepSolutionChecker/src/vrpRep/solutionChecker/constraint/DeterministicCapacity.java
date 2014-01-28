/**
 * 
 */
package vrpRep.solutionChecker.constraint;

import java.util.ArrayList;
import java.util.HashMap;

import vrpRep.structure.instance.Compartment;
import vrpRep.structure.instance.DoubleValue;
import vrpRep.structure.instance.Instance;
import vrpRep.structure.solution.Demand;
import vrpRep.structure.solution.Request;
import vrpRep.structure.solution.Route;
import vrpRep.structure.solution.Solution;
import vrpRep.utilities.ConstraintResult;

/**
 * Class used to evaluate capacity constraints
 * 
 * @author Maxim HOSKINS, Romain LIENARD, Raphael MOLY and Alexandre RENAUD
 * 
 */
public class DeterministicCapacity implements IConstraint {

	private boolean				cValid	= true;
	private ArrayList<String>	details	= new ArrayList<String>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * vrpRep.solutionChecker.constraint.IConstraint#evaluate(vrpRep.solutionChecker
	 * .instance.DefaultInstance,
	 * vrpRep.solutionChecker.solution.DefaultSolution)
	 */
	@Override
	public ConstraintResult evaluate() {
		VehicleCResult vcr;
		boolean multiVehiTypes;

		// chekc multi type
		if (Solution.getRoutes().get(0).isHasType())
			multiVehiTypes = true;
		else
			multiVehiTypes = false;

		// each route
		for (Route r : Solution.getRoutes()) {
			if (multiVehiTypes)
				vcr = new VehicleCResult(r.getType());
			else
				vcr = new VehicleCResult(-1);

			// sum demands
			for (Request n : r.getRequests()) {
				if (n.getDemand().size() != 0) {
					for (Demand d : n.getDemand()) {
						vcr.addDemand(d.getId(), d.getDemand());
					}
				}
			}

			checkDemands(vcr, multiVehiTypes);
		}
		if (!cValid) {
			String sResult = "";
			sResult.concat(details.get(0));
			for (int i = 1; i < details.size(); i++)
				sResult.concat("\n" + details.get(i));

			return new ConstraintResult(cValid, sResult,
					"Deterministic capacity");
		} else {
			return new ConstraintResult(cValid, "Deterministic capacity");
		}

	}

	private void checkDemands(VehicleCResult vcr, boolean multiVehiTypes) {
		double capacityMin, capacityMax;
		Compartment compartement;
		if (vcr.getProductIds().size() > 1) { // multi products
			for (int pId : vcr.getProductIds()) {
				if (multiVehiTypes) {
					compartement = (Compartment) Instance.getVehicle(
							vcr.getVehiType()).getAttribute("compartment");
					capacityMin = compartement.getMin();
					capacityMax = compartement.getMax();
					if (vcr.getSumDemands().get(pId) > capacityMax) {
						details.add("Vehicle capacity type "
								+ vcr.getVehiType() + " , Product Id " + pId
								+ " - " + vcr.getSumDemands().get(pId) + " > "
								+ capacityMax);
						cValid = false;
					} else if (vcr.getSumDemands().get(pId) < capacityMin) {
						details.add("Vehicle capacity type "
								+ vcr.getVehiType() + " , Product Id " + pId
								+ " - " + vcr.getSumDemands().get(pId) + " < "
								+ capacityMin);
						cValid = false;
					}
				} else {
					compartement = (Compartment) Instance.getVehicle()
							.getAttribute("compartment");
					capacityMin = compartement.getMin();
					capacityMax = compartement.getMax();
					if (vcr.getSumDemands().get(pId) > capacityMax) {
						details.add("Vehicle capacity , Product Id " + pId
								+ " - " + vcr.getSumDemands().get(pId) + " > "
								+ capacityMax);
						cValid = false;
					} else if (vcr.getSumDemands().get(pId) < capacityMin) {
						details.add("Vehicle capacity , Product Id " + pId
								+ " - " + vcr.getSumDemands().get(pId) + " < "
								+ capacityMin);
						cValid = false;
					}
				}
			}
		} else {
			if (multiVehiTypes) {
				capacityMax = ((DoubleValue) Instance.getVehicle(
						vcr.getVehiType()).getAttribute("capacity")).getValue();
				if (vcr.getSumDemands().get(vcr.getProductIds().get(0)) > capacityMax) {
					details.add("Vehicle capacity " + vcr.getVehiType() + " - "
							+ vcr.getSumDemands().get(0) + " > " + capacityMax);
					cValid = false;
				}
			} else {
				capacityMax = ((DoubleValue) Instance.getVehicle()
						.getAttribute("capacity")).getValue();
				if (vcr.getSumDemands().get(vcr.getProductIds().get(0)) > capacityMax) {
					details.add("Vehicle capacity - "
							+ vcr.getSumDemands().get(0) + " > " + capacityMax);
					cValid = false;
				}
			}
		}

	}

	private class VehicleCResult {
		private int							vehiType;
		private ArrayList<Integer>			productIds	= new ArrayList<Integer>();
		private HashMap<Integer, Double>	sumDemands	= new HashMap<Integer, Double>();

		public VehicleCResult(int vehiType) {
			this.vehiType = vehiType;
		}

		public void addDemand(int productId, double demand) {
			if (!productIds.contains(productId)) {
				productIds.add(productId);
				sumDemands.put(productId, demand);
			} else {
				sumDemands.put(productId, sumDemands.get(productId) + demand);
			}
		}

		/**
		 * @return the vehiType
		 */
		public int getVehiType() {
			return vehiType;
		}

		/**
		 * @return the productIds
		 */
		public ArrayList<Integer> getProductIds() {
			return productIds;
		}

		/**
		 * @return the sumDemands
		 */
		public HashMap<Integer, Double> getSumDemands() {
			return sumDemands;
		}

	}
}
