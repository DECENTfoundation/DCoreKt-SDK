package ch.decent.sdk.api

import ch.decent.sdk.*
import ch.decent.sdk.crypto.Credentials
import io.reactivex.schedulers.Schedulers
import org.amshove.kluent.`should equal`
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class MessagingApiTest(channel: Channel) : BaseApiTest(channel) {

  @Test fun `should get message operations for receiver`() {
    val test = api.messagingApi.getAllOperations(receiver = Helpers.account2)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get messages for account and decrypt for receiver`() {
    val test = api.messagingApi.getAll(receiver = Helpers.account2).cache()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

    val unencrypted = test.values().single()
        .filter { it.encrypted.not() }
        .toSet()

    unencrypted.all { it.sender == Helpers.account } `should equal` true
    unencrypted.all { it.receiver == Helpers.account2 } `should equal` true

    val credentials = Credentials(Helpers.account, Helpers.private)
    val decrypted = test.values().single()
        .filter { it.encrypted }
        .map { it.decrypt(credentials) }

    decrypted.all { it.sender == Helpers.account } `should equal` true
    decrypted.all { it.receiver == Helpers.account2 } `should equal` true
  }

  @Test fun `should get decrypted messages for sender account`() {
    val credentials = Credentials(Helpers.account, Helpers.private)
    val test = api.messagingApi.getAllDecryptedForSender(credentials)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

    test.values().single().all { it.encrypted.not() } `should equal` true
  }

  @Test fun `should get fail decrypt messages for sender account with wrong credentials`() {
    val test = api.messagingApi.getAll(Helpers.account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()

    val credentials = Credentials(Helpers.account, Helpers.private2)
    test.values().single()
        .filter { it.encrypted }
        .map { it.decrypt(credentials) }
        .all { it.encrypted } `should equal` true
  }
}