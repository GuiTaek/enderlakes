## Fixed things
### pi
$\pi = (1)(2)(3)...(n_1)$

$\circ (n_1 + 1, n_1 + 2)(n_1 + 3, n_1 + 4)...(n_1 + n_2 - 1, n_1 + n_2)$

$\circ ...$

$\circ (n_1 + ... + n_k - k + 1 ... n_1 + ... + n_k)$

Described in words: A permutation with each element has defined probabilities of being inside a cycle of special length.

### n
is a large prime

### c[.]
A bijection that maps $\mathbb Z \to \mathbb Z \times \mathbb Z$

### pos[., .]
The base chunk positions of the lakes based on x and y integer coordinates. An example for this would be:
$f(c) = d \cdot c^2 + 64$ if $c > 0$
$f(0) = 0$ and
$f(c) = -d \cdot c^2 - 64$ if $c < 0$
$pos[0, 0] = null$
$pos[x, y] = [f(x), f(y)]$

## Seed-dependent things
### g
1. Yield a random number g between 2 and n - 1 from the seed
2. Find out, if g is a primitive root mod n (follow )