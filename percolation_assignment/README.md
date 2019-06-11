# Percolation

This is an assignment written in Java to use the union-find data structure to determine the percolation threshold of an n-by-n system. 
Read more about percolation and the assignment [here](http://coursera.cs.princeton.edu/algs4/assignments/percolation.html)

The main program `Percolation.java` uses the union-find datastructure to efficiently determine when the system will percolate using methods:
## Percolation API
1. `public Percolation(int n)` - Initializes an n-by-n grid and union-find datastructures
2. `public void open(int row, int col)` - Opens a site at position (row, col) and connects to the open sites in the union-find datastructure
3. `public boolean isOpen(int row, int col)` - Return true if the site is opened
4. `public boolean isFull(int row, int col)` - Return true if the site is in a component that is connected to the top row
5. `public int numberOfOpenSites()` - Return the current number of sites opened
6. `public boolean percolates()` - Return true if the bottom row is connected to the top row

## To run
Make sure to set up the java classpath using the princeton algs-4 as specified in the link above. Then the following to compile all the code in `perc_code`
```bash
$  javac-algs4 Percolation.java PercolationStats.java PercolationVisualizer.java InteractivePercolationVisualizer.java
```
and to run the Percolation visualizer, use the `PercolationVisualizer.class` file and an input.txt file from `input_txt_files/` directory
```bash
$  java-algs4 PercolationVisualizer ../images/input10.txt
```

## Screenshots

10 by 10 grid

![10-by-10](https://github.com/EltonK888/cousera_algorithms/blob/master/percolation_assignment/images/input10.png)

20 by 20 grid

![20-by-20](https://github.com/EltonK888/cousera_algorithms/blob/master/percolation_assignment/images/input20.png)
## Known issues
* Uses 2 union-find datastructures to solve the backwash bug making it slightly memory inefficient
