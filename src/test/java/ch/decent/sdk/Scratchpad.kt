package ch.decent.sdk

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.DumpedPrivateKey
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.wrap
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.TransactionConfirmation
import ch.decent.sdk.utils.Hex
import ch.decent.sdk.utils.bytes
import ch.decent.sdk.utils.hex
import com.google.common.primitives.Bytes
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be equal to`
import org.junit.Test
import java.math.BigInteger
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.text.DecimalFormat

class Scratchpad {

  @Test fun str() {
    val v = 1.12345678900
    var d: Int? = null
    var f = d?.let { ".$d" } ?: ""
    println("%${f}f".format(v))

    d = 2
    f = d?.let { ".$d" } ?: ""
    println("%${f}f".format(v))

    val df = DecimalFormat.getInstance()
    println(df.format(v))
    println(df.format(1.1239))

    df.maximumFractionDigits = 20
    println(df.format(v))
    println(df.format(1))
    println(df.format(1.22))

    println(Globals.DCT.format(BigInteger.valueOf(9), 2))
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
    println(m.bytes().joinToString())

    println("----------")
    println(333781 and 0xffff) //6100 + 1
    println("----------")

    "10:100"
    val b = "0".toByte()
    val i = "1".toInt()
    val ba = byteArrayOf(b, i.toByte(), (i shr 8).toByte(), (i shr 16).toByte())

    val vote = i shl 8 or b.toInt()
    println(vote)
    println(vote.bytes().hex())
    println(ba.hex())

  }

  @Test fun hex() {
    fun hexToBytes(s: String): ByteArray {
      val len = s.length
      val data = ByteArray(len / 2)
      var i = 0
      while (i < len) {
        data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
        i += 2
      }
      return data
    }

    println(Hex.decode(Globals.DCT_CHAIN_ID).joinToString())
    println(hexToBytes(Globals.DCT_CHAIN_ID).joinToString())
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
      sig = key.signature(bytes.wrap())
      i++
    }

    println(sig)

    sig `should not be equal to` ""
  }

  @Test fun toBytes() {
    val chainId = Globals.DCT_CHAIN_ID

    var buffer = ByteArray(chainId.length / 2)
    var i = 0
    while (i < chainId.length) {
      buffer[i / 2] = ((Character.digit(chainId[i], 16) shl 4) + Character.digit(chainId[i + 1], 16)).toByte()
      i += 2
    }
    println(Bytes.asList(*buffer).joinToString())
    println(Hex.decode(chainId).joinToString())

  }

  @Test fun `encrypt and decrypt from BC`() {
    val encrypted = "6571485783a7f6c11295fa94866f3cad"
    val plain = "Hello Memo"
    val nonce = 1521105802161233
    val sender = Address.decode("DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4")
    val key = ECKeyPair.fromBase58(private)

    val memo = Memo(plain, key, sender, nonce.toBigInteger())

    memo.message `should be equal to` encrypted
    memo.decrypt(key) `should be equal to` plain

  }

  @Test fun `encrypt and decrypt long`() {
    val plain = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    val sender = Address.decode("DCT5j2bMj7XVWLxUW7AXeMiYPambYFZfCcMroXDvbCfX1VoswcZG4")
    val key = ECKeyPair.fromBase58(private)

    val memo = Memo(plain, key, sender)

    memo.decrypt(key) `should be equal to` plain
  }

  val modulus = BigInteger("11760620558671662461946567396662025495126946227619472274601251081547302009186313201119191293557856181195016058359990840577430081932807832465057884143546419")
  val generator = 3.toBigInteger()
  fun publicElGamal(private: BigInteger) = generator.modPow(private, modulus)

  @Test fun `el gamal keys generation`() {
    val g = 3.toBigInteger()
    val elPrivate = BigInteger("10264811947384987455806884361188312159337997349773266680031652882869271200883393026310091771774151908862673648846588359689442630336710264201803312709689478")
    val elPublic = BigInteger("7317752633383033582159088041509593492238468350205070200236191783227692402591973343242224306276612029080797696757604654009350847591901976526778157668840202")
    val key = ECKeyPair.fromBase58("5JDFQN3T8CFT1ynhgd5s574mTV9UPf9WamkHojBL4NgbhSBDmBj")
    val sha512 = MessageDigest.getInstance("SHA-512")

//    val secret = Sha256Hash.hash(keyPair.public.getEncoded(false))
//    val hash = sha512.digest(secret)
    val hash = sha512.digest(key.private!!.toByteArray())
    val k = BigInteger(1, hash)

    println(k)
    println(k.modInverse(modulus))
    println(k.mod(modulus))
//    println(publicElGamal(k))
//    println(publicElGamal(elPrivate))


  }

  @Test fun `processed transaction result`() {
    val str = "{\"id\":\"3d91dae75b55e34cddf43ebc56aeec8488db8012\",\"block_num\":454977,\"trx_num\":0,\"trx\":{\"ref_block_num\":61760,\"ref_block_prefix\":1485276657,\"expiration\":\"2018-03-22T12:56:29\",\"operations\":[[0,{\"fee\":{\"amount\":500000,\"asset_id\":\"1.3.0\"},\"from\":\"1.2.30\",\"to\":\"1.2.31\",\"amount\":{\"amount\":150000000,\"asset_id\":\"1.3.0\"},\"memo\":{\"from\":\"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz\",\"to\":\"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP\",\"nonce\":\"1029839728655420928\",\"message\":\"4acb403a35d4b73ec88a1cd287d93ccebd07809941b182b54c54ddb718ff98be\"},\"extensions\":[]}]],\"extensions\":[],\"signatures\":[\"1f75e472a20d5a8cdddccb26957a20b87c7fb8e6788d3c7cccd0abf48bd94455517c609bb4186821480655eb57a7956785e3c611ed61d2d9ef07c173969ddfffc0\"],\"operation_results\":[[0,{}]]}}"
    val trx = DCoreApi.gsonBuilder.create().fromJson(str, TransactionConfirmation::class.java)
    println(trx)
  }


}