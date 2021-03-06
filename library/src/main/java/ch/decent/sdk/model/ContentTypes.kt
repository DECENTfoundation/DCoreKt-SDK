package ch.decent.sdk.model

/**
 * create content type string
 */
fun contentType(app: ApplicationType = ApplicationType.DECENT_CORE, category: CategoryType = CategoryType.NONE): String =
    "${app.ordinal}.${category.ordinal}.0"

enum class ApplicationType {
  DECENT_CORE,
  DECENT_GO,
  ALAX
}

enum class CategoryType {
  NONE,
  MUSIC,
  MOVIE,
  BOOK,
  AUDIO_BOOK,
  SOFTWARE,
  GAME,
  PICTURE,
  DOCUMENT
}
