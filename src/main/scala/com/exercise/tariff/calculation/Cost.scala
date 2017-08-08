package com.exercise.tariff.calculation

import com.exercise.tariff.cli.CostCommand
import com.exercise.tariff.pricing.Tariff

case class TariffCost(name: String, amount: Double)

object Cost {
  def calculate(cmd: CostCommand, tariffs: Seq[Tariff]): Seq[TariffCost] =
    applicableTariffs(cmd, tariffs)
      .map(costUsing(cmd))
      .sortBy(c => c.amount)

  private def applicableTariffs(cmd: CostCommand, tariffs: Seq[Tariff]): Seq[Tariff] = {
    tariffs.filter(t =>
      (t.rates.power.isDefined || cmd.power == 0) &&
      (t.rates.gas.isDefined || cmd.gas == 0)
    )
  }

  private def costUsing(cmd: CostCommand)(tariff: Tariff): TariffCost = {
    val netPowerCost = netFuelCost(cmd.power, tariff.rates.power, tariff.standing_charge)
    val netGasCost = netFuelCost(cmd.gas, tariff.rates.gas, tariff.standing_charge)

    TariffCost(tariff.tariff, (netPowerCost + netGasCost) * (1 + Vat.rate))
  }

  private def netFuelCost(annualUsage: Int, rate: Option[Double], standingCharge: Double): Double = {
    if (annualUsage == 0)
      0
    else
      annualUsage * rate.get + 12 * standingCharge
  }
}
