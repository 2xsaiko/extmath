package therealfarfetchd.math

data class Vec2i(val x: Int, val y: Int) {

  operator fun plus(other: Vec2i) = Vec2i(x + other.x, y + other.y)
  operator fun minus(other: Vec2i) = Vec2i(x - other.x, y - other.y)
  operator fun div(other: Vec2i) = Vec2i(x / other.x, y / other.y)
  operator fun times(other: Vec2i) = Vec2i(x * other.x, y * other.y)

  operator fun div(other: Float) = Vec2(x / other, y / other)
  operator fun times(other: Float) = Vec2(x * other, y * other)

  operator fun div(other: Int) = Vec2i(x / other, y / other)
  operator fun times(other: Int) = Vec2i(x * other, y * other)

  operator fun unaryMinus() = Vec2i(-x, -y)
  operator fun unaryPlus() = this

  val length by lazy { getDistance(x.toFloat(), y.toFloat()) }

  val normalized by lazy { this / length }

  fun components() = listOf(x, y)

  fun toVec2() = Vec2(x, y)

  companion object {
    val Origin = Vec2i(0, 0)
  }

}