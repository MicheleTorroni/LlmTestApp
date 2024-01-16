//package llmTests.a201802a.scala.gpt4
//import llmTests.a201802a.scala.{Tournament, TournamentBuilder}
//
//import java.util.{Optional, HashMap}
//import scala.jdk.CollectionConverters._
//
//class TournamentBuilderImpl extends TournamentBuilder {
//  private var name: Option[String] = None
//  private var ttype: Option[Tournament.Type] = None
//  private var initialRank: Option[Map[String, Integer]] = None
//  private var results: Map[String, Tournament.Result] = Map.empty
//  private var built: Boolean = false
//
//  override def setType(`type`: Tournament.Type): TournamentBuilder = {
//    require(`type` != null, "Type can't be null!")
//    require(!built, "Tournament has been built!")
//    ttype = Some(`type`)
//    this
//  }
//
//  override def setName(name: String): TournamentBuilder = {
//    require(name != null, "Name can't be null!")
//    require(!built, "Tournament has been built!")
//    this.name = Some(name)
//    this
//  }
//
//  override def setPriorRanking(ranking: java.util.Map[String,Integer]): TournamentBuilder = {
//    require(name.isDefined, "Name has to be defined before setting ranking!")
//    require(ttype.isDefined, "Type has to be defined before setting ranking!")
//    require(!built, "Tournament has been built!")
//    this.initialRank = Some(ranking.asScala.toMap)
//    this
//  }
//
//  override def addResult(player: String, result: Tournament.Result): TournamentBuilder = {
//    require(initialRank.isDefined, "Ranking has to be defined before adding results!")
//    require(!built, "Tournament has been built!")
//    require(!results.contains(player), "Player has already a set result!")
//    require(results.count(_._2 == Tournament.Result.WINNER) == 0 || result != Tournament.Result.WINNER, "There can only be one winner!")
//    results += (player -> result)
//    this
//  }
//
//  override def build(): Tournament = {
//    require(name.isDefined, "Name is mandatory!")
//    require(ttype.isDefined, "Type is mandatory!")
//    require(initialRank.isDefined, "Ranking is mandatory!")
//    require(results.values.count(_ == Tournament.Result.WINNER) == 1, "One player has to be the winner!")
//
//    built = true
//
//    var resultingRank: Map[String, Integer] = initialRank.get
//    results.foreach {
//      case (player, result) => {
//        val playerInitialRank = initialRank.get.getOrElse(player, 0)
//        val points = pointsFor(result, ttype.get)
//        resultingRank = resultingRank.updated(player, Integer.valueOf(playerInitialRank.intValue() + points))
//      }
//    }
//
//    new Tournament {
//      private val finalRank: Map[String,Integer] = resultingRank
//      private val tournamentWinner: String = results.find(_._2 == Tournament.Result.WINNER).get._1
//      override def getType: Tournament.Type = ttype.get
//      override def getName: String = name.get
//      override def initialRanking(): java.util.Map[String,Integer] = initialRank.get.asJava
//      override def getResult(player: String): Optional[Tournament.Result] = Optional.ofNullable(results.getOrElse(player, null))
//      override def resultingRanking(): java.util.Map[String,Integer] = finalRank.asJava
//      override def winner(): String = tournamentWinner
//      override def rank(): java.util.List[String] = finalRank.toSeq.sortBy(-_._2).map(_._1).asJava
//    }
//  }
//
//  private def pointsFor(result: Tournament.Result, `type`: Tournament.Type): Int = {
//    val basePoints = `type` match {
//      case Tournament.Type.MAJOR => 2500
//      case Tournament.Type.ATP1000 => 1000
//      case Tournament.Type.ATP500 => 500
//      case Tournament.Type.ATP250 => 250
//    }
//
//    result match {
//      case Tournament.Result.WINNER => basePoints
//      case Tournament.Result.FINALIST => (basePoints * 0.5).toInt
//      case Tournament.Result.SEMIFINALIST => (basePoints * 0.2).toInt
//      case Tournament.Result.QUARTERFINALIST => (basePoints * 0.1).toInt
//      case Tournament.Result.PARTICIPANT => 0
//    }
//  }
//}