package emperorfin.android.currencyconverter.ui.screens

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import emperorfin.android.currencyconverter.ui.screens.Screens.SCREEN_ABOUT
import emperorfin.android.currencyconverter.ui.screens.Screens.SCREEN_CURRENCY_CONVERSION


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 16th November, 2023.
 */



/**
 * Screens used in [Destinations]
 */
private object Screens {
    const val SCREEN_CURRENCY_CONVERSION: String = "currencyConversion"
    const val SCREEN_ABOUT: String = "about"
}

/**
 * Destinations used in the [MainActivity]
 */
object Destinations {
    const val ROUTE_CURRENCY_CONVERSION: String = SCREEN_CURRENCY_CONVERSION
    const val ROUTE_ABOUT: String = SCREEN_ABOUT
}

/**
 * Models the navigation actions in the app.
 */
class NavigationActions(private val navController: NavHostController) {

    fun navigateToCurrencyConversion() {
        val navigatesFromDrawer = true

        navController.navigate(Destinations.ROUTE_CURRENCY_CONVERSION) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = !navigatesFromDrawer
                saveState = navigatesFromDrawer
            }
            launchSingleTop = true
            restoreState = navigatesFromDrawer
        }
    }

    fun navigateToAbout() {
        navController.navigate(Destinations.ROUTE_ABOUT) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

}