package kg.gopay.sdk.model

import com.google.gson.annotations.SerializedName

enum class PaymentStatus(val value: String) {
    CREATED("CREATED"),
    PENDING("PENDING"),
    FAILED("FAILED"),
    COMMITTED("COMMITTED"),
    EXPIRED("EXPIRED")
}


data class Payment(
    val paymentId: String,
    val orderId: String,
    val amount: String,
    val status: PaymentStatus,
    val description: String,
    val appLinks: Map<String, String>,
    val checkoutUrl: String,
    val callbackUrl: String?,
    val successUrl: String?,
    val failureUrl: String?,
    val qrUrl: String,
    val createdAt: String,
    val expiresAt: String,
    val committedAt: String?
) {
    fun isCreated(): Boolean {
        return this.status === PaymentStatus.CREATED
    }

    fun isPending(): Boolean {
        return this.status === PaymentStatus.PENDING
    }

    fun isFailed(): Boolean {
        return this.status === PaymentStatus.FAILED
    }

    fun isCommitted(): Boolean {
        return this.status === PaymentStatus.COMMITTED
    }

    fun isExpired(): Boolean {
        return this.status === PaymentStatus.EXPIRED
    }

    fun isProcessed(): Boolean {
        return this.status === PaymentStatus.CREATED ||
                this.status === PaymentStatus.PENDING
    }

    fun isFinished(): Boolean {
        return this.status === PaymentStatus.COMMITTED ||
                this.status === PaymentStatus.EXPIRED ||
                this.status === PaymentStatus.FAILED
    }
}

data class CreatePaymentRequest(
    @SerializedName("order_id") val orderId: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("description") val description: String? = null,
    @SerializedName("testing_mode") val testingMode: Boolean? = false,
    @SerializedName("lifetime") val lifetime: Int? = 3600,
    @SerializedName("callback_url") val callbackUrl: String? = null,
    @SerializedName("success_url") val successUrl: String? = null,
    @SerializedName("failure_url") val failureUrl: String? = null,
)

data class GetPaymentRequest(
    @SerializedName("payment_id") val paymentId: String? = null,
    @SerializedName("order_id") val orderId: String? = null
)

class PaymentResponse(
    @SerializedName("code") override val code: String,
    @SerializedName("status") override val status: ResponseStatus,
    @SerializedName("error_message") override val errorMessage: String,
    @SerializedName("data") val data: Payment
) : BaseResponse {
    fun getResponseCode(): ResponseCode {
        return ResponseCode.fromString(code)
    }
}
