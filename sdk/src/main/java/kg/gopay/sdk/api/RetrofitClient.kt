package kg.gopay.sdk.api

import kg.gopay.sdk.interceptor.SignedRequestInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(
    private val apiKey: String,
    private val apiSecretKey: String,
    private val apiUrl: String,
    private val apiHost: String
) {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(SignedRequestInterceptor(apiKey, apiSecretKey, apiHost))
        .build()

    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}