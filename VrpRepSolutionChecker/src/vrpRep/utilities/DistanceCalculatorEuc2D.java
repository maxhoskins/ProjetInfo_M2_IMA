/**
 * 
 */
package vrpRep.utilities;

import vrpRep.structure.instance.Euclidian;
import vrpRep.structure.instance.Instance;

/**
 * Calculate distance between two sets of euclidean 2D points. <br />
 * Distance measurements usable are : Euclidean, Manhattan/Taxi cab andGeodesic
 * 
 * @author Maxim HOSKINS, Romain LIENARD, Raphael MOLY and Alexandre RENAUD
 * 
 */
public class DistanceCalculatorEuc2D extends DistanceCalculator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see vrpRep.utilities.DistanceCalculator#calculate(int, int)
	 */
	@Override
	public double calculate(int tail, int head) {
		String distanceType = Instance.getNetwork().getDistanceType();
		Euclidian h, t;
		try {
			h = (Euclidian) Instance.getNode(head).getAttribute("location")
					.get(0);
			t = (Euclidian) Instance.getNode(tail).getAttribute("location")
					.get(0);
			if (distanceType.equals("") || distanceType.contains("euclidean")) {
				return Math.sqrt(Math.pow(h.getCx() - t.getCx(), 2)
						+ Math.pow(h.getCy() - t.getCy(), 2));
			} else if (!distanceType.equals("")
					&& distanceType.contains("manhattan")) {
				return Math.abs(h.getCx() - t.getCx())
						+ Math.abs(h.getCy() - t.getCy());
			} else if (!distanceType.equals("")
					&& distanceType.contains("geodesic")) {
				return 0.0;
			} else {
				throw new Exception("Unknown distance type");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
}
