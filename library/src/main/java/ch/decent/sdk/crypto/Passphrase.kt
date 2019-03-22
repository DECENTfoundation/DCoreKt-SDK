package ch.decent.sdk.crypto

import ch.decent.sdk.mnemonic.wordlists.WordList
import ch.decent.sdk.mnemonic.wordlists.WordListProvider
import ch.decent.sdk.utils.generateEntropy

const val WORD_COUNT: Int = 16

class Passphrase(private val words: Array<String>) {

  fun size() = words.size

  override fun toString() = words.joinToString(separator = " ")

  public companion object {

    public fun generate(count: Int = WORD_COUNT, wordListProvider: WordListProvider, languageId: Int) =
        create(generateEntropy(), wordListProvider.get(languageId), count)

    private fun create(entropy: ByteArray, seed: WordList?, count: Int): Passphrase {
      requireNotNull(seed) { "Word list provider must not be null" }
      val phrases = mutableListOf<String>()
      for (i in 0 until (count * 2) step 2) {

        // randomBuffer has 256 bits / 16 bits per word == 16 words
        val num = (entropy[i].toInt() shl 8) + entropy[i + 1]

        // convert into a int between 0 and 1 (inclusive)
        val multiplier = num / Math.pow(2.0, 16.0)
        val index = Math.abs(Math.round(seed.words.size * multiplier))

        phrases.add(seed.words[index.toInt()])
      }
      return Passphrase(phrases.toTypedArray())
    }
  }
}

