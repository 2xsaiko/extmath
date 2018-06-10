package therealfarfetchd.math

data class Vec4(val x: Float, val y: Float, val z: Float, val w: Float) {

  constructor(x: Int, y: Int, z: Int, w: Int) : this(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())

  operator fun plus(other: Vec4) = Vec4(x + other.x, y + other.y, z + other.z, w + other.w)
  operator fun minus(other: Vec4) = Vec4(x - other.x, y - other.y, z - other.z, w - other.w)
  operator fun div(other: Vec4) = Vec4(x / other.x, y / other.y, z / other.z, w / other.w)
  operator fun times(other: Vec4) = Vec4(x * other.x, y * other.y, z * other.z, w / other.w)

  operator fun div(other: Float) = Vec4(x / other, y / other, z / other, w / other)
  operator fun times(other: Float) = Vec4(x * other, y * other, z * other, w * other)

  operator fun div(other: Int) = Vec4(x / other, y / other, z / other, w / other)
  operator fun times(other: Int) = Vec4(x * other, y * other, z * other, w * other)

  operator fun unaryMinus() = Vec4(-x, -y, -z, -w)
  operator fun unaryPlus() = this

  infix fun dotProduct(other: Vec4) = x * other.x + y * other.y + z * other.z + w * other.w

  val length by lazy { getDistance(x, y, z, w) }

  val normalized by lazy { this / length }

  fun components() = listOf(x, y, z, w)

  fun toVec3() = Vec3(x / w, y / w, z / w)

  companion object {
    val Origin = Vec4(0, 0, 0, 0)
  }

}