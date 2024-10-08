package com.example.data.local.room

import androidx.room.TypeConverter
import com.example.shared.models.Address
import com.example.shared.models.Experience
import com.example.shared.models.Salary
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.TreeSet

class Converters {

    @TypeConverter
    fun addressFromString(string: String?): Address? {
        return Gson().fromJson(string, Address::class.java)
    }

    @TypeConverter
    fun addressToString(address: Address?): String? {
        return Gson().toJson(address)
    }

    @TypeConverter
    fun experienceFromString(string: String?): Experience? {
        return Gson().fromJson(string, Experience::class.java)
    }

    @TypeConverter
    fun experienceToString(experience: Experience?): String? {
        return Gson().toJson(experience)
    }

    @TypeConverter
    fun salaryFromString(string: String?): Salary? {
        return Gson().fromJson(string, Salary::class.java)
    }

    @TypeConverter
    fun salaryToString(salary: Salary?): String? {
        return Gson().toJson(salary)
    }

    @TypeConverter
    fun fromStringList(arrayList: List<String>?): String? {
        if (arrayList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>?>() {}.type
        return gson.toJson(arrayList, type)
    }

    @TypeConverter
    fun toStringList(arrayList: String?): ArrayList<String>? {
        if (arrayList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<String>?>() {}.type
        return gson.fromJson(arrayList, type)
    }
}