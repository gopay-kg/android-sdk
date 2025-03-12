package kg.gopay.sdk.repository

import kg.gopay.sdk.model.ApiException
import kg.gopay.sdk.model.BaseResponse
import kg.gopay.sdk.model.ResponseCode
import kg.gopay.sdk.model.ResponseStatus

open class BaseRepository {
    protected suspend fun <T : BaseResponse> handleResponse(
        call: suspend () -> T
    ): T {
        val response = call()

        if (response.status !== ResponseStatus.OK) {
            throw ApiException(
                code = ResponseCode.fromString(response.code),
                repository = this.javaClass.simpleName,
                message = response.errorMessage
            )
        }

        return response
    }
}