package therealfarfetchd.math

import java.util.Random as JRandom

object Random : JRandom() {

  fun nextShort(): Short = nextInt(65535).toShort()

  fun nextByte(): Byte = nextInt(255).toByte()

  fun nextVec2(length: Float = 1f): Vec2 = Vec2(nextFloat(), nextFloat()).normalized * length

  fun nextVec3(length: Float = 1f): Vec3 = Vec3(nextFloat(), nextFloat(), nextFloat()).normalized * length

}

fun JRandom.nextShort(): Short = nextInt(65535).toShort()

fun JRandom.nextByte(): Byte = nextInt(255).toByte()

fun JRandom.nextVec2(length: Float = 1f): Vec2 = Vec2(nextFloat(), nextFloat()).normalized * length

fun JRandom.nextVec3(length: Float = 1f): Vec3 = Vec3(nextFloat(), nextFloat(), nextFloat()).normalized * length