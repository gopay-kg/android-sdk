package kg.gopay.sdk.repository

import kg.gopay.sdk.api.RetrofitClient
import kg.gopay.sdk.model.PaymentAppPlatform
import kg.gopay.sdk.model.PaymentAppRequest
import kg.gopay.sdk.model.PaymentAppResponse

class PaymentAppRepository(private val client: RetrofitClient) {
    suspend fun getPaymentApps(platform: PaymentAppPlatform = PaymentAppPlatform.ANY): PaymentAppResponse {
        return client.apiService.getPaymentApps(PaymentAppRequest(platform = platform))
    }
}