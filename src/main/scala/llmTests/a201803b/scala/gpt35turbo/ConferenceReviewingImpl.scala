//package llmTests.a201803b.scala.gpt35turbo
//
//import llmTests.shared.java.Pair
//import llmTests.a201803b.scala.ConferenceReviewing
//import llmTests.a201803b.scala.ConferenceReviewing.Question
//import scala.collection.mutable.ListBuffer
//
//class ConferenceReviewingImpl extends ConferenceReviewing {
//
//  private val reviews: Map[Int, ListBuffer[Map[Question, Int]]] = Map.empty
//
//  override def loadReview(article: Int, scores: Map[Question, Int]): Unit = {
//    val review = reviews.getOrElseUpdate(article, ListBuffer.empty)
//    review += scores
//  }
//
//  override def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int, fin: Int): Unit = {
//    val scores = Map(
//      Question.RELEVANCE -> relevance,
//      Question.SIGNIFICANCE -> significance,
//      Question.CONFIDENCE -> confidence,
//      Question.FINAL -> fin
//    )
//    loadReview(article, scores)
//  }
//
//  override def orderedScores(article: Int, question: Question): List[Int] = {
//    val review = reviews.getOrElse(article, ListBuffer.empty)
//    val scores = review.map(_.getOrElse(question, 0))
//    scores.sorted.toList
//  }
//
//  override def averageFinalScore(article: Int): Double = {
//    val review = reviews.getOrElse(article, ListBuffer.empty)
//    val finals = review.flatMap(_.get(Question.FINAL))
//    if (finals.nonEmpty) finals.sum.toDouble / finals.length.toDouble
//    else 0.0
//  }
//
//  override def acceptedArticles(): Set[Int] = {
//    val accepted = reviews.filter { case (_, reviews) =>
//      reviews.exists(r => r.getOrElse(Question.RELEVANCE, 0) >= 8) &&
//        averageFinalScore(_) > 5.0
//    }
//    accepted.keySet
//  }
//
//  override def sortedAcceptedArticles(): List[Pair[Int, Double]] = {
//    val accepted = acceptedArticles()
//    val pairs = accepted.toList.map(article => Pair(article, averageFinalScore(article)))
//    pairs.sortBy(pair => -pair.getY())
//  }
//
//  override def averageWeightedFinalScoreMap(): Map[Int, Double] = {
//    val weightedScores = reviews.transform { (_, reviews) =>
//      val scores = reviews.flatMap { review =>
//        val confidence = review.getOrElse(Question.CONFIDENCE, 0).toDouble
//        val fin = review.getOrElse(Question.FINAL, 0).toDouble
//        val weighted = confidence * fin / 10.0
//        Some(weighted)
//      }
//      if (scores.isEmpty) 0.0
//      else scores.sum / scores.length
//    }
//    weightedScores.filter { case (_, score) => score > 0.0 }
//  }
//}