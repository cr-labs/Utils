package com.challengeandresponse.utils;

import java.util.*;

public class ChatUtils {

	/**
	 * "do the right thing" to print out objects of various kinds.
	 * This method handles Maps (Key = Value), List and String objects
	 * @param separator the character to place between objects from a Map or List, e.g. a linefeed
	 */
	public static String objectToString(Object o, String separator) {
		StringBuffer sb = new StringBuffer();
		if (o == null)
			return "";
		else if (o instanceof String)
			sb.append(o);
		else if (o instanceof Integer)
			sb.append( ((Integer)o).toString());
		else if (o instanceof Long)
			sb.append( ((Long)o).toString());
		else if (o instanceof Short)
			sb.append( ((Short)o).toString());
		else if (o instanceof Double)
			sb.append( ((Double)o).toString());
		else if (o instanceof Float)
			sb.append( ((Float)o).toString());
		else if (o instanceof Byte)
			sb.append( ((Byte)o).toString());
		else if (o instanceof List) {
			Iterator it = ((List) o).iterator();
			while (it.hasNext()) 
				sb.append(it.next()).append(separator);
		}
		else if (o instanceof Map) {
			Map oMap = (Map) o;
			Set ss = oMap.keySet();
			Iterator it = ss.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				sb.append(key+"="+oMap.get(key)+separator);
			}
		}
		// DEFAULT: just call toString() on the thing - we don't have a converter here for it
		else {
			sb.append(o.toString());
		}
		return sb.toString();
	}


}
