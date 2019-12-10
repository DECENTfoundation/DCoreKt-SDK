package ch.decent.sdk

import ch.decent.sdk.qr.QrTransfer
import ch.decent.sdk.qr.asQrContent
import ch.decent.sdk.qr.asWalletReceiveQrInput
import org.junit.Test
import kotlin.test.assertEquals

class QrTransferTest {

  private val qrStringEncoded = "decent:all-txs?asset=DCT&amount=1.4&memo=some%20weird%20test%20text%20%3D%20and%20so%20on%20%25%20with%20special%20chars%20%24%20%2C%20%2F%20%3A%20%3B%20%3D%20%3F%20%40%20"
  private val qrString = "decent:all-txs?asset=DCT&amount=1.4&memo=some%20weird%20test%20text%20=%20and%20so%20on%20%25%20with%20special%20chars%20$%20,%20/%20:%20;%20=%20?%20@%20"
  private val memo = "some weird test text = and so on % with special chars $ , / : ; = ? @ "
  private val name = "all-txs"
  private val amount = "1.4"
  private val asset = "DCT"

  @Test fun `create QrTransfer from string with encoded special chars`() {
    val qrTransfer = qrStringEncoded.asWalletReceiveQrInput()
    assertEquals(name, qrTransfer.accountName)
    assertEquals(asset, qrTransfer.assetSymbol)
    assertEquals(amount, qrTransfer.amount)
    assertEquals(memo, qrTransfer.memo)
  }

  @Test fun `create QrTransfer from string with special chars`() {
    val qrTransfer = qrString.asWalletReceiveQrInput()
    assertEquals(name, qrTransfer.accountName)
    assertEquals(asset, qrTransfer.assetSymbol)
    assertEquals(amount, qrTransfer.amount)
    assertEquals(memo, qrTransfer.memo)
  }

  @Test fun `create string from QrTransfer`() {
    val qrTransfer = QrTransfer(name, asset, amount, memo)
    assertEquals(qrStringEncoded, qrTransfer.asQrContent())
  }

  @Test fun `create QrTransfer from string with account name only`() {
    val qrTransfer = name.asWalletReceiveQrInput()
    assertEquals(name, qrTransfer.accountName)
  }

}