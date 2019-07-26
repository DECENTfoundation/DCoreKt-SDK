package ch.decent.sdk

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.DumpedPrivateKey
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.model.operation.CustomOperation
import ch.decent.sdk.model.operation.TransferOperation
import ch.decent.sdk.model.toObjectId
import ch.decent.sdk.utils.hash512
import ch.decent.sdk.utils.hex
import ch.decent.sdk.utils.unhex
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import okio.Buffer
import okio.ByteString.Companion.decodeHex
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be equal to`
import org.junit.Ignore
import org.junit.Test
import org.slf4j.LoggerFactory
import java.math.BigInteger
import java.nio.ByteBuffer
import java.text.DecimalFormat

@Ignore
class Scratchpad {

  @Test fun str() {
    val v = 1.12345678900
    var d: Int? = null
    var f = d?.let { ".$d" } ?: ""
    println("%${f}f".format(v))

    d = 2
    f = d.let { ".$d" }
    println("%${f}f".format(v))

    val df = DecimalFormat.getInstance()
    println(df.format(v))
    println(df.format(1.1239))

    df.maximumFractionDigits = 20
    println(df.format(v))
    println(df.format(1))
    println(df.format(1.22))

    println(DCoreConstants.DCT.format(9))
  }

  @Test fun `chain object id`() {
//    ByteBuffer.allocate(8).putLong(10).put(0, 1).put(1, 2).getLong(0).print()
//    val b = "1.2.34".toChainObject().objectTypeIdBytes
//    Longs.fromByteArray(b).shl(56).print()
    val l = 1L.shl(56) or (2L.shl(48)) or 34L
//    1L.shl(56).bytes().hex().print()
//    2L.shl(48).bytes().hex().print()
//    l.bytes().hex().print()
//    b.hex().print()
  }

  @Test fun bitwise() {
    println(Byte.MAX_VALUE.toInt() and 0xff)
    println(Byte.MIN_VALUE.toInt() and 0xff)
    println(-1 and 0xff)
    println(Byte.MIN_VALUE.toInt())

    println("----------")
    val n = 1200
    println(n.toByte())
    println((n shr 8).toByte())
    println((n and 0xff).toByte())
    println(((n shr 8) and 0xff).toByte())

    println("----------")
    val m = 5616123156
    println(ByteArray(8, { idx -> (m shr 8 * idx).toByte() }).joinToString())
    println(ByteBuffer.allocate(8).putLong(m).array().reversed().joinToString())
//    println(m.bytes().joinToString())

    println("----------")
    println(333781 and 0xffff) //6100 + 1
    println("----------")

//    "10:100"
    val b = "0".toByte()
    val i = "1".toInt()
    val ba = byteArrayOf(b, i.toByte(), (i shr 8).toByte(), (i shr 16).toByte())

    val vote = i shl 8 or b.toInt()
    println(vote)
//    println(vote.bytes().hex())
    println(ba.hex())

  }

  @Test fun base58() {

    val private = "5JVHeRffGsKGyDf7T9i9dBbzVHQrYprYeaBQo2VCSytj7BxpMCq"
    val public = "DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP"
    val dpk = DumpedPrivateKey.fromBase58(private)
    val key = ECKeyPair.fromPrivate(dpk.bytes, dpk.compressed)
    val address = Address(key.public)

    dpk.toString() `should be equal to` private
    address.encode() `should be equal to` public
  }

  @Test fun signature() {

    val encoded = "5HueCGU8rMjxEXxiPuD5BDku4MkFqeZyd4dZ1jvhTVqvbTLvyTJ"
    val wif = DumpedPrivateKey.fromBase58(encoded)
    val key = ECKeyPair.fromPrivate(wif.bytes, wif.compressed)

    var i = 0
    var bytes: ByteArray
    var sig = ""
    while (sig.isEmpty()) {
      bytes = "${i}asdasdsa12easdas".toByteArray()
      sig = key.signature(bytes)
      i++
    }

    println(sig)

    sig `should not be equal to` ""
  }

  @Test fun `encrypt and decrypt from BC`() {
    val encrypted = "6571485783a7f6c11295fa94866f3cad"
    val plain = "Hello Memo"
    val nonce = 1521105802161233
    val sender = Address.decode("DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4")
    val key = ECKeyPair.fromBase58(Helpers.private)

    val memo = Memo(plain, key, sender, nonce.toBigInteger())

    memo.message `should be equal to` encrypted
    memo.decrypt(key) `should be equal to` plain

  }

  @Test fun `encrypt and decrypt long`() {
    val plain = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    val sender = Address.decode("DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4")
    val key = ECKeyPair.fromBase58(Helpers.private)

    val memo = Memo(plain, key, sender)

    memo.decrypt(key) `should be equal to` plain
  }

  val modulus = BigInteger("11760620558671662461946567396662025495126946227619472274601251081547302009186313201119191293557856181195016058359990840577430081932807832465057884143546419")
  val generator = 3.toBigInteger()
  fun publicElGamal(private: BigInteger) = generator.modPow(private, modulus)

  @Test fun `el gamal keys generation`() {
//    val g = 3.toBigInteger()
//    val elPrivate = BigInteger("10264811947384987455806884361188312159337997349773266680031652882869271200883393026310091771774151908862673648846588359689442630336710264201803312709689478")
//    val elPublic = BigInteger("7317752633383033582159088041509593492238468350205070200236191783227692402591973343242224306276612029080797696757604654009350847591901976526778157668840202")
    val key = ECKeyPair.fromBase58("5JDFQN3T8CFT1ynhgd5s574mTV9UPf9WamkHojBL4NgbhSBDmBj")

//    val secret = Sha256Hash.hash(keyPair.public.getEncoded(false))
//    val hash = sha512.digest(secret)
    val hash = key.privateBytes.hash512()
    val k = BigInteger(1, hash)

    println(k)
    println(k.modInverse(modulus))
    println(k.mod(modulus))
//    println(publicElGamal(k))
//    println(publicElGamal(elPrivate))


  }

  @Test fun `processed transaction result`() {
    val str = "{\"id\":\"3d91dae75b55e34cddf43ebc56aeec8488db8012\",\"block_num\":454977,\"trx_num\":0,\"trx\":{\"ref_block_num\":61760,\"ref_block_prefix\":1485276657,\"expiration\":\"2018-03-22T12:56:29\",\"operations\":[[0,{\"fee\":{\"amount\":500000,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.30\",\"to\":\"1.2.31\",\"amount\":{\"amount\":150000000,\"asset_id\":\"1.3.0\"},\"memo\":{\"from\":\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",\"to\":\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",\"nonce\":\"1029839728655420928\",\"message\":\"4acb403a35d4b73ec88a1cd287d93ccebd07809941b182b54c54ddb718ff98be\"},\"extensions\":[]}]],\"extensions\":[],\"signatures\":[\"1f75e472a20d5a8cdddccb26957a20b87c7fb8e6788d3c7cccd0abf48bd94455517c609bb4186821480655eb57a7956785e3c611ed61d2d9ef07c173969ddfffc0\"],\"operation_results\":[[0,{}]]}}"
    val trx = DCoreClient.gsonBuilder.create().fromJson(str, TransactionConfirmation::class.java)
    println(trx)
  }

/*  @Test fun `transfers to various receivers`() {
    val api = DCoreClient.createApiRx(client, url, logger = LoggerFactory.getLogger("DCoreApi"))
    val dpk = DumpedPrivateKey.fromBase58(private)
    val key = ECKeyPair.fromPrivate(dpk.bytes, dpk.compressed)
    val credentials = Credentials(account, key)

//    api.transfer(credentials, account2.objectId, 0.0001).blockingGet()
//    api.transfer(credentials, accountName2, 0.0002).blockingGet()
    api.transfer(credentials, public2, 0.0003).blockingGet()
  }
  */

  @Test fun `transaction signature`() {
    val key = ECKeyPair.fromBase58(Helpers.private)

    val op = TransferOperation(
        Helpers.account,
        Helpers.account2,
        AssetAmount(100000),
        fee = Fee(amount = 5000)
    )
//    op.bytes.hash256().print()
//    key.signature(op.bytes.hash256()).print()
  }

  @Test fun `retry when`() {
    val some = PublishSubject.create<Unit>()
    val o = Observable.error<Unit>(Exception())
        .doOnError { "error".print() }
        .retryWhen { some }

    o.test()

    some.onNext(Unit)
    some.onNext(Unit)

  }

  @Test fun `inner subscribe flatMap`() {
    val o = Observable.range(1, 10)
        .subscribeOn(Schedulers.computation())
        .doOnNext { "$it  ${Thread.currentThread()}".print() }
        .flatMap { idx -> Observable.create<String> { it.onNext("hello $idx") }.subscribeOn(Schedulers.io()) }
        .doOnNext { "$it  ${Thread.currentThread()}".print() }

    o.test().awaitTerminalEvent()
  }

  @Test fun lifecycle() {
    val o = Observable.range(1, 10)
        .doOnSubscribe { "subscribe".print() }
        .doOnNext { "next".print() }
        .doAfterNext { "after next".print() }
        .doOnComplete { "complete".print() }
        .doOnDispose { "dispose".print() }
        .doOnTerminate { "terminate".print() }
        .doAfterTerminate { "after terminate".print() }
        .doFinally { "finally".print() }
        .flatMap {
          Observable.just(it)
              .doOnSubscribe { "inner subscribe".print() }
              .doOnNext { "inner next".print() }
              .doAfterNext { "inner after next".print() }
              .doOnComplete { "inner complete".print() }
              .doOnDispose { "inner dispose".print() }
              .doOnTerminate { "inner terminate".print() }
              .doAfterTerminate { "inner after terminate".print() }
              .doFinally { "inner finally".print() }

        }
        .doOnSubscribe { "end subscribe".print() }
        .doOnNext { "end next".print() }
        .doAfterNext { "end after next".print() }
        .doOnComplete { "end complete".print() }
        .doOnDispose { "end dispose".print() }
        .doOnTerminate { "end terminate".print() }
        .doAfterTerminate { "end after terminate".print() }
        .doFinally { "end finally".print() }


    o.test().awaitTerminalEvent()
  }

  @Test fun `ref block`() {
    val id = "003482ff012880f806baa6f220538425804136be"
//    val num = 3441407
    val refId = 4169148417
    val refNum = 33535

    val bytes = Buffer().write(id.decodeHex())
    bytes.skip(2)
    bytes.readShort().toInt() and 0xFFFF `should be equal to` refNum
    bytes.readIntLe().toLong() and 0xFFFFFFFF `should be equal to` refId

//    val b = ByteBuffer.fromHex(id, true);
//    b.readUint32(4).should.equal(refId);
//    b.BE().readUint32(0).should.equal(num);
//    b.BE().readUint16(2).should.equal(refNum);
  }

  @Test fun `reflection`() {
    "1.2.3".toObjectId<AccountObjectId>().print()
  }

  @Test fun `custom operation`() {
    val logger = LoggerFactory.getLogger("LOG")
    val api = DCoreClient.createForWebSocket(Helpers.client(logger), Helpers.wsUrl, logger)

    val op = CustomOperation(4, Helpers.account, listOf(Helpers.account), "hello data".toByteArray().hex())
    api.broadcastApi.broadcastWithCallback(Helpers.private, op)
        .testCheck()
  }

  @Test fun `check hex`() {
    "hello hex".unhex()
  }

}
