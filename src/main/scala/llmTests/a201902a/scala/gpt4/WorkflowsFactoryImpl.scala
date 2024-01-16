package llmTests.a201902a.scala.gpt4

import llmTests.a201902a.scala.WorkflowsFactory
import llmTests.a201902a.scala.Workflow
import java.util.{HashSet, List, ArrayList, Set, LinkedHashMap, HashMap}
import scala.jdk.CollectionConverters._

class WorkflowsFactoryImpl extends WorkflowsFactory {

  override def singleTask[T](task: T): Workflow[T] = {
    new SingleTaskWorkflow(task)
  }

  override def tasksSequence[T](tasks: List[T]): Workflow[T] = {
    var nextToDone: T = if(!tasks.isEmpty) tasks.get(0) else null.asInstanceOf[T]
    val result = new OrderedWorkflow[T](tasks, nextToDone)
    result.setupDependencies()
    result
  }

  override def tasksJoin[T](initialTasks: Set[T], finalTask: T): Workflow[T] = {
    val taskList = new ArrayList[T](initialTasks)
    taskList.add(finalTask)
    tasksSequence(taskList)
  }

  override def tasksFork[T](initialTask: T, finalTasks: Set[T]): Workflow[T] = {
    val taskList = new ArrayList[T](finalTasks)
    taskList.add(0, initialTask)
    tasksSequence(taskList)
  }

  override def concat[T](first: Workflow[T], second: Workflow[T]): Workflow[T] = {
    val firstTasks = new ArrayList[T](first.getTasks)
    firstTasks.addAll(second.getTasks)
    tasksSequence(firstTasks)
  }
}
