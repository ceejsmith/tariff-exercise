package com.exercise.tariff.cli

sealed trait Command
case class CostCommand(power: Int, gas: Int) extends Command
case class UsageCommand(tariffName: String, fuel: Fuel, targetMonthlySpend: Int) extends Command