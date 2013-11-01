package com.pkukielka.stronghold.utils

import scala.annotation.tailrec
import scala.collection.LinearSeq

object Utils {

  @tailrec final def foreach[A](xs: Traversable[A], f: A => Unit) {
    if (xs.nonEmpty) {
      f(xs.head)
      foreach(xs.tail, f)
    }
  }

  final def foreach[A](xs: Array[A], f: A => Unit) {
    var i = 0
    while (i < xs.size) {
      f(xs(i))
      i += 1
    }
  }

  @tailrec
  private def minBy[A, B <% Ordered[B]](xs: LinearSeq[A], f: A => B, min: A): A = {
    if (xs.isEmpty) {
      min
    }
    else {
      minBy(xs.tail, f, if (f(min) < f(xs.head)) min else xs.head)
    }
  }

  def minBy[A, B <% Ordered[B]](xs: LinearSeq[A], f: A => B): A = minBy(xs, f, xs.head)

  def minBy[A, B <% Ordered[B]](xs: Array[A], f: A => B): A = {
    var i = 0
    var min = xs(0)
    while (i < xs.size) {
      min = if (f(min) < f(xs(i))) min else xs(i)
      i += 1
    }
    min
  }
}
