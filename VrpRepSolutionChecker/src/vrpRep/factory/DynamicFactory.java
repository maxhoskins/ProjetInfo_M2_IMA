/**
 * 
 */
package vrpRep.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import vrpRep.fileReaders.InstanceTranslator;
import vrpRep.fileReaders.SolutionTranslator;
import vrpRep.utilities.DistanceCalculator;

/**
 * Dynamic factory containing links to all dynamic classes of the solution
 * checker such as the element readers
 * 
 * @author Maxim HOSKINS, Romain LIENARD, Raphael MOLY and Alexandre RENAUD
 * 
 */
public class DynamicFactory {

	/**
	 * Properties containing dynamic initialization parameters
	 */
	private Properties	properties;

	/**
	 * Constructor
	 * 
	 * @param propertiesFilePath
	 *            path to properties XML File
	 * @throws IOException
	 * @throws InvalidPropertiesFormatException
	 */
	public DynamicFactory(String propertiesFilePath) {
		try {
			FileInputStream file = new FileInputStream(propertiesFilePath);
			this.properties = new Properties();
			this.properties.loadFromXML(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Loads instance. If exception thrown, ensure xml configuration file
	 * contains class path to instance reader under key "instanceReader".
	 * 
	 * @param instanceXmlPath
	 *            path to instance xml file
	 */
	public void buildDefaultInstance(String instanceXmlPath) {
		new InstanceTranslator(new File(instanceXmlPath));
	}

	/**
	 * Loads solution. If exception thrown, ensure xml configuration file
	 * contains class path to solution reader under key "solutionReader".
	 * 
	 * @param instanceXmlPath
	 *            path to solution xml file
	 */
	public void buildSolution(String solutionXmlPath) {
		new SolutionTranslator(new File(solutionXmlPath));
	}

	/**
	 * Retrieves distance calculator. If exception thrown, ensure xml
	 * configuration file contains class path to demand reader under key
	 * "distanceCalculator".
	 * 
	 */
	public void getDistanceCalculator() {
		try {
			if (this.properties.getProperty("distanceCalculator") != null) {
				Class<?> tClass;
				tClass = Class.forName(this.properties
						.getProperty("distanceCalculator"));
				DistanceCalculator
						.setDistanceCalculator((DistanceCalculator) tClass
								.newInstance());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cloning forbidden
	 */
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException(
				"Singleton pattern implemented on this class");
	}
}
