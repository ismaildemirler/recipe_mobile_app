package com.ismail.openapi.ui

import com.ismail.openapi.session.SessionManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(){

    protected val TAG: String = "AppDebug"

    @Inject
    lateinit var sessionManager: SessionManager


}