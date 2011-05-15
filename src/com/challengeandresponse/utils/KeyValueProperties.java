package com.challengeandresponse.utils;

import java.util.HashSet;
import java.util.Properties;

/**
 * Extension of Properties, providing transparent mappings to primitive data types
 * and an optional dictionary to enforce a limited set of keys.
 * 
 * <p>This class extends Properites, adding the following functionality:<br />
 * - optional dictionary: Strings in the dictionary, if any, are the only permissible keys. If there is nothing in the dictionary, then keys are not checked<br />
 * - setters and getters for primitives that automatically convert to Strings on set() and back to primitives on get(): int, long, double, boolean<br />
 * - overridden put/get methods to help assure that only key,value pairs comprised of String,String are accepted<br />
 * 
 * 
 * @author jim
 */
public class KeyValueProperties extends Properties {
	private static final long serialVersionUID = 1L;

	private HashSet <String> dictionary;
	
	public KeyValueProperties()
	throws IllegalArgumentException {
		this.dictionary = new HashSet<String>();
	}

	/**
	 * Add a permitted key to the dictionary
	 * @param label the key to add
	 * @throws IllegalArgumentException - label cannot be added to the dictionary 
	 * @throws NullPointerException if label is null
	 */
	public void addToDictionary(String label) {
		if (! this.dictionary.contains(label))
			this.dictionary.add(label);
	}

	 
	/**
	 * Puts a namespace-prefixed key after checking for errors<br />
	 * Catches attempts to put nulls (silently returns).<br />
	 * If the dictionary contains 1 or more entries, the key is checked and if not in the dictionary, the put does not happen
	 * @throws IllegalArgumentException if the dictionary contains one or more entries and the key is not in the dictionary
	 * @returns the previous value of the specified key in this PropertyThang2, or null if it did not have one.
	 */
	private String internalPut(String key, String value)
	throws IllegalArgumentException {
		if (key == null)
			throw  new NullPointerException("key cannot be null");		
		if (dictionary.size() > 0) {
			if (! dictionary.contains(key))
				throw new IllegalArgumentException("Key not in dictionary: "+key);
		}
		return (String) super.put(key,value);
	}
	
	
	/**
	 * Set a named int property.
	 * @param key
	 * @param value
	 * @throws NullPointerException if the key is null
	 * @return the previous value of the property, or null if there was none
	 */
	public String setProperty(String key, int value) {
		return internalPut(key,String.valueOf(value));
	}

	
	/**
	 * Set a named long property. if key is null, the method will silently do nothing
	 * @param key
	 * @param value
	 * @return the previous value of the property, or null if there was none
	 */
	public String setProperty(String key, long value) {
		return internalPut(key,String.valueOf(value));
	}

	
	/**
	 * Set a named double property. if key is null, the method will silently do nothing
	 * @param key
	 * @param value
	 * @throws NullPointerException if the key is null
	 * @return the previous value of the property, or null if there was none
	 */
	public String setProperty(String key, double value) {
		return internalPut(key,String.valueOf(value));
	}

	/**
	 * Set a named String property. if key is null, the method will silently do nothing
	 * @param key
	 * @param value
	 * @return the previous value of the property, or null if there was none
	 */
	public String setProperty(String key, String value) {
		return internalPut(key,String.valueOf(value));
	}

	/**
	 * Set a named boolean property. if key is null, the method will silently do nothing
	 * @param key
	 * @param value
	 * @return the previous value of the property, or null if there was none
	 */
	public String setProperty(String key, boolean value) {
		return internalPut(key,String.valueOf(value));
	}

	
		
	/**
	 * Get a property object as a String
	 * @param key The name of the desired property
	 * @param dflt the default value to return if the property is not set
	 * @return The property object, or 'dflt' if it was not found or if 'key' was null
	 */
	public String getStringProperty(String key, String dflt) {
		try {
			if (containsKey(key))
				return (getProperty(key));
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
		try {
			if (containsKey(key))
				return Integer.parseInt(getProperty(key));
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
		try {
			if (containsKey(key))
				return Long.parseLong(getProperty(key));
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
		try {
			if (containsKey(key))
				return Double.parseDouble(getProperty(key));
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
		try {
			if (containsKey(key)) 
				return Boolean.parseBoolean(getProperty(key));
		}
		catch (Exception e) { // class cast exception (wrong type), or null pointer exception (not there)
		}
		return dflt;
	}
	
	
	//// these delegate methods override the base HashTable's calls that don't enforce String params
	@Override
	public Object get(Object key) {
		if (! (key instanceof String))
			throw new IllegalArgumentException("get() method in PrimitiveProperties requires String key");
		else
			return super.get((String) key);
	}

	@Override
	public Object put(Object key, Object value) {
		if ( (! (key instanceof String)) || (! (value instanceof String)) )
			throw new IllegalArgumentException("put() method in PrimitiveProperties requires String key and String value");
		else
			return internalPut((String) key, (String) value);
	}

	@Override
	public Object remove(Object key) {
		if (! (key instanceof String))
			throw new IllegalArgumentException("remove() method in PrimitiveProperties requires String key");
		else
			return super.remove((String) key);
	}

	
	
	
	
	// for testing
	public static void main(String[] args) {
		KeyValueProperties pt = new KeyValueProperties();
		
		pt.setProperty("INT", 132);
		System.out.println(pt.getProperty("INT"));

		pt.setProperty("TRUEBOOL",true);
		pt.setProperty("FALSEBOOL", false);
		pt.setProperty("INT",1322);
		pt.setProperty("LONGISH",123L);
		pt.setProperty("STRING","hey");
		
		System.out.println("Making dictionary active with one key");
		pt.addToDictionary("SUPERKEY");
		try {
			pt.setProperty("notindictionary","12");
			System.out.println("FAIL: setProperty() did not throw IllegalArgumentException for key not in dictionary");
		}
		catch (IllegalArgumentException e) {
			System.out.println("OK: setProperty() threw IllegalArgumentException for key not in dictionary");
		}
		
		System.out.println("Keyset: "+pt.keySet());
		System.out.println( (pt.size() == 5) ? "OK: keySet contains 5 items" : "FAIL: Keyset should contain 5 items, but contains "+pt.size());
		
		System.out.println("test getIntProperty for existing key:"+(pt.getIntProperty("INT",-1) == 1322 ? "OK":"FAIL"));
		pt.remove("INT");
		System.out.println("test getIntProperty after removal of existing key:"+(pt.getIntProperty("INT",-1) == -1 ? "OK":"FAIL"));
		
		System.out.println("test getIntProperty for never-existing key:"+(pt.getIntProperty("eeee",-1) == -1 ? "OK" : "FAIL"));
		
		System.out.println("Should be TRUE"+pt.getBooleanProperty("TRUEBOOL", false));
		System.out.println("Should be TRUE"+pt.getBooleanProperty("TRUXBOOL", true));
		
		System.out.println("Should be FALSE"+pt.getBooleanProperty("FALSEBOOL", true));
		System.out.println("Should be FALSE"+pt.getBooleanProperty("TRUXBOOL", false));
		
		try {
			pt.put(new Integer(12), "A");
			System.out.println("FAIL: put() did not throw IllegalArgumentException for non-String keyy");
		}
		catch (IllegalArgumentException e) {
			System.out.println("OK: put() threw IllegalArgumentException for non-String key");
		}

		
	}

}
