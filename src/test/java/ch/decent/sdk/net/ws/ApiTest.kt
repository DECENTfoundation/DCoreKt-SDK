package ch.decent.sdk.net.ws

import ch.decent.sdk.*
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.DumpedPrivateKey
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.address
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.*
import ch.decent.sdk.net.model.request.*
import ch.decent.sdk.utils.publicElGamal
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import org.junit.Ignore
import org.junit.Test
import java.security.SecureRandom

class ApiTest {

  @Test fun loginTest() {
    val observer = TestObserver<Boolean>()

    socket.request(Login()).subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertValue(true)
  }


  @Test fun `should login, request db access and get balance for account`() {
    val observer = TestObserver<List<AssetAmount>>()

    socket.request(GetAccountBalances(account)).subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should login, request db access and get account`() {
    val observer = TestObserver<List<Account>>()

    socket.request(GetAccountById(account2)).subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should login, request db access and get account by name`() {
    val observer = TestObserver<Account>()

    socket.request(GetAccountByName(accountName)).subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should login, request db access and get account history`() {
    val observer = TestObserver<List<TransactionDetail>>()

    socket.request(SearchAccountHistory(account)).subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
  }

  @Test fun `get miners, load their accounts and put it into map with miner names`() {
    val observer = TestObserver<Map<String, Miner>>()

    socket.request(LookupMinerAccounts())
        .doOnSuccess { println(it) }
        .flatMap { result ->
          socket.request(GetMiners(result.map { ChainObject.parse(it[1].asString) }.toSet()))
              .map { result.map { it.get(0).asString!! }.zip(it).toMap() }
        }
        .doOnSuccess { println(it) }
        .subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should login, request history access and get account history`() {
    val observer = TestObserver<List<OperationHistory>>()

    socket.request(GetAccountHistory(account)).subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()

    observer.values()[0].forEach { println(it) }
  }

  @Test fun `get assets`() {
    val observer = TestObserver<List<Asset>>()

    socket.request(GetAssets(listOf("1.3.0".toChainObject()))).subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
    observer.values()[0].forEach { println(it) }
  }

  @Test fun `transaction test`() {
    val observer = TestObserver<TransactionConfirmation>()

    val dpk = DumpedPrivateKey.fromBase58(private)
    val key = ECKeyPair.fromPrivate(dpk.bytes, dpk.compressed)

    val op = TransferOperation(
        account,
        account2,
        AssetAmount(15000000),
        Memo("hello memo here i am", key, Address.decode(public2))
    )

    val props = socket.request(GetDynamicGlobalProps).blockingGet()
    val fees = socket.request(GetRequiredFees(listOf(op))).blockingGet()

    val transaction = Transaction(
        BlockData(props),
        listOf(op.apply { fee = fees.first() })
    ).withSignature(key)

    socket.request(BroadcastTransactionWithCallback(transaction, 27185)).subscribe(observer)

    println(transaction.id)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
  }

  @Ignore
  //  can be bought only once
  @Test fun `buy content`() {
    val observer = TestObserver<TransactionConfirmation>()

    val key = ECKeyPair.fromBase58(private)

    val content = socket.request(GetContentById("2.13.3".toChainObject())).blockingGet().first()

    val op = BuyContentOperation(
        content.uri,
        account,
        content.price(),
        key.publicElGamal()
    )

    val props = socket.request(GetDynamicGlobalProps).blockingGet()
    val fees = socket.request(GetRequiredFees(listOf(op))).blockingGet()

    val transaction = Transaction(
        BlockData(props),
        listOf(op.apply { fee = fees.first() })
    ).withSignature(key)

    socket.request(BroadcastTransactionWithCallback(transaction, 27185)).subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
  }

  @Test fun `vote for miners`() {
    val votes = setOf("0:5", "0:8")
    val observer = TestSubscriber<Account>()

    vote(votes).concatWith(vote(emptySet())).subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
        .assertValueAt(0, { it.options.votes == votes })
        .assertValueAt(1, { it.options.votes.isEmpty() })
  }

  private fun vote(votes: Set<String>): Single<Account> {
    val key = ECKeyPair.fromBase58(private)
    val account = socket.request(GetAccountById(account)).blockingGet().first()
    val op = AccountUpdateOperation(
        account.id, options = account.options.copy(votes = votes)
    )
    val props = socket.request(GetDynamicGlobalProps).blockingGet()
    val fees = socket.request(GetRequiredFees(listOf(op))).blockingGet()

    val transaction = Transaction(
        BlockData(props),
        listOf(op.apply { fee = fees.first() })
    ).withSignature(key)

    return socket.request(BroadcastTransactionWithCallback(transaction, 27185))
        .flatMap { socket.request(GetAccountById(account.id)).map { it.first() } }
  }

  @Ignore
  //  can be created only once
  @Test fun `create account`() {

    val observer = TestObserver<TransactionConfirmation>()

    val key = ECKeyPair.fromBase58(private)
    val public = "DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU".address()
    val op = AccountCreateOperation(account, "mikeeeee", public)

    val props = socket.request(GetDynamicGlobalProps).blockingGet()
    val fees = socket.request(GetRequiredFees(listOf(op))).blockingGet()

    val transaction = Transaction(
        BlockData(props),
        listOf(op.apply { fee = fees.first() })
    ).withSignature(key)

    socket.request(BroadcastTransactionWithCallback(transaction, 27185)).subscribe(observer)

    observer.awaitTerminalEvent()
    observer.assertComplete()
        .assertNoErrors()
  }

  @Test fun `balance`() {
    val test = api.getBalance(accountName, "DCT").test()
    test.awaitTerminalEvent()
    test.assertNoErrors()

    apiSync.getBalance("as", "DCT")
    apiSync.createCredentials("u961279ec8b7ae7bd62f304f7c1c3d345", "5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn")
  }
}