package ch.decent.sdk

import ch.decent.sdk.model.Account
import org.amshove.kluent.`should be`
import org.junit.Test

class AccountTest {

  @Test fun `should be a valid name`() {
    Account.isValidName("a-name") `should be` true
    Account.isValidName("a1234") `should be` true
    Account.isValidName("hello.world") `should be` true
    Account.isValidName("hello.world.part.two") `should be` true
    Account.isValidName("a-a.b-b.a23") `should be` true
  }
}