**What is Lens**

Lens in essence is a pair of functions:

- `get(s: S): A`
- `set(a: A): S => S`

**What is Prism**

Prism is essentially a pair of functions:

- `getOption: S => Option[A]`
- `reverseGet: A => S`

**What is Iso**

Navigating from `S` and `A` is always successful (as in Lens)
and navigating from `A` to `S` does not need any additional information 
besides of `A` value (as in Prism)

– in other words transformation from S to A is lossless.

- `get: S => A`
- `reverseGet: A => S` 