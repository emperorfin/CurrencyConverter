package emperorfin.android.currencyconverter.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import emperorfin.android.currencyconverter.ui.screens.Destinations
import emperorfin.android.currencyconverter.ui.screens.NavigationActions
import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.ui.theme.CurrencyConverterTheme
import emperorfin.android.currencyconverter.ui.theme.md_theme_dark_primary
import emperorfin.android.currencyconverter.ui.theme.md_theme_light_primary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 16th November, 2023.
 */


@Composable
fun AppModalDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                navigateToCurrencyConversion = { navigationActions.navigateToCurrencyConversion() },
                navigateToAbout = { navigationActions.navigateToAbout() },
                closeDrawer = { coroutineScope.launch { drawerState.close() } }
            )
        },
        content = content,
        scrimColor = Color.White
    )
}

@Composable
private fun AppDrawer(
    currentRoute: String,
    navigateToCurrencyConversion: () -> Unit,
    navigateToAbout: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(end = 32.dp)) {
        DrawerHeader()
        DrawerButton(
            painter = painterResource(id = R.drawable.ic_list),
            label = stringResource(id = R.string.app_name),
            isSelected = currentRoute == Destinations.ROUTE_CURRENCY_CONVERSION,
            action = {
                navigateToCurrencyConversion()
                closeDrawer()
            }
        )
        DrawerButton(
            painter = painterResource(id = R.drawable.ic_about),
            label = stringResource(id = R.string.app_about),
            isSelected = currentRoute == Destinations.ROUTE_ABOUT,
            action = {
                navigateToAbout()
                closeDrawer()
            }
        )
    }
}

@Composable
private fun DrawerHeader(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(md_theme_light_primary)
            .height(dimensionResource(id = R.dimen.header_height))
            .padding(dimensionResource(id = R.dimen.header_padding))
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_no_fill),
            contentDescription = null,
            modifier = Modifier.width(dimensionResource(id = R.dimen.header_image_width))
        )
        Text(
            text = stringResource(id = R.string.app_name),
//            color = MaterialTheme.colors.surface
            color = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
private fun DrawerButton(
    painter: Painter,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tintColor = if (isSelected) {
//        MaterialTheme.colorScheme.secondary
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    }

    TextButton(
        onClick = action,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin))
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painter,
                contentDescription = null, // decorative
                tint = tintColor
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = label,
//                style = MaterialTheme.typography.body2,
                style = MaterialTheme.typography.bodyMedium,
                color = tintColor
            )
        }
    }
}

@Preview("Drawer contents")
@Composable
fun PreviewAppDrawer() {
    CurrencyConverterTheme {
        Surface {
            AppDrawer(
                currentRoute = Destinations.ROUTE_CURRENCY_CONVERSION,
                navigateToCurrencyConversion = {},
                navigateToAbout = {},
                closeDrawer = {}
            )
        }
    }
}