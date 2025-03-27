package com.husttwj.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.husttwj.composedemo.databinding.ActivityNativeNestedCompose2Binding
import com.husttwj.composedemo.ui.theme.ComposeDemoTheme

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
            CustomComposeView(
                input = {
                    mBinding.et.text.toString()
                },
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

/**
 * 状态提升：利用高阶函数，将状态提升到外部，提升复用性和可测性
 * Composable函数不要传入固定的字符串，而是传入一个 lambda，使得在点击按钮时可以实时获取 EditText 的当前文本
 */
@Composable
fun CustomComposeView(input: () -> String, modifier: Modifier = Modifier) {
    var content by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                content = input()
            },
            modifier = modifier
        ) {
            Text("提交", fontSize = 16.sp)
        }
        Text(content.ifEmpty { "empty" }, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun CustomComposePreview() {
    ComposeDemoTheme {
        CustomComposeView({ "测试" })
    }
}
