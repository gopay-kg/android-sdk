package kg.gopay.sdk.repository

import kg.gopay.sdk.model.ApiException
import kg.gopay.sdk.model.BaseResponse
import kg.gopay.sdk.model.ResponseCode
import kg.gopay.sdk.model.ResponseStatus
import retrofit2.HttpException
import java.net.SocketTimeoutException


open class BaseRepository {
    protected suspend fun <T : BaseResponse> handleResponse(
        call: suspend () -> T
    ): T {
        try {
            val response = call()

            if (response.status !== ResponseStatus.OK) {
                throw ApiException(
                    code = ResponseCode.fromString(response.code),
                    repository = this.javaClass.simpleName,
                    message = response.errorMessage
                )
            }

            return response
        } catch (e: HttpException) {
            throw ApiException(
                code = ResponseCode.UNKNOWN_ERROR,
                repository = this.javaClass.simpleName,
                message = e.message(),
                httpException = e
            )
        } catch (e: SocketTimeoutException) {
            throw ApiException(
                code = ResponseCode.UNKNOWN_ERROR,
                repository = this.javaClass.simpleName,
                message = "API Server Unavailable"
            )
        }
    }
}