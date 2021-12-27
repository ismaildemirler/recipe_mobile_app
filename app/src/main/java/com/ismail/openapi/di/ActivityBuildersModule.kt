package com.ismail.openapi.di

import com.ismail.openapi.di.auth.AuthFragmentBuildersModule
import com.ismail.openapi.di.auth.AuthModule
import com.ismail.openapi.di.auth.AuthScope
import com.ismail.openapi.di.auth.AuthViewModelModule
import com.ismail.openapi.ui.auth.AuthActivity
import com.ismail.openapi.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}