package kg.gopay.sdk.helper

import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import org.json.JSONObject

object RequestHelper {
    // Convert JSON string to Map for signing
    fun jsonToMap(jsonString: String): Map<String, Any> {
        val jsonObject = JSONObject(jsonString)
        val resultMap = mutableMapOf<String, Any>()

        for (key in jsonObject.keys()) {
            resultMap[key] = jsonObject.get(key) // Extract values manually
        }

        return resultMap
    }

    // Extract Request JSON body safely (OkHttp 3.x allows access)
    fun extractRequestBodySafely(request: Request): String {
        if (request.body == null) return "{}" // No body, return empty JSON

        return try {
            val buffer = Buffer()
            request.body!!.writeTo(buffer) // Write body into buffer
            buffer.readUtf8() // Read it as a string
        } catch (e: Exception) {
            "{}" // Return empty JSON on failure
        }
    }

    // Extract Response JSON body safely (OkHttp 3.x allows access)
    fun extractResponseBodySafely(response: Response): String {
        return try {
            response.body?.string() ?: "{}" // Safely read the response body
        } catch (e: Exception) {
            "{}" // Return empty JSON on failure
        }
    }
}