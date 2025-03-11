package kg.gopay.sdk.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.HourglassBottom
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import kg.gopay.sdk.R

@Composable
fun PaymentExpired() {
    PaymentStatusScreen(
        message = stringResource(R.string.payment_expired_message),
        color = colorResource(R.color.expired_payment_icon),
        icon = Icons.Rounded.HourglassBottom
    )
}