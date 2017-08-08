package com.exercise.tariff

import org.json4s.native.Serialization.{formats, read}
import org.json4s.{Formats, NoTypeHints}

package object pricing {
  implicit val formatsImplicit: Formats = formats(NoTypeHints)

  def parse(json: String): Seq[Tariff] = read[Seq[Tariff]](json)
}
