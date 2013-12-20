/**
 * 
 */
package vrpRep.fileReaders;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import vrpRep.instance.v2.Instance;
import vrpRep.instance.v2.Network;
import vrpRep.utilities.XmlReader;

/**
 * Class used to store XML instance data.
 * 
 * @author Maxim HOSKINS, Romain LIENARD, Raphael MOLY and Alexandre RENAUD
 * 
 */
public class InstanceReader {

	/**
	 * Master node containing all xml file info
	 */
	private vrpRep.schema.instance.Instance	schemaInstance;
	private Instance						instance;

	/**
	 * Constructor that creates xml tree on class instantiation
	 * 
	 * @param xmlFile
	 *            xml file to extract
	 */
	public InstanceReader(File xmlFile) {
		try {
			XmlReader<vrpRep.schema.instance.Instance> iR = new XmlReader<vrpRep.schema.instance.Instance>();
			this.schemaInstance = iR.unmarshallDocument(xmlFile,
					vrpRep.schema.instance.Instance.class.getName());
			this.instance = new Instance();
		} catch (ClassNotFoundException | JAXBException | IOException e) {
			e.printStackTrace();
		}
	}

	private void translateInstance() {
		networkTransformation();
		nodeTransformation();
		linkTransformation();
		vehicleTransformation();
		requestTransformation();
	}

	private void requestTransformation() {
		// TODO Auto-generated method stub

	}

	private void vehicleTransformation() {
		// TODO Auto-generated method stub

	}

	private void linkTransformation() {
		// TODO Auto-generated method stub

	}

	private void nodeTransformation() {
		// TODO Auto-generated method stub

	}

	private void networkTransformation() {
		Network n = instance.getNetwork();
		if (schemaInstance.getNetwork().getDescriptor().isIsComplete() != null)
			n.setComplete(schemaInstance.getNetwork().getDescriptor()
					.isIsComplete());
		if (schemaInstance.getNetwork().getDescriptor().getDistanceType() != null)
			n.setDistanceType(schemaInstance.getNetwork().getDescriptor()
					.getDistanceType());
		if (schemaInstance.getNetwork().getDescriptor().getRoundingRule() != null)
			n.setRoundingRule(schemaInstance.getNetwork().getDescriptor()
					.getRoundingRule());
	}

}