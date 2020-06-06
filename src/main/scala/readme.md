**What is Lens**

Lens in essence is a pair of functions:

- `get(s: S): A`
- `set(a: A): S => S`

What is Prism

Prism is essentially a pair of functions:

- `getOption: S => Option[A]`
- `reverseGet: A => S`

