package ch.decent.sdk

import ch.decent.sdk.crypto.*
import ch.decent.sdk.model.CipherKeyPairAdapter
import ch.decent.sdk.model.ExtraKeysAdapter
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.utils.*
import ch.decent.sdk.utils.ElGamal.publicElGamal
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should equal`
import org.junit.Test
import java.math.BigInteger
import java.security.MessageDigest

class Crytpo {

  @Test fun `priv key dump`() {
    val key = ECKeyPair.fromBase58(private)
    val dump = DumpedPrivateKey.toBase58(key)

    dump `should be equal to` private
  }

  @Test fun `nonce generation`() {
    var nonce = BigInteger.ZERO
    for (i in 0..100) {
//      println("$nonce  ${nonce.toByteArray().size} ${nonce.toLong().bytes().joinToString()}")
      nonce = generateNonce()
      nonce.toLong().bytes().size `should be equal to` 8
    }
  }

  @Test fun `el gamal keys generation`() {
    val priv = BigInteger("8149734503494312909116126763927194608124629667940168421251424974828815164868905638030541425377704620941193711130535974967507480114755414928915429397074890")
    val pub = BigInteger("5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437")

    val key = ECKeyPair.fromBase58(private)
    val sha512 = MessageDigest.getInstance("SHA-512")

    val hash = sha512.digest(key.private!!.toByteArray())

    val k = BigInteger(1, hash)

    k `should equal` priv
    publicElGamal(k) `should equal` pub
  }

  @Test fun `encrypt and decrypt from BC`() {
    val encrypted = "b23f6afb8eb463704d3d752b1fd8fb804c0ce32ba8d18eeffc20a2312e7c60fa"
    val plain = "hello memo here i am"
    val nonce = BigInteger("10872523688190906880")
    val from = Address.decode("DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz")
    val to = Address.decode("DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP")
    val key = ECKeyPair.fromBase58(private)

    val memo = Memo(plain, key, to, nonce)

    memo.message `should be equal to` encrypted
    memo.decrypt(key) `should be equal to` plain

    val msg = decryptAesWithChecksum(key.secret(to, nonce), encrypted.unhex())
    msg `should be equal to` plain
  }

  @Test fun `encrypt private key as wallet file`() {
    val pass = "quick brown fox jumped over a lazy dog"
    val key = ECKeyPair.fromBase58(private)
    val wallet = Wallet.create(Credentials("1.2.30".toChainObject(), key), pass)
    Wallet.decrypt(wallet, pass).keyPair.private `should equal` key.private
  }

  @Test fun `encrypt private key as wallet file with empty password`() {
    val key = ECKeyPair.fromBase58(private)
    val wallet = Wallet.create(Credentials("1.2.30".toChainObject(), key))
    Wallet.decrypt(wallet).keyPair.private `should equal` key.private
  }

  @Test fun `decrypt walet`() {
    val cipher = "4d09b1b3ba398c40aaaa03c988df25597254a84137c0ef90e2f426f57dafc6ed12f65f98d346ecaa0a2911ac7d6d07adb19c26e24107356ede6f56e56de3541ccf02594d4fd94134d7426933e697e9f430ed4756a2952d81fd05ebce027f6a8cae2cf5247e0b3429d006c454c4a33dfc527aa7e16a399b98ede29ad7ccf0d5539dbb96e6aab74c7eafa659267cf8862298822f0f3b5e757d569deebe9105e3ab6c4f82fdc4ef3d99c6ba16af92dde5973a27051295c6712aae2c2572e7f386ac8b2cd45663c102103024da870ca740af8ea14128034c3cf99a26812867ae8779281d9006afc5ad17e41bf1e60e621b3827f1be30b1092a972c1e51d71f0b39bc7fe4e409ac5fda97262507843c08096de60d9818778df0648146756dc3aea740cd034b17662b9372ecb32bf3dcb7274d490a76fb8f16ee819b6240e660b0ca4dccc018dc8bdf53b7695ac2d7b53a1f597f5385c80955b28298295f49d4a1284cbd495286532c848ddeda981c809aee400944facab124592e79f710b95090016b330856e81e761448d03586ab80c6840032c9e292f7b2604752eef91043734501c00f51e6d95b191d0ec7f70c743165d6b5ce9652df979729430463b02047e830e610d5dcad65db49a14a0a09171fa609a5dd13c6fa8fa4b4a3b39dfdc6be6379cc55599d119a59a3c077d90e17ecc7c603f762455abafe0c7cc2fe071a48b1c651b4f93f0c988c4cecec5e7e7264e96def636387cc61f85f37b96ca05861a3e0becf99d946ff7498e38fe78cc9c9620d93348ecf1535bbe265decd0fdd43169c8dfef8a3ddcbbdc2153092f949038a45d02d607062f44c177235dc062cc2086bc881526d20f5d3e5b84c275a2922b7993f896dcb5d9688b5251d676d6f6af39bac25dea55c044cbfe09bcc79bde4fbc0db2a279be361bd7f8f9bd32c737c99de40fe9d5a8c690bf7ed2541be6ad80bff0fc46bac5a27532462264b17220e7a45cfb74d7651ed193f5ac8596094c631270d361baf1d63b329f675bfdb3b2370ea33ca9f4ac6fddbd87762158890da7bec3fec156fa8836767a93c8ca74e0f6a011e65ce14442cf8c86a7311150c8a604b58c7b239a8b87ca9eb80ea1cae9deccdec6e3598ba076f82da7f56215ce911c766b93e3aca54348861b8b536b7157e06a3a70ed486367090d99b695c5992df06754869967cb820883027a9c39708c653a117c0928528bad99cd75bac0445d2a103117270c2f14c41a7ecd46717f165bc75303f8952aa7ee2e27754f196eb2ce1aa4571f40795720fb068541f4ac89d18dcada2584f2333755610e6202318bb140c0dc014d593720ae09f221eaaabe7c6cf9a0a0a0af759b916dfbbe97b307cde1963b75d2b02567fdf1fe77c295362e826f669f4bab9d7f2d261ef54ca45d33d1b4158fed5854b98467c5cd2fc2c6eaa57a5885de93cf00d2816d21b20871cd473970956b957b3683c9389358c1d37c0186c9cce92a0198f3dd816ca1f756766f91960ab74a81d9605a524f728d5d9b4d620cea3e2200db4daa1de520117abfc"
    val pass = MessageDigest.getInstance("SHA-512").digest("aaa".toByteArray())

    val wallet = decryptAes(pass, cipher.unhex())

    println(wallet)
  }

  @Test fun `import dcore wallet`() {
    val account = "1.2.30".toChainObject()
    val json = """
{
  "version": 1,
  "update_time": "2018-04-18T09:36:28",
  "chain_id": "17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc",
  "my_accounts": [{
      "id": "1.2.30",
      "registrar": "1.2.15",
      "name": "u961279ec8b7ae7bd62f304f7c1c3d345",
      "owner": {
        "weight_threshold": 1,
        "account_auths": [],
        "key_auths": [[
            "DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",
            1
          ]
        ]
      },
      "active": {
        "weight_threshold": 1,
        "account_auths": [],
        "key_auths": [[
            "DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",
            1
          ]
        ]
      },
      "options": {
        "memo_key": "DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",
        "voting_account": "1.2.3",
        "num_miner": 0,
        "votes": [],
        "extensions": [],
        "allow_subscription": false,
        "price_per_subscribe": {
          "amount": 0,
          "asset_id": "1.3.0"
        },
        "subscription_period": 0
      },
      "rights_to_publish": {
        "is_publishing_manager": false,
        "publishing_rights_received": [],
        "publishing_rights_forwarded": []
      },
      "statistics": "2.5.30",
      "top_n_control_flags": 0
    }
  ],
  "cipher_keys": "9731bf8fe43e4f75bda9623035cfb9afd5238d094b79a5a4e4dba6df89d0c5b0eda3278f5ba998a8917425d19b9e3d1109ef15d7d4bec22aeb473442abbae004767098f23acdec9b9d17f51c90a34a9d424f4accf7575a5fff9e788bc74a3b4350f1dda216c0fa6311f433162363e1b6a2b530d65999516413d6dc0706889a1992d3e7b87ac699f07c11b69cfcf7e2028937962e8f25db8769dcdb3aab9aed464ea6949d0578946adac50bfe3b0da5ff49d1ed7b04422034c5dffa2d0c17f59f76027272dd2a9dbd54e5ebae7a326e755e5984fc36b6d589aa055e2d9078f2ce364a17a0f9e87a90d3b9d1b6efec25c07dad74a1851be7613172a50eb2df2cdaa09545770813e9b9f1e538901b6253790caab5b13a89922841c9a556366cba4263e8da2acfa7fb5bb875f456cb9d3e4958210da8dfaa4cdbac61e19814b35a4db9f493a0770df4e9311d0b67bd13d8ad67462ddfe1f938a4d12e8802614121ca740cb5a71629af63857fe5aeae8d60842afa201445063958f3e7f4eda3b25396209eb79f30935125d0f708e52709be494cf02089724c21fde7d0e29fd28502f6ac470d1ed57a26fbb59d430de7bc42bc1aa25620c2f084f72cef1402eddc75d69ee25a7468d32bc569083074260dfad6bcce761943989e55df406e361e4ab3cc34dfe95a1d6f6b095700d55d1b8ef3257fe5a6d6aefeaef2b825131ca141f5e440f611acb652b1920253952ea469d85dcdcb0b855221a858aa1da8160214eef4a33fbdbaad7aec9c948017ddec07b0d6747ee54df5fd74537e6eecbb19aa22f5e9fd9e542a4ec20650cf9bfefe865699dd74fea33b51a5a5eeb6fb9b2df676569ced5e232501430eb304d05220a9609862b179aa054d13cf630538458b6eb87118e22199f22ccabea811eaed9d9dafa330ce78a97a42c615a443850842d2d3feca23af1dae74f178481414850f20d6857f7ab25a09e25d649df3e865b6b8d51096ef494c38ef5c85f736212a87cea4ead3e147c290fd9ed1b4b19a0ba3fc9b60a9a14d2fb4ea871cbf2fc9197de7968987f23439d860d6c391cd09818126416a20bc9f6e472760261f3bb978202a8b8fa75c7fe483849b7d1ba197deeb55859576a657247b7f27f0919158726f67cd2813bf9d95b2983b320ff1e541ebe61a2de996bb67fa66995db4c9408e374cb8a851b0ec1d144ed7f8f5256c981208d3503ef1f0073110c87829a06bf8b8184ccd",
  "extra_keys": [[
      "1.2.30",[
        "DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",
        "DCT5Vawkes5PFCa4HMXA7kLKccZJrYCPsXfVfTb4NGuM9uu992PuP",
        "DCT6wkHSfnv9qhMJ3K5QKR8HhdJH33CowCb6EwNRKsYQZ5GBVXxNm"
      ]
    ]
  ],
  "pending_account_registrations": [],
  "pending_miner_registrations": [],
  "ws_server": "ws://localhost:8090",
  "ws_user": "",
  "ws_password": ""
}      """

    val credentials = Wallet.importDCoreWallet(json, "pw")
    credentials `should equal` Credentials(account, private.dpk().ecKey())
  }

  @Test fun `dcore wallet export`() {
    val credentials = Credentials(account, private.dpk().ecKey())
    val account = api.getAccountById(credentials.account).blockingGet()

    val wallet = Wallet.exportDCoreWallet(credentials, account, "pw")

    val gson = DCoreApi.gsonBuilder
        .registerTypeAdapter(Wallet.ExtraKeys::class.java, ExtraKeysAdapter)
        .registerTypeAdapter(Wallet.CipherKeyPair::class.java, CipherKeyPairAdapter)
        .create()

    println(gson.toJson(wallet))
  }
}