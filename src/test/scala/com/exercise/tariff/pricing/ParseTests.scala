package com.exercise.tariff.pricing

import org.scalatest.{FunSpec, Matchers}

class ParseTests extends FunSpec with Matchers {
  describe("Tariff parsing") {
    it("successfully parses tariff data") {
      val json = """[
                   |  {"tariff": "better-energy", "rates": {"power":  0.1367, "gas": 0.0288}, "standing_charge": 8.33},
                   |  {"tariff": "2yr-fixed", "rates": {"power": 0.1397, "gas": 0.0296}, "standing_charge": 8.75},
                   |  {"tariff": "greener-energy", "rates": {"power":  0.1544}, "standing_charge": 8.33},
                   |  {"tariff": "simpler-energy", "rates": {"power":  0.1396, "gas": 0.0328}, "standing_charge": 8.75}
                   |]""".stripMargin
      parse(json).size should be(4)
    }
  }
}
