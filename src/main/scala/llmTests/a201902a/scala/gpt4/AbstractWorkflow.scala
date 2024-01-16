package llmTests.a201902a.scala.gpt4

import llmTests.a201902a.scala.WorkflowsFactory
import llmTests.a201902a.scala.Workflow
import java.util.{HashSet, List, ArrayList, Set, LinkedHashMap, HashMap}
import scala.jdk.CollectionConverters._

abstract class AbstractWorkflow[T](protected val tasks: Set[T], var nextTasksToDo: Set[T]) extends Workflow[T] {
  protected val doneTasks: Set[T] = new HashSet[T]
  override def getTasks: Set[T] = tasks
  override def isCompleted: Boolean = doneTasks.equals(tasks)
  override def getNextTasksToDo: Set[T] = nextTasksToDo
  override def doTask(t: T): Unit = {
    if(!tasks.contains(t)) throw new IllegalArgumentException
    if(!nextTasksToDo.contains(t)) throw new IllegalStateException
    doneTasks.add(t)
    nextTasksToDo.remove(t)
  }
}

