package mmlTests.a201803b.scala.gpt4


import mmlTests.a201803b.scala.{ConferenceReviewing, Question}
import mmlTests.shared.scala.Pair

import scala.collection.mutable.{HashMap, ListBuffer, Map, Set}

class ConferenceReviewingImpl extends ConferenceReviewing {
  import mmlTests.a201803b.scala.Question._
  val reviews: Map[Int, ListBuffer[Map[Question, Int]]] = HashMap()

  def loadReview(article: Int, scores: Map[Question, Int]): Unit = {
    val rev = reviews.getOrElseUpdate(article, ListBuffer())
    rev += scores
  }

  def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int, fin: Int): Unit = {
    val scores = HashMap[Question, Int](
      Question.RELEVANCE -> relevance,
      Question.SIGNIFICANCE -> significance,
      Question.CONFIDENCE -> confidence,
      Question.FINAL -> fin
    )
    loadReview(article, scores)
  }

  def orderedScores(article: Int, question: Question): List[Int] = {
    val scores = reviews(article).map(_(question))
    scores.sorted.toList
  }

  def averageFinalScore(article: Int): Double = {
    val scores = reviews(article).map(_(Question.FINAL))
    if (scores.isEmpty) 0 else scores.sum.toDouble / scores.size
  }

  def acceptedArticles(): Set[Int] = {
    val set = Set[Int]()
    reviews.foreach { case (article, rs) =>
      val final_score = rs.map(_(Question.FINAL)).sum.toDouble / rs.size
      val max_relevance = rs.map(_(Question.RELEVANCE)).max
      if (final_score > 5 && max_relevance >= 8) set += article
    }
    set
  }

  def sortedAcceptedArticles(): List[Pair[Int, Double]] = {
    acceptedArticles().toList.map { a =>
      new Pair(a, averageFinalScore(a))
    }.sortWith(_.snd < _.snd)
  }

  def averageWeightedFinalScoreMap(): Map[Int, Double] = {
    val scoreMap: Map[Int, Double] = reviews.map { case (article, rs) =>
      val weightedScores = rs.map(r => r(Question.CONFIDENCE) * r(Question.FINAL) / 10.0)
      article -> (weightedScores.sum / weightedScores.size)
    }
    scoreMap
  }
}