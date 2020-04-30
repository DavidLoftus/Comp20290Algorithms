# Practical 7: String search algorithms

### Algorithmic Development

##### Brute Force

See
[BruteForceSearch.java](../src/main/java/ie/davidloftus/algorithms/substrings/BruteForceSearch.java)
for brute force string search implementation.

##### KMP Search

See
[KMPSearch.java](../src/main/java/ie/davidloftus/algorithms/substrings/KMPSearch.java)
for KMP (Knuth-Morris-Pratt) string search implementation.

##### Performance Analysis

Here is the benchmarks for the search algorithms using different testcases.
You can find the exact value for these testcases

|                   Description |    BruteForceSearch |           KMPSearch |
|-------------------------------|---------------------|---------------------|
|       tiny string not present |              0.0025 |              0.0026 |
|    small string, present, end |              0.0027 |              0.0026 |
|repeated string, short circuit |              0.0026 |              0.0025 |
|           100 false positives |              0.0291 |              0.0194 |
|super long needle and haystack |            635.7659 |              4.5062 |

As you can see for small strings brutre force is marginally better, but KMP quickly becomes the more optimal algorithm.
It is very apparent in the final test case where both the needle and haystack are long.