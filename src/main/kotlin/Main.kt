import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

fun main() {
    val apiKey = System.getenv("ANTHROPIC_API_KEY")
    if (apiKey.isNullOrBlank()) {
        println("Ошибка: установите переменную окружения ANTHROPIC_API_KEY")
        println("Пример: export ANTHROPIC_API_KEY=\"sk-ant-...\"")
        return
    }

    val prompt = "Расскажи коротко, что такое Kotlin и почему он популярен."

    println("Отправляю запрос к Claude API...")
    println()

    val response = sendMessage(apiKey, prompt)
    println("Ответ от Claude:")
    println(response)
}

fun sendMessage(apiKey: String, userMessage: String): String {
    val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    val gson = Gson()

    val requestBody = JsonObject().apply {
        addProperty("model", "claude-sonnet-4-20250514")
        addProperty("max_tokens", 1024)
        add("messages", gson.toJsonTree(
            listOf(mapOf("role" to "user", "content" to userMessage))
        ))
    }

    val request = Request.Builder()
        .url("https://api.anthropic.com/v1/messages")
        .addHeader("x-api-key", apiKey)
        .addHeader("anthropic-version", "2023-06-01")
        .addHeader("content-type", "application/json")
        .post(requestBody.toString().toRequestBody("application/json".toMediaType()))
        .build()

    client.newCall(request).execute().use { response ->
        val body = response.body?.string() ?: return "Пустой ответ"

        if (!response.isSuccessful) {
            return "Ошибка API (${response.code}): $body"
        }

        val json = gson.fromJson(body, JsonObject::class.java)
        val content = json.getAsJsonArray("content")
        return content
            ?.map { it.asJsonObject.get("text").asString }
            ?.joinToString("\n")
            ?: "Не удалось извлечь текст из ответа"
    }
}
