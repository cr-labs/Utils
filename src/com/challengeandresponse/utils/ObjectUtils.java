package com.challengeandresponse.utils;

import java.io.*;


/**
 * Utilities for handling objects.
 * 
 * @author jim
 *
 */
public class ObjectUtils {

	
	/**
	 * Return true if object o has an interface named ifName
	 * @param o the object to inspect
	 * @param iName the fully qualified name of the interface to look for (e.g. "java.io.Serializable", not "Serializable")
	 * @return true if object o has an interface named ifName
	 */
//	public static boolean hasInterface(Object o, String ifName) {
//		Class[] interfaces = o.getClass().getInterfaces();
//
//		for (int i = 0; i < interfaces.length; i++)
//			if (interfaces[i].getName().equals(ifName))
//				return true;
//
//		return false;
//	}
	
	
	
	
	/**
	 * @param c the Class to check for presence of the requested implementations
	 * @param impls One or more interfaces that the class 'c' should implement
	 * @return true if 'c' implements all the interfaces of 'impls', and false otherwise. Short-circuit evaluation, returns true when the first non-listed interface is found
	 */
//	public static boolean hasInterfaces (Class c, Class... impls ) {
//		// a list of all interfaces in the actual class 'c'
//		List ifList = Arrays.asList(c.getInterfaces());
//
//		// check each item in 'impls' to be sure it's in the class's interfaces; return false if not
//		for (int i = 0; i < impls.length; i++) {
//			if (! ifList.contains(impls[i]))
//				return false;
//		}
//		return true;
//	}
	

	
	
	
	
	/**
	 * Source: http://www.javaworld.com/javaworld/javaqa/2003-01/02-qa-0124-clone.html?page=2
	 * Vladimir Roubtsov, JavaWorld.com, 01/24/03
	 * 
	 * @param obj
	 * @return a clone of obj, using serialization
	 */
	public static Object cloneSerial(Object obj) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream ();
			ObjectOutputStream oout = new ObjectOutputStream (out);
			oout.writeObject (obj);

			ObjectInputStream in = new ObjectInputStream (
					new ByteArrayInputStream (out.toByteArray ()));
			return in.readObject ();
		}
		catch (Exception e) {
			throw new RuntimeException ("cannot clone class [" +
			                                                 obj.getClass ().getName () + "] via serialization: " +
			                                                 e.toString ());
		}
	}
	
	
	

 	/**
 	 * For testing only
 	 * @param args
 	 */
 	public static void main(String[] args) {
 		String s = "HELLO";
 		String s2 = s;
 		String s3 = "";
 		
 		System.out.println("s / s1 / s2 "+s+" "+s2+" "+s3);
 		System.out.println("s == s2? "+(s == s2));

 		s3 = (String) cloneSerial(s);
 		System.out.println("s / s1 / s2 "+s+" "+s2+" "+s3);
 		System.out.println("s == s3? "+(s == s3));
 		
 		
 		

 		
 		
 	}
 	

	
	
}
