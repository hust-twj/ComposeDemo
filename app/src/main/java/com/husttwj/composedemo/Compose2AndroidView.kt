package com.husttwj.composedemo

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext
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

    // 用于保存内部 WebView 实例的引用
    private var _webView: WebView? = null

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    override fun Content() {
        // WebViewPage()

        // 使用记忆函数创建 WebView，并绑定生命周期
        val webView = rememberWebViewWithLifecycle()
        // 保存引用到外部变量 _webView，确保外部可以获取到
        DisposableEffect(Unit) {
            _webView = webView
            onDispose { _webView = null }
        }
        // 使用 AndroidView 显示该 WebView
        AndroidView(
            factory = { webView },
            modifier = Modifier.fillMaxSize(),
            update = { view ->
                view.settings.apply {
                    javaScriptEnabled = true
                }
                // 可在此处执行其它 WebView 的更新操作，例如加载 URL
                // view.loadUrl("https://www.baidu.com")
            }
        )
    }

    /**
     * 提供公开方法获取内部的 WebView 实例
     */
    fun getWebView(): WebView? = _webView
}

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
                else -> android.util.Log.e("TAG", "hello world")
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