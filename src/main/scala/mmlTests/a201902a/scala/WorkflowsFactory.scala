package mmlTests.a201902a.scala

import java.util
import java.util.{List, Set}


trait WorkflowsFactory { /**
 * @param <T>
 * @param task
 * @return a workflow made of a single 'task'
 */def singleTask[T](task: T): Workflow[T]
  /**
   * @param <T>
   * @param tasks
   * @return a workflow made of first element of 'tasks', then second, then third, and so on
   */def tasksSequence[T](tasks: util.List[T]): Workflow[T]
  /**
   * @param <T>
   * @param tasks
   * @return a workflow made of 'initialTasks' (any order), and then all are executed, of 'finalTask'  
   */def tasksJoin[T](initialTasks: util.Set[T], finalTask: T): Workflow[T]
  /**
   * @param <T>
   * @param initialTask
   * @param finalTasks
   * @return a workflow made of initialTasks, and then (in any order) all tasks in 'finalTask'   
   */def tasksFork[T](initialTask: T, finalTasks: util.Set[T]): Workflow[T]
  /**
   * >>> This method is optional in this exam!
   * @param <T>
   * @param first
   * @param second
   * @return a workflow where we execute entire workflow 'first', and then entire workflow 'second'
   */def concat[T](first: Workflow[T], second: Workflow[T]): Workflow[T]
}
