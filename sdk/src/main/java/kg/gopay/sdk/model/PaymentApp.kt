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
    @SerializedName("Android")
    ANDROID("Android"),

    @SerializedName("iOs")
    IOS("iOS"),

    @SerializedName("any")
    ANY("any")
}

data class PaymentAppRequest(
    @SerializedName("platform") val platform: PaymentAppPlatform
)

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