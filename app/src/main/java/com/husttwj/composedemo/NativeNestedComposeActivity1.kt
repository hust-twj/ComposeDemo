package com.husttwj.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text

/**
 * 原生使用Compose：直接使用Compose
 */
class NativeNestedComposeActivity1: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column {
                Text("原生嵌套 Compose")

                Text("原生嵌套 Compose2")
            }

//            Icon(this).apply {
//                setBackgroundColor(context.resources.getColor(R.color.purple_200))
//                setImageResource(R.mipmap.ic_launcher)
//            }
        }
    }
}