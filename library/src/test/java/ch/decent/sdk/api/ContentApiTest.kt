package ch.decent.sdk.api

import ch.decent.sdk.Helpers
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.utils.privateElGamal
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ContentApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should generate content keys`() {
    val test = api.contentApi.generateKeys(listOf(ChainObject.parse("1.2.17"), ChainObject.parse("1.2.18")))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get content by id`() {
    val test = api.contentApi.get("2.13.3".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get contents by ids`() {
    val test = api.contentApi.getAll(listOf("2.13.3".toChainObject(), "2.13.4".toChainObject()))
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get content by uri`() {
    val test = api.contentApi.get("ipfs:QmWBoRBYuxzH5a8d3gssRbMS5scs6fqLKgapBfqVNUFUtZ")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of publishing managers`() {
    val test = api.contentApi.listAllPublishersRelative("")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should restore content encryption key`() {
    val test = api.contentApi.restoreEncryptionKey(Helpers.credentials.keyPair.privateElGamal(), "2.12.3".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }


  @Test fun `should get list of content by search`() {
    val test = api.contentApi.findAll("")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}