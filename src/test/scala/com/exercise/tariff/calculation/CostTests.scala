package com.exercise.tariff.calculation

import com.exercise.tariff.cli.CostCommand
import com.exercise.tariff.pricing.{Rates, Tariff}
import org.scalatest.{FunSpec, Matchers}

class CostTests extends FunSpec with Matchers {
  describe("cost calculation for tariff set") {
    it("filters out tariffs not applicable to fuel with non-zero usage") {
      // Tariff does not include gas
      val tariff = Tariff("test", Rates(Some(1), None), 1)
      // Customer uses gas
      val cmd = CostCommand(1, 1)

      Cost.calculate(cmd, Seq(tariff)) should be(empty)
    }
    it("does not filter out tariffs not applicable to fuel with zero usage") {
      // Tariff does not include gas
      val tariff = Tariff("test", Rates(Some(1), None), 1)
      // Customer doesn't use gas
      val cmd = CostCommand(1, 0)

      Cost.calculate(cmd, Seq(tariff)) shouldNot be(empty)
    }
    it("gives zero cost for zero usage (no standing charge)") {
      val tariff = Tariff("test", Rates(Some(1), Some(1)), 1)
      val cmd = CostCommand(0, 0)

      Cost.calculate(cmd, Seq(tariff))(0).amount should be(0)
    }
    it("calculates correctly for non-zero usage") {
      val tariff = Tariff("test", Rates(Some(1), Some(2)), 3)
      val cmd = CostCommand(4, 5)

      // ((1 * 4 + 12 * 3) + (2 * 5 + 12 * 3)) * 1.05
      Cost.calculate(cmd, Seq(tariff))(0).amount should be(90.3)
    }
    it("orders by cost ascending") {
      val tariffs = Seq(
        // Most expensive first
        Tariff("test expensive", Rates(Some(100), Some(100)), 0),
        Tariff("test cheap", Rates(Some(1), Some(1)), 0)
      )
      val cmd = CostCommand(10, 10)

      val costs = Cost.calculate(cmd, tariffs)

      // Reordered to give cheapest first
      costs(0).name should be("test cheap")
      costs(1).name should be("test expensive")
    }
  }
}
