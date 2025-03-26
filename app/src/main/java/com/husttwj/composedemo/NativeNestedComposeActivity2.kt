package com.husttwj.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.husttwj.composedemo.databinding.ActivityNativeNestedCompose2Binding

/**
 * 原生使用Compose：xml 中使用ComposeView
 */
class NativeNestedComposeActivity2 : ComponentActivity() {

    private lateinit var mBinding: ActivityNativeNestedCompose2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityNativeNestedCompose2Binding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initViews()
    }

    private fun initViews() {
        mBinding.composeView.setContent {
            var content by remember { mutableStateOf("") }
            Column(modifier = Modifier.fillMaxSize()) {
                Button(
                    onClick = {
                        content = mBinding.et.text.toString().trim()
                    }) {
                    Text("提交")
                }
                Text(content)
            }
        }
    }
}