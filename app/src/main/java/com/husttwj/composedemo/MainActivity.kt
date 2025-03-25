package com.husttwj.composedemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.husttwj.composedemo.ui.theme.ComposeDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Greeting("Android")
                       // CounterComponent()

                    }
                }

                BackPressedHandler(true) {
                    Toast.makeText(this@MainActivity, "返回", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column {
//        Text(
//            text = "Hello $name!",
//            modifier = modifier
//        )
        //PaddingElement
        Log.e("twj124", "1: " + Modifier.padding(10.dp) + "  " + (Modifier.padding(10.dp)))
        //CombinedModifier
        Log.e(
            "twj124", "2: " + Modifier
                .padding(10.dp)
                .size(width = 190.dp, height = 40.dp)
        )
        //CombinedModifier
        Log.e(
            "twj124", "2: " + Modifier
                .padding(10.dp)
                .size(width = 190.dp, height = 40.dp)
                .alpha(0.3f)
        )

        Button(
            onClick = {
                context.startActivity(Intent(context, ComposeNestedNativeActivity::class.java))
            },

            //size前设置padding（即margin），size后设置是内间距，即padding
            modifier = Modifier
                .padding(10.dp)
                .size(width = 290.dp, height = 40.dp)
              //  .background(Color.Red)
        ) {
            Text(
                text = "跳转到 Compose 嵌套原生 View",
                modifier = modifier.alpha(0.5f)
            )
        }

        Button(
            onClick = {
                context.startActivity(Intent(context, ComposeNestedXmlActivity::class.java))
            },

            //size前设置padding（即margin），size后设置是内间距，即padding
            modifier = Modifier
                .padding(10.dp)
                .size(width = 290.dp, height = 40.dp)
               // .background(Color.Red)
        ) {
            Text(
                text = "跳转到 Compose 嵌套 xml",
                modifier = modifier.alpha(0.5f)
            )
        }

        Button(
            onClick = {
                Toast.makeText(context, "跳转", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, ImageActivity::class.java))
            },
            //先设置padding = margin
            modifier = Modifier
                .padding(10.dp)
                .size(width = 290.dp, height = 40.dp)
        ) {
            Text(
                text = "跳转到ImageActivity",
                modifier = modifier.alpha(0.5f)
            )
        }
        //分割符
        Spacer(modifier = Modifier.size(10.dp))

    }
}

@Composable
fun CounterComponent() {
    Column(Modifier.background(Color(0x40ee0000))) {
        //todo：页面旋转，重建后，数据不会保存，还是0
        var counter by remember {
            mutableIntStateOf(0)
        }
        Text(text = "点击下面按钮计数：")
        Text(
            text = "$counter",
            Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        //垂直方向空白间隔：height
        Spacer(Modifier.height(20.dp))
        Row {
            Button(
                onClick = { counter-- },
                Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
            ) {
                Text(text = "-")
            }

            //水平方向空白间隔：width
            Spacer(Modifier.width(20.dp))
            Button(
                onClick = { counter++ },
                Modifier
                    .padding(end = 10.dp)
                    .weight(1f)
            ) {
                Text(text = "+")
            }
        }
    }

}

/**
 * 处理返回键
 */
@Composable
fun BackPressedHandler(enable: Boolean = true, onBackPressed: () -> Unit) {
    val backDispatcher =
        checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
            "no OnBackPressedDispatcher"
        }.onBackPressedDispatcher

    val backCallback = remember {
        object : OnBackPressedCallback(enable) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }
    DisposableEffect(backDispatcher) {
        backDispatcher.addCallback(backCallback)
        onDispose {
            //onDispose时，移除backCallback，避免泄露
            backCallback.remove()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeDemoTheme {
        Column {
            Greeting("Android")
            CounterComponent()
        }
    }
}