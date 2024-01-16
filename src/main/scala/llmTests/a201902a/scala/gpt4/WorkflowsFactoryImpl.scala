//package llmTests.a201902a.scala.gpt4
//
//import java.util.{LinkedHashSet, LinkedList}
//import llmTests.a201902a.scala.{Workflow, WorkflowsFactory}
//import llmTests.a201902a.scala.gpt4.WorkflowImpl
//
//import scala.collection.mutable
//import scala.jdk.CollectionConverters._
//
//class WorkflowsFactoryImpl extends WorkflowsFactory {
//
//  override def singleTask[T](task: T): Workflow[T] =
//    new WorkflowImpl(Set(task), Map.empty)
//
//  override def tasksSequence[T](tasks: java.util.List[T]): Workflow[T] = {
//    val tasksSeq = tasks.asScala.toList
//    val dependencyMap = tasksSeq.zipWithIndex.map {
//      case (task, index) => if (index < tasks.size() - 1) task -> Set(tasksSeq(index + 1)) else task -> Set.empty[T]
//    }.toMap
//    new WorkflowImpl(Set(tasksSeq.head), dependencyMap)
//  }
//
//  override def tasksJoin[T](initialTasks: java.util.Set[T], finalTask: T): Workflow[T] = {
//    val tasksSet = initialTasks.asScala
//    val dependencyMap = tasksSet.map(_ -> Set(finalTask)).toMap + (finalTask -> Set.empty[T])
//    new WorkflowImpl(tasksSet.toSet, dependencyMap)
//  }
//
//  override def tasksFork[T](initialTask: T, finalTasks: java.util.Set[T]): Workflow[T] = {
//    val tasksSet = finalTasks.asScala
//    val dependencyMap = (initialTask -> tasksSet.toSet) +: tasksSet.map(_ -> Set.empty[T]).toMap
//    new WorkflowImpl(Set(initialTask), dependencyMap)
//  }
//
//  override def concat[T](first: Workflow[T], second: Workflow[T]): Workflow[T] = {
//    val firstSet = first.getTasks.asScala.toList
//    val secondSet = second.getTasks.asScala.toList
//    val tasks = new LinkedList[T]()
//    tasks.addAll(firstSet.asJava)
//    tasks.addAll(secondSet.asJava)
//    tasksSequence(tasks)
//  }
//}
