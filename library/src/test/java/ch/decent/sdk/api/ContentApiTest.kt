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
  override val useMock: Boolean = false

  @Test fun `should get content by uri`() {
    val test = api.contentApi.getContent("http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get content by id`() {
    val test = api.contentApi.getContent("2.13.3".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of content by search`() {
    val test = api.contentApi.searchContent("")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of publishing managers`() {
    val test = api.contentApi.listPublishingManagers("")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should generate content keys`() {
    val test = api.contentApi.generateContentKeys(emptyList())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should restore content encryption key`() {
    val test = api.contentApi.restoreEncryptionKey(ECKeyPair.fromBase58(private).privateElGamal(), "2.12.3".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}