package com.exercise.tariff

import scala.util.{Failure, Success, Try}

package object cli {
  def   parse(args: Array[String]): Either[Report, Command] = {
    if (args.length == 0)
      Left(Report("Please supply command line arguments."))
    else
      args(0).toLowerCase match {
        case "cost" => parseCost(args)
        case "usage" => parseUsage(args)
        case _ => Left(Report("Please issue either a cost command or a usage command."))
      }
  }

  private def parseCost(args: Array[String]): Either[Report, CostCommand] = {
    if (args.length != 3) return Left(Report("Command format: cost <POWER_USAGE> <GAS_USAGE>"))

    (parseInt(args(1)), parseInt(args(2))) match {
      case (Failure(_), _) => Left(Report(s"$args(1) is not a valid number."))
      case (_, Failure(_)) => Left(Report(s"$args(2) is not a valid number."))
      case(Success(power), Success(gas)) => Right(CostCommand(power, gas))
    }
  }

  private def parseUsage(args: Array[String]): Either[Report, UsageCommand] = {
    if (args.length != 4) return Left(Report("Command format: usage <TARIFF_NAME> <FUEL_TYPE> <TARGET_MONTHLY_SPEND>"))

    (parseFuel(args(2)), parseInt(args(3))) match {
      case (Failure(_), _) => Left(Report(s"$args(2) is not a valid fuel type. Please supply power or gas."))
      case (_, Failure(_)) => Left(Report(s"$args(3) is not a valid number."))
      case (Success(fuel), Success(usage)) => Right(UsageCommand(args(1), fuel, usage))
    }
  }

  private def parseInt(raw: String): Try[Int] = {
    try {
      Success(raw.toInt)
    }
    catch {
      case e: NumberFormatException => Failure(e)
    }
  }

  private def parseFuel(raw: String): Try[Fuel] = raw.toLowerCase match {
    case "power" => Success(Power)
    case "gas" => Success(Gas)
    case _ => Failure(new Exception("Invalid fuel type"))
  }
}
