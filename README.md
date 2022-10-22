# Analysis of the Stable Marriage Problem

Algorithms:

1) Gale-Shapley (Deferred Acceptance)
2) Brute-Force

## Compile

javac RunAlgorithms.java

## Run

java RunAlgorithms problem_size (problem size larger than 10 will take a long time and most likely exhaust your heap space)

### Note

We will be separating the algorithms in the future to make it easier to run them individually.

## Troubleshooting

If you run out of heap space, try increasing the heap size: java -Xmx2g RunAlgorithms problem_size.
This will increase the heap size to 2GB.
