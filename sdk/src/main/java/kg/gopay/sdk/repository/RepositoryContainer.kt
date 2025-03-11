package kg.gopay.sdk.repository

import kg.gopay.sdk.api.RetrofitClient

class RepositoryContainer(client: RetrofitClient) {
    val paymentRepository: PaymentRepository = PaymentRepository(client)
    val paymentAppRepository: PaymentAppRepository = PaymentAppRepository(client)
}