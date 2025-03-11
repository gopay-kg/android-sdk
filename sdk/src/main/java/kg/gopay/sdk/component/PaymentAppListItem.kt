package kg.gopay.sdk.component

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kg.gopay.sdk.R
import kg.gopay.sdk.model.PaymentListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun PaymentAppListItem(
    app: PaymentListItem,
    rowModifier: Modifier = Modifier,
    logoModifier: Modifier = Modifier,
    spacerModifier: Modifier = Modifier,
    nameModifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val click: () -> Unit = {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(app.url)
            setPackage(app.packageName)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context,
                context.getString(R.string.payment_app_not_installed_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = click)
            .then(rowModifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RemoteImage(
            url = "https://media.gopay.kg/${app.icon}",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .then(logoModifier)
        )

        Spacer(
            modifier = Modifier
                .width(16.dp)
                .then(spacerModifier)
        )

        Text(
            text = app.name,
            modifier = Modifier
                .padding(start = 8.dp)
                .then(nameModifier)
        )
    }
}

@Composable
fun RemoteImage(url: String, modifier: Modifier = Modifier) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(url) {
        bitmap = withContext(Dispatchers.IO) {
            loadImageFromUrl(url)
        }
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            modifier = modifier
        )
    }
}

private fun loadImageFromUrl(urlString: String): Bitmap? {
    return try {
        val url = URL(urlString)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val inputStream = connection.inputStream
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}