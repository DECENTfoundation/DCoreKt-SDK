package ch.decent.sdk.model

data class VoteId(
    val type: Int,
    val id: Int
) {

  companion object {
    private val regex = Regex("^[0-9]+:[0-9]+\$")
    fun parse(vote: String): VoteId =
        if (regex.matches(vote)) vote.split(":").let { VoteId(it[0].toInt(), it[1].toInt()) }
        else throw IllegalArgumentException("invalid vote id string: $vote")
  }

  override fun toString(): String = "$type:$id"
}
