package ch.decent.sdk.mnemonic

import ch.decent.sdk.TimeOutTest
import ch.decent.sdk.crypto.Passphrase
import ch.decent.sdk.crypto.base58
import ch.decent.sdk.crypto.generatePrivateFromPassPhrase
import ch.decent.sdk.print
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PassphraseTest : TimeOutTest() {

  @Test fun `load default seed`() {
    val passphrase = Passphrase.generate()
    passphrase.print()
  }

  @Test fun `generate English normalized brain key test`() {
    val passphrase = Passphrase.generate(ENGLISH_WORD_LIST)
    assertEquals(16, passphrase.size)
    passphrase.toString().split(" ").forEach {
      assertTrue { ENGLISH_WORD_LIST.contains(it.toLowerCase()) }
      assertEquals(1, "^[A-Z]*\$".toRegex().matchEntire(it)!!.groupValues.size)
    }
  }

  @Test fun `generate English brain key test`() {
    val passphrase = Passphrase.generate(ENGLISH_WORD_LIST, normalize = false)
    assertEquals(16, passphrase.size)
    passphrase.toString().split(" ").forEach {
      assertTrue { ENGLISH_WORD_LIST.contains(it) }
      assertNull("^[A-Z]*\$".toRegex().matchEntire(it))
    }
  }

  @Test fun `generate Chinese simplified normalized brain key test`() {
    val passphrase = Passphrase.generate(CHINESE_SIMPLIFIED_WORD_LIST)
    assertEquals(16, passphrase.size)
    passphrase.toString().split(" ").forEach {
      assertTrue { CHINESE_SIMPLIFIED_WORD_LIST.contains(it) }
    }
  }

  @Test fun `generate Chinese simplified brain key test`() {
    val passphrase = Passphrase.generate(CHINESE_SIMPLIFIED_WORD_LIST, normalize = false)
    assertEquals(16, passphrase.size)
    passphrase.toString().split(" ").forEach {
      assertTrue { CHINESE_SIMPLIFIED_WORD_LIST.contains(it) }
    }
  }

  @Test fun `generate Chinese traditional normalized brain key test`() {
    val passphrase = Passphrase.generate(CHINESE_TRADITIONAL_WORD_LIST)
    assertEquals(16, passphrase.size)
    passphrase.toString().split(" ").forEach {
      assertTrue { CHINESE_TRADITIONAL_WORD_LIST.contains(it) }
    }
  }

  @Test fun `generate Chinese traditional brain key test`() {
    val passphrase = Passphrase.generate(CHINESE_TRADITIONAL_WORD_LIST, normalize = false)
    assertEquals(16, passphrase.size)
    passphrase.toString().split(" ").forEach {
      assertTrue { CHINESE_TRADITIONAL_WORD_LIST.contains(it) }
    }
  }

  @Test fun `generate private key from normalized brain key starting with LORDING`() {
    val brainKey = arrayOf("LORDING", "TALAR", "ZOONAL", "PIBROCH", "MUFFLER", "SORGHO", "SKIRTY", "CARPER", "SCROBE", "EVENS", "ESERE", "CHAUTE", "ACKER", "AVINE", "BERHYME", "CANIONS")
    val expectedKey = "5J1XV99mVwsd5n6zhwvDV1KnhC17opdxDtnHg3ut57KKVJrGm6m"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from brain key starting with lording`() {
    val brainKey = arrayOf("lording", "talar", "zoonal", "pibroch", "muffler", "sorgho", "skirty", "carper", "scrobe", "evens", "esere", "chaute", "acker", "avine", "berhyme", "canions")
    val expectedKey = "5J6npTJWXCr4J4pahKdQstvzifgtsNQGgnAayGxK8AV1m9JHcS8"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey), normalized = false).base58())
  }

  @Test fun `generate private key from normalized normalized brain key starting with lording`() {
    val brainKey = arrayOf("lording", "talar", "zoonal", "pibroch", "muffler", "sorgho", "skirty", "carper", "scrobe", "evens", "esere", "chaute", "acker", "avine", "berhyme", "canions")
    val expectedKey = "5J1XV99mVwsd5n6zhwvDV1KnhC17opdxDtnHg3ut57KKVJrGm6m"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key for normalized chinese brain key`() {
    val brainKey = arrayOf("低", "仅", "单", "牛", "原", "做", "运", "就", "识", "星", "创", "哈", "纸", "降", "吸", "根")
    val expectedKey = "5JPvvEDbVJjUD4Jfj4kLt7A6MaLadbSJWGxvPnJfV873QCc9HwX"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key for chinese brain key`() {
    val brainKey = arrayOf("低", "仅", "单", "牛", "原", "做", "运", "就", "识", "星", "创", "哈", "纸", "降", "吸", "根")
    val expectedKey = "5JPvvEDbVJjUD4Jfj4kLt7A6MaLadbSJWGxvPnJfV873QCc9HwX"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey), normalized = false).base58())
  }

  @Test fun `generate private key from normalized brain key starting with SWANKY`() {
    val brainKey = arrayOf("SWANKY", "CULBUT", "QUIRK", "BESTER", "SOWL", "UNCEDED", "MUSIMON", "PANTILE", "UNCITY", "SANDBUR", "TUTORY", "PRIME", "GILLED", "VOLUTE", "PLUVINE", "WATTLED")
    val expectedKey = "5JrwhLye4HB9mZp2sQmT8FG4rp5iiSfUXFFtYzMvgHT5bYaF2tG"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key for lowercase brain key starting with swanky`() {
    val brainKey = arrayOf("swanky", "culbut", "quirk", "bester", "sowl", "unceded", "musimon", "pantile", "uncity", "sandbur", "tutory", "prime", "gilled", "volute", "pluvine", "wattled")
    val expectedKey = "5JrwhLye4HB9mZp2sQmT8FG4rp5iiSfUXFFtYzMvgHT5bYaF2tG"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with QUIETER`() {
    val brainKey = arrayOf("QUIETER", "LOUTY", "LUCKILY", "RECOOL", "MANO", "CHARER", "TOGLESS", "FLUKING", "HAPTENE", "UNREAD", "BOOKED", "LAVAGE", "ADRENIN", "TSARINA", "ASAPHIA", "CRUTTER")
    val expectedKey = "5KAobLVbRKwgZKzK8coT4JkwdYeSTFQFm9NkFQCs7anT5uxHbnk"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with WITSHIP`() {
    val brainKey = arrayOf("WITSHIP", "HUZOOR", "TEMENOS", "GORSE", "BELAM", "TANJIB", "BICE", "GOSPEL", "UNQUEEN", "LANKLY", "ACROBAT", "SERVE", "BERSEEM", "CARPENT", "BAREFIT", "LARYNX")
    val expectedKey = "5JnYXpCZfZjc4pTW3Nux56jBUSJ2hnWbnxDkLk2ph7GvaHvi2pg"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with ARPENT`() {
    val brainKey = arrayOf("ARPENT", "SCORN", "EXACTLY", "BEDRUG", "MEMORIA", "ENGAUD", "DISHFUL", "GUMMOUS", "INANE", "BIRTH", "CEPA", "CUDWEED", "PORTER", "HERMIT", "SLOSH", "EDIBLE")
    val expectedKey = "5K8arHh5htpRxZXgvrasNiygijQod2g4yNew2UbN7ngQridc1PL"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with OPALISH`() {
    val brainKey = arrayOf("OPALISH", "VERNAL", "EXOGEN", "FATUISM", "ZOMBIE", "CRIME", "YEGG", "COACHEE", "WARRIN", "CORTEX", "ANALYST", "DILATED", "RISPER", "YUTU", "COTTOID", "PILM")
    val expectedKey = "5KfYZH8LX3UZheaUoCTXZPgy29Dvve6875P4DWo52C8QwX1xopp"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with PUSSCAT`() {
    val brainKey = arrayOf("PUSSCAT", "UNLUTE", "REBOISE", "REMIMIC", "AVAHI", "BURNOUS", "PEDRO", "RELAMP", "POLLAGE", "GABY", "INLIER", "SNOOZLE", "ROMAINE", "FERRITE", "DAEMON", "REMELT")
    val expectedKey = "5KREFgMSk93fAQuemiNuU1axEKKzs9qnk9LPkRhvHbWEDgMxhvR"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with TAURIC`() {
    val brainKey = arrayOf("TAURIC", "MISDEAL", "GYN", "HAMALD", "WALAHEE", "ARCADE", "WIDDY", "POWDIKE", "GROOSE", "COWRIE", "TYND", "UPSTARE", "SAYA", "NAVITE", "DRIPPER", "WOG")
    val expectedKey = "5JfRK3G363W59U13JXCp1Q7suCwvG7hkQG4KusY5zfn4Wc9nE6N"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with BOLSON`() {
    val brainKey = arrayOf("BOLSON", "SETBOLT", "PRUNTED", "SHIPPER", "LANOLIN", "REPAGE", "TANGHAN", "REDDEN", "REGAUGE", "UNGYVE", "REMOVED", "FICHE", "CLIPEI", "URAZINE", "COADORE", "MOBSTER")
    val expectedKey = "5JnV3szBr5jFJrpWyZP9FsfXLXjQPi1g4RaLUTFokKAZoDZNjL4"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with GNAWN`() {
    val brainKey = arrayOf("GNAWN", "ROUNDUP", "DIPWARE", "ALQUIER", "MASKER", "JIGMAN", "BELTING", "ORCHIL", "PISCIAN", "READORN", "SLADANG", "OPIANIC", "QINTAR", "MIMER", "TYPICA", "REPRINT")
    val expectedKey = "5JzZVsKFma5v3Ym3JvcSTehVU6WxaZbp6H1YnSU8RnMvH2gDwpC"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with ANALYSE`() {
    val brainKey = arrayOf("ANALYSE", "PIE", "MADWORT", "HENTER", "FESTAL", "ANTOECI", "AHINT", "MELOE", "JUGALE", "COCKER", "SORRA", "KMET", "HURLEY", "IVYWOOD", "OFFTYPE", "BUDA")
    val expectedKey = "5KVyEhTSpGdJwndzLwmanaWj75NSpnwVAgeJQKhWGNNzJ4G12Me"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with TELLING`() {
    val brainKey = arrayOf("TELLING", "BRASS", "BECHECK", "WHILE", "OOPOD", "QUARL", "SNIRTLE", "WOLFEN", "DARNED", "DARGAH", "DOLLISH", "REDRAG", "ALCHIMY", "JAGGY", "ICOTYPE", "TOAST")
    val expectedKey = "5KFHEuMGNFbS2L2AiWh84e6nxAhTKhPCB7EjtFmtDCyc5G3HpJb"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with EVENT`() {
    val brainKey = arrayOf("EVENT", "RHINE", "REVIVAL", "UNCUT", "OUTDURE", "CARROCH", "SPECTRA", "LATITE", "STUMBLY", "GEODIST", "DHANUK", "BIVOCAL", "POOPED", "ANALGIC", "MOELLON", "DORHAWK")
    val expectedKey = "5K2UD9qk8UXETGjXvLmX88LM3wEHLS9QH4z9EHiX8aQfxjKBfKc"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with VAREC`() {
    val brainKey = arrayOf("VAREC", "SOOL", "TOAST", "DEFEAT", "CHANCE", "TUGMAN", "KILT", "FUSION", "GETUP", "UNWEDGE", "TERBIUM", "SANGA", "HOSTAGE", "BREED", "MAHSEER", "SCIARID")
    val expectedKey = "5JpTSs4DhxMPrZ1MErSg6dr7PqF7p9f2Z9N8kqz2hwRqbEBYnAK"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with ORIEL`() {
    val brainKey = arrayOf("ORIEL", "IRONMAN", "SHALLON", "VANISH", "CHROMIC", "SLOPING", "AWNING", "AKOASMA", "XYLIC", "RAMAGE", "FECK", "MONADIC", "BEBED", "RUSSUD", "FORANE", "PRAISE")
    val expectedKey = "5JXZzi651GwDeFKYZ98ivq2bamyE9zjAXxi6hXEDEfvxHDDhRRL"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with VAMPED`() {
    val brainKey = arrayOf("VAMPED", "SHEAF", "NODDER", "STAIR", "GYPE", "ELFSHIP", "BRITTEN", "INPORT", "PHON", "BAYBUSH", "COOJA", "RENEGE", "MUTA", "DISFAME", "Y", "FRENAL")
    val expectedKey = "5K9gNn7XwRRR3pfSuVmVrrfWy7CyVnZE3mfZoppv2foY116k39R"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with PARSON`() {
    val brainKey = arrayOf("PARSON", "JEWBIRD", "SLIDAGE", "BYGONE", "ROGUING", "BOKARK", "WARMTH", "BILCH", "RIBBER", "GENISTA", "CUDDY", "TUSCHE", "MALL", "DOUDLE", "VOLENCY", "CLOACAL")
    val expectedKey = "5KMFry3FPvSeYyA4pgP24NzENAye1HDWv71tQHDv5aonLF4fyW7"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with TURTOSA`() {
    val brainKey = arrayOf("TURTOSA", "WRYLY", "REBATHE", "ANTHEMA", "PATU", "SUBBEAU", "UNADAPT", "GAPA", "ADIPATE", "ATHAR", "JOSS", "KELT", "BLENT", "SAMOVAR", "FIBULAR", "OAFISH")
    val expectedKey = "5KZ5931QcXEv8rMvpYcR3Mx69i9bLdoiHjGxpG3FxdE7xFTMA67"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with AMATIVE`() {
    val brainKey = arrayOf("AMATIVE", "LEANING", "LADEN", "LOCA", "PUMICED", "BLOTTO", "SKEWLY", "CADELLE", "CIMELIA", "SLANTLY", "BOIST", "GORFLY", "PIRNER", "MOUL", "SERT", "WOODISH")
    val expectedKey = "5J6Vd7U8FQHpCydgRFKc8X6KY2HXoi28t8T449vXWfVVGNuZkqb"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with OATLIKE`() {
    val brainKey = arrayOf("OATLIKE", "BIOS", "PEMBINA", "ZAPAS", "MYSTAX", "REALLOT", "SQUAME", "PITH", "DANCING", "KETTY", "LOSS", "CRANKED", "HOOF", "BOLT", "INTOWN", "AMIDASE")
    val expectedKey = "5KUjLvHV4FnJ2qAUYs3TdyhHEAHktEdTticckwST8598ZDY2hT4"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with HIPPED`() {
    val brainKey = arrayOf("HIPPED", "SLEDFUL", "MOCMAIN", "TENACE", "WAYBACK", "ZOONAL", "INDRAWN", "SHRIMPY", "SIRRAH", "EXHIBIT", "DOWED", "TEALESS", "GOSSOON", "FORFARS", "TRILABE", "AGAMIC")
    val expectedKey = "5Jen6kmS2z8cmDj8L7Pgb2wtdkAHzVs8voVTMVmAYnDirvHMpon"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with SPOFFLE`() {
    val brainKey = arrayOf("SPOFFLE", "TWAS", "PALSY", "BINAL", "ROSETTY", "HORMIC", "PLUCKED", "JOUSTER", "BROGUE", "PICKED", "HIEROS", "UROHYAL", "ZOOLITE", "QUAGGY", "TRANCE", "DILLING")
    val expectedKey = "5Jxxoy1xxcCsGFPRwrL8knvTgRSitsB8XdSMZ3xGHjdNxinmwGV"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with YUFT`() {
    val brainKey = arrayOf("YUFT", "AMIRAY", "HOYMAN", "REVUIST", "FRESHLY", "SLEEKY", "PARTED", "TESTATE", "PASTE", "BUCCATE", "BEKNIT", "URMAN", "HEEDILY", "MISSEL", "MODE", "RANGEY")
    val expectedKey = "5JDtxEJzpSGCbHU9V8cwVSSqakjpnn5wkK1mXhavBrczrVj5Bo2"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with SISTERN`() {
    val brainKey = arrayOf("SISTERN", "SLANGY", "SILICYL", "LABROSE", "ANKLONG", "VOUCHEE", "HEPATIC", "KISSAGE", "GRAZER", "JUTKA", "SUNDIK", "RHEME", "FOULLY", "BLESBOK", "SERMON", "RODENT")
    val expectedKey = "5JNMmuasxkoZPMHWRB19HzKLMGHwZwNqxMjGeep1UjPs5Ci4UsE"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with CADRE`() {
    val brainKey = arrayOf("CADRE", "COUSIN", "COOLISH", "STABLER", "AWARE", "THOF", "VAIRAGI", "LANSEH", "WOUNDER", "RECESS", "FOXSHIP", "STEVEL", "ETYPIC", "THIASUS", "MAGNETA", "PINITOL")
    val expectedKey = "5JSSAmURFRf57PQr8XmkPUKuZq8jWQkFh5v8nFV1wdwC6M52MGk"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with OBITUAL`() {
    val brainKey = arrayOf("OBITUAL", "AZULITE", "SMUGISM", "UNFROCK", "CLAPNET", "SOMEHOW", "TIDDLEY", "BEFLAP", "AIDLESS", "PATELLA", "PHIZES", "INDYL", "ASCARID", "YARRAN", "ELEMIN", "REBASIS")
    val expectedKey = "5JvGtcMJp5LukiU7gQ14uWeYqytRHSbJsaByQ5SPHWnvBcdQeki"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with JARKMAN`() {
    val brainKey = arrayOf("JARKMAN", "FITTERS", "TROCAR", "TURFDOM", "ADATOM", "TOSSY", "SKATIKU", "BULLER", "RODD", "ACID", "DROWSE", "SCOTALE", "SMUR", "TANTRUM", "ERUCT", "PURIRI")
    val expectedKey = "5HyrPDogx9ZKvsLxKA3xaCQpR3ooNbVVXeXUT17NtXNqXPKLLF3"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with CEPS`() {
    val brainKey = arrayOf("CEPS", "BEDWAYS", "SASHING", "LANKY", "BETRAIL", "PERPEND", "LAIC", "OUTLOVE", "FADEDLY", "CONCEAL", "PLATINA", "RUTTEE", "UPHELYA", "SAB", "POCKY", "CHIRAL")
    val expectedKey = "5J6pvwqwx1C7ctbhnJLDUnYHnHz6itvqy8PF2uBSPpySM2c7QbT"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with VECTION`() {
    val brainKey = arrayOf("VECTION", "CAPRONE", "MARLOCK", "VISILE", "TEDIUM", "CAITIFF", "EFFABLE", "DRUCKEN", "RELATER", "TOKEN", "CANOPIC", "TORSO", "TAIGLE", "KOKAKO", "ICICLE", "TORPENT")
    val expectedKey = "5JfUgm1hMxrXWwce237e4ju9XBzpb8FXcwwEFDno1oADStEat3G"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with MUSSAL`() {
    val brainKey = arrayOf("MUSSAL", "MUFF", "ASHERAH", "KOVIL", "APEREA", "JOYANCY", "UNCIAL", "UKULELE", "WOOABLE", "BIBBLER", "PYKE", "OUTERLY", "THUOC", "IDYLIST", "ROWDY", "CHILLER")
    val expectedKey = "5JPzUzQTcN7bs8FdqjsHF39NnjUJ73m2SXeczXXQrM9BbXk2KPJ"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with OVA`() {
    val brainKey = arrayOf("OVA", "GUFA", "WEBBY", "UPCHUCK", "MIL", "DRAWOUT", "HOOVEY", "BRAIL", "TOFT", "CALKIN", "INSIST", "AZOTIZE", "LABRUM", "SKANCE", "FAIRISH", "CRAPPLE")
    val expectedKey = "5JaRtaDcxWtvoWfo4gWDbRUQXG2dZsMwXtwaDRepuvjpnp4uRsi"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with RETILL`() {
    val brainKey = arrayOf("RETILL", "VIERTEL", "REBEGIN", "UVIC", "FLAKER", "SNOUT", "BORSHT", "SWURE", "RESHINE", "TINLIKE", "ERECT", "LOUGH", "TALLOWY", "INFARE", "OUTTASK", "OWRELAY")
    val expectedKey = "5KGk5Mb72BwFUbXh4X8GcrLXnDqoRmedocZiZBjKXj8DF1X8gTy"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with UPMOVE`() {
    val brainKey = arrayOf("UPMOVE", "JAMBEAU", "MAKO", "HORNILY", "ATWIXT", "GLOOM", "ORANG", "COPPING", "ARCANUM", "YAHAN", "LOPPY", "MUSTIFY", "FEUED", "VANITY", "VAGUELY", "ENVELOP")
    val expectedKey = "5HpeAM23qqMtmFCrUT7zKMAWdBVv5cK4DJ7W6q3Abr2PNTpWS1p"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with NECTAR`() {
    val brainKey = arrayOf("NECTAR", "INDICES", "TWITTEN", "HORMIGO", "HOWADJI", "INVEIL", "FOUGHTY", "WINNEL", "AMIDIN", "GIFT", "RANCH", "UNFIRM", "LIAISON", "REDAUB", "PEDEE", "SHULER")
    val expectedKey = "5JFqc23GmoNG4f1qGZNbXBcmaxNPUti4GCSYnAz9HZGfWwyeeer"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with IRONER`() {
    val brainKey = arrayOf("IRONER", "FRANKER", "SCRUFFY", "PECKLE", "ROTULUS", "CANTLE", "PIABA", "MISPLAY", "SINGED", "RESPRAY", "CHELONE", "COCK", "ESPY", "RECALK", "LOAM", "TRAYFUL")
    val expectedKey = "5J7m2U9dQnYEanbL8vQoEtpnRx5iR6EyhFN7UWzUBjyZDLwksZn"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with WAYGONE`() {
    val brainKey = arrayOf("WAYGONE", "COWAL", "BUSKET", "MANLIKE", "BIDDING", "GORCROW", "TUCHIT", "RHODING", "RUN", "PILARY", "PILAR", "SHAFTER", "FIGENT", "CLAVUS", "BECUIBA", "TUART")
    val expectedKey = "5JHAPE3GL3rA4wJqBJtsmx11V89zLhAZy1CzFqmNfCw8gHu1Bos"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with EBONIST`() {
    val brainKey = arrayOf("EBONIST", "CUFFIN", "CAPIVI", "FAINS", "TAZIA", "NICEISH", "SIALOID", "TETTER", "PHOBIC", "HURST", "ARCHEAL", "BABELET", "ANONOL", "PINKED", "DIVORCE", "LESSER")
    val expectedKey = "5KGTUXHSxVK61XhKsGkTA6PrhGejnr97qBTffMBLL7fKN9bbMFi"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with SPILOMA`() {
    val brainKey = arrayOf("SPILOMA", "BEGLOOM", "CENTIME", "DASTARD", "TOE", "CRUMBLY", "REE", "INHIBIT", "ARZUN", "PURSE", "HAET", "AZURITE", "DEVICE", "BEDFOOT", "TOWING", "PIGROOT")
    val expectedKey = "5JNsLFTpia3C7facyxMtKD9UhUCFhFSPTTuup2SEdVSusAvdEdU"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with TRIKER`() {
    val brainKey = arrayOf("TRIKER", "PARD", "BESTUD", "PINHOLE", "USAGE", "OCTAGON", "WOPPISH", "INFANTA", "ACARINE", "DOPER", "QUABIRD", "JAGER", "SIMOUS", "BRIEFLY", "MISREAD", "GUARA")
    val expectedKey = "5KhnjqS7Xa6jH5NywJ1cYYG1Lv6g9dGPadZH91uB6pCsptC34z8"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with TAGSORE`() {
    val brainKey = arrayOf("TAGSORE", "SAMEN", "WASH", "ASCOMA", "FAVILLA", "RESING", "UNTRITE", "MIMICRY", "DANAIDE", "PHORID", "CARBRO", "STRAWY", "MARE", "DEMAGOG", "MOIL", "ONCOME")
    val expectedKey = "5Jq4PWerzJE2pWaXApY9PtUuGGvZGvz3jqmvxiYfihN7pvAmRiv"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with DECIMA`() {
    val brainKey = arrayOf("DECIMA", "RUNLET", "DOLMEN", "NOON", "UNPOT", "STOOK", "WHASE", "REPORT", "BUNA", "VAGRANT", "ARARAO", "APORT", "SORDA", "BEPINCH", "FLUXER", "ACIDYL")
    val expectedKey = "5KAgKUBZ3RyebVneFgaRiLMGFrbMGjT8TdQ5J38Qcp46GEm2Ayn"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with STAR`() {
    val brainKey = arrayOf("STAR", "BIOSIS", "LOCOISM", "DEN", "ZOLLE", "COUPLED", "CNIDA", "TUSCHE", "CODER", "WALKOUT", "BALL", "IDLISH", "WHIZGIG", "CAB", "BRAWNED", "HUNKIES")
    val expectedKey = "5KcvF5ZFK4Ci3KuXzUFTcdt85JBjjRispnchqsBgEgkUkBpfgmJ"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with XOANA`() {
    val brainKey = arrayOf("XOANA", "GUNDI", "SANE", "ORATE", "SOFAR", "SEARY", "WOUNDS", "FEIF", "VELO", "ZENU", "ONICOLO", "SYMBION", "BRAWN", "BITOLYL", "BESTOCK", "WHIRLER")
    val expectedKey = "5JProYWZdUyFw17EpUEmhGtyLVvcfGytVjR9GBDvve4ff5Wif6k"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with WEDLOCK`() {
    val brainKey = arrayOf("WEDLOCK", "CATFOOT", "SAPAN", "CHAW", "NIPPILY", "QERI", "PERUSE", "PATHIC", "ACOLOUS", "DEE", "FORAGE", "VOYAGE", "ABORD", "GOONDIE", "POLT", "TETEL")
    val expectedKey = "5K2892hRJMowAqGNZs4czxQNYn7Laay4RuvkaAFCPievL2CdPLT"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with CHIT`() {
    val brainKey = arrayOf("CHIT", "DELFT", "ENDMOST", "ATABEK", "FILER", "SABINO", "HENOTIC", "ZENDIK", "VEERY", "REMEND", "SIAL", "POND", "DISK", "FARADAY", "DUOTONE", "INULA")
    val expectedKey = "5JRy46JzeSHf8EfQSphmS3HevUrTvahLbWABn9Qf7YKCxUqSnQV"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

  @Test fun `generate private key from normalized brain key starting with SHREE`() {
    val brainKey = arrayOf("SHREE", "OPTIMAL", "RECOOL", "RADIX", "DISSUIT", "BAILOR", "REBEGET", "BETHUMB", "NAGARA", "MANGI", "RICRAC", "TERSION", "UPSLANT", "KRONUR", "TMESIS", "CEBIL")
    val expectedKey = "5JF7rTmRbjCbfXqSHsSPo2vC8s64NbkTusybmEzvRW9G4d5hGQ2"
    assertEquals(expectedKey, generatePrivateFromPassPhrase(Passphrase(brainKey)).base58())
  }

}