# lagom-chat
Реализация чата используя Kotlin, Lagom и Akka

## Запуск в dev-mode
```shall
./mvnw lagom:runAll -Dplay.server.http.idleTimeout=1h -ea
```

## Возможные проблемы и их решения

1. Socket соединение по умолчание без взаимодействия живет ~90c. Чтобы увеличть время нужно задать необходимое значение используя `-Dplay.server.http.idleTimeout` 

2. В проекте используются корутины. Если использовать функцию `withContext` для смены диспатчера и при этом ее будет вызывать функция, которая является `tail-call function` , то при возникновении исключения
stack-trace будет неполным. Для исправления этой проблемы есть workaround - https://github.com/Kotlin/kotlinx.coroutines/issues/1660
Именно поэтому приложение нужно запускать в debug-mode (даже на проде) и использовать описанное решение.
Пример:

```kotlin
suspend fun changeContextAndThrow(): String {
    logger.info("Call function changeContextAndThrow")
    return withContext(Dispatchers.IO) {
        throw IllegalArgumentException("Throw from changeContextAndThrow")
    }
}
suspend fun tailCallFunction(): String {
    logger.info("Call function tailCallFunction")
    return changeContextAndThrow().also { } // Empty 'also' call (workaround)
}
```
