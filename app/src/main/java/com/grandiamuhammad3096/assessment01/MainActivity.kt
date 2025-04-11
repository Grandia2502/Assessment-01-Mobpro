package com.grandiamuhammad3096.assessment01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.grandiamuhammad3096.assessment01.navigation.SetupNavGraph
import com.grandiamuhammad3096.assessment01.ui.theme.Assessment01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assessment01Theme {
                SetupNavGraph()
            }
        }
    }
}