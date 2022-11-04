# Analysis of the Stable Marriage Problem

Algorithms:

1) Gale-Shapley (Deferred Acceptance)
2) Brute-Force

## Compile

javac RunAlgorithms.java

## Run

java RunAlgorithms problem_size
or
java RunAlgorithms --exclude problem_size (use this to exclude the brute-force algorithm, which will exhaust the heap space for large problem sizes)

## Troubleshooting

If you run out of heap space, try increasing the heap size: java -Xmx2g RunAlgorithms problem_size.
This will increase the heap size to 2GB. Feel free to change this within the constraints of your machine.
