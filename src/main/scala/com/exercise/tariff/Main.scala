package com.exercise.tariff

import cli.{CostCommand, Report, UsageCommand, parse => parseArgs}
import pricing.{parse => parseTariffs}
import com.exercise.tariff.calculation.Cost

object Main {
  val json = """[
               |  {"tariff": "better-energy", "rates": {"power":  0.1367, "gas": 0.0288}, "standing_charge": 8.33},
               |  {"tariff": "2yr-fixed", "rates": {"power": 0.1397, "gas": 0.0296}, "standing_charge": 8.75},
               |  {"tariff": "greener-energy", "rates": {"power":  0.1544}, "standing_charge": 8.33},
               |  {"tariff": "simpler-energy", "rates": {"power":  0.1396, "gas": 0.0328}, "standing_charge": 8.75}
               |]""".stripMargin

  val tariffs = parseTariffs(json)

  def main(args: Array[String]): Unit = parseArgs(args) match {
    case Left(Report(text)) => {
      println(text)
      System.exit(1)
    }
    case Right(cmd @ CostCommand(_, _)) => Cost.calculate(cmd, tariffs).foreach(c => println(f"${c.name} ${c.amount}%.2f"))
    case Right(cmd @ UsageCommand(_, _, _)) => println(cmd)
  }
}
