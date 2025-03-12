# Android GoPay SDK

Библиотека для интеграции с платежной системой GoPay в Android-приложениях.
Оно предоставляет удобные методы для работы с платежами,
позволяя разработчикам легко обрабатывать транзакции,
получать информацию о платежах и использовать различные методы оплаты.

## Установка

Для подключения GoPaySDK используйте JitPack. Добавьте репозиторий JitPack `maven("https://jitpack.io")` в `settings.gradle.kts`:
```kotlin
// ./settings.gradle.kts

pluginManagement {
    repositories {
        // ...
        maven("https://jitpack.io") // ✔️
        // ...
    }
}
```
Затем добавьте зависимость в `build.gradle.kts` на уровне приложения:
```kotlin
// ./app/build.gradle.kts

dependencies {
    // ...
    implementation("com.github.gopay-kg:android-sdk:v0.0.2") // ✔️
    // ...
}
```

## Использование
### Инициализация SDK

Перед началом работы необходимо инициализировать SDK с вашими API-ключами. Это позволяет системе авторизовать запросы и взаимодействовать с API GoPay:
```kotlin
import kg.gopay.sdk.GoPaySDK

GoPaySDK.initialize(
    apiKey = "your_api_key",
    apiSecretKey = "your_api_secret_key"
)
```
После инициализации SDK можно использовать его методы для работы с [Go Pay API](https://doc.gopay.kg/v1/redoc-ui/).

### Создание платежа

Для того что бы создать новый платеж в GoPaySDK реализован метод `createPayment`.
```kotlin
    runBlocking {
        val payment = GoPaySDK.createPayment(orderId = "1234567890", amount = 100)    
    }
```
Описание допустимых и обязательных полей вы сможете найти в [Go Pay API - Создать платеж](https://doc.gopay.kg/v1/redoc-ui/#tag/Platezhi/operation/root_create)

### Информация о платеже
Для получения информации о ранее созданном платеже используйте метод `getPayment`.
```kotlin
    runBlocking {
        val payment = GoPaySDK.getPayment(orderId = "1234567890")    
    }
```
Описание допустимых и обязательных полей вы сможете найти в [Go Pay API - Запрос платеж](https://doc.gopay.kg/v1/redoc-ui/#tag/Platezhi/operation/query_create)

### Обработка Ошибок

В случае возникновения ошибки на уроне API будет выброшена ошибка `ApiException`.
`code`: Код типа ошибки
`message`: Текст ошибки

Коды ошибок описаны [Go Pay API - Успешный Платеж - Response](https://doc.gopay.kg/v1/redoc-ui/#tag/Platezhi/operation/root_create)

Так же необходимо обработать ошибки типов:

- `HttpException` - в случае HTTP Response Code > 200

- `SocketTimeoutException` - в случае отсутствия связи с сервером API

## Готовый компонент PaymentAppList
Мы рекомендуем использовать готовый компонент для отображения списка платежных систем.

### Пример использования готового компонент
В данном примере предполагается что платеж был создан ранее (с использованием GoPaySDK или через бэкенд сервер на вашей стороне).
```kotlin
package com.example.your_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kg.gopay.sdk.GoPaySDK
import kg.gopay.sdk.component.DisclosureState
import kg.gopay.sdk.component.PaymentAppList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        GoPaySDK.initialize(
            apiKey = "YOUR_API_KEY",
            apiSecretKey = "YOUR_SECRET_KEY",
        )
        setContent {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DisclosureState { isOpened, open, close ->
                    PayButton("Оплатить", open)
                    PaymentAppList(
                        orderId = "3c41a13cfdca456c87243614e3040104",
                        isOpened = isOpened,
                        close = close
                    )
                }
            }
        }
    }
}

@Composable
fun PayButton(text: String, open: () -> Unit) {
    Button(onClick = open) {
        Text(text)
    }
}
```
В данном примере у вас должна появиться кнопка `Оплатить`. По нажатию на которую откроется всплывающий снизу список с платежными системами.
После открытия списка компонент автоматически запустит цикл с интервалом в 5 секунд для проверки состояния платежа.
Когда пользователь перейдет из списка в одно из приложений и сделает оплату, список приложений сменится на отображение статуса платежа.

### Настройки отображения PaymentAppList
При желании вы можете переопределить часть настроек компонента, которые отвечают за отображения стилей и текста.
Просто переопределите строки в ресурсах вашего приложения:
```xml
<resources>
    ...
    <string name="payment_loading_message">Загрузка информации</string>
    ...
</resources>
```

Доступные для определения параметры вы можете найти в [нашем репозитории](https://github.com/gopay-kg/android-sdk/tree/main/sdk/src/main/res/values) 


