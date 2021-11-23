package kr.ac.kumoh.s20170419.everydaymath

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // ���� ���� �ҷ�����
//        val sps = PreferenceManager.getDefaultSharedPreferences(this)
//        val userGrade = sps.getString("user_grade", "")
//        val userProblemsNums = sps.getString("user_problems_nums", "")
//        val appTheme = sps.getBoolean("app_theme", false)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0F

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //toolbar�� backŰ ������ �� ����
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}