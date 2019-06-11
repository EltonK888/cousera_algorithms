import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/* *****************************************************************************
 *  Name: Elton Kok
 *  Date: 09 June 2019
 *  Description: Stats to determine the percolation threshold
 **************************************************************************** */
public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] thresholds; // array that stores all the perc thresholds
    private final int numTrials; // number of trials performed to percolate
    private final double means; // to store the mean of thresholds
    private final double stdDev; // to store the std deviation of thresholds
    private final double loConf; // to store the lower end of the confidence interval
    private final double hiConf; // to store the higher end of the confidence interval

    /**
     * Constructor that performs T independent trials on an n-by-n grid.
     * @param n grid size
     * @param trials number of percolation threshold trials performed
     */
    public PercolationStats(int n, int trials) {
       if (n <= 0 || trials <= 0) {
           throw new java.lang.IllegalArgumentException(
                   "grid size/trials should be greater than 0");
       }
       numTrials = trials;
       thresholds = new double[trials];
       for (int i = 0; i < trials; i++) {
           Percolation p = new Percolation(n);
           while (!p.percolates()) {
               int row = StdRandom.uniform(1, n + 1);
               int col = StdRandom.uniform(1, n + 1);
               if (!p.isOpen(row, col)) {
                   p.open(row, col);
               }
           }
           thresholds[i] = (double) p.numberOfOpenSites()/(n * n);
       }
       means = mean();
       stdDev = stddev();
       loConf = confidenceLo();
       hiConf = confidenceHi();
    }

    /**
     * Return a double that is the mean of all the percolation thresholds.
     * @return a double representing the mean
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * Return a double that is the sample standard deviation of the thresholds.
     * @return a double representing the standard deviation
     */
    public double stddev() {
        if (numTrials ==  1) {
            return Double.NaN;
        }
        return StdStats.stddev(thresholds);
    }

    /**
     * Return a double for the low endpoint of the 95% confidence interval
     * @return double of the lower end of the 95% confidence interval
     */
    public double confidenceLo() {
        return (means - ((CONFIDENCE_95 * stdDev)/Math.sqrt(numTrials)));
    }

    /**
     * Return a double for the high endpoint of the 95% confidence interval
     * @return double of the high end of the 95% confidence interval
     */
    public double confidenceHi() {
        return (means + ((CONFIDENCE_95 * stdDev)/Math.sqrt(numTrials)));
    }

    public static void main(String[] args) {
        System.out.print(args[0] + " ");
        System.out.println(args[1]);
        int gridSize = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats pS = new PercolationStats(gridSize, trials);
        System.out.println("mean is: " + pS.means);
        System.out.println("stddev is: " + pS.stdDev);
        System.out.println(
                "95% confidence interval: " + "[" + pS.loConf +
                        ", " + pS.hiConf + "]");
    }
}
