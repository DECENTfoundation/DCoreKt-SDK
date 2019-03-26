package ch.decent.sdk.crypto.wordlist

interface WordListProvider {
  fun all(): Collection<WordList>
  fun get(id: Int): WordList?
}