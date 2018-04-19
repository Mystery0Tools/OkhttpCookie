/*
 * Created by Mystery0 on 18-3-20 下午2:01.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vip.mystery0.cookie

import android.content.Context
import android.content.SharedPreferences

object CookieManager {
	var cookiePreferences: SharedPreferences? = null

	@JvmStatic
	fun getCookiePreference(context: Context): SharedPreferences {
		return getCookiePreference(context, preferenceName = "cookie")
	}

	@JvmStatic
	fun getCookiePreference(context: Context, preferenceName: String): SharedPreferences {
		return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
	}

	fun putCookie(domain: String, cookie: String?) {
		if (cookiePreferences == null) return
		cookiePreferences!!.edit().putString(domain, cookie).apply()
	}

	fun getCookie(domain: String): String? {
		return cookiePreferences?.getString(domain, null)
	}
}