package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

public class Stopwatch implements IStopwatch{
  private String id;
  private State state;
  private long startTime;
  private long pauseTime;
  private long elapsedTime;
  private final List<Long> lapTime = new CopyOnWriteArrayList<Long>();
  
  private enum State {
    Running, Idle, Paused
  }
  
  private Stopwatch(String id) {
    this.id = id;
    state = State.Idle;
  }
  
  static Stopwatch getInstance(String id){
    return new Stopwatch(id);
  }
  
  @Override
  public String getId() { ////// DOUBT -> NO need of synchronized na?
    return id;
  }

  @Override
  public void start() {
    long currentTime = System.nanoTime();
    synchronized(this) {
      if (state == State.Running) {
        throw new IllegalStateException("Stopwatch is already Running");
      }    
      if (state == State.Idle) {
        startTime = currentTime;
      } else {
        elapsedTime = currentTime - pauseTime;
        startTime = startTime + elapsedTime;
      }
      state = State.Running;
    }
  }

  @Override
  public void lap() {
    long currentTime = System.nanoTime();
    synchronized(this) {
      if (state != State.Running) {
        throw new IllegalStateException("Stopwatch is not Running");
      }
      lapTime.add((currentTime - startTime)/1000000);
    }
    
  }

  @Override
  public void stop() {
    long currentTime = System.nanoTime();
    synchronized(this) {
      if (state != State.Running) {
        throw new IllegalStateException("Stopwatch is not Running");
      }
      pauseTime = currentTime;
      state = State.Paused;
    }
  }

  @Override
  public void reset() {
    synchronized(this) {
      if (state == State.Idle) {
        throw new IllegalStateException("Stopwatch is already Idle");
      }
      lapTime.clear();
      state = State.Idle;
    }
  }

  @Override
  public List<Long> getLapTimes() {
    return new ArrayList<Long>(lapTime);  ///// DOUBT -> Arraylist or copyonwritearraylist?
  }

  @Override
  public String toString() {
    return "Stopwatch [id=" + id + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Stopwatch other = (Stopwatch) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }
  
  

}
