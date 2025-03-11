package kg.gopay.sdk.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import kg.gopay.sdk.R

@Composable
fun PaymentSuccess() {
    PaymentStatusScreen(
        message = stringResource(R.string.payment_success_message),
        color = colorResource(R.color.committed_payment_icon),
        icon = Icons.Rounded.Check
    )
}