package com.heyooo.heymail.utils

import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.*


class ContextWrapperUtils {

    companion object {

        fun wrap(newBase: Context, newLocale: Locale): Context {
            val res = newBase.resources
            val configuration = res.configuration
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.locales = localeList
            } else {
                configuration.setLocale(newLocale)
            }
            val newConfigContext = newBase.createConfigurationContext(configuration)
            val outerContext = newBase::class.java.getDeclaredField("mOuterContext")
            if (outerContext != null) {
                outerContext.isAccessible = true
                val activity = outerContext.get(newBase)
                val setOuterContext = newConfigContext::class.java.getDeclaredMethod("setOuterContext", Context::class.java)
                setOuterContext.isAccessible = true
                setOuterContext.invoke(newConfigContext, activity)
            }
            return newConfigContext
        }

    }
}