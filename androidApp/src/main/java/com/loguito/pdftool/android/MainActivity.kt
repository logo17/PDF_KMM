package com.loguito.pdftool.android

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.loguito.pdftool.PDFTool
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var clientName by remember { mutableStateOf("") }
                    var amount by remember { mutableStateOf("") }
                    var description by remember { mutableStateOf("") }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Please enter the following information:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        TextField(
                            value = clientName,
                            onValueChange = { clientName = it },
                            label = { Text("Client name") }
                        )

                        TextField(
                            value = amount,
                            onValueChange = { amount = it },
                            label = { Text("Amount") }
                        )

                        TextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description") }
                        )

                        Button(onClick = {
                            val monthFormatter = DateTimeFormatter.ofPattern("dd-MM")
                            val yearFormatter = DateTimeFormatter.ofPattern("YY")
                            val currentMonth = LocalDateTime.now().format(monthFormatter)
                            val currentYear = LocalDateTime.now().format(yearFormatter)

                            val information = mapOf(
                                "clientName" to clientName,
                                "amount" to amount,
                                "description" to description,
                                "dateMonthDay" to currentMonth,
                                "dateYear" to currentYear
                            )
                            PDFTool(
                                applicationContext,
                                R.raw.void_cheque,
                                information
                            ).generatedFilePath.toUri()
                                .apply {
                                    val intent = Intent(Intent.ACTION_SEND)
                                    intent.putExtra(Intent.EXTRA_STREAM, this)
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    intent.type = "application/pdf"
                                    startActivity(Intent.createChooser(intent, "Share PDF File"))
                                }
                        }) {
                            Text(text = "Submit")
                        }

                    }
                }
            }
        }
    }

}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
