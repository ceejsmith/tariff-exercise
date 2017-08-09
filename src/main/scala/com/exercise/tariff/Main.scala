package com.exercise.tariff

import java.io.InputStream

import scala.io.Source
import cli.{CostCommand, Report, UsageCommand, parse => parseCmd}
import pricing.{parse => parseTariffs}
import com.exercise.tariff.calculation.{Cost, TariffCost}

object Main {
  val stream : InputStream = getClass.getResourceAsStream("/prices.json")
  val json = Source.fromInputStream(stream).mkString

  val tariffs = parseTariffs(json)

  def main(args: Array[String]): Unit = parseCmd(args) match {
    case Left(Report(text)) => {
      println(text)
      System.exit(1)
    }
    case Right(cmd @ CostCommand(_, _)) => {
      val costs = Cost.calculate(cmd, tariffs)
      print(costs)
    }
    case Right(cmd @ UsageCommand(_, _, _)) => println(cmd)
  }

  private def print(costs: Seq[TariffCost]) = {
    costs.foreach(c => println(f"${c.name} ${c.amount}%.2f"))
  }
}
