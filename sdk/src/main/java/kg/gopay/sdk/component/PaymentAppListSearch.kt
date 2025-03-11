package kg.gopay.sdk.component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kg.gopay.sdk.R

import kg.gopay.sdk.model.PaymentListItem

@Composable
fun PaymentAppListSearch(
    modifier: Modifier = Modifier,
    defaultValue: String = "",
    apps: List<PaymentListItem>,
    onValueChange: (List<PaymentListItem>) -> Unit,
) {
    val search = remember { mutableStateOf(defaultValue) }
    val handleChange: (String) -> Unit = { newText ->
        search.value = newText
        onValueChange(
            apps.filter {
                it.name.contains(newText) || it.code.contains(newText)
            }
        )
    }
    val completedModifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
        .then(modifier)

    OutlinedTextField(
        value = search.value,
        onValueChange = handleChange,
        modifier = completedModifier,
        label = { Text(stringResource(R.string.payment_app_list_search_label)) }
    )
}