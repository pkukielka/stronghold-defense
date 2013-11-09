package com.pkukielka.stronghold.utils

trait Poolable[T] extends com.badlogic.gdx.utils.Pool.Poolable {
  def reset() {}
}
