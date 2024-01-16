package llmTests.a201902a.scala.gpt4

import java.util.{LinkedHashSet, LinkedList}
import llmTests.a201902a.scala.{Workflow, WorkflowsFactory}

import scala.collection.mutable
import scala.jdk.CollectionConverters._

private class WorkflowImpl[T](initialTasks: Set[T], dependencyMap: Map[T, Set[T]]) extends Workflow[T] {
  private val tasks: mutable.Set[T] = initialTasks.to(mutable.Set)
  private val tasksToDo = mutable.Set.from(initialTasks)
  private val completedTasks = mutable.Set.empty[T]

  override def getTasks: java.util.Set[T] = tasks.asJava
  override def getNextTasksToDo: java.util.Set[T] = tasksToDo.asJava

  override def doTask(t: T): Unit = {
    if (!tasks.contains(t)) throw new IllegalArgumentException("Unknown task")
    if (!tasksToDo.contains(t)) throw new IllegalStateException("Cannot execute this task yet")
    tasksToDo.remove(t)
    completedTasks.add(t)

    dependencyMap.getOrElse(t, Set()).foreach { dependentTask =>
      if (!completedTasks.exists(dependencyMap(dependentTask).contains)) {
        tasksToDo.add(dependentTask)
      }
    }
  }

  override def isCompleted: Boolean = tasks.isEmpty || tasks == completedTasks
}

