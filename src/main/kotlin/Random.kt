package therealfarfetchd.math

import java.util.Random

object Random : Random() {

  fun nextShort(): Short = nextInt(65535).toShort()

  fun nextByte(): Byte = nextInt(255).toByte()

}