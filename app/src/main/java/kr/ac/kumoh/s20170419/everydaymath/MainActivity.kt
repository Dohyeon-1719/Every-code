package kr.ac.kumoh.s20170419.everydaymath

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kr.ac.kumoh.s20170419.everydaymath.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        //setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        startAnimations()

        view.btnSettings.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
            overridePendingTransition(R.anim.slide_up_enter, R.anim.slide_up_exit)
        }

        view.btnGotoTest.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    TestActivity::class.java
                )
            )
            overridePendingTransition(R.anim.slide_up_enter, R.anim.slide_up_exit)
        }

        view.barChart.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AnalysisActivity::class.java
                )
            )
            overridePendingTransition(R.anim.slide_up_enter, R.anim.slide_up_exit)
        }

        view.btnGotoAnalysis.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AnalysisActivity::class.java
                )
            )
            overridePendingTransition(R.anim.slide_up_enter, R.anim.slide_up_exit)
        }
    }

    private fun startAnimations() {
        val translateUpAnim1 = AnimationUtils.loadAnimation(this, R.anim.translate_up)
        val translateUpAnim2 = AnimationUtils.loadAnimation(this, R.anim.translate_up)
        translateUpAnim2.startOffset = 100

        view.mainContents1.startAnimation(translateUpAnim1)
        view.btnGotoTest.startAnimation(translateUpAnim2)
    }

    override fun onResume() {
        super.onResume()

        val sps = PreferenceManager.getDefaultSharedPreferences(this)
        val userNickname = sps.getString("user_nickname", "?????????")
        val userGrade = sps.getString("user_grade", "1??????")
        val userProblemsNums = sps.getString("user_problems_nums", "10")
        val appTheme = sps.getBoolean("app_theme", false)

        view.mainTitle.text = "${userNickname}??? ???????????????\uD83D\uDE0E"
        view.mainSubTitle.text = "${userGrade}, ${userProblemsNums}??????"

        if (appTheme) ThemeManager.applyTheme(ThemeManager.ThemeMode.DARK)
        else ThemeManager.applyTheme(ThemeManager.ThemeMode.LIGHT)

        initChart()
    }

    private fun initChart() {
        val entryList = mutableListOf<BarEntry>()
        val am = AnalysisManager(UserLogManager(filesDir).readTextFile())
        val recentGrades = am.getRecentGrades() as List<Float>
        val labels = am.getRecentDaysLabel() as ArrayList<String>

        for ((index, value) in recentGrades.withIndex()) {
            entryList.add(BarEntry(index.toFloat(), value))
        }

        // #2 ??? ????????? ???
        val barDataSet = BarDataSet(entryList, "MyBarDataSet")
        barDataSet.color = ColorTemplate.rgb("#F2CED1")

        // #3 ??? ?????????
        val barData = BarData(barDataSet)
        barData.barWidth = 0.7f
        view.barChart.data = barData
        view.barChart.apply {
            //??????, Pinch ????????????
            setTouchEnabled(false)
            setScaleEnabled(true)
            setPinchZoom(false)

            legend.isEnabled = false

            // Chart ??? ???????????? ???????????????
            animateXY(300, 500)

            //Chart ?????? description ?????? ??????
            description = null

            //Legend??? ????????? ????????? ???????????????
            //????????? ????????? ????????? ??????
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT

            //????????? ???, ?????? ??????/???????????? ???????????????.
            //?????? ?????? ?????? 0?????? ???????????? ?????? ?????? ???????????????.
            axisLeft.axisMinimum = 0f
            axisRight.axisMinimum = 0f

            //??????????????? ?????? ?????? ????????? ???????????? ???????????????
            //?????? ?????????/???????????? ?????? ??????
            axisRight.setDrawLabels(false)

            //xAxis, yAxis ?????? ???????????? ?????? ????????? ???????????????
            //?????? ????????? Grid ?????? ??????
            xAxis.setDrawGridLines(false)

            //x??? ????????? ?????? ??????
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            //x??? ????????? ?????? ??????
            xAxis.labelCount = entryList.size

            xAxis.granularity = 1f
        }

        view.barChart.xAxis.valueFormatter = object: ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return labels[value.toInt()]
            }
        }

        view.barChart.invalidate()
    }
}