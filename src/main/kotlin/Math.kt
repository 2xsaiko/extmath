package therealfarfetchd.math

import kotlin.math.*

const val toDegrees = 360.0 / (2.0 * PI)
const val toRadians = (2.0 * PI) / 360.0
const val toDegreesf = toDegrees.toFloat()
const val toRadiansf = toRadians.toFloat()

fun getDistance(x: Double, y: Double) =
  sqrt(x * x + y * y)

/**
 * n-dimensional pythagorean theorem, just for the fun of it :P
 */
fun getDistance(vararg dimensions: Double) =
  sqrt(dimensions.map { it * it }.sum())

fun getDistance(vararg dimensions: Float) =
  sqrt(dimensions.map { it * it }.sum())

fun getDistance(p1: Vec3, p2: Vec3) =
  getDistance(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z)

fun sind(f: Float): Float = sin(f * toRadiansf)

fun cosd(f: Float): Float = cos(f * toRadiansf)

fun sind(f: Double): Double = sin(f * toRadians)

fun cosd(f: Double): Double = cos(f * toRadians)

fun Float.round() = roundToInt().toFloat()

fun Double.round() = roundToLong().toDouble()

/**
 * Something like modulo, but has a variable minimum instead of 0.
 * min: Minimum (inclusive)
 * max: Maximum (exclusive)
 */
fun wheel(min: Int, max: Int, v: Int) = ((v - min) pmod (max - min)) + min

fun wheel(min: Float, max: Float, v: Float) = ((v - min) pmod (max - min)) + min

infix fun Int.pmod(i: Int): Int = (this % i).let { if (it < 0) it + i else it }
infix fun Long.pmod(i: Int): Int = (this % i).let { if (it < 0) (it + i).toInt() else it.toInt() }
infix fun Long.pmod(l: Long): Long = (this % l).let { if (it < 0) it + l else it }
infix fun Byte.pmod(i: Int): Byte = (this % i).let { if (it < 0) (it + i).toByte() else it.toByte() }
infix fun Float.pmod(i: Float): Float = (this % i).let { if (it < 0) it + i else it }
