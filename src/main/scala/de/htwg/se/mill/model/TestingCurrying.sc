def checker[T](check: T => Boolean)(values: Vector[T]): Boolean = values.forall(check(_))

val values = Vector("Test", "Yeah", "Yuhe")
print(checker[String](value => value.contains("a"))(values))
