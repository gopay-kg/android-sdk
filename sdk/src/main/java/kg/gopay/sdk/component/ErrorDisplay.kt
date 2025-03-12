package kg.gopay.sdk.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kg.gopay.sdk.R
import retrofit2.HttpException
import java.net.SocketTimeoutException

@Composable
fun ErrorDisplay(error: Exception, repeat: () -> Unit, close: () -> Unit) {
    val message = if (error is HttpException) {
        stringResource(
            when (error.code()) {
                404 -> R.string.http_error_404
                500 -> R.string.http_error_500
                else -> R.string.http_error_unknown
            }
        )
    } else {
        if (error is SocketTimeoutException) {
            stringResource(R.string.api_error_unreachable)
        } else {
            error.message!!
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = message,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
            style = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center)
        )
        Row {
            Button(
                modifier = Modifier.padding(horizontal = 5.dp),
                onClick = repeat
            ) {
                Text(stringResource(R.string.payment_error_retry))
            }
            Button(
                modifier = Modifier.padding(horizontal = 5.dp),
                onClick = close
            ) {
                Text(stringResource(R.string.payment_error_cancel))
            }
        }
    }
}
