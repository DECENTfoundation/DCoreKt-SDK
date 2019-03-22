package ch.decent.sdk.mnemonic.android

import android.content.Context
import ch.decent.sdk.mnemonic.wordlists.WordList
import ch.decent.sdk.mnemonic.wordlists.WordListProvider
import ch.decent.sdk.mnemonic_android.R

class AndroidWordListProvider(private val context: Context) : WordListProvider {
  companion object {
    val supportedWordList = mapOf(
        R.id.english to R.raw.english
    )
  }

  private val cache = mutableMapOf<Int, WordList?>()

  override fun all() = supportedWordList.mapNotNull { (languageId, _) -> get(languageId) }.toCollection(mutableListOf())

  override fun get(id: Int) = cache.getOrElse(id) { readWordlistFromResource(id)?.also { cache[id] = it } }

  private fun readWordlistFromResource(id: Int): WordList? = readWordsFromResource(id)?.let { words ->
    when (id) {
      R.id.english -> WordList(" ", words)
      else -> null
    }
  }

  private fun readWordsFromResource(id: Int): List<String>? = supportedWordList[id]?.let { resource ->
    context.resources.openRawResource(resource).bufferedReader().use { it.readLines() }.filter { it.isNotBlank() }
  }
}