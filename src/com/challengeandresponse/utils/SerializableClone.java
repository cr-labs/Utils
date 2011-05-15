package com.challengeandresponse.utils;


import java.io.*;

// ----------------------------------------------------------------------------
/**
 * original source:  package com.vladium.utils.clone;
 * http://www.javaworld.com/javaworld/javaqa/2003-01/02-qa-0124-clone.html?page=2#resources
 * 
 * This non-instantiable non-extendible class provides a static clone() method
 * suitable for cloning an instance of any Serializable class.<P>
 * 
 * MT-safety: this class is safe for use from mutliple concurrent threads.
 * 
 * @author (C) <a href="mailto:vroubtsov@illinoisalumni.org">Vlad Roubtsov</a>, 2002
 */
public abstract class SerializableClone
{
    // public: ................................................................
    
    /**
     * Makes a Serialization-based deep clone of 'obj'.
     * 
     * @param obj input object to clone [null will cause a
     * NullPointerException]
     * @return obj's deep clone [never null; can be == to 'obj']
     * 
     * @throws RuntimeException on any failure
     */
    public static Object clone (final Object obj)
    {
        try
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream ();
            ObjectOutputStream oout = new ObjectOutputStream (out);

            oout.writeObject (obj);

            // note: toByteArray() does a defensive data copy
            ObjectInputStream in = new ObjectInputStream (new ByteArrayInputStream (out.toByteArray ()));
            
            return in.readObject ();
        }
        catch (Exception e)
        {
            throw new RuntimeException ("cannot clone class [" + obj.getClass ().getName () + "] via serialization: " + e.toString ());
        }
    }
    
    // protected: .............................................................

    // package: ...............................................................
    
    // private: ...............................................................
    

    private SerializableClone () {} // prevent subclassing

} // end of class
// ----------------------------------------------------------------------------