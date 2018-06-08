package ch.decent.sdk

import org.junit.Rule
import org.junit.rules.Timeout

abstract class TimeOutTest {
  @Rule @JvmField val timeout: Timeout = Timeout.seconds(10)
}