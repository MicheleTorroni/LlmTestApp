//package mmlTests.a201802a.scala.gpt35turbo
//
//import mmlTests.a201802a.scala.TournamentBuilder
//import mmlTests.a201802a.scala.Tournament
//import mmlTests.a201802a.scala.Result
//import mmlTests.a201802a.scala.Type
//
//import java._
//import scala.collection.mutable.{ListBuffer, Map, Set, HashMap}
//
//class TournamentBuilderImpl extends TournamentBuilder {
//  private var `type`: Type.Type = _
//  private var name: String = _
//  private var priorRanking: Map[String, Integer] = new HashMap[String, Integer]()
//  private var results: Map[String, Result.Result] = new HashMap[String, Result.Result]()
//
//  override def setType(`type`: Type.Type): TournamentBuilder = {
//    this.`type` = `type`
//    this
//  }
//
//  override def setName(name: String): TournamentBuilder = {
//    this.name = name
//    this
//  }
//
//  override def setPriorRanking(ranking: Map[String, Integer]): TournamentBuilder = {
//    priorRanking = ranking
//    this
//  }
//
//  override def addResult(player: String, result: Result.Result): TournamentBuilder = {
//    results.put(player, result)
//    this
//  }
//
//  override def build(): Tournament = {
//    validate()
//    val resultingRanking: Map[String, Integer] = new HashMap[String, Integer](priorRanking)
//    results.forEach((player: String, result: Result.Result) => {
//      val playerPoints: Integer = priorRanking.getOrDefault(player, 0)
//      val tournamentPoints: Integer = getTournamentPoints(result)
//      resultingRanking.put(player, playerPoints + tournamentPoints)
//    })
//    new Tournament {
//      override def getType: Type.Type = `type`
//
//      override def getName: String = name
//
//      override def getResult(player: String): Optional[Result.Result] = Optional.ofNullable(results.get(player))
//
//      override def winner: String = {
//        var maxPoints: Int = 0
//        var winner: String = ""
//        resultingRanking.forEach((player: String, points: Integer) => {
//          if (points > maxPoints) {
//            maxPoints = points
//            winner = player
//          }
//        })
//        winner
//      }
//
//      override def initialRanking: Map[String, Integer] = new HashMap[String, Integer](priorRanking)
//
//      override def resultingRanking: Map[String, Integer] = resultingRanking
//
//      override def rank: List[String] = {
//        val sortedRanking: List[String] = new ArrayList[String](resultingRanking.keySet())
//        sortedRanking.sort((s1: String, s2: String) => {
//          val points1: Int = resultingRanking.getOrDefault(s1, 0)
//          val points2: Int = resultingRanking.getOrDefault(s2, 0)
//          points2.compareTo(points1)
//        })
//        sortedRanking
//      }
//
//      private def getTournamentPoints(result: Result.Result): Integer = result match {
//        case Result.WINNER => getTypePoints(1.0)
//        case Result.FINALIST => getTypePoints(0.5)
//        case Result.SEMIFINALIST => getTypePoints(0.2)
//        case Result.QUARTERFINALIST => getTypePoints(0.1)
//        case Result.PARTICIPANT => 0
//      }
//
//      private def getTypePoints(multiplier: Double): Integer = `type` match {
//        case Type.MAJOR => (2500 * multiplier).toInt
//        case Type.ATP1000 => (1000 * multiplier).toInt
//        case Type.ATP500 => (500 * multiplier).toInt
//        case Type.ATP250 => (250 * multiplier).toInt
//      }
//    }
//  }
//
//  private def validate(): Unit = {
//    if (`type` == null) throw new IllegalStateException("Type not specified")
//    if (name == null) throw new IllegalStateException("Name not specified")
//    if (priorRanking == null) throw new IllegalStateException("Prior ranking not specified")
//    if (results.isEmpty) throw new IllegalStateException("Results not specified")
//    if (results.containsValue(Result.Result.WINNER) && results.values().stream().filter(_ == Result.Result.WINNER).count() > 1) {
//      throw new IllegalStateException("Multiple winners specified")
//    }
//  }
//}
