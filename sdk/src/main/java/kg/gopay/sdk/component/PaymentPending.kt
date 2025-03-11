package kg.gopay.sdk.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CompareArrows
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import kg.gopay.sdk.R

@Composable
fun PaymentPending() {
    PaymentStatusScreen(
        message = stringResource(R.string.payment_pending_message),
        color = colorResource(R.color.pending_payment_icon),
        icon = Icons.Rounded.CompareArrows
    )
}