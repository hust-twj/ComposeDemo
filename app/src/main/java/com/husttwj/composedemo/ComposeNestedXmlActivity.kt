package com.husttwj.composedemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.husttwj.composedemo.databinding.ActivityNestXmlBinding

/**
 * Compose 嵌套 xml
 */
class ComposeNestedXmlActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    ComposeNestedXml()
                }
            }
        }
    }

}

@Composable
fun ComposeNestedXml() {
    val context = LocalActivity.current as ComposeNestedXmlActivity
    AndroidViewBinding(
        factory = { inflater, parent, attachToParent ->
            ActivityNestXmlBinding.inflate(inflater, parent, attachToParent)
        },
        modifier = Modifier.fillMaxSize(),
        update = {
            calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                val date = "${year}年${month + 1}月${dayOfMonth}日"
                Toast.makeText(view.context, date, Toast.LENGTH_SHORT).show()
                dateTv.text = date
            }

        }
    )
}
