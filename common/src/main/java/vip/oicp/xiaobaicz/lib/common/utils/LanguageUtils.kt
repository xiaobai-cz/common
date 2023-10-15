@file:Suppress("unused")
package vip.oicp.xiaobaicz.lib.common.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import androidx.core.content.edit
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import java.util.Locale

/**
 * 语言环境工具
 * @see android.content.ContextWrapper.attachBaseContext
 * @see android.app.Application.attachBaseContext
 * @see android.app.Activity.attachBaseContext
 * @see android.app.Activity.onRestart
 */
object LanguageUtils {

    private const val KEY_LOCALE = "locale"

    /**
     * 当前语言环境
     */
    @JvmStatic
    var locale: Locale = Locale.getDefault()
        private set

    /**
     * 在[Activity.onRestart]调用，语言环境变更时，重启[Activity]
     * @see Activity.onRestart
     */
    @JvmStatic
    fun onRestart(activity: Activity) {
        if (ConfigurationCompat.getLocales(activity.resources.configuration)[0] != locale) {
            activity.recreate()
        }
    }


    /**
     * 在[Activity.attachBaseContext]调用，语言环境变更时，重启[Activity]
     * @see Activity.attachBaseContext
     */
    @JvmStatic
    fun handleBaseContext(base: Context?): Context? {
        base ?: return null
        val configuration = base.resources.configuration
        ConfigurationCompat.setLocales(configuration, LocaleListCompat.create(locale))
        return base.createConfigurationContext(configuration)
    }

    /**
     * 在[android.app.Application.attachBaseContext]调用，语言环境变更时，重启[android.app.Application]
     * @see android.app.Application.attachBaseContext
     */
    @JvmStatic
    fun handleBaseApplicationContext(base: Context?): Context? {
        loadLocale(base)
        handleSystemResource()
        return handleBaseContext(base)
    }

    @JvmStatic
    private fun handleSystemResource() {
        onChangeLocale(Resources.getSystem())
    }

    @JvmStatic
    private fun handleApplicationResource(context: Context) {
        onChangeLocale(context.applicationContext.resources)
    }

    @JvmStatic
    private fun onChangeLocale(resources: Resources) {
        val configuration = resources.configuration
        ConfigurationCompat.setLocales(configuration, LocaleListCompat.create(locale))
        @Suppress("DEPRECATION")
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    @JvmStatic
    private fun loadLocale(context: Context?) {
        context ?: return
        val sp = context.getSharedPreferences(LanguageUtils::class.java.name, Context.MODE_PRIVATE)
        val tag = sp.getString(KEY_LOCALE, null)
        setDefault(if (tag == null) Locale.getDefault() else Locale.forLanguageTag(tag))
    }

    @JvmStatic
    private fun saveLocale(context: Context) {
        context.getSharedPreferences(LanguageUtils::class.java.name, Context.MODE_PRIVATE).edit(true) {
            putString(KEY_LOCALE, locale.toLanguageTag())
        }
    }

    @JvmStatic
    private fun setDefault(locale: Locale) {
        try {
            this.locale = locale.clone() as Locale
            Locale.setDefault(locale.clone() as Locale)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    /**
     * 设置语言环境，马上生效
     */
    @JvmStatic
    fun setLocale(activity: Activity, locale: Locale) {
        if (this.locale == locale) return
        setDefault(locale)
        saveLocale(activity)
        handleSystemResource()
        handleApplicationResource(activity)
        activity.recreate()
    }

}