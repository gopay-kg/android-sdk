package kg.gopay.sdk.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kg.gopay.sdk.R

@Composable
fun PaymentLoading() {
    Text(
        text = stringResource(R.string.payment_loading_message),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 100.dp),
        style = TextStyle(
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    )
}