package kg.gopay.sdk.model

import retrofit2.HttpException

enum class ResponseStatus(val value: String) {
    OK("OK"),
    FAIL("FAIL")
}

enum class ResponseCode(val value: String) {
    SUCCESS("0000"),
    INVALID_REQUEST("0001"),
    NOT_FOUND("0002"),
    UNAUTHORIZED("0003"),
    RESOURCE_EXHAUSTED("0004"),
    MISSING_HEADER("0005"),
    METHOD_NOT_ALLOWED("0006"),
    MEDIA_TYPE_NOT_SUPPORTED("0007"),
    INVALID_TIMESTAMP("0008"),
    INVALID_API_KEY("0009"),
    INVALID_SIGNATURE("0010"),
    INVALID_ORDER_ID("0011"),
    INVALID_PARAMETER("0012"),
    TRANSITION_NOT_ALLOWED("0013"),
    RESTRICTED_IP_ADDRESS("0014"),
    BANK_API_FAILURE("0015"),
    UNKNOWN_ERROR("9999");

    companion object {
        fun fromString(code: String): ResponseCode {
            return entries.find { it.value == code }
                ?: UNKNOWN_ERROR // Default to ERROR if the code is unknown
        }
    }
}

interface BaseResponse {
    val code: String
    val status: ResponseStatus
    val errorMessage: String
}

class ApiException(
    val code: ResponseCode,
    val repository: String,
    val httpException: HttpException? = null,
    message: String
) : Exception(message)