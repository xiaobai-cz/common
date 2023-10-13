package vip.oicp.xiaobaicz.lib.common.app

import android.content.Context
import androidx.fragment.app.FragmentActivity
import vip.oicp.xiaobaicz.lib.common.utils.LanguageUtils

abstract class FragmentActivity : FragmentActivity() {

    override fun attachBaseContext(newBase: Context?) {
        val context = LanguageUtils.handleBaseContext(newBase)
        super.attachBaseContext(context)
    }

    override fun onRestart() {
        super.onRestart()
        LanguageUtils.onRestart(this)
    }

}