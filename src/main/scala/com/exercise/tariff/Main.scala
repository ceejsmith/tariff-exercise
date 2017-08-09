package com.exercise.tariff

import java.io.InputStream

import scala.io.Source
import cli.{CostCommand, UsageCommand, parse => parseCmd}
import pricing.{parse => parseTariffs}
import com.exercise.tariff.calculation.{Cost, TariffCost, Usage}

object Main {
  val stream : InputStream = getClass.getResourceAsStream("/prices.json")
  val json = Source.fromInputStream(stream).mkString

  val tariffs = parseTariffs(json)

  def main(args: Array[String]): Unit = parseCmd(args) match {
    case Left(ErrorReport(text)) => println(text)
    case Right(cmd @ CostCommand(_, _)) => {
      val costs = Cost.calculate(cmd, tariffs)
      print(costs)
    }
    case Right(cmd @ UsageCommand(_, _, _)) => {
      val usage = Usage.calculate(cmd, tariffs)
      print(usage)
    }
  }

  private def print(costs: Seq[TariffCost]) = {
    costs.foreach(c => println(f"${c.name} ${c.amount}%.2f"))
  }

  private def print(usage: Either[ErrorReport, Double]) = {
    usage match {
      case Left(ErrorReport(text)) => println(text)
      case Right(u) => println(f"$u%.2f")
    }
  }
}
