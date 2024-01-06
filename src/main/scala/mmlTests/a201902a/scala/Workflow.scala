package mmlTests.a201902a.scala

import java.util
import java.util.Set


/**
 * An interface to model execution of a workflow: essentially a workflow knows which tasks has to be executed
 * before another one can be executed. During execution one has to keep track of the tasks executed so far.
 * A workflow is completed if all tasks have been executed.
 *
 * @param <T> is the type of tasks
 */trait Workflow[T] { /**
 * @return the set of all tasks
 */def getTasks: util.Set[T]
  /**
   * @return the set of tasks that one can execute now
   */def getNextTasksToDo: util.Set[T]
  /**
   * @param t is the task one wants to execute now
   * @throws IllegalStateException, if t is not a 'nextTastToDo' (see method getNextTasksToDo): >>> It is optional in this exam!
   * @throws IllegalArgumentException, if t is not a task (see method getTasks): >>> It is optional in this exam!
   */def doTask(t: T): Unit
  /**
   * @return if all tasks have been executed
   */def isCompleted: Boolean
}
