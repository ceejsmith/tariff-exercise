package com.exercise.tariff.cli

sealed trait Command
case class CostCommand(power: Double, gas: Double) extends Command
case class UsageCommand(tariffName: String, fuel: Fuel, targetMonthlySpend: Double) extends Command