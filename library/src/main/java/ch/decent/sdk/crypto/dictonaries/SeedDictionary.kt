package ch.decent.sdk.crypto.dictonaries

object SeedDictionary {
  val DEFAULT
    get() = this.javaClass.classLoader.getResource("default.txt").readText().split("\n")
}