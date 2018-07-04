package therealfarfetchd.math

data class Vec2(val x: Float, val y: Float) {

  constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())

  operator fun plus(other: Vec2) = Vec2(x + other.x, y + other.y)
  operator fun minus(other: Vec2) = Vec2(x - other.x, y - other.y)
  operator fun div(other: Vec2) = Vec2(x / other.x, y / other.y)
  operator fun times(other: Vec2) = Vec2(x * other.x, y * other.y)

  operator fun plus(other: Vec2i) = Vec2(x + other.x, y + other.y)
  operator fun minus(other: Vec2i) = Vec2(x - other.x, y - other.y)
  operator fun div(other: Vec2i) = Vec2(x / other.x, y / other.y)
  operator fun times(other: Vec2i) = Vec2(x * other.x, y * other.y)

  operator fun div(other: Float) = Vec2(x / other, y / other)
  operator fun times(other: Float) = Vec2(x * other, y * other)

  operator fun div(other: Int) = Vec2(x / other, y / other)
  operator fun times(other: Int) = Vec2(x * other, y * other)

  operator fun unaryMinus() = Vec2(-x, -y)
  operator fun unaryPlus() = this

  val length by lazy { getDistance(x, y) }

  val normalized by lazy { this / length }

  fun components() = listOf(x, y)

  fun toVec2i() = Vec2i(x.toInt(), y.toInt())

  companion object {
    val Origin = Vec2(0, 0)
  }

}