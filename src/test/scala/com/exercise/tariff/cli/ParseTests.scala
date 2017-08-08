package com.exercise.tariff.cli

import org.scalatest.{FunSpec, Matchers}

class ParseTests extends FunSpec with Matchers {
  describe("Command line parsing") {
    it("fails if there are no arguments") {
      parse(Array()).isLeft should be(true)
    }
    it("fails if first argument is not cost or usage") {
      parse(Array("blah")).isLeft should be(true)
    }
    describe("cost") {
      it("fails if there are not three arguments (incl. cost)") {
        parse(Array("cost", "1", "1", "too many")).isLeft should be(true)
        parse(Array("cost", "1")).isLeft should be(true)
      }
      it("fails if power usage is not a valid number") {
        parse(Array("cost", "error", "1")).isLeft should be(true)
      }
      it("fails if gas usage is not a valid number") {
        parse(Array("cost", "1", "error")).isLeft should be(true)
      }
      it("correctly parses valid arguments") {
        parse(Array("cost", "2000", "2300")).right.get should equal(CostCommand(2000, 2300))
      }
    }
    describe("usage") {
      it("fails if there are not four arguments (incl. usage)") {
        parse(Array("usage", "tariff", "power", "1", "too many")).isLeft should be(true)
        parse(Array("usage", "tariff", "power")).isLeft should be(true)
      }
      it("fails if fuel type is not valid") {
        parse(Array("usage", "tariff", "error", "1")).isLeft should be(true)
      }
      it("fails if target monthly usage is not a valid number") {
        parse(Array("usage", "tariff", "power", "error")).isLeft should be(true)
      }
      it("correctly parses valid power arguments") {
        parse(Array("usage", "tariff", "power", "1")).right.get should equal(UsageCommand("tariff", Power, 1))
      }
      it("correctly parses valid gas arguments") {
        parse(Array("usage", "tariff", "gas", "1")).right.get should equal(UsageCommand("tariff", Gas, 1))
      }
    }
  }
}
