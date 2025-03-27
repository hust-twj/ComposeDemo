package com.husttwj.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

/**
 * 原生使用Compose：直接使用Compose
 */
class NativeNestedComposeActivity1: ComponentActivity() {

    @OptIn(ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column {
                Text("原生嵌套 Compose")

                GlideImage(
                    model = R.mipmap.ic_launcher,
                    contentDescription = "",
                    modifier = Modifier.width(100.dp)
                )
            }
        }
    }
}