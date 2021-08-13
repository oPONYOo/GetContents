package com.example.getcontents.activity

import android.os.Bundle

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


private val TAG = BaseActivity::class.java.simpleName

open class BaseActivity : RxAppCompatActivity() {




    private val effects = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    override fun onResume() {
        super.onResume()
    }
    companion object {
        private const val POINTER_SIZE = 100
        lateinit var instance: BaseActivity
            private set
    }
}