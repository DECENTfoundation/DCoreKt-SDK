package ch.decent.sdk.model

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.net.model.request.GetNftData
import com.google.gson.Gson
import org.amshove.kluent.`should be equal to`
import org.junit.Before
import org.junit.Test

class NftTest {

  val json = """
    [
       {
         "id":"1.11.1",
         "nft_id":"1.10.2",
         "owner":"1.2.34",
         "data":[5, "red", false]
       },
       {
         "id":"1.11.1",
         "nft_id":"1.10.2",
         "owner":"1.2.34",
         "data":[5, "green", false]
       },
       {
         "id":"1.11.1",
         "nft_id":"1.10.3",
         "owner":"1.2.34",
         "data":[1,2,3]
       }
    ]

  """.trimIndent()

  lateinit var gson: Gson

  @Before fun init() {
    gson = DCoreSdk.gsonBuilder.create()
//    NftTypeFactory.idToModel.clear()
  }

  @Test fun `should parse nft generic`() {
    val type = GetNftData(emptyList()).returnClass
    val v = gson.fromJson<List<NftData<GenericNft>>>(json, type)
    v.all { it.data!!.values().let { it[0] == 5 && (it[1] == "green" || it[1] == "red") && it[3] == false } }
  }

  @Test fun `should parse nft apple`() {
    val type = GetNftData(emptyList()).returnClass
    val v = gson.fromJson<List<NftData<GenericNft>>>(json, type)
    v.map { it.data!!.make<NftApple>() }.count() `should be equal to` 2
  }
}
