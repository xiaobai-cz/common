package vip.oicp.xiaobaicz.lib.common.app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import vip.oicp.xiaobaicz.lib.common.utils.LanguageUtils

abstract class AppCompatActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        val context = LanguageUtils.handleBaseContext(newBase)
        super.attachBaseContext(context)
    }

    override fun onRestart() {
        super.onRestart()
        LanguageUtils.onRestart(this)
    }

}