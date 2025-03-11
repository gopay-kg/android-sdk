package kg.gopay.sdk.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import kg.gopay.sdk.R

@Composable
fun PaymentFailed() {
    PaymentStatusScreen(
        message = stringResource(R.string.payment_failed_message),
        color = colorResource(R.color.failed_payment_icon),
        icon = Icons.Rounded.Close
    )
}