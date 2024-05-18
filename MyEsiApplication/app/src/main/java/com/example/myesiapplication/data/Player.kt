package com.example.myesiapplication.data

import com.google.firebase.firestore.PropertyName
import java.text.DecimalFormat
data class Player(
    @PropertyName("playerId") var PlayerID: Int = 0,
    val Team: String = "",
    val Jersey: Int = 0,
    val Position: String = "",
    @PropertyName("firstname") var FirstName: String = "",
    @PropertyName("lastname") var LastName: String = "",
    val Height: Int = 0,
    val Weight: Int = 0,
    val BirthDate: String = "",
    val BirthCity: String = "",
    val BirthCountry: String = "",
    val HighSchool: String = "",
    val College: String = "",
    val Salary: Int = 0,
    val Experience: Int = 0,
    @PropertyName("nbaDotComId") var NbaDotComPlayerID: Int = 0,
) {
    val BirthDateWithoutTime: String
        get() = if (BirthDate.length >= 10) BirthDate.substring(0, 10) else ""

    val WeightInKg: String
        get() {
            val weightInKilo = Weight * 0.453592
            val decimalFormat = DecimalFormat("#.##")
            return decimalFormat.format(weightInKilo)
        }

    val HeightInM: String
        get() {
            val heightInMeters = Height / 39.37
            val decimalFormat = DecimalFormat("#.##")
            return decimalFormat.format(heightInMeters)
        }
}
