package com.exercise.tariff.cli

sealed trait Fuel
case object Gas extends Fuel
case object Power extends Fuel
