/**
 * 
 */
package vrpRep.solutionChecker.instance;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import vrpRep.schema.instance.Instance;
import vrpRep.schema.instance.Instance.Fleet.Vehicle;
import vrpRep.schema.instance.Instance.Network.Links.Link;
import vrpRep.schema.instance.Instance.Network.Nodes.Node;
import vrpRep.schema.instance.Instance.Requests.Request;
import vrpRep.utilities.DistanceCalculator;
import vrpRep.utilities.XmlReader;

/**
 * Class used to store XML instance data.
 * @author Maxim HOSKINS, Romain LIENARD, Raphael MOLY and Alexandre RENAUD
 *
 */
public class DefaultInstance {

	
	/**
	 * Master node containing all xml file info
	 */
	protected Instance instance;
	

	
	
	/**
	 * Constructor that creates xml tree on class instantiation 
	 * @param xmlFile xml file to extract
	 */
	public DefaultInstance(File xmlFile){	
		try {
			XmlReader<Instance> iR = new XmlReader<Instance>();
			this.instance = iR.unmarshallDocument(xmlFile, Instance.class.getName());
		} catch (ClassNotFoundException | JAXBException | IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * 
	 * @return instance
	 */
	public Instance getInstance(){
		return this.instance;
	}
	
	
	/**
	 * 
	 * @return list containing all nodes
	 */
	public List<Node> getNodes(){
		return instance.getNetwork().getNodes().getNode();
	}
	
	
	/**
	 * 
	 * @return list containing all links
	 */
	public List<Link> getLinks(){
		return instance.getNetwork().getLinks().getLink();
	}
	
	
	/**
	 * 
	 * @return list containing all vehicles
	 */
	public List<Vehicle> getFleet(){
		return instance.getFleet().getVehicle();
	}
	
	
	/**
	 * 
	 * @return list containing all requests
	 */
	public List<Request> getResquests(){
		return instance.getRequests().getRequest();
	}
}
