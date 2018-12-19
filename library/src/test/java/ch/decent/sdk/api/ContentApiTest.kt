package ch.decent.sdk.api

import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.private
import ch.decent.sdk.utils.ElGamal
import ch.decent.sdk.utils.privateElGamal
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ContentApiTest(channel: Channel) : BaseApiTest(channel) {
//  override val useMock: Boolean = false

  @Test fun `should get content by uri`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_content",["http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56"]],"id":1}""",
            """{"id":1,"result":{"id":"2.13.3","author":"1.2.17","co_authors":[],"expiration":"2019-04-26T15:34:51","created":"2018-04-26T15:34:50","price":{"map_price":[[1,{"amount":100000000,"asset_id":"1.3.0"}]]},"size":1,"synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56","quorum":0,"key_parts":[],"_hash":"0000000000000000000000000000000000000000","last_proof":[],"is_blocked":false,"AVG_rating":0,"num_of_ratings":0,"times_bought":1,"publishing_fee_escrow":{"amount":0,"asset_id":"1.3.0"},"seeder_price":[]}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"id":"2.13.3","author":"1.2.17","co_authors":[],"expiration":"2019-04-26T15:34:51","created":"2018-04-26T15:34:50","price":{"map_price":[[1,{"amount":100000000,"asset_id":"1.3.0"}]]},"size":1,"synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56","quorum":0,"key_parts":[],"_hash":"0000000000000000000000000000000000000000","last_proof":[],"is_blocked":false,"AVG_rating":0,"num_of_ratings":0,"times_bought":1,"publishing_fee_escrow":{"amount":0,"asset_id":"1.3.0"},"seeder_price":[]}}"""
    )

    val test = api.contentApi.getContent("http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get content by id`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_objects",[["2.13.3"]]],"id":1}""",
            """{"id":1,"result":[{"id":"2.13.3","author":"1.2.17","co_authors":[],"expiration":"2019-04-26T15:34:51","created":"2018-04-26T15:34:50","price":{"map_price":[[1,{"amount":100000000,"asset_id":"1.3.0"}]]},"size":1,"synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56","quorum":0,"key_parts":[],"_hash":"0000000000000000000000000000000000000000","last_proof":[],"is_blocked":false,"AVG_rating":0,"num_of_ratings":0,"times_bought":1,"publishing_fee_escrow":{"amount":0,"asset_id":"1.3.0"},"seeder_price":[]}]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[{"id":"2.13.3","author":"1.2.17","co_authors":[],"expiration":"2019-04-26T15:34:51","created":"2018-04-26T15:34:50","price":{"map_price":[[1,{"amount":100000000,"asset_id":"1.3.0"}]]},"size":1,"synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56","quorum":0,"key_parts":[],"_hash":"0000000000000000000000000000000000000000","last_proof":[],"is_blocked":false,"AVG_rating":0,"num_of_ratings":0,"times_bought":1,"publishing_fee_escrow":{"amount":0,"asset_id":"1.3.0"},"seeder_price":[]}]}"""
    )

    val test = api.contentApi.getContent("2.13.3".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of content by search`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"search_content",["","-created","","default","0.0.0","1.0.0",100]],"id":1}""",
            """{"id":1,"result":[]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[]}"""
    )

    val test = api.contentApi.searchContent("")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of publishing managers`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"list_publishing_managers",["",100]],"id":1}""",
            """{"id":1,"result":[]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[]}"""
    )

    val test = api.contentApi.listPublishingManagers("")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should generate content keys`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"generate_content_keys",[[]]],"id":1}""",
            """{"id":1,"result":{"key":"b44e0749395e1009a90cdd7e545897b66f7abacada463091c53bf76e1af74e6f","parts":[]}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"key":"b44e0749395e1009a90cdd7e545897b66f7abacada463091c53bf76e1af74e6f","parts":[]}}"""
    )

    val test = api.contentApi.generateContentKeys(emptyList())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should restore content encryption key`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"restore_encryption_key",[{"s":"8149734503494312909116126763927194608124629667940168421251424974828815164868905638030541425377704620941193711130535974967507480114755414928915429397074890."},"2.12.3"]],"id":1}""",
            """{"id":1,"result":"0000000000000000000000000000000000000000000000000000000000000000"}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":"0000000000000000000000000000000000000000000000000000000000000000"}"""
    )

    val test = api.contentApi.restoreEncryptionKey(ECKeyPair.fromBase58(private).privateElGamal(), "2.12.3".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}