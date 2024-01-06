package mmlTests.a201902a.scala.gpt4

import mmlTests.a201902a.scala.WorkflowsFactory
import mmlTests.a201902a.scala.Workflow
import java.util.{HashSet, List, ArrayList, Set, LinkedHashMap, HashMap}
import scala.jdk.CollectionConverters._

class OrderedWorkflow[T](allTasks: List[T], nextTask: T) extends AbstractWorkflow[T](new HashSet[T](allTasks), Set.of(nextTask)) {
  private val taskOrder: LinkedHashMap[T, T] = createOrderMap(allTasks)

  def setupDependencies(): Unit = {
    for(task <- allTasks.asScala) {
      if(!task.equals(nextTask)) nextTasksToDo.remove(task)
    }
  }

  private def createOrderMap(tasks: List[T]): LinkedHashMap[T, T] = {
    val map = new LinkedHashMap[T, T]
    for(i <- tasks.asScala.indices) {
      if (i + 1 < tasks.size()) {
        map.put(tasks.get(i), tasks.get(i + 1))
      }
    }
    map
  }

  override def doTask(t: T): Unit = {
    super.doTask(t)
    if(taskOrder.containsKey(t)) {
      nextTasksToDo.add(taskOrder.get(t))
    }
  }
}
