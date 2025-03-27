package com.husttwj.composedemo

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * 封装：将Compose转为Android View，方便在 Android 原生中直接使用
 */
class Compose2AndroidView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    // 用于保存内部 WebView 实例的引用，确保外部可以获取到
    private var _webView: WebView? = null

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    override fun Content() {
        // TODO:
        // WebViewPage()

        val webView = rememberWebViewWithLifecycle()
        DisposableEffect(Unit) {
            _webView = webView
            onDispose {
                _webView = null
            }
        }
        Column {
            Text(
                "测试",
                modifier = Modifier
                    .background(Color(0x40ee0000))
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            // 使用 AndroidView 显示该 WebView
            AndroidView(
                factory = { webView },
                modifier = Modifier.fillMaxSize(),
                update = { view ->
                    view.settings.apply {
                        javaScriptEnabled = true
                    }
                }
            )
        }
    }

    /**
     * 提供公开方法获取内部的 WebView 实例
     */
    fun getWebView(): WebView? = _webView
}

/**
 *  创建 WebView，并绑定生命周期，防止内存泄露
 */
@Composable
fun rememberWebViewWithLifecycle(): WebView {
    val context = LocalContext.current
    val webView = remember {
        WebView(context)
    }
    val lifecycleObserver = rememberWebViewLifecycleObserve(webView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    return webView
}

@Composable
fun rememberWebViewLifecycleObserve(webView: WebView): LifecycleEventObserver {
    return remember(webView) {
        LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> webView.onResume()
                Lifecycle.Event.ON_PAUSE -> webView.onPause()
                Lifecycle.Event.ON_DESTROY -> webView.destroy()
                else -> {
                    //ignore
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage() {
    val webView = rememberWebViewWithLifecycle()
    AndroidView(
        factory = { webView },
        modifier = Modifier.fillMaxSize(),
        update = { view ->
            view.settings.apply {
                javaScriptEnabled = true
            }
            //  webView.loadUrl(xxx)
        }
    )
}