package ch.decent.sdk.crypto

import ch.decent.sdk.crypto.dictonaries.SeedDictionary
import ch.decent.sdk.utils.SIZE_32
import ch.decent.sdk.utils.generateEntropy

class Passphrase(val words: Array<String>) {

  val size
    get() = words.size

  override fun toString() = words.joinToString(separator = " ")

  companion object {
    const val WORD_COUNT: Int = 16

    /**
     * If parameter [normalize] is true, all provided word will be converted to upper case before entropy creation. In other case word stays
     * as it was provided.
     *
     * @param seed words list
     * @param count word count, default 16
     * @param normalize normalization flag, default true
     */
    @JvmOverloads
    fun generate(seed: List<String> = SeedDictionary.DEFAULT, count: Int = WORD_COUNT, normalize: Boolean = true) =
        create(generateEntropy(), seed, count, normalize)

    private fun create(entropy: ByteArray, seed: List<String>, count: Int, normalize: Boolean): Passphrase {
      val phrases = mutableListOf<String>()
      for (i in 0 until (count * 2) step 2) {

        // randomBuffer has 256 bits / 16 bits per word == 16 words
        val num = (entropy[i].toInt() shl 8) + entropy[i + 1]

        // convert into a int between 0 and 1 (inclusive)
        @Suppress("MagicNumber")
        val multiplier = num / Math.pow(2.0, 16.0)
        val index = Math.abs(Math.round(seed.size * multiplier))

        phrases.add(seed[index.toInt()].let { if (normalize) it.toUpperCase() else it })
      }
      return Passphrase(phrases.toTypedArray())
    }
  }
}
