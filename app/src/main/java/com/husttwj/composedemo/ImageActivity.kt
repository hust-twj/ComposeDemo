package com.husttwj.composedemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.husttwj.composedemo.ui.theme.ComposeDemoTheme

class ImageActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ImageGreeting("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageGreeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column {
        //todo:问题1 不能直接加载webp图片，只能加载png jpg等
        Image(
            painter = painterResource(id = R.drawable.icon_feed_topic),
            contentDescription = "",
            modifier = Modifier
                .size(60.dp)
                .alpha(0.5f) //设置图片宽高
        )

        Text(text = "Glide加载网络图片：")
        Log.e("twj124", "dd:" + Modifier.width(100.dp))
        //Glide 加载网络图片  https://bumptech.github.io/glide/int/compose.html
        GlideImage(
            model = "https://wx-love-img.afunapp.com/FpuzxvUQLDAOoPpwfa637xB4_e2u",
            contentDescription = "",
            modifier = Modifier.width(100.dp)
        )
        GlideImage(
            model = R.drawable.icon_feed_topic,
            contentDescription = "",
            modifier = Modifier.width(100.dp)
        )
        //Glide 支持加载 webp图片
        GlideImage(
            model = R.mipmap.ic_launcher,
            contentDescription = "",
            modifier = Modifier.width(100.dp)
        )
    }
}