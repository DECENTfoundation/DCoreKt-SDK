package ch.decent.sdk.net

import ch.decent.sdk.DCoreSdk
import ch.decent.sdk.Helpers
import ch.decent.sdk.TimeOutTest
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.address
import ch.decent.sdk.model.Account
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.AssetOptions
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ExchangeRate
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.MessagePayload
import ch.decent.sdk.model.MessagePayloadReceiver
import ch.decent.sdk.model.MonitoredAssetOptions
import ch.decent.sdk.model.PubKey
import ch.decent.sdk.model.RegionalPrice
import ch.decent.sdk.model.Regions
import ch.decent.sdk.model.Synopsis
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.operation.AccountCreateOperation
import ch.decent.sdk.model.operation.AccountUpdateOperation
import ch.decent.sdk.model.operation.AddOrUpdateContentOperation
import ch.decent.sdk.model.operation.AssetClaimFeesOperation
import ch.decent.sdk.model.operation.AssetCreateOperation
import ch.decent.sdk.model.operation.AssetFundPoolsOperation
import ch.decent.sdk.model.operation.AssetIssueOperation
import ch.decent.sdk.model.operation.AssetReserveOperation
import ch.decent.sdk.model.operation.LeaveRatingAndCommentOperation
import ch.decent.sdk.model.operation.OperationType
import ch.decent.sdk.model.operation.PurchaseContentOperation
import ch.decent.sdk.model.operation.RemoveContentOperation
import ch.decent.sdk.model.operation.SendMessageOperation
import ch.decent.sdk.model.operation.TransferOperation
import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.net.serialization.Serializer
import ch.decent.sdk.utils.hex
import org.amshove.kluent.`should be equal to`
import org.junit.Test
import org.threeten.bp.LocalDateTime
import java.math.BigInteger

class SerializerTest : TimeOutTest() {

  @Test fun `serialize transfer`() {
    val bytes = "278813000000000000001e1f000000000002018096980000000000000102c03f8e840c1699fd7808c2bb858e249c688c5be8acf0a0c1c484ab0cfb27f0a802e0ced80260630f641f61f6d6959f32b5c43b1a38be55666b98abfe8bafcc556b5521e507000000001086d54a9e1f8fc6e5319dbae0b087b6cc00"
    val priv = ECKeyPair.fromBase58("5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn")
    val address = Address.decode("DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP")

    val op = TransferOperation(
        "1.2.30".toChainObject(),
        "1.2.31".toChainObject(),
        AssetAmount(10000000),
        Memo("hello memo", priv, address, BigInteger("132456789")),
        Fee(amount = 5000)
    )

    Serializer.serialize(op).hex() `should be equal to` bytes
  }

  @Test fun `serialize buy content`() {
    val bytes = "1500000000000000000033697066733a516d61625537574e485a77636f6a674a724352774b68734a7666696e4c54397866666e4b4d4673726343474746501e809698000000000000020000009b01393130383430393539353932363431303631383538343930393638383830363132333831353335303037303838393138373132303036303039303236323639383330353937313939383532363530313030393830343535343035383735383238393637363235373630393334303934393631353931343538333133383834313435363939373639383133333939313030343939313437333637302e"

    val op = PurchaseContentOperation(
        "ipfs:QmabU7WNHZwcojgJrCRwKhsJvfinLT9xffnKMFsrcCGGFP",
        "1.2.30".toChainObject(),
        AssetAmount(10000000),
        PubKey("9108409595926410618584909688806123815350070889187120060090262698305971998526501009804554058758289676257609340949615914583138841456997698133991004991473670")
    )

    Serializer.serialize(op).hex() `should be equal to` bytes
  }

  @Test fun `serialize buy content encoded uri`() {
    val bytes = "150000000000000000006c687474703a2f2f616c61782e696f2f3f736368656d653d616c6c6178253341253246253246636f6d2e6578616d706c652e68656c6c6f776f726c64253341332676657273696f6e3d37663438623234652d386162392d343831302d386239612d3933363134366164366164321e00e1f5050000000000020000009b01353138323534353438383331383039353030303439383138303536383533393732383231343534353437323437303937343935383333383934323432363735393531303132313835313730383533303632353932313433363737373535353531373238383133393738373936353235333534373538383334303830333534323736323236383732313635363133383837363030323032383433372e"

    val op = PurchaseContentOperation(
        "http://alax.io/?scheme=allax%3A%2F%2Fcom.example.helloworld%3A3&version=7f48b24e-8ab9-4810-8b9a-936146ad6ad2",
        "1.2.30".toChainObject(),
        AssetAmount(100000000),
        PubKey("5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437")
    )

    Serializer.serialize(op).hex() `should be equal to` bytes
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
    val account = DCoreSdk.gsonBuilder.create().fromJson(json, Account::class.java)

    val op = AccountUpdateOperation(
        account.id,
        null,
        null,
        account.options,
        Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` bytes
  }

  @Test fun `serialize create account`() {
    val bytes = "010000000000000000001b086d696b656565656501000000000102a01c045821676cfc191832ad22cc5c9ade0ea1760131c87ff2dd3fed2f13dd33010001000000000102a01c045821676cfc191832ad22cc5c9ade0ea1760131c87ff2dd3fed2f13dd33010002a01c045821676cfc191832ad22cc5c9ade0ea1760131c87ff2dd3fed2f13dd330300000000000000000000000000000000000000"

    val op = AccountCreateOperation(Helpers.account, "mikeeeee", "DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU".address())

    Serializer.serialize(op).hex() `should be equal to` bytes
  }

  @Test fun `should serialize add new content or update operation`() {
    val expected = "140000000000000000000100000000000000220016687474703a2f2f68656c6c6f2e696f2f776f726c6432000000000101000000e80300000000000000222222222222222222222222222222222222222200007238ed5c0000000000000000004c7b227469746c65223a2247616d65205469746c65222c226465736372697074696f6e223a224465736372697074696f6e222c22636f6e74656e745f747970655f6964223a22312e352e35227d00"

    val op = AddOrUpdateContentOperation(
        author = ChainObject.parse("1.2.34"),
        uri = "http://hello.io/world2",
        price = listOf(RegionalPrice(AssetAmount(1000), Regions.NONE.id)),
        expiration = LocalDateTime.parse("2019-05-28T13:32:34"),
        synopsis = Synopsis("Game Title", "Description", "1.5.5".toChainObject()).json,
        hash = "2222222222222222222222222222222222222222"
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize remove existing content operation`() {
    val expected = "200000000000000000002216687474703a2f2f68656c6c6f2e696f2f776f726c6432"

    val op = RemoveContentOperation(
        ChainObject.parse("1.2.34"),
        "http://hello.io/world2"
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize transfer op transaction`() {
    val expected = "3e322ef4e4170c88615b012720a10700000000000022230000000000020160e3160000000000000102c03f8e840c1699fd7808c2bb858e249c688c5be8acf0a0c1c484ab0cfb27f0a802e0ced80260630f641f61f6d6959f32b5c43b1a38be55666b98abfe8bafcc556b002ea2558d64350a204bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a70000"
    val gson = DCoreSdk.gsonBuilder.create()
    val rawOp = """{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7","nonce":735604672334802432},"fee":{"amount":500000,"asset_id":"1.3.0"}}"""
    val op = gson.fromJson(rawOp, TransferOperation::class.java)
    op.type = OperationType.TRANSFER2_OPERATION

    val trx = Transaction(
        listOf(op),
        LocalDateTime.parse("2018-08-01T10:14:36"),
        12862,
        400880686,
        "17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc")

    Serializer.serialize(trx).hex() `should be equal to` expected
  }


  @Test fun `serialize send message op`() {
    val bytes = "1222a1070000000000001b011b01009e027b2266726f6d223a22312e322e3237222c227265636569766572735f64617461223a5b7b22746f223a22312e322e3238222c2264617461223a2263646132383562326165653737303435343233333661633236626339613361666333393735363562616531353561316535663766353131393338326434303633222c227075625f746f223a224443543550776353696967665450547775626164743835656e784d464331385474566f746933676e54624737544e39663952334670222c226e6f6e6365223a343736343232313338393335393932363237327d5d2c227075625f66726f6d223a2244435436546a4c6872387545537667747872625775584e414e337663717a424d7735657945757033504d694432676e567865755462227d"

    val keyPair = ECKeyPair.fromBase58(Helpers.private)
    val memo = Memo("hello messaging api", keyPair, Helpers.public2.address(), 4764221389359926272.toBigInteger())
    val payloadReceiver = MessagePayloadReceiver(Helpers.account2, memo.message, Helpers.public2.address(), memo.nonce)
    val payload = MessagePayload(Helpers.account, listOf(payloadReceiver), Helpers.public.address())
    val json = DCoreSdk.gsonBuilder.create().toJson(payload)
    val op = SendMessageOperation(json, Helpers.account, fee = Fee(amount = 500002))

    Serializer.serialize(op).hex() `should be equal to` bytes
  }

  @Test fun `should serialize rate and comment operation`() {
    val expected = "1600000000000000000033697066733a516d57426f52425975787a48356138643367737352624d53357363733666714c4b676170426671564e554655745a1b07636f6d6d656e740100000000000000"

    val op = LeaveRatingAndCommentOperation(
        "ipfs:QmWBoRBYuxzH5a8d3gssRbMS5scs6fqLKgapBfqVNUFUtZ", Helpers.account, 1, "comment"
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize create asset operation`() {
    val expected = "0320a1070000000000001b0353444b010968656c6c6f20617069fad456864c011a000100000000000000000100000000000000e70701010100000100"

    val ex = ExchangeRate(AssetAmount(1), AssetAmount(1, ChainObject.parse("1.3.999")))
    val op = AssetCreateOperation(Helpers.account, "SDK", 1, "hello api", AssetOptions(ex))
    op.fee = AssetAmount(500000)

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize create monitored asset operation`() {
    val expected = "03a086010000000000001b0453444b4d041368656c6c6f20617069206d6f6e69746f7265640000000000000000000000000000000000000000000000000000010101000100000000000000000000000000000000000000480fb65c80510100010100"

    val opt = MonitoredAssetOptions(currentFeedPublicationTime = LocalDateTime.parse("2019-04-16T17:22:16"))
    val op = AssetCreateOperation(Helpers.account, "SDKM", 4, "hello api monitored", AssetOptions(ExchangeRate.EMPTY, 0), opt)
    op.fee = AssetAmount(100000)
    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize issue asset operation`() {
    val expected = "040a00000000000000001b0a00000000000000241b0000"

    val op = AssetIssueOperation(Helpers.account, AssetAmount(10, ChainObject.parse("1.3.36")), Helpers.account)
    op.fee = AssetAmount(10)
    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize reserve asset operation`() {
    val expected = "220a00000000000000001b0a000000000000002400"

    val op = AssetReserveOperation(Helpers.account, AssetAmount(10, ChainObject.parse("1.3.36")))
    op.fee = AssetAmount(10)
    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize fund asset pool operation`() {
    val expected = "210a00000000000000001b0a00000000000000240a000000000000000000"

    val op = AssetFundPoolsOperation(Helpers.account, AssetAmount(10, ChainObject.parse("1.3.36")), AssetAmount(10))
    op.fee = AssetAmount(10)
    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize claim asset pool operation`() {
    val expected = "230a00000000000000001b0a00000000000000240a000000000000000000"

    val op = AssetClaimFeesOperation(Helpers.account, AssetAmount(10, ChainObject.parse("1.3.36")), AssetAmount(10))
    op.fee = AssetAmount(10)
    Serializer.serialize(op).hex() `should be equal to` expected
  }

}
