package com.husttwj.composedemo

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.unit.dp
import com.husttwj.composedemo.ui.theme.ComposeDemoTheme

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SecondGreeting("Second")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        window.decorView.postDelayed({
            Modifier.size(10.dp).alpha(0.1f)
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
                text = "跳转到SecondActivity",
                //   modifier = modifier.alpha(0.5f)
            )
        }
    }
}