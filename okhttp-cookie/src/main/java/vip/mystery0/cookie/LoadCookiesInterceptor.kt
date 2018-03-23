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
import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response

class LoadCookiesInterceptor : Interceptor {
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
		val builder = request.newBuilder()
		val cookie = CookieManager.getCookie(request.url().host())
		if (!TextUtils.isEmpty(cookie)) builder.addHeader("Cookie", cookie!!)
		return chain.proceed(builder.build())
	}
}