package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {
  
  private static List<IStopwatch> stopwatches= new ArrayList<IStopwatch>();
	/**
	 * Creates and returns a new IStopwatch object
	 * @param id The identifier of the new object
	 * @return The new IStopwatch object
	 * @throws IllegalArgumentException if <code>id</code> is empty, null, or already
   *     taken.
	 */
	public synchronized static IStopwatch getStopwatch(String id) { ///DOUBT Synchronized?
	  if (id == null) {
	    throw new IllegalArgumentException("ID cannot be NULL");
	  } else {
	    for (IStopwatch stopwatch : stopwatches) {
        if (stopwatch.getId() == id) {
          throw new IllegalArgumentException("ID name is already taken");
        }
      }
	  }
	  IStopwatch stopwatch = Stopwatch.getInstance(id);
    stopwatches.add(stopwatch);
    return stopwatch; 
	}

	/**
	 * Returns a list of all created stopwatches
	 * @return a List of all creates IStopwatch objects.  Returns an empty
	 * list if no IStopwatches have been created.
	 */
	public static List<IStopwatch> getStopwatches() { ///DOUBT Synchronized?
		return new ArrayList<IStopwatch>(stopwatches);
	}
	
}
