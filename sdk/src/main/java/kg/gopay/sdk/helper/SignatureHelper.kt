package kg.gopay.sdk.helper

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.nio.charset.StandardCharsets
import org.json.JSONObject

object SignatureHelper {
    // Generate SHA-512 HMAC signature
    fun generateSignature(nonce: String, secret: String, data: Map<String, Any>): String {
        val dataStr = JSONObject(data).toString()  // Convert JSON to string
        val payload = "$nonce\n$dataStr\n"  // Concatenate values with line breaks

        val mac = Mac.getInstance("HmacSHA512")
        val keySpec = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA512")
        mac.init(keySpec)
        val hash = mac.doFinal(payload.toByteArray(StandardCharsets.UTF_8))

        return hash.joinToString("") { "%02X".format(it) }  // Convert to uppercase hex
    }

    // Generate a random nonce (max 32 characters)
    fun generateNonce(): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..32)
            .map { allowedChars.random() }
            .joinToString("")
    }
}