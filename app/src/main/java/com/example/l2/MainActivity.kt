package com.example.l2

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.l2.ui.theme.L2Theme

class MainActivity : ComponentActivity() {

    private var bt_full: Button? = null
    private var bt_part: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_layout)
        bt_full = findViewById(R.id.BT_full)
        bt_part = findViewById(R.id.BT_part)

        bt_full?.setOnClickListener{
            var full_intent = Intent(this@MainActivity, Activity_2::class.java)
            full_intent.putExtra("data", "full")
            startActivity(full_intent)
        }
        bt_part?.setOnClickListener{
            var part_intent = Intent(this@MainActivity, Activity_2::class.java)
            part_intent.putExtra("data", "part")
            startActivity(part_intent)
        }

    }
}

