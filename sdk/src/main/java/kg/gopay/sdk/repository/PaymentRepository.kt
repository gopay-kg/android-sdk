package kg.gopay.sdk.repository

import kg.gopay.sdk.api.RetrofitClient
import kg.gopay.sdk.model.CreatePaymentRequest
import kg.gopay.sdk.model.GetPaymentRequest
import kg.gopay.sdk.model.PaymentResponse

class PaymentRepository(private val client: RetrofitClient) : BaseRepository() {
    suspend fun getPayment(
        paymentId: String? = null,
        orderId: String? = null
    ): PaymentResponse {
        require(paymentId != null || orderId != null) { "Either paymentId or orderId must be provided." }
        require(paymentId == null || orderId == null) { "Only one of paymentId or orderId can be provided." }

        return handleResponse(
            call = {
                client.apiService.getPayment(
                    GetPaymentRequest(
                        paymentId = paymentId,
                        orderId = orderId
                    )
                )
            }
        )
    }

    suspend fun createPayment(request: CreatePaymentRequest): PaymentResponse {
        return client.apiService.createPayment(request)
    }
}