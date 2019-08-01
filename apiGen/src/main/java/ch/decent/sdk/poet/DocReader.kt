package ch.decent.sdk.poet

import com.squareup.kotlinpoet.FunSpec
import kastree.ast.Node
import java.io.File

object DocReader {
  private const val TOKEN_START = "/**"
  private const val TOKEN_END = "*/"
  private val REGEX_FUN = Regex("\\s*fun\\s(?:<.*?>\\s)?(\\w+)\\(.*")
  val REGEX_DOC_LINK = Regex("\\[\\w+]")

  data class FunDoc(val name: String, val docs: String)

  private fun File.docs(): List<FunDoc> {
    val funDocs = mutableListOf<FunDoc>()
    useLines {
      val iter = it.iterator()
      var hasDoc = false
      var acc = ""
      while (iter.hasNext()) {
        val line = iter.next().trim()
        if (line.startsWith(TOKEN_END)) hasDoc = false
        if (hasDoc) acc = acc + line.substringAfter("*").trim() + "\n"
        if (line.matches(REGEX_FUN)) {
          funDocs += FunDoc(REGEX_FUN.matchEntire(line)!!.groups[1]!!.value, acc)
          acc = ""
        }
        if (line.startsWith(TOKEN_START)) hasDoc = true
      }
    }
    return funDocs
  }

  val docs: Map<String, List<FunDoc>>
    get() = File(srcApi).listFiles()!!
        .associate { it.nameWithoutExtension to it.docs() }

  fun applyDocs(b: FunSpec.Builder, docs: MutableList<FunDoc>, f: Node.Decl.Func, i: List<String>) {
    val doc = docs.find { it.name == f.name }
    doc?.let {
      val args = mutableListOf<Any>()
      val withImports = it.docs.replace(REGEX_DOC_LINK) {
        it.value.drop(1).dropLast(1).let {
          if (it.first().isLowerCase()) {
            args.add(it.member(i))
            "[%M]"
          } else {
            args.add(it.className(i))
            "[%T]"
          }
        }
      }
      b.addKdoc(withImports, *args.toTypedArray())
      docs.remove(it)
    }

  }
}
