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
 * @see android.content.ContextWrapper.attachBaseContext
 * @see android.app.Application.attachBaseContext
 * @see android.app.Activity.attachBaseContext
 * @see android.app.Activity.onRestart
 */
object LanguageUtils {

    private const val KEY_LOCALE = "locale"

    @JvmStatic
    var locale: Locale = Locale.getDefault()
        private set

    /**
     * @see android.app.Activity.onRestart
     */
    @JvmStatic
    fun onRestart(activity: Activity) {
        if (ConfigurationCompat.getLocales(activity.resources.configuration)[0] != locale) {
            activity.recreate()
        }
    }


    /**
     * @see android.app.Activity.attachBaseContext
     */
    @JvmStatic
    fun handleBaseContext(base: Context?): Context? {
        base ?: return null
        val configuration = base.resources.configuration
        ConfigurationCompat.setLocales(configuration, LocaleListCompat.create(locale))
        return base.createConfigurationContext(configuration)
    }

    /**
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