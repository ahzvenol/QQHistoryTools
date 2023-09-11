package xco.util

object FPUtil {
  extension[A] (any: A)
    def |>[B](f: Function[A, B]): B = f(any)
}