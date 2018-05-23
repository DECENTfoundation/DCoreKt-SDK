package ch.decent.sdk.net

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.account
import ch.decent.sdk.account2
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.address
import ch.decent.sdk.model.*
import ch.decent.sdk.utils.hex
import org.amshove.kluent.`should be equal to`
import org.junit.Test
import java.math.BigInteger

class Serializer {

  @Test fun `serialize transfer`() {
    val bytes = "008813000000000000001e1f8096980000000000000102c03f8e840c1699fd7808c2bb858e249c688c5be8acf0a0c1c484ab0cfb27f0a802e0ced80260630f641f61f6d6959f32b5c43b1a38be55666b98abfe8bafcc556b5521e507000000001086d54a9e1f8fc6e5319dbae0b087b6cc00"
    val priv = ECKeyPair.fromBase58("5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn")
    val address = Address.decode("DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP")

    val op = TransferOperation(
        "1.2.30".toChainObject(),
        "1.2.31".toChainObject(),
        AssetAmount(10000000),
        Memo("hello memo", priv, address, BigInteger("132456789"))
    ).apply { fee = AssetAmount(5000) }

    op.bytes.hex() `should be equal to` bytes

  }

  @Test fun `serialize buy content`() {
    val bytes = "1500000000000000000033697066733a516d61625537574e485a77636f6a674a724352774b68734a7666696e4c54397866666e4b4d4673726343474746501e809698000000000000010000009b01393130383430393539353932363431303631383538343930393638383830363132333831353335303037303838393138373132303036303039303236323639383330353937313939383532363530313030393830343535343035383735383238393637363235373630393334303934393631353931343538333133383834313435363939373639383133333939313030343939313437333637302e"

    val op = BuyContentOperation(
        "ipfs:QmabU7WNHZwcojgJrCRwKhsJvfinLT9xffnKMFsrcCGGFP",
        "1.2.30".toChainObject(),
        AssetAmount(10000000),
        PubKey("9108409595926410618584909688806123815350070889187120060090262698305971998526501009804554058758289676257609340949615914583138841456997698133991004991473670")
    )

    op.bytes.hex() `should be equal to` bytes

  }

  @Test fun `serialize buy content encoded uri`() {
    val bytes = "150000000000000000006c687474703a2f2f616c61782e696f2f3f736368656d653d616c6c6178253341253246253246636f6d2e6578616d706c652e68656c6c6f776f726c64253341332676657273696f6e3d37663438623234652d386162392d343831302d386239612d3933363134366164366164321e00e1f5050000000000010000009b01353138323534353438383331383039353030303439383138303536383533393732383231343534353437323437303937343935383333383934323432363735393531303132313835313730383533303632353932313433363737373535353531373238383133393738373936353235333534373538383334303830333534323736323236383732313635363133383837363030323032383433372e"

    val op = BuyContentOperation(
        "http://alax.io/?scheme=allax%3A%2F%2Fcom.example.helloworld%3A3&version=7f48b24e-8ab9-4810-8b9a-936146ad6ad2",
        "1.2.30".toChainObject(),
        AssetAmount(100000000),
        PubKey("5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437")
    )

    op.bytes.hex() `should be equal to` bytes

  }

  @Test fun `serialize vote`() {
    val bytes = "0220a1070000000000002200000102c03f8e840c1699fd7808c2bb858e249c688c5be8acf0a0c1c484ab0cfb27f0a803000002000500000008000000000000000000000000000000000000"

    val json = """
      {
  "id": "1.2.34",
  "registrar": "1.2.15",
  "name": "u961279ec8b7ae7bd62f304f7c1c3d345",
  "owner": {
    "weight_threshold": 1,
    "account_auths": [],
    "key_auths": [
      [
        "DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",
        1
      ]
    ]
  },
  "active": {
    "weight_threshold": 1,
    "account_auths": [],
    "key_auths": [
      [
        "DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",
        1
      ]
    ]
  },
  "options": {
    "memo_key": "DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",
    "voting_account": "1.2.3",
    "num_miner": 0,
    "votes": [
      "0:5",
      "0:8"
    ],
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
  "statistics": "2.5.34",
  "top_n_control_flags": 0
}
      """
    val account = DCoreApi.gsonBuilder.create().fromJson(json, Account::class.java)

    val op = AccountUpdateOperation(
        account.id,
        null,
        null,
        account.options).apply { fee = AssetAmount(500000) }

    op.bytes.hex() `should be equal to` bytes
  }

  @Test fun `serialize create account`() {
    val bytes = "0100000000000000000022086d696b656565656501000000000102a01c045821676cfc191832ad22cc5c9ade0ea1760131c87ff2dd3fed2f13dd33010001000000000102a01c045821676cfc191832ad22cc5c9ade0ea1760131c87ff2dd3fed2f13dd33010002a01c045821676cfc191832ad22cc5c9ade0ea1760131c87ff2dd3fed2f13dd330300000000000000000000000000000000000000"

    val op = AccountCreateOperation(account, "mikeeeee", "DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU".address())
        .apply { fee = AssetAmount(0) }

    op.bytes.hex() `should be equal to` bytes
  }
}