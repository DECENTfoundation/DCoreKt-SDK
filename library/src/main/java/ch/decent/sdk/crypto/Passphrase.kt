package ch.decent.sdk.crypto

import ch.decent.sdk.crypto.wordlist.WordList
import ch.decent.sdk.crypto.wordlist.WordListProvider
import ch.decent.sdk.utils.generateEntropy

class Passphrase(private val words: Array<String>) {

  val size
    get() = words.size

  override fun toString() = words.joinToString(separator = " ")

  companion object {
    private const val WORD_COUNT: Int = 16

    /**
     * Method generates pass phrase from word list provided by [WordListProvider] for language with id provided by parameter [languageId].
     * If parameter [normalize] is true, all provided word will be converted to upper case before entropy creation. In other case word stays
     * as it was provided.
     *
     * @param wordListProvider word list provider
     * @param languageId language id
     * @param count word count, default 16
     * @param normalize normalization flag, default true
     */
    @JvmOverloads
    fun generate(wordListProvider: WordListProvider, languageId: Int, count: Int = WORD_COUNT, normalize: Boolean = true) =
        create(generateEntropy(), wordListProvider.get(languageId), count, normalize)

    private fun create(entropy: ByteArray, seed: WordList?, count: Int, normalize: Boolean): Passphrase {
      requireNotNull(seed) { "Word list provider must not be null" }
      val phrases = mutableListOf<String>()
      for (i in 0 until (count * 2) step 2) {

        // randomBuffer has 256 bits / 16 bits per word == 16 words
        val num = (entropy[i].toInt() shl 8) + entropy[i + 1]

        // convert into a int between 0 and 1 (inclusive)
        val multiplier = num / Math.pow(2.0, 16.0)
        val index = Math.abs(Math.round(seed.words.size * multiplier))

        phrases.add(seed.words[index.toInt()].let { if (normalize) it.toUpperCase() else it })
      }
      return Passphrase(phrases.toTypedArray())
    }
  }
}
