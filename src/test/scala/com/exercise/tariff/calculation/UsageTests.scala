package com.exercise.tariff.calculation

import com.exercise.tariff.cli.{Power, UsageCommand}
import com.exercise.tariff.pricing.{Rates, Tariff}
import org.scalatest.{FunSpec, Matchers}

class UsageTests extends FunSpec with Matchers {
  describe("usage calculation with tariff set") {
    it("fails if tariff is not in set") {
      val tariffs = Seq(Tariff("test", Rates(None, None), 0))
      val cmd = UsageCommand("not there", Power, 1)

      Usage.calculate(cmd, tariffs).isLeft should be(true)
    }
    it("fails if tariff does not contain requested fuel") {
      val tariffs = Seq(Tariff("no power", Rates(None, Some(1)), 0))
      val cmd = UsageCommand("no power", Power, 1)

      Usage.calculate(cmd, tariffs).isLeft should be(true)
    }
    it("correctly calculates usage") {
      val tariffs = Seq(Tariff("test", Rates(Some(0.15), None), 1))
      val cmd = UsageCommand("test", Power, 100)

      // Calculate target annual usage, remove VAT, remove standing charge, apply rate
      // ((100 * 12 / 1.05) - (12 * 1)) / 0.15 = 7539.047619
      val usage = Usage.calculate(cmd, tariffs).right.get
      val usage2dp = (math floor usage * 1E6) / 1E6

      usage2dp should be(7539.047619)
    }
  }
}
