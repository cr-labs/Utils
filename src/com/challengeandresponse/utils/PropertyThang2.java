package com.challengeandresponse.utils;

import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>PropertyThang stores sets of key:value pairs. Each collection represents the content of a namespace.
 * PropertyThang is fundamentally just a big collection of KeyValeProperties objects.</p>
 * 
 * 
 * TODO Add some atomic operation functionality so that, for example, an agent could stash its entire state in PropertyThang key:value pairs, prior to a shutdown or suspend call.
 * @author jim
 *
 */
public class PropertyThang2 {
	private static final long serialVersionUID = 1L;

	private Properties namespacedProperties; // top level, is a collection of Properties objects whose keys are the namespaces. Key is namespace, object is a KeyValueProperties object
	private CopyOnWriteArrayList <String> allocatedNamespaces;

	/**
	 */
	public PropertyThang2() {
		this.namespacedProperties = new Properties();
		this.allocatedNamespaces = new CopyOnWriteArrayList<String> ();
	}

	/**
	 * When a request is made for a namespaced kvp, if it exists, then return it and mark it "in use".
	 * Only the first request for a namespaced set of properties gets it. Any subsequent caller throws an exception.
	 * @param namespace
	 * @throws UnsupportedOperationException if the namespace has already been claimed
	 * @return the KeyValueProperties object for the given namespace
	 */

	public synchronized KeyValueProperties getProperties(String namespace)
	throws UnsupportedOperationException {
		if (namespacedProperties.containsKey(namespace)) {
			if (allocatedNamespaces.addIfAbsent(namespace)) // if true, we've got a lock on it
				return (KeyValueProperties) namespacedProperties.get(namespace);
			else
				throw new UnsupportedOperationException("Namespace: "+namespace+" has already been allocated to another caller.");
		}
		else {
			if (allocatedNamespaces.addIfAbsent(namespace)) {
				KeyValueProperties kvp = new KeyValueProperties();
				namespacedProperties.put(namespace, kvp);
				return kvp;			
			}
			else
				throw new UnsupportedOperationException("Namespace: "+namespace+" has already been allocated to another caller.");
		}
	}




	public void loadFromXML(InputStream in) throws IOException,
	InvalidPropertiesFormatException {
		namespacedProperties.loadFromXML(in);
	}

	public void storeToXML(OutputStream os, String comment, String encoding)
	throws IOException {
		namespacedProperties.storeToXML(os, comment, encoding);
	}


	public void storeToXML(OutputStream os, String comment) throws IOException {
		namespacedProperties.storeToXML(os, comment);
	}





	// for testing
	public static void main(String[] args) {
		PropertyThang2 pt = new PropertyThang2();

		KeyValueProperties kvp1 = pt.getProperties("NS1");
		kvp1.setProperty("INT", 132);
		kvp1.setProperty("TRUEBOOL",true);
		kvp1.setProperty("FALSEBOOL", false);
		kvp1.setProperty("INT",1322);
		kvp1.setProperty("LONGISH",123L);
		kvp1.setProperty("STRING","hey");

		System.out.println("Should be 1322: "+kvp1.getIntProperty("INT",-1));
		System.out.println("Should be -1: "+kvp1.getIntProperty("eeee",-1));
		System.out.println("Should be TRUE"+kvp1.getBooleanProperty("TRUEBOOL", false));
		System.out.println("Should be TRUE"+kvp1.getBooleanProperty("TRUXBOOL", true));

		System.out.println("Should be FALSE"+kvp1.getBooleanProperty("FALSEBOOL", true));
		System.out.println("Should be FALSE"+kvp1.getBooleanProperty("TRUXBOOL", false));

		try {
			pt.getProperties("NS1");
			System.out.println("FAIL: Did not throw exception on 2nd attempt to allocate a namespace");
		}
		catch (UnsupportedOperationException e) {
			System.out.println("OK: Threw exception on 2nd attempt top allocate a namespace");
		}


		System.out.println("Namespace 2");
		KeyValueProperties kvp2 = pt.getProperties("NS2");
		kvp2.setProperty("eep", 1000);
		kvp2.setProperty("kaboom", 12);

		System.out.println("access to first namespace's name should return -1:"+kvp2.getIntProperty("INT",-1));
		System.out.println("should return 1000:"+kvp2.getIntProperty("eep",-1));
		System.out.println("should return -1:"+kvp2.getIntProperty("whee",-1));
		System.out.println("should return [kaboom, eep]:"+kvp2.keySet());

	}

}
