package emperorfin.android.currencyconverter.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.pullrefresh.pullRefresh
import androidx.compose.material3.pullrefresh.rememberPullRefreshState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 17th November, 2023.
 */


/**
 * Display an initial empty state or swipe to refresh content.
 *
 * @param loading (state) when true, display a loading spinner over [content]
 * @param empty (state) when true, display [emptyContent]
 * @param emptyContent (slot) the content to display for the empty state
 * @param onRefresh (event) event to request refresh
 * @param modifier the modifier to apply to this layout.
 * @param content (slot) the main content to show
 */
@Composable
fun LoadingContent(
    loading: Boolean,
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
//        SwipeRefresh(
//            state = rememberSwipeRefreshState(loading),
//            onRefresh = onRefresh,
//            modifier = modifier,
//            content = content,
//        )

//        val refreshScope = rememberCoroutineScope()
//        var refreshing by remember { mutableStateOf(false) }
//        var itemCount by remember { mutableStateOf(15) }
//
//        fun refresh() = refreshScope.launch {
//            refreshing = true
//            delay(1500)
//            itemCount += 5
//            refreshing = false
//        }
//
        val state = rememberPullRefreshState(loading, onRefresh)

        Box(modifier = Modifier.pullRefresh(state = state)) {
//            LazyColumn(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(space = 8.dp),
//            ) {
//                if (!loading) {
//                    items(10) {
//                        Card {
//                            Box(modifier = Modifier.padding(all = 8.dp)) {
//                                Text(text = "Item ${10 - it}")
//                            }
//                        }
//                    }
//                }
//            }
            content()
            PullRefreshIndicator(
                modifier = Modifier.align(alignment = Alignment.TopCenter),
                refreshing = loading,
                state = state,
            )
        }
    }

}