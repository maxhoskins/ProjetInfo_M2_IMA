package vrpRep.structure.instance;

public class RequestTimeWindow implements RequestAtt {

	private double	begin;
	private double	end;
	private int		period;
	private boolean	isHardStart;
	private boolean	isHardEnd;

	public RequestTimeWindow() {

	}

	/**
	 * @param begin
	 * @param end
	 * @param period
	 */
	public RequestTimeWindow(double begin, double end, int period) {
		super();
		this.begin = begin;
		this.end = end;
		this.period = period;
	}

	public boolean isFlexBegin() {
		// TODO
		return false;
	}

	public boolean isFlexEnd() {
		// TODO
		return false;
	}

	/**
	 * @return the begin
	 */
	public double getBegin() {
		return begin;
	}

	/**
	 * @param begin
	 *            the begin to set
	 */
	public void setBegin(double begin) {
		this.begin = begin;
	}

	/**
	 * @return the end
	 */
	public double getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(double end) {
		this.end = end;
	}

	/**
	 * @return the period
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * @param period
	 *            the period to set
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	public boolean isHardStart() {
		return isHardStart;
	}

	public void setHardStart(boolean isHardStart) {
		this.isHardStart = isHardStart;
	}

	public boolean isHardEnd() {
		return isHardEnd;
	}

	public void setHardEnd(boolean isHardEnd) {
		this.isHardEnd = isHardEnd;
	}

}