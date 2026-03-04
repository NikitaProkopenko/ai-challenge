# Claude API Client

Консольный клиент для взаимодействия с Claude API, написанный на Kotlin. Отправляет текстовый запрос к модели Claude и выводит ответ в терминал.

## Структура проекта

```
claude-api-client/
├── build.gradle.kts              # Конфигурация сборки
├── settings.gradle.kts           # Настройки Gradle-проекта
├── src/
│   └── main/
│       └── kotlin/
│           └── Main.kt           # Точка входа и логика обращения к API
└── README.md
```

### `build.gradle.kts`

Файл сборки проекта на Gradle (Kotlin DSL). Определяет:

- **Плагин `kotlin("jvm")`** — компиляция Kotlin под JVM (версия 1.9.22).
- **Плагин `application`** — позволяет запускать проект командой `gradle run`. Точка входа — класс `MainKt`.
- **Зависимости:**
  - `okhttp:4.12.0` — HTTP-клиент для отправки запросов к API.
  - `gson:2.11.0` — библиотека для сериализации/десериализации JSON.
- **JVM toolchain** — проект компилируется и запускается под Java 21.

### `settings.gradle.kts`

Настройки Gradle-проекта:

- Задаёт имя проекта — `claude-api-client`.
- Указывает репозитории для поиска плагинов: Maven Central и Gradle Plugin Portal.

### `src/main/kotlin/Main.kt`

Основной файл приложения. Содержит две функции:

- **`main()`** — точка входа. Читает API-ключ из переменной окружения `ANTHROPIC_API_KEY`, проверяет его наличие, отправляет предустановленный запрос (*«Расскажи коротко, что такое Kotlin и почему он популярен»*) к Claude и печатает ответ.
- **`sendMessage(apiKey, userMessage)`** — формирует HTTP POST-запрос к `https://api.anthropic.com/v1/messages` с помощью OkHttp. Тело запроса — JSON с указанием модели (`claude-sonnet-4-20250514`), лимита токенов (1024) и сообщения пользователя. Ответ парсится через Gson: из массива `content` извлекается текст и возвращается как строка.

## Как развернуть и запустить

### Требования

- **Java 21** или новее
- **Gradle 8+** (или используйте Gradle Wrapper, если он добавлен в проект)
- **API-ключ Anthropic** — получите на [console.anthropic.com](https://console.anthropic.com/)

### Пошаговая инструкция

**1. Клонируйте репозиторий**

```bash
git clone https://github.com/NikitaProkopenko/ai-challenge.git
cd ai-challenge
```

**2. Убедитесь, что Java 21 установлена**

```bash
java -version
```

Если Java не установлена, установите её:

```bash
# Ubuntu / Debian
sudo apt update && sudo apt install openjdk-21-jdk

# macOS (через Homebrew)
brew install openjdk@21
```

**3. Установите Gradle (если ещё не установлен)**

```bash
# Ubuntu / Debian
sudo apt install gradle

# macOS (через Homebrew)
brew install gradle
```

**4. Задайте API-ключ Anthropic**

```bash
export ANTHROPIC_API_KEY="sk-ant-ваш-ключ"
```

**5. Запустите проект**

```bash
gradle run
```

### Ожидаемый результат

```
Отправляю запрос к Claude API...

Ответ от Claude:
Kotlin — это современный язык программирования, разработанный компанией JetBrains...
```

### Как изменить запрос

Откройте файл `src/main/kotlin/Main.kt` и измените строку:

```kotlin
val prompt = "Расскажи коротко, что такое Kotlin и почему он популярен."
```

на любой другой текст, затем снова выполните `gradle run`.
