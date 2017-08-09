package com.exercise.tariff.calculation

import com.exercise.tariff.ErrorReport
import com.exercise.tariff.cli.{Fuel, Gas, Power, UsageCommand}
import com.exercise.tariff.pricing.Tariff

object Usage {
  def calculate(cmd: UsageCommand, tariffs: Seq[Tariff]): Either[ErrorReport, Double] = {
    val tariff = tariffs.find(_.tariff == cmd.tariffName)
    tariff match {
      case None => Left(ErrorReport(s"Cannot find tariff ${cmd.tariffName}"))
      case Some(tariff) if !tariff.containsFuel(cmd.fuel) =>
        Left(ErrorReport(s"Tariff ${cmd.tariffName} does not contain ${cmd.fuel}"))
      case Some(tariff) => Right(usage(tariff.rateFor(cmd.fuel), cmd.targetMonthlySpend, tariff.standing_charge))
    }
  }

  private def usage(rate: Double, targetMonthlySpend: Double, standingCharge: Double) = {
    val targetAnnualSpend = targetMonthlySpend * 12
    val netSpend = targetAnnualSpend / (1 + Vat.rate)
    val consumptionSpend = netSpend - standingCharge * 12

    consumptionSpend / rate
  }

  implicit class TariffOps(tariff: Tariff) {
    def containsFuel(fuel: Fuel): Boolean =
      (fuel == Power) && tariff.rates.power.isDefined ||
      (fuel == Gas) && tariff.rates.gas.isDefined

    def rateFor(fuel: Fuel): Double = {
      fuel match {
        case Power => tariff.rates.power.get
        case Gas => tariff.rates.gas.get
      }
    }
  }
}
