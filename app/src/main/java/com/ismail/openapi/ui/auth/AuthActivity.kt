package com.ismail.openapi.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ismail.openapi.R
import com.ismail.openapi.ui.BaseActivity
import com.ismail.openapi.ui.ResponseType
import com.ismail.openapi.ui.ResponseType.*
import com.ismail.openapi.ui.main.MainActivity
import com.ismail.openapi.viewmodels.ViewModelProviderFactory
import javax.inject.Inject
import kotlin.math.log

class AuthActivity : BaseActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewModel = ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)

        subscribeObservers()
    }

    fun subscribeObservers(){
        viewModel.dataState.observe(this, Observer { dataState ->

            dataState.data?.let { data ->
                data.data?.let { event ->
                    event.getContentIfNotHandled()?.let {
                        it.authToken?.let {
                            Log.d(TAG, "AuthActivity, DataState: ${it} ")
                        }
                    }
                }

                data.response?.let { event ->
                    event.getContentIfNotHandled()?.let {
                        when(it.responseType){
                            is Dialog -> {

                            }
                            is Toast -> {

                            }

                            is None -> {
                                Log.e(TAG, "AuthActivity, Response: ${it.message}")
                            }
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(this, Observer {
            it.authToken?.let {
                sessionManager.login(it)
            }
        })

        sessionManager.cachedToken.observe(this, Observer { authToken ->
            Log.d(TAG, "AuthActivity: subscribeObservers: AuthToken: ${authToken}")
            if (authToken != null && authToken.account_pk != -1 && authToken.token != null){
                navMainActivity()
                finish()
            }
        })
    }

    private fun navMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}