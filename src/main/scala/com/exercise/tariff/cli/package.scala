package com.exercise.tariff

package object cli {
  def   parse(args: Array[String]): Either[Report, Command] = {
    if (args.length == 0)
      Left(Report("Please supply command line arguments"))
    else
      args(0).toLowerCase match {
        case "cost" => parseCost(args)
        case "usage" => parseUsage(args)
        case _ => Left(Report("Please issue either a cost command or a usage command"))
      }
  }

  private def parseUsage(args: Array[String]): Either[Report, UsageCommand] = {
    Right(UsageCommand("", Power, 0.0))
  }

  private def parseCost(args: Array[String]): Either[Report, CostCommand] = {
    Right(CostCommand(0.0, 0.0))
  }
}
