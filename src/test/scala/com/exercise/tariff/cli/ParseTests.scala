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
  }
}
