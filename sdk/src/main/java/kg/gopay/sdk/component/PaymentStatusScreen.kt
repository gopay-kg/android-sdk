package kg.gopay.sdk.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kg.gopay.sdk.R

@Composable
fun PaymentStatusScreen(message: String, color: Color, icon: ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp),
    ) {
        Icon(
            icon,
            contentDescription = message,
            tint = color,
            modifier = Modifier
                .padding(stringResource(R.string.payment_status_icon_padding_bottom).toFloat().dp)
                .width(stringResource(R.string.payment_status_icon_width).toFloat().dp)
                .height(stringResource(R.string.payment_status_icon_height).toFloat().dp)
                .border(
                    width = stringResource(R.string.payment_status_icon_border_width).toFloat().dp,
                    shape = CircleShape,
                    color = color
                )
                .padding(stringResource(R.string.payment_status_icon_inner_padding).toFloat().dp)
        )
        Text(
            text = message,
            style = TextStyle(
                fontSize = stringResource(R.string.payment_status_font_size).toFloat().sp
            )
        )
    }
}