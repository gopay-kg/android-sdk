package kg.gopay.sdk.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun DisclosureState(content: @Composable (isOpened: Boolean, open: () -> Unit, close: () -> Unit) -> Unit) {
    var isOpened by remember { mutableStateOf(false) }

    content(isOpened, { isOpened = true }, { isOpened = false })
}