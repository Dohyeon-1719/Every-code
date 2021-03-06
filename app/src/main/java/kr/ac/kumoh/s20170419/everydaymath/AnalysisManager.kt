package kr.ac.kumoh.s20170419.everydaymath

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class AnalysisManager(private val userLog: String) {
    private val userLogList = initList()

    private fun initList(): ArrayList<List<String>> {
        val tmpList = userLog.split("\n")
        val userLogList = ArrayList<List<String>>()
        for (i in tmpList.indices) userLogList.add(tmpList[i].split(","))
        userLogList.removeAt(userLogList.size - 1)

        // check log size
        while (userLogList.size < 7) {
            userLogList.add(0, listOf("2001-01-01","0","11","0"))
        }

        return userLogList
    }

    fun getTimes(): List<Float> {
        val timeList = ArrayList<Float>()
        for (i in userLogList) {
            if (i[3] != "0") timeList.add((i[3].toFloat() / i[2].toFloat() * 100).roundToInt() / 100f)
        }
        return listOf<Float>(
            timeList.maxOrNull()!!,
            (timeList.average() * 100).roundToInt() / 100f,
            timeList.minOrNull()!!)
    }

    fun getProblemNums(): HashMap<String, Int> {
        val problemNumsList = ArrayList<String>()
        val result = HashMap<String, Int>()
        for (i in userLogList) problemNumsList.add(i[2])
        for (i in problemNumsList) {
            if (!result.containsKey(i)) result[i] = 1
            else result.replace(i, result[i]!! + 1)
        }
        return result
    }

    fun getRecentGrades(): ArrayList<Float> {
        val gradeList = ArrayList<Float>()
        for (i in userLogList) {
            gradeList.add(((i[1].toFloat() / i[2].toFloat()) * 10000).roundToInt() / 100f)
        }
        return gradeList
    }

    private fun getDateDay(date: String, dateType: String): String? {
        val dateFormat = SimpleDateFormat(dateType)
        val nDate: Date = dateFormat.parse(date)
        val cal = Calendar.getInstance()
        cal.time = nDate

        return when (cal[Calendar.DAY_OF_WEEK]) {
            1 -> "일"
            2 -> "월"
            3 -> "화"
            4 -> "수"
            5 -> "목"
            6 -> "금"
            7 -> "토"
            else -> "?"
        }
    }

    fun getRecentDaysLabel(): List<String> {
        val daysList = ArrayList<String>()
        for (i in userLogList) {
            daysList.add(getDateDay(i[0], "yyyy-MM-dd").toString())
        }
        return daysList
    }
}