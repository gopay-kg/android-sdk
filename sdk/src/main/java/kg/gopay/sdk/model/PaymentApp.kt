package kg.gopay.sdk.model

import com.google.gson.annotations.SerializedName

data class PaymentApp(
    val code: String,
    val name: String,
    val order: Int,
    val icon: String,
    val url: String,
    val androidPackageName: String,
    val isActiveOnIOS: Boolean
)

data class PaymentListItem(
    val code: String,
    val name: String,
    val icon: String,
    val packageName: String,
    val url: String,
    val order: Int
)

enum class PaymentAppPlatform(val value: String) {
    ANDROID("Android"),
    IOS("iOS"),
    ANY("any")
}

data class PaymentAppRequest(val platform: PaymentAppPlatform)

class PaymentAppResponse(
    @SerializedName("code") override val code: String,
    @SerializedName("status") override val status: ResponseStatus,
    @SerializedName("errorMessage") override val errorMessage: String,
    @SerializedName("data") val data: List<PaymentApp>
) : BaseResponse {
    fun getResponseCode(): ResponseCode {
        return ResponseCode.fromString(code)
    }
}