package com.husttwj.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.husttwj.composedemo.databinding.ActivityNativeNestedCompose3Binding

/**
 * 原生使用Compose：将Compose转为Android View，方便在原生view中使用
 */
class NativeNestedComposeActivity3 : ComponentActivity() {

    private lateinit var mBinding: ActivityNativeNestedCompose3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityNativeNestedCompose3Binding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initViews()
    }

    private fun initViews() {
        //post保证能获取到实例
        mBinding.composeView.post {
            mBinding.composeView.getWebView()?.loadUrl("https://www.baidu.com")
        }
    }

}

