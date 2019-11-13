package ch.decent.sdk.model

import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal`
import org.junit.Test

class ObjectIdTest {

  @Test fun `test object ids equals`() {
    val accountId = "1.2.34".toObjectId<AccountObjectId>()
    val genericId = ObjectId.parse("1.2.34")

    accountId `should equal` genericId
  }
}
