package kg.gopay.sdk

import kg.gopay.sdk.api.RetrofitClient
import kg.gopay.sdk.helper.HelperContainer
import kg.gopay.sdk.model.CreatePaymentRequest
import kg.gopay.sdk.model.Payment
import kg.gopay.sdk.model.PaymentApp
import kg.gopay.sdk.model.PaymentListItem
import kg.gopay.sdk.repository.RepositoryContainer

object GoPaySDK {
    private lateinit var client: RetrofitClient
    private lateinit var repository: RepositoryContainer
    private lateinit var helper: HelperContainer
    private var apiKey: String? = null
    private var apiSecretKey: String? = null
    private const val apiUrl = "https://api.gopay.kg/"
    private const val apiHost = "api.gopay.kg"

    fun initialize(
        apiKey: String,
        apiSecretKey: String,
        apiUrl: String = this.apiUrl,
        apiHost: String = this.apiHost
    ) {
        this.apiKey = apiKey
        this.apiSecretKey = apiSecretKey
        this.client = RetrofitClient(
            apiKey = apiKey,
            apiSecretKey = apiSecretKey,
            apiUrl = apiUrl,
            apiHost = apiHost
        )

        this.repository = RepositoryContainer(this.client)
        this.helper = HelperContainer()
    }

    fun getHelper(): HelperContainer {
        return this.helper
    }

    fun getRepository(): RepositoryContainer {
        return this.repository
    }

    suspend fun getPayment(paymentId: String? = null, orderId: String? = null): Payment {
        return this.getRepository().paymentRepository.getPayment(paymentId, orderId).data
    }

    suspend fun createPayment(
        orderId: String,
        amount: String,
        description: String? = null,
        testingMode: Boolean? = false,
        lifetime: Int? = 3600,
        callbackUrl: String? = null,
        successUrl: String? = null,
        failureUrl: String? = null,
    ): Payment {
        return this.getRepository().paymentRepository.createPayment(
            CreatePaymentRequest(
                orderId,
                amount,
                description,
                testingMode,
                lifetime,
                callbackUrl,
                successUrl,
                failureUrl
            )
        ).data
    }

    suspend fun getPaymentApps(): List<PaymentApp> {
        return this.getRepository().paymentAppRepository.getPaymentApps().data
    }

    suspend fun getPaymentAppItems(
        payment: Payment? = null,
        orderId: String? = null,
        apps: List<PaymentApp>? = null
    ): List<PaymentListItem> {
        var actualPayment = payment
        var actualApps = apps

        require(orderId != null || payment != null) { "Either order or Payment must be provided." }

        if (actualPayment == null) {
            actualPayment = this.getPayment(orderId = orderId)
        }

        if (actualApps == null) {
            actualApps = this.getPaymentApps()
        }

        return actualApps.map { app ->
            PaymentListItem(
                app.code,
                app.name,
                app.icon,
                app.androidPackageName,
                actualPayment.appLinks[app.code] ?: "",
                app.order
            )
        }
    }
}
