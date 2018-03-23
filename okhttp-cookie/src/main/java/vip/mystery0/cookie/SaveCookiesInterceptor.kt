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
import okhttp3.Interceptor
import okhttp3.Response

class SaveCookiesInterceptor : Interceptor {
	private lateinit var context: Context
	private lateinit var preferenceName: String

	constructor(context: Context) : this(context, "cookies")
	constructor(context: Context, preferenceName: String) {
		this.context = context
		this.preferenceName = preferenceName
	}

	init {
		CookieManager.cookiePreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
	}

	override fun intercept(chain: Interceptor.Chain): Response {
		val request = chain.request()
		val response = chain.proceed(request)
		if (response.headers("set-cookie").isNotEmpty()) {
			val cookies = encodeCookie(response.headers("set-cookie"))
			CookieManager.putCookie(request.url().host(), cookies)
		}
		return response
	}


	//整合cookie为唯一字符串
	private fun encodeCookie(cookies: List<String>): String {
		val sb = StringBuilder()
		val set = HashSet<String>()
		cookies.map { cookie ->
			cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
		}.forEach { arr ->
					arr.filterNot { set.contains(it) }.forEach { set.add(it) }
				}

		val ite = set.iterator()
		while (ite.hasNext()) {
			val cookie = ite.next()
			sb.append(cookie).append(';')
		}

		val last = sb.lastIndexOf(';')
		if (sb.length - 1 == last) {
			sb.deleteCharAt(last)
		}

		return sb.toString()
	}
}