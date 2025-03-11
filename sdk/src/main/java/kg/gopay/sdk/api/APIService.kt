package kg.gopay.sdk.api

import retrofit2.http.Body
import retrofit2.http.POST
import kg.gopay.sdk.model.CreatePaymentRequest
import kg.gopay.sdk.model.GetPaymentRequest
import kg.gopay.sdk.model.PaymentAppRequest
import kg.gopay.sdk.model.PaymentAppResponse
import kg.gopay.sdk.model.PaymentResponse


interface ApiService {
    @POST("payment-app")
    suspend fun getPaymentApps(@Body request: PaymentAppRequest): PaymentAppResponse

    @POST("payments")
    suspend fun createPayment(@Body request: CreatePaymentRequest): PaymentResponse

    @POST("payments/query")
    suspend fun getPayment(@Body request: GetPaymentRequest): PaymentResponse
}