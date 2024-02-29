package emperorfin.android.currencyconverter.ui.screens.about

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.ui.utils.AboutTopAppBar


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 29th February, 2024.
 */



@Composable
fun AboutScreen(
    context: Context,
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            AboutTopAppBar(
                openDrawer = openDrawer
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->

        Content(
            modifier = Modifier.padding(paddingValues)
        )

    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.horizontal_margin),
                vertical = dimensionResource(id = R.dimen.horizontal_margin)
            )
    ) {
        Text(text = "This is just a simple About screen.")
    }

}