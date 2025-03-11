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
import kg.gopay.sdk.model.ApiException
import kg.gopay.sdk.model.ResponseCode

@Composable
fun ErrorDisplay(error: ApiException, repeat: () -> Unit, close: () -> Unit) {
    var message = ""
    if (error.httpException !== null) {
        message = stringResource(
            when (error.httpException.code()) {
                404 -> R.string.http_error_404
                500 -> R.string.http_error_500
                else -> R.string.http_error_unknown
            }
        )
    } else {
        message = stringResource(
            when (error.code) {
                ResponseCode.NOT_FOUND -> R.string.api_error_not_found
                else -> R.string.api_error_unknown
            }
        )
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
