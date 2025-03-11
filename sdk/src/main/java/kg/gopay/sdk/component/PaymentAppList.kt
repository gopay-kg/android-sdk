package kg.gopay.sdk.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kg.gopay.sdk.GoPaySDK
import kg.gopay.sdk.model.ApiException
import kg.gopay.sdk.model.Payment
import kg.gopay.sdk.model.PaymentListItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentAppList(isOpened: Boolean, close: () -> Unit, orderId: String) {
    if (isOpened) {
        val apps = remember { mutableStateOf<List<PaymentListItem>>(emptyList()) }
        val payment = remember { mutableStateOf<Payment?>(null) }
        val filteredApps = remember { mutableStateOf<List<PaymentListItem>>(emptyList()) }
        val error = remember { mutableStateOf<ApiException?>(null) }
        val isLoading = remember { mutableStateOf(true) }

        val coroutineScope = rememberCoroutineScope()

        fun load() {
            isLoading.value = true
            error.value = null
            coroutineScope.launch {
                try {
                    payment.value = GoPaySDK.getPayment(orderId = orderId)
                    apps.value = GoPaySDK.getPaymentAppItems(payment.value)
                    filteredApps.value = apps.value
                    isLoading.value = false
                } catch (e: ApiException) {
                    isLoading.value = false
                    error.value = e
                }
            }
        }

        ModalBottomSheet(onDismissRequest = close) {
            LaunchedEffect(Unit) {
                println("Load from render")
                load()
            }

            if (isLoading.value && payment.value == null) {
                PaymentLoading()
            } else {
                if (error.value != null) {
                    ErrorDisplay(
                        error.value!!,
                        repeat = { load() },
                        close = {
                            error.value = null
                            close()
                        }
                    )
                } else {
                    PaymentStatusPlacer(
                        payment.value!!,
                        pollingCall = { load() },
                        5000
                    ) {
                        PaymentAppListSearch(
                            apps = apps.value,
                            onValueChange = { searchedApps ->
                                filteredApps.value = searchedApps
                            }
                        )
                        LazyColumn {
                            items(filteredApps.value) { app -> PaymentAppListItem(app) }
                        }
                    }
                }
            }
        }
    }
}