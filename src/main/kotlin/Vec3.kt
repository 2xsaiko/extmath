package therealfarfetchd.math

data class Vec3(val x: Float, val y: Float, val z: Float) {

  constructor(x: Int, y: Int, z: Int) : this(x.toFloat(), y.toFloat(), z.toFloat())

  operator fun plus(other: Vec3) = Vec3(x + other.x, y + other.y, z + other.z)
  operator fun minus(other: Vec3) = Vec3(x - other.x, y - other.y, z - other.z)
  operator fun div(other: Vec3) = Vec3(x / other.x, y / other.y, z / other.z)
  operator fun times(other: Vec3) = Vec3(x * other.x, y * other.y, z * other.z)

  operator fun div(other: Float) = Vec3(x / other, y / other, z / other)
  operator fun times(other: Float) = Vec3(x * other, y * other, z * other)

  operator fun div(other: Int) = Vec3(x / other, y / other, z / other)
  operator fun times(other: Int) = Vec3(x * other, y * other, z * other)

  operator fun unaryMinus() = Vec3(-x, -y, -z)
  operator fun unaryPlus() = this

  val length by lazy { getDistance(x, y, z) }

  val normalized by lazy { this / length }

  infix fun crossProduct(other: Vec3): Vec3 {
    val cx = y * other.z - z * other.y
    val cy = z * other.x - x * other.z
    val cz = x * other.y - y * other.x
    return Vec3(cx, cy, cz)
  }

  infix fun dotProduct(other: Vec3) =
    this.x * other.x +
    this.y * other.y +
    this.z * other.z

  fun components() = listOf(x, y, z)

  fun toVec4() = Vec4(x, y, z, 1f)

  companion object {
    val Origin = Vec3(0, 0, 0)
  }

}