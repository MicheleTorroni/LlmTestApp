//package mmlTests.a201802a.scala.gpt35turbo
//
//import java.util.{List, Optional}
//import scala.collection.mutable.Map
//
///**
// * An interface to model the results of a tennis tournament, and the overall points a player
// * has after participating to the tournament.
// * Check TournamentBuilder to see how a Tournament should be created
// */trait Tournament { /**
// * @return the type of the tournament
// */def getType: Nothing
//  /**
//   * @return the name of the tournament
//   */def getName: String
//  /**
//   * @param player
//   * @return the result of a given player, or 'empty' if he/she did not participate
//   */def getResult(player: String): Optional[Nothing]
//  /**
//   * @return the winner of the tournament
//   */def winner: String
//  /**
//   * @return a map associating to each player the number of overall points he/she has before the tournament
//   */def initialRanking: Map[String, Integer]
//  /**
//   * @return a map associating to each player the number of overall points he/she has after the tournament
//   */def resultingRanking: Map[String, Integer]
//  /**
//   * @return the ordered list of players, from the top player down to the last one
//   */def rank: List[String]
//}
