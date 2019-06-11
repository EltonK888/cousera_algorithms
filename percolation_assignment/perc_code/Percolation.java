import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name: Elton Kok
 *  Date: 09 June 2019
 *  Description: Percolation class that will have methods to determine if
 *               the system will percolate or not
 **************************************************************************** */

/**
 * Class for percolation.
 */
public class Percolation {

    private int[][] grid; // the grid to hold the opensites
    /* union find data structures to determine if the system percolates or not
     * One is used to fix backwash bug
     */
    private final WeightedQuickUnionUF quickUnion1, quickUnion2;
    private final int gridSize, topIndex, bottomIndex; // important values
    private int openSites; // number of open sites

    /**
     * The constructor for the Percolation data structure. Initializes the two
     * union find data structures, the grid, and important integers
     * @param n the integer for the size of the grid
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException(
                    "invalid integer, n is not > 0");
        }
        grid = new int[n][n];
        quickUnion1 = new WeightedQuickUnionUF(n * n + 2);
        quickUnion2 = new WeightedQuickUnionUF(n * n + 1);
        openSites = 0;
        gridSize = n;
        topIndex = gridSize * gridSize;
        bottomIndex = gridSize * gridSize + 1;
    }

    /**
     * Opens a site in the grid and unions the neighboring sites to it.
     * @param row an int representing the row number
     * @param col an int representing the column number
     */
    public void open(int row, int col) {
        validateIndex(row, col);
        if (isOpen(row, col)) {
            return;
        }
        grid[row - 1][col - 1] = 1;
        openSites += 1;
        if (row == 1 || isOpen(row - 1, col)) { // Top
            unionTop(row, col);
        }
        if (row == gridSize || isOpen(row + 1, col))  { // Bottom
            unionBottom(row, col);
        }
        if (col != 1 && isOpen(row, col - 1)) { // Left
            unionLeft(row, col);
        }
        if (col != gridSize && isOpen(row, col + 1)) { // Right
            unionRight(row, col);
        }
    }

    /**
     * Return a bool that is true if the square on the grid is already open.
     * @param row the row number
     * @param col the column number
     * @return a bool that is true if the site is open (grid at the index == 1)
     */
    public boolean isOpen(int row, int col) {
        validateIndex(row, col);
        return (grid[row - 1][col - 1] == 1);
    }

    /**
     * Return a bool if the site is connected to an open site on the top.
     * @param row Integer representing the row number
     * @param col Integer representing the col number
     * @return a bool which is true if the component is connected to the Top
     */
    public boolean isFull(int row, int col) {
        validateIndex(row, col);
        return quickUnion2.connected(quickUnionIndex(row, col), topIndex);
    }


    /**
     * Return an int representing the number of open sites.
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * Return a bool which is true if an open site on the bottom row is.
     * connected to an open site on the top row
     * @return bool which is true if the bottom row is connected to the top row
     */
    public boolean percolates() {
        return quickUnion1.connected(topIndex, bottomIndex);
    }

    /**
     * Checks whether the row and col indices are valid.
     * @param row the row number
     * @param col the col number
     */
    private void validateIndex(int row, int col) {
        if (row <= 0 || row > gridSize || col <= 0 || col > gridSize) {
            throw new java.lang.IllegalArgumentException(
                    "invalid column/row entered. Not within grid boundary");
        }
    }
    /**
     * Return an int which is the index in the union find data structure given
     * the @row and @col
     * @param row the row number
     * @param col the col number
     * @return an int that gives the index of the unionfind data structure
     */
    private int quickUnionIndex(int row, int col) {
        return ((row * gridSize) - (gridSize - col) - 1);
    }

    /**
     * Unions the current @row and @col position to the bottom in the union
     * find data structure. If the @row is on the bottom, union to the virtual
     * site in the quickUnion1 datastructure
     * @param row the row number
     * @param col the col number
     */
    private void unionBottom(int row, int col) {
        if (row == gridSize) {
            quickUnion1.union(quickUnionIndex(row, col), bottomIndex);
            return;
        }
        quickUnion1.union(quickUnionIndex(row, col), quickUnionIndex(
                row + 1, col));
        quickUnion2.union(quickUnionIndex(row, col), quickUnionIndex(
                row + 1, col));
    }

    /**
     * Unions the current @row and @col position to the top in the union
     * find data structure. If the @row is on the top, union to the virtual
     * site in both union find datastructures
     * @param row the row number
     * @param col the col number
     */
    private void unionTop(int row, int col) {
        if (row == 1) {
            quickUnion1.union(topIndex, quickUnionIndex(row, col));
            quickUnion2.union(topIndex, quickUnionIndex(row, col));
            return;
        }
        quickUnion1.union(quickUnionIndex(row, col), quickUnionIndex(
                row - 1, col));
        quickUnion2.union(quickUnionIndex(row, col), quickUnionIndex(
                row - 1, col));
    }

    /**
     * Unions to the right of the given site in the union find data structure
     * @param row the row number
     * @param col the col number
     */
    private void unionRight(int row, int col) {
        quickUnion1.union(quickUnionIndex(row, col), quickUnionIndex(
                row, col + 1));
        quickUnion2.union(quickUnionIndex(row, col), quickUnionIndex(
                row, col + 1));
    }

    /**
     * Unions to the left of the given site in the union find data structure
     * @param row the row number
     * @param col the col number
     */
    private void unionLeft(int row, int col) {
        quickUnion1.union(quickUnionIndex(row, col), quickUnionIndex(
                row, col - 1));
        quickUnion2.union(quickUnionIndex(row, col), quickUnionIndex(
                row, col - 1));
    }
}

