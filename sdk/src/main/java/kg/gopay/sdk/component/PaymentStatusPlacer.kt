package kg.gopay.sdk.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kg.gopay.sdk.model.Payment
import kg.gopay.sdk.model.PaymentStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun PaymentStatusPlacer(
    payment: Payment,
    pollingCall: () -> Unit,
    delayMs: Long,
    content: @Composable () -> Unit
) {

    if (payment.isCreated() || payment.isPending()) {
        LaunchedEffect(Unit) {
            while (isActive) {
                delay(delayMs)
                pollingCall()
            }
        }
    }

    when (payment.status) {
        PaymentStatus.PENDING -> PaymentPending()
        PaymentStatus.FAILED -> PaymentFailed()
        PaymentStatus.EXPIRED -> PaymentExpired()
        PaymentStatus.COMMITTED -> PaymentSuccess()
        else -> content()
    }
}