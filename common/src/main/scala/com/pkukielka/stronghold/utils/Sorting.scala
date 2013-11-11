package com.pkukielka.stronghold.utils

import scala.collection.mutable.ArrayBuffer

object Sorting {

  def swap[T](xs: ArrayBuffer[T], a: Int, b: Int) {
    val t = xs(a)
    xs(a) = xs(b)
    xs(b) = t
  }

  def partition[T](xs: ArrayBuffer[T], lo: Int, hi: Int, predicate: T => Boolean): Int = {
    var first = lo
    var last = hi
    while (first != last) {
      while (predicate(xs(first))) {
        first += 1
        if (first == last) return first
      }
      do {
        last -= 1
        if (first == last) return first
      } while (!predicate(xs(last)))

      swap(xs, first, last)
      first += 1
    }

    return first
  }

  def stableSort[K](a: ArrayBuffer[K], lo: Int, hi: Int, scratch: ArrayBuffer[K], f: K => Float) {
    if (lo < hi) {
      val mid = (lo+hi) / 2
      stableSort(a, lo, mid, scratch, f)
      stableSort(a, mid+1, hi, scratch, f)
      var k, t_lo = lo
      var t_hi = mid + 1
      while (k <= hi) {
        if ((t_lo <= mid) && ((t_hi > hi) || f(a(t_hi)) <= f(a(t_lo)))) {
          scratch(k) = a(t_lo)
          t_lo += 1
        } else {
          scratch(k) = a(t_hi)
          t_hi += 1
        }
        k += 1
      }
      k = lo
      while (k <= hi) {
        a(k) = scratch(k)
        k += 1
      }
    }
  }
}
