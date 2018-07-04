package therealfarfetchd.math

data class Vec3i(val x: Int, val y: Int, val z: Int) {

  operator fun plus(other: Vec3i) = Vec3i(x + other.x, y + other.y, z + other.z)
  operator fun minus(other: Vec3i) = Vec3i(x - other.x, y - other.y, z - other.z)
  operator fun div(other: Vec3i) = Vec3i(x / other.x, y / other.y, z / other.z)
  operator fun times(other: Vec3i) = Vec3i(x * other.x, y * other.y, z * other.z)

  operator fun plus(other: Vec3) = Vec3(x + other.x, y + other.y, z + other.z)
  operator fun minus(other: Vec3) = Vec3(x - other.x, y - other.y, z - other.z)
  operator fun div(other: Vec3) = Vec3(x / other.x, y / other.y, z / other.z)
  operator fun times(other: Vec3) = Vec3(x * other.x, y * other.y, z * other.z)

  operator fun div(other: Float) = Vec3(x / other, y / other, z / other)
  operator fun times(other: Float) = Vec3(x * other, y * other, z * other)

  operator fun div(other: Int) = Vec3i(x / other, y / other, z / other)
  operator fun times(other: Int) = Vec3i(x * other, y * other, z * other)

  operator fun unaryMinus() = Vec3i(-x, -y, -z)
  operator fun unaryPlus() = this

  val length by lazy { getDistance(x.toFloat(), y.toFloat(), z.toFloat()) }

  val normalized by lazy { this / length }

  infix fun crossProduct(other: Vec3i): Vec3i {
    val cx = y * other.z - z * other.y
    val cy = z * other.x - x * other.z
    val cz = x * other.y - y * other.x
    return Vec3i(cx, cy, cz)
  }

  infix fun dotProduct(other: Vec3i) =
    this.x * other.x +
    this.y * other.y +
    this.z * other.z

  fun components() = listOf(x, y, z)

  fun toVec3() = Vec3(x, y, z)

  companion object {
    val Origin = Vec3i(0, 0, 0)
  }

}