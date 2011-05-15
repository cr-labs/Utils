package com.challengeandresponse.utils;

import java.util.*;

/**
 * PropertyThang encapsulate a set of named properties in (key,value) form
 * where value can be any object and key is a String.
 * 
 * This class uses generics to lazily enforce types over the key values,
 * and thus is only compatible with Java 1.5 (aka 5.0) and up.
 * 
 * @author jim
 *
 */
public class PropertyThang {

	/** A variable set of named properties  */
	private Hashtable <String,Object> properties;
	
	/**
	 * If a dictionary is initialized, only keys listed in the dictionary
	 * may be used to add or remove objects from the property list. Otherwise
	 * any key may be used
	 */
	private HashSet <String> dictionary;
	
	private static final String NS_DELIMITER = ":";
	private String namespace;
 	

	/**
	 * Create a propertythang, and access it via the namespace 'namespace'
	 * Namespacing is a rudimentary filter - keys are prefixed with the namespace,
	 * and this PropertyThang can contain objects in other namespaces - they won't
	 * be seen my set/get calls, which confine the view to the configured namespace
	 */
	public PropertyThang(String namespace) {
		this.properties = new Hashtable <String, Object> ();
		this.dictionary = null;
		this.namespace = namespace;
	}
	
	
	/**
	 * Use PropertyThang with your own property set (must be a Hashtable (String,Object) )
	 */
//	public PropertyThang(Hashtable <String, Object> properties) {
//		this.properties = properties;
//		dictionary = null;
//	}
	
	/**
	 * Access an existing PropertyThang, filtering it with a different namespace prefix
	 */
	public PropertyThang(PropertyThang pt, String namespace) {
		this.properties = pt.properties;
		this.dictionary = pt.dictionary;
		this.namespace = namespace;
	}
	
	

	/**
	 * Return a key with current namespace prefixed
	 * @param key add namespace to this key
	 * @return
	 */
	private String namespacedKey(String key) {
		return namespace+NS_DELIMITER+key;
	}
	
	/**
	 * Remove the namespace from a key
	 * @param key add namespace to this key
	 * @return
	 */
	private String denamespacedKey(String key) {
		return (key.replace(namespace+NS_DELIMITER,""));
	}

	
	
	
	/**
	 * A dictionary is optional. If one is set, only key values from the dictionary
	 * are permitted as keys. The dictionary is case and white-space sensitive.
	 * If no dictionary is set, any string can be a key.
	 * Dictionaries are NOT in any namespace - a dictionary rules ALL namespaces
	 * @param dictionary a HashSet of all the legal property names.
	 */
	public void setDictionary(HashSet<String> dictionary) {
		this.dictionary = dictionary;
	}

	
	/**
	 * @return the contents of the current dictionary, as a Vector of Strings
	 */
	public Vector <String> getDictionary() {
		Vector <String> v = new Vector<String>();
		Iterator <String> it = this.dictionary.iterator();
		while (it.hasNext())
			v.add(it.next());
		return v;
	}
	
	
	/**
	 * Careful with this one. The Hashtable is live, and should not be altered
	 * except through calls to PropertyThang methods
	 * @return
	 */
//	public Hashtable <String,Object> getProperties() {
//		return properties;
//	}
	
	/**
	 * @return all the property keys for the current namespace as a vector of strings
	 */
	public Vector <String> getPropertyKeys() {
		Enumeration <String> en = properties.keys();
		Vector <String> keys = new Vector <String> ();
		while (en.hasMoreElements()) {
			String key = en.nextElement();
			if (key.startsWith(namespace+NS_DELIMITER))
				keys.add(denamespacedKey(key));
		}
		return keys;
	}
	
	/**
	 * @return all the property keys and values for the current namespace
	 */
	public Hashtable <String,Object> getPropertyKeysAndValues() {
		Enumeration <String> en = properties.keys();
		Hashtable <String,Object> result = new Hashtable <String,Object> ();
		while (en.hasMoreElements()) {
			String key = en.nextElement();
			if (key.startsWith(namespace+NS_DELIMITER))
				result.put(denamespacedKey(key),properties.get(key));
		}
		return result;
	}
	
	
	private void internalPut(String key, Object value)
	throws IllegalArgumentException {
		if (key == null)
			return;		
		if (dictionary != null) {
			if (! dictionary.contains(key))
				throw new IllegalArgumentException("Key not in dictionary: "+key);
		}
		properties.put(namespace+":"+key,value);
	}
	
	
	/**
	 * Set a named int property. if key is null, the method will silently do nothing
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, int value) {
		internalPut(key,new Integer(value));
	}

	
	/**
	 * Set a named long property. if key is null, the method will silently do nothing
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, long value) {
		internalPut(key,new Long(value));
	}

	
	/**
	 * Set a named double property. if key is null, the method will silently do nothing
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, double value) {
		internalPut(key,new Double(value));
	}

	/**
	 * Set a named String property. if key is null, the method will silently do nothing
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value) {
		internalPut(key,value);
	}

	/**
	 * Set a named boolean property. if key is null, the method will silently do nothing
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, boolean value) {
		internalPut(key,value);
	}

	
	/**
	 * Get a property object. 
	 * @param key The name of the desired property
	 * @param dflt the default value to return if the property is not set
	 * @return The property object, or 'dflt' if it was not found or if 'key' was null
	 */
	public Object getProperty(String key, Object dflt) {
		if (key == null)
			return dflt;
		if (properties.containsKey(namespacedKey(key)))
			return properties.get(namespacedKey(key));
		else
			return dflt;
	}
	
	/**
	 * Get a property object as a String
	 * @param key The name of the desired property
	 * @param dflt the default value to return if the property is not set
	 * @return The property object, or 'dflt' if it was not found or if 'key' was null
	 */
	public String getStringProperty(String key, String dflt) {
		if (key == null)
			return dflt;
		try {
			if (properties.containsKey(namespacedKey(key)))
				return (String) properties.get(namespacedKey(key));
		}
		catch (Exception e) {
		}
		return dflt;
	}

	/**
	 * Get a property object as a primitive int
	 * @param key The name of the desired property
	 * @param dflt the default value to return if the property is not set
	 * @return The property object, or 'dflt' if it was not found or if 'key' was null
	 */
	public int getIntProperty(String key, int dflt) {
		if (key == null)
			return dflt;
		try {
			if (properties.containsKey(namespacedKey(key)))
				return ((Integer) properties.get(namespacedKey(key))).intValue();
		}
		catch (Exception e) { // class cast exception (wrong type), or null pointer exception (not there)			
		}
		return dflt;
	}

	/**
	 * Get a property object as a primitive long
	 * @param key The name of the desired property
	 * @param dflt the default value to return if the property is not set
	 * @return The property object, or 'dflt' if it was not found or if 'key' was null
	 */
	public long getLongProperty(String key, long dflt) {
		if (key == null)
			return dflt;
		try {
			if (properties.containsKey(namespacedKey(key)))
				return ((Long) properties.get(namespacedKey(key))).longValue();
		}
		catch (Exception e) { // class cast exception (wrong type), or null pointer exception (not there)			
		}
		return dflt;
	}

	/**
	 * Get a property object as a primitive double
	 * @param key The name of the desired property
	 * @param dflt the default value to return if the property is not set
	 * @return The key object, or 'dflt' if it was not found or if 'key' was null
	 */
	public double getDoubleProperty(String key, double dflt) {
		if (key == null)
			return dflt;
		try {
			if (properties.containsKey(namespacedKey(key)))
				return ((Double) properties.get(namespacedKey(key))).doubleValue();
		}
		catch (Exception e) { // class cast exception (wrong type), or null pointer exception (not there)
		}
		return dflt;
	}

	/**
	 * Get a property object as a primitive boolean
	 * @param key The name of the desired property
	 * @param dflt the default value to return if the property is not set
	 * @return The property object, or 'dflt' if it was not found or if 'key' was null
	 */
	public boolean getBooleanProperty(String key, boolean dflt) {
		if (key == null)
			return dflt;
		try {
			if (properties.containsKey(namespacedKey(key)))
				return ((Boolean) properties.get(namespacedKey(key))).booleanValue();
		}
		catch (Exception e) { // class cast exception (wrong type), or null pointer exception (not there)
		}
		return dflt;
	}
	
	/**
	 * @return true if a property named 'key' exists, false otherwise
	 */
	public boolean hasProperty(String key) {
		if (key == null)
			return false;
		return properties.containsKey(namespacedKey(key));
	}
	
	
	
	// for testing
	public static void main(String[] args) {
		PropertyThang pt = new PropertyThang("NS1");
		pt.setProperty("INT", 132);
		System.out.println(pt.getIntProperty("INT",-1));
		
		
		PropertyThang pt2 = new PropertyThang(pt,"NS2");
		pt2.setProperty("eep", 1000);
		pt2.setProperty("kaboom", 12);
		
		System.out.println("First namespace");
		System.out.println(pt.getIntProperty("INT",-1));
		System.out.println(pt.getPropertyKeys());

		System.out.println("Second namespace");
		System.out.println(pt2.getIntProperty("eep",-1));
		System.out.println(pt2.getIntProperty("whee",-1));
		System.out.println(pt2.getPropertyKeys());
		
	
	}

	
}
