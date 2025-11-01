package com.hanzelius.week7_8_hayya.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    errorMessage: String = "Tidak ada koneksi internet.",
    onRetry: () -> Unit = {}
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF282828)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Error",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF9B9B8E)
        )
        Text(
            text = errorMessage,
            color = Color.Red,
            fontSize = 14.sp
        )
        if (onRetry != null){
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA6A07A),
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Coba Lagi",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Preview
@Composable
fun ErrorViewPreview() {
    ErrorView()
}
