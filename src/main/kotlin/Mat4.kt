package therealfarfetchd.math

import kotlin.math.tan

data class Mat4(
  val c00: Float, val c01: Float, val c02: Float, val c03: Float,
  val c10: Float, val c11: Float, val c12: Float, val c13: Float,
  val c20: Float, val c21: Float, val c22: Float, val c23: Float,
  val c30: Float, val c31: Float, val c32: Float, val c33: Float
) {
  val r0 by lazy { Vec4(c00, c01, c02, c03) }
  val r1 by lazy { Vec4(c10, c11, c12, c13) }
  val r2 by lazy { Vec4(c20, c21, c22, c23) }
  val r3 by lazy { Vec4(c30, c31, c32, c33) }

  val c0 by lazy { Vec4(c00, c10, c20, c30) }
  val c1 by lazy { Vec4(c01, c11, c21, c31) }
  val c2 by lazy { Vec4(c02, c12, c22, c32) }
  val c3 by lazy { Vec4(c03, c13, c23, c33) }

  val elements by lazy { listOf(c00, c01, c02, c03, c10, c11, c12, c13, c20, c21, c22, c23, c30, c31, c32, c33) }

  fun array() = elements.toFloatArray()

  val inverse by lazy { invert(elements) ?: error("Matrix doesn't have an inverse") }

  val transpose by lazy {
    Mat4(
      c00, c10, c20, c30,
      c01, c11, c21, c31,
      c02, c12, c22, c32,
      c03, c13, c23, c33
    )
  }

  fun translate(x: Float, y: Float, z: Float) = this * translateMat(x, y, z)

  fun translate(xyz: Vec3) = this * translateMat(xyz)

  fun scale(x: Float, y: Float, z: Float) = this * scaleMat(x, y, z)

  fun rotate(x: Float, y: Float, z: Float, angle: Float) = this * rotationMat(x, y, z, angle)

  companion object {
    @JvmStatic
    val Identity = Mat4(
      1f, 0f, 0f, 0f,
      0f, 1f, 0f, 0f,
      0f, 0f, 1f, 0f,
      0f, 0f, 0f, 1f
    )

    fun translateMat(x: Float, y: Float, z: Float) = Mat4(
      1f, 0f, 0f, x,
      0f, 1f, 0f, y,
      0f, 0f, 1f, z,
      0f, 0f, 0f, 1f
    )

    fun translateMat(xyz: Vec3) = translateMat(xyz.x, xyz.y, xyz.z)

    fun scaleMat(x: Float, y: Float, z: Float) = Mat4(
      x, 0f, 0f, 0f,
      0f, y, 0f, 0f,
      0f, 0f, z, 0f,
      0f, 0f, 0f, 1f
    )

    fun rotationMat(x: Float, y: Float, z: Float, angle: Float): Mat4 {
      val c = cosd(-angle)
      val s = sind(-angle)
      val t = 1 - c

      return Mat4(
        t * x * x + c, t * x * y - s * z, t * x * z + s * y, 0f,
        t * x * y + s * z, t * y * y + c, t * y * z - s * x, 0f,
        t * x * z - s * y, t * y * z + s * x, t * z * z + c, 0f,
        0f, 0f, 0f, 1f
      )
    }

    fun perspective(fovY: Float, aspect: Float, zNear: Float, zFar: Float): Mat4 {
      val halfFovyRadians = toRadiansf * (fovY / 2f)
      val range = tan(halfFovyRadians) * zNear
      val left = -range * aspect
      val right = range * aspect
      val bottom = -range

      return Mat4(
        2f * zNear / (right - left), 0f, 0f, 0f,
        0f, 2f * zNear / (range - bottom), 0f, 0f,
        0f, 0f, (-(zFar + zNear) / (zFar - zNear)), -(2f * zFar * zNear) / (zFar - zNear),
        0f, 0f, -1f, 0f
      )
    }

    fun frustum(left: Float, right: Float, bottom: Float, top: Float, nearVal: Float, farVal: Float): Mat4 {
      val m00 = 2f * nearVal / (right - left)
      val m11 = 2f * nearVal / (top - bottom)
      val m02 = (right + left) / (right - left)
      val m12 = (top + bottom) / (top - bottom)
      val m22 = -(farVal + nearVal) / (farVal - nearVal)
      val m23 = -(2f * farVal * nearVal) / (farVal - nearVal)

      return Mat4(
        m00, 0f, m02, 0f,
        0f, m11, m12, 0f,
        0f, 0f, m22, m23,
        0f, 0f, -1f, 0f
      )
    }

    fun lookAt(eye: Vec3, center: Vec3, up: Vec3): Mat4 {
      val f = (center - eye).normalized
      val s = (f crossProduct up.normalized).normalized
      val u = s crossProduct f

      return Mat4(
        s.x, s.y, s.z, -(s dotProduct eye),
        u.x, u.y, u.z, -(u dotProduct eye),
        -f.x, -f.y, -f.z, f dotProduct eye,
        0f, 0f, 0f, 1f
      )
    }

    fun ortho(left: Float, right: Float, bottom: Float, top: Float, zNear: Float, zFar: Float): Mat4 {
      val m00 = 2f / (right - left)
      val m11 = 2f / (top - bottom)
      val m22 = -2f / (zFar - zNear)
      val m03 = -(right + left) / (right - left)
      val m13 = -(top + bottom) / (top - bottom)
      val m23 = -(zFar + zNear) / (zFar - zNear)

      return Mat4(
        m00, 0f, 0f, m03,
        0f, m11, 0f, m13,
        0f, 0f, m22, m23,
        0f, 0f, 0f, 1f
      )
    }
  }
}

operator fun Mat4.plus(other: Mat4) = Mat4(
  c00 + other.c00, c01 + other.c01, c02 + other.c02, c03 + other.c03,
  c10 + other.c10, c11 + other.c11, c12 + other.c12, c13 + other.c13,
  c20 + other.c20, c21 + other.c21, c22 + other.c22, c23 + other.c23,
  c30 + other.c30, c31 + other.c31, c32 + other.c32, c33 + other.c33
)

operator fun Float.times(other: Mat4) = Mat4(
  this * other.c00, this * other.c01, this * other.c02, this * other.c03,
  this * other.c10, this * other.c11, this * other.c12, this * other.c13,
  this * other.c20, this * other.c21, this * other.c22, this * other.c23,
  this * other.c30, this * other.c31, this * other.c32, this * other.c33
)

operator fun Mat4.times(other: Mat4) = Mat4(
  r0 dotProduct other.c0, r0 dotProduct other.c1, r0 dotProduct other.c2, r0 dotProduct other.c3,
  r1 dotProduct other.c0, r1 dotProduct other.c1, r1 dotProduct other.c2, r1 dotProduct other.c3,
  r2 dotProduct other.c0, r2 dotProduct other.c1, r2 dotProduct other.c2, r2 dotProduct other.c3,
  r3 dotProduct other.c0, r3 dotProduct other.c1, r3 dotProduct other.c2, r3 dotProduct other.c3
)

operator fun Mat4.times(other: Vec4) =
  Vec4(r0 dotProduct other, r1 dotProduct other, r2 dotProduct other, r3 dotProduct other)

operator fun Mat4.times(other: Vec3) =
  (this * other.toVec4()).toVec3()

private fun invert(m: List<Float>): Mat4? {
  val inv = FloatArray(16)

  inv[0] = m[5] * m[10] * m[15] -
           m[5] * m[11] * m[14] -
           m[9] * m[6] * m[15] +
           m[9] * m[7] * m[14] +
           m[13] * m[6] * m[11] -
           m[13] * m[7] * m[10]

  inv[4] = -m[4] * m[10] * m[15] +
           m[4] * m[11] * m[14] +
           m[8] * m[6] * m[15] -
           m[8] * m[7] * m[14] -
           m[12] * m[6] * m[11] +
           m[12] * m[7] * m[10]

  inv[8] = m[4] * m[9] * m[15] -
           m[4] * m[11] * m[13] -
           m[8] * m[5] * m[15] +
           m[8] * m[7] * m[13] +
           m[12] * m[5] * m[11] -
           m[12] * m[7] * m[9]

  inv[12] = -m[4] * m[9] * m[14] +
            m[4] * m[10] * m[13] +
            m[8] * m[5] * m[14] -
            m[8] * m[6] * m[13] -
            m[12] * m[5] * m[10] +
            m[12] * m[6] * m[9]

  inv[1] = -m[1] * m[10] * m[15] +
           m[1] * m[11] * m[14] +
           m[9] * m[2] * m[15] -
           m[9] * m[3] * m[14] -
           m[13] * m[2] * m[11] +
           m[13] * m[3] * m[10]

  inv[5] = m[0] * m[10] * m[15] -
           m[0] * m[11] * m[14] -
           m[8] * m[2] * m[15] +
           m[8] * m[3] * m[14] +
           m[12] * m[2] * m[11] -
           m[12] * m[3] * m[10]

  inv[9] = -m[0] * m[9] * m[15] +
           m[0] * m[11] * m[13] +
           m[8] * m[1] * m[15] -
           m[8] * m[3] * m[13] -
           m[12] * m[1] * m[11] +
           m[12] * m[3] * m[9]

  inv[13] = m[0] * m[9] * m[14] -
            m[0] * m[10] * m[13] -
            m[8] * m[1] * m[14] +
            m[8] * m[2] * m[13] +
            m[12] * m[1] * m[10] -
            m[12] * m[2] * m[9]

  inv[2] = m[1] * m[6] * m[15] -
           m[1] * m[7] * m[14] -
           m[5] * m[2] * m[15] +
           m[5] * m[3] * m[14] +
           m[13] * m[2] * m[7] -
           m[13] * m[3] * m[6]

  inv[6] = -m[0] * m[6] * m[15] +
           m[0] * m[7] * m[14] +
           m[4] * m[2] * m[15] -
           m[4] * m[3] * m[14] -
           m[12] * m[2] * m[7] +
           m[12] * m[3] * m[6]

  inv[10] = m[0] * m[5] * m[15] -
            m[0] * m[7] * m[13] -
            m[4] * m[1] * m[15] +
            m[4] * m[3] * m[13] +
            m[12] * m[1] * m[7] -
            m[12] * m[3] * m[5]

  inv[14] = -m[0] * m[5] * m[14] +
            m[0] * m[6] * m[13] +
            m[4] * m[1] * m[14] -
            m[4] * m[2] * m[13] -
            m[12] * m[1] * m[6] +
            m[12] * m[2] * m[5]

  inv[3] = -m[1] * m[6] * m[11] +
           m[1] * m[7] * m[10] +
           m[5] * m[2] * m[11] -
           m[5] * m[3] * m[10] -
           m[9] * m[2] * m[7] +
           m[9] * m[3] * m[6]

  inv[7] = m[0] * m[6] * m[11] -
           m[0] * m[7] * m[10] -
           m[4] * m[2] * m[11] +
           m[4] * m[3] * m[10] +
           m[8] * m[2] * m[7] -
           m[8] * m[3] * m[6]

  inv[11] = -m[0] * m[5] * m[11] +
            m[0] * m[7] * m[9] +
            m[4] * m[1] * m[11] -
            m[4] * m[3] * m[9] -
            m[8] * m[1] * m[7] +
            m[8] * m[3] * m[5]

  inv[15] = m[0] * m[5] * m[10] -
            m[0] * m[6] * m[9] -
            m[4] * m[1] * m[10] +
            m[4] * m[2] * m[9] +
            m[8] * m[1] * m[6] -
            m[8] * m[2] * m[5]

  val det =
    (m[0] * inv[0] + m[1] * inv[4] + m[2] * inv[8] + m[3] * inv[12])
      .takeUnless { it == 0f }
      ?.let { 1f / it }
    ?: return null

  for (i in inv.indices)
    inv[i] *= det

  return Mat4(
    inv[0], inv[1], inv[2], inv[3],
    inv[4], inv[5], inv[6], inv[7],
    inv[8], inv[9], inv[10], inv[11],
    inv[12], inv[13], inv[14], inv[15]
  )
}