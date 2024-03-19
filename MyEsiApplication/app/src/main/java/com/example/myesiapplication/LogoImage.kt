package com.example.myesiapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myesiapplication.ui.theme.EsiPageTheme

@Composable
fun LogoImage(modifier: Modifier = Modifier) {
    val viewModel: LogoImageViewModel = viewModel() //pas utilisé car aucune logique métier.

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val image = painterResource(R.drawable.logoesi)

        Image(
            painter = image,
            contentDescription = null
        )

    }
}

@Preview(showBackground = false)
@Composable
fun EsiPreview() {
    EsiPageTheme {
        LogoImage()
    }
}

