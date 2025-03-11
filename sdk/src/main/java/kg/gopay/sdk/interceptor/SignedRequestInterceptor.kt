package kg.gopay.sdk.interceptor

import kg.gopay.sdk.helper.RequestHelper
import kg.gopay.sdk.helper.SignatureHelper
import okhttp3.Interceptor
import okhttp3.Response

class SignedRequestInterceptor(
    private val apiKey: String,
    private val apiSecret: String,
    private val host: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val jsonBody = RequestHelper.extractRequestBodySafely(originalRequest)
        val dataMap = RequestHelper.jsonToMap(jsonBody)
        val nonce = SignatureHelper.generateNonce()
        // Generate signature
        val signature = SignatureHelper.generateSignature(nonce, apiSecret, dataMap)

        // Build new request with headers
        val builder = originalRequest.newBuilder()
            .addHeader("GoPay-Nonce", nonce)
            .addHeader("GoPay-Signature", signature)
            .addHeader("GoPay-Api-Key", apiKey)
            .addHeader("Content-Type", "application/json")

        if (host.isNotBlank()) {
            builder.addHeader("Host", host)
        }

        return chain.proceed(builder.build())
    }
}