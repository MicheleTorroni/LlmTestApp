import scala.collection.mutable.{ListBuffer, Map, Set}
import scala.collection.immutable.ListMap
import mmlTests.shared.scala.Pair
import mmlTests.a201803b.scala.{ConferenceReviewing, Question}

abstract class ConferenceReviewingImpl extends ConferenceReviewing {
  private val reviews: Map[Int, ListBuffer[(Question.Question, Int)]] = Map.empty
  private val finalScores: Map[Int, ListBuffer[Int]] = Map.empty

  override def loadReview(article: Int, scores: Map[Question.Question, Int]): Unit = {
    reviews.getOrElseUpdate(article, ListBuffer.empty)
    scores.foreach { case (q, s) => reviews(article).append((q, s)) }
  }

  override def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int, fin: Int): Unit = {
    reviews.getOrElseUpdate(article, ListBuffer.empty)
    reviews(article).append((Question.RELEVANCE, relevance))
    reviews(article).append((Question.SIGNIFICANCE, significance))
    reviews(article).append((Question.CONFIDENCE, confidence))
    reviews(article).append((Question.FINAL, fin))
  }

  override def orderedScores(article: Int, question: Question.Question): List[Int] = {
    reviews.get(article) match {
      case Some(scores) => scores.filter(_._1 == question).map(_._2).toList.sorted
      case None => List.empty
    }
  }

  override def averageFinalScore(article: Int): Double = {
    finalScores.get(article) match {
      case Some(scores) => scores.sum.toDouble / scores.length.toDouble
      case None => 0.0
    }
  }

//  override def acceptedArticles(): Set[Int] = {
//    reviews.keys.toSet.filter(article => {
//      val finalScore = averageFinalScore(article)
//      finalScore > 5 && reviews(article).exists(_._1 == Question.RELEVANCE && _._2 >= 8)
//    })
//  }

  override def sortedAcceptedArticles(): List[Pair[Int, Double]] = {
    val accepted = acceptedArticles()
    val scores = accepted.map(article => Pair(article, averageFinalScore(article))).toList
    scores.sortBy(_.getSnd())
  }

  override def averageWeightedFinalScoreMap(): Map[Int, Double] = {
    val averageScores = reviews.map { case (article, scores) =>
      val finalScore = scores.find(_._1 == Question.FINAL).map(_._2).getOrElse(0)
      val confidenceScore = scores.find(_._1 == Question.CONFIDENCE).map(_._2).getOrElse(0)
      val weightedScore = (confidenceScore * finalScore) / 10.0
      (article, weightedScore)
    }
    Map.from(ListMap(averageScores.toSeq.sortBy(_._1): _*))
  }
}