package com.shrpereira.tweetsense.app.common.di

import com.shrpereira.tweetsense.app.BuildConfig
import com.shrpereira.tweetsense.data.cache.authentication.AccessTokenDataSource
import com.shrpereira.tweetsense.data.cache.authentication.AccessTokenDataSourceImpl
import com.shrpereira.tweetsense.data.remote.RetrofitBuilder
import com.shrpereira.tweetsense.data.remote.authentication.GoogleAuthDataSource
import com.shrpereira.tweetsense.data.remote.authentication.GoogleAuthDataSourceImpl
import com.shrpereira.tweetsense.data.remote.authentication.TwitterAuthDataSource
import com.shrpereira.tweetsense.data.remote.authentication.TwitterAuthDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val uiModule = module(override = true) {

}

val domainModule = module(override = true) {

}

val dataModule = module(override = true) {
	factory("TwitterRetrofitBuilder") {
		RetrofitBuilder(
			BuildConfig.TWITTER_API_URL,
			androidContext().cacheDir,
			RetrofitBuilder.ApiType.TwitterAPI(
				BuildConfig.TWITTER_API_KEY,
				BuildConfig.TWITTER_API_SECRET_KEY,
				get()
			)
		)
	}
	factory("GoogleRetrofitBuilder") {
		RetrofitBuilder(
			BuildConfig.GOOGLE_API_URL,
			androidContext().cacheDir,
			RetrofitBuilder.ApiType.GoogleAPI(get())
		)
	}

	factory { TwitterAuthDataSourceImpl(get("TwitterRetrofitBuilder")) as TwitterAuthDataSource }
	factory { GoogleAuthDataSourceImpl(androidContext(), get()) as GoogleAuthDataSource }
	single { AccessTokenDataSourceImpl(androidContext()) as AccessTokenDataSource }
}