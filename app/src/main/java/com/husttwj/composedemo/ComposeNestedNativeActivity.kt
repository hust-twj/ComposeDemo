package com.husttwj.composedemo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.husttwj.composedemo.ui.theme.ComposeDemoTheme

/**
 * Compose 嵌套原生view
 */
class ComposeNestedNativeActivity : ComponentActivity() {

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
                        SecondGreeting("原生view")

                        //background函数在前面、padding在后面就是padding效果，否则就是margin效果
                        //嵌套Android 原生view
                        AndroidView(
                            factory = { context: Context ->
                                ImageView(context).apply {
                                    setBackgroundColor(context.resources.getColor(R.color.purple_200))
                                    setImageResource(R.mipmap.ic_launcher)
                                }
                            },
                            modifier = Modifier
                                .size(100.dp, 200.dp)
                                .padding(top = 20.dp),

                            update = {
                                //布局加载后，width还是0，需要post才能获取到真正的宽高
                                Log.e("twj124", "update: " + it + "  " + it.width)
                                it.post {
                                    Log.e("twj124", "post update: " + it + "  " + it.width)
                                }
                            }
                        )

                        AndroidView(
                            factory = { context: Context ->
                                TextView(context).apply {
                                    setBackgroundColor(context.resources.getColor(R.color.teal_200))
                                    text = "测试"
                                    gravity = Gravity.CENTER
                                }
                            },
                            modifier = Modifier
                                .size(100.dp, 100.dp)
                                .padding(top = 20.dp)
                        )

                        AndroidView(
                            factory = { CalendarView(it) },
                            modifier = Modifier.fillMaxSize(),
                            update = {
                                it.setOnDateChangeListener { view, year, month, dayOfMonth ->
                                    Toast.makeText(
                                        view.context,
                                        "${year}年${month + 1}月${dayOfMonth}日",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        window.decorView.postDelayed({
            Modifier
                .size(10.dp)
                .alpha(0.1f)
        }, 2000)
    }
}


@Composable
fun SecondGreeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )

        //   Log.e("twj124", "1: " + Modifier.padding(10.dp) + "  " + (Modifier.padding(10.dp)))

        Button(
            onClick = {
                Toast.makeText(context, "跳转", Toast.LENGTH_SHORT).show()
                //  context.startActivity(Intent(context, SecondActivity::class.java))
            },
            //   modifier = Modifier.size(width = 190.dp, height = 40.dp)
        ) {
            Text(
                text = "Compose 嵌套 Android 原生 View",
                //   modifier = modifier.alpha(0.5f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeDemoTheme  {
        Column {
            SecondGreeting("原生view")

            //background函数在前面、padding在后面就是padding效果，否则就是margin效果
            //嵌套Android 原生view
            AndroidView(
                factory = { context: Context ->
                    ImageView(context).apply {
                        setBackgroundColor(context.resources.getColor(R.color.purple_200))
                        setImageResource(R.mipmap.ic_launcher)
                    }
                },
                modifier = Modifier
                    .size(100.dp, 200.dp)
                    .padding(top = 20.dp),

                update = {
                    //布局加载后，width还是0，需要post才能获取到真正的宽高
                    Log.e("twj124", "update: " + it + "  " + it.width)
                    it.post {
                        Log.e("twj124", "post update: " + it + "  " + it.width)
                    }
                }
            )

            AndroidView(
                factory = { context: Context ->
                    TextView(context).apply {
                        setBackgroundColor(context.resources.getColor(R.color.teal_200))
                        text = "测试"
                        gravity = Gravity.CENTER
                    }
                },
                modifier = Modifier
                    .size(100.dp, 100.dp)
                    .padding(top = 20.dp)
            )

            AndroidView(
                factory = { CalendarView(it) },
                modifier = Modifier.fillMaxSize(),
                update = {
                    it.setOnDateChangeListener { view, year, month, dayOfMonth ->
                        Toast.makeText(
                            view.context,
                            "${year}年${month + 1}月${dayOfMonth}日",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }
}

