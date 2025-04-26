import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16 {
    static int[][] dirs = new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } }; // right, down, left, up

    private static long getScore(List<List<Character>> grid, long currScore, int currX, int currY, int endX, int endY,
            int dir, int currRotations, int prevX, int prevY, long[][] dp) {
        // Base case: reached destination
        if (currX == endX && currY == endY) {
            return 0;
        }

        // Base case: out of bounds or obstacle
        if (currX < 0 || currX >= grid.size() || currY < 0 || currY >= grid.get(0).size()
                || grid.get(currX).get(currY) == '#') {
            return Integer.MAX_VALUE;
        }

        // Prune paths with higher scores
        if (currX != prevX && currY != prevY && dp[currX][currY] < currScore) {
            return dp[currX][currY];
        }

        // Update DP table with current score
        dp[currX][currY] = currScore;

        // Move in the current direction
        int newX = currX + dirs[dir][0];
        int newY = currY + dirs[dir][1];

        long minScore = Integer.MAX_VALUE;

        // Continue in the current direction
        minScore = Math.min(minScore,
                1 + getScore(grid, currScore + 1, newX, newY, endX, endY, dir, 0, currX, currY, dp));

        // Try rotating to the left (dir + 3) % 4
        if (currRotations < 1) {
            minScore = Math.min(minScore, 1000 + getScore(grid, currScore + 1000, currX, currY, endX, endY,
                    (dir + 3) % 4, currRotations + 1, currX, currY, dp));
        }

        // Try rotating to the right (dir + 1) % 4
        if (currRotations < 1) {
            minScore = Math.min(minScore, 1000 + getScore(grid, currScore + 1000, currX, currY, endX, endY,
                    (dir + 1) % 4, currRotations + 1, currX, currY, dp));
        }

        // Update DP with the minimum score found
        dp[currX][currY] = minScore;
        return minScore;
    }

    private static long getScore1(List<List<Character>> grid, int currX, int currY, int endX, int endY,
            int dir, int currRotations, Map<String, Long> dp) {
        if (currX == endX && currY == endY) {
            return 0;
        }
        if (grid.get(currX).get(currY) == '#') {
            return Integer.MAX_VALUE;
        }
        if (currRotations > 1) {
            return Integer.MAX_VALUE;
        }

        int newX = currX + dirs[dir][0], newY = currY + dirs[dir][1];

        long score1 = 1 + getScore1(grid, newX, newY, endX, endY, dir, 0, dp);
        long score2 = 1000 + getScore1(grid, currX, currY, endX, endY, (dir + 1) % 4, currRotations + 1, dp);
        long score3 = 1000 + getScore1(grid, currX, currY, endX, endY, (dir + 3) % 4, currRotations + 1, dp);

        return Math.min(score1, Math.min(score2, score3));
    }

    public static void main(String[] args) {
        List<List<Character>> grid = new ArrayList<>();

        String filePath = "input/inp16.1.txt";

        int startX = 0, startY = 0;
        int endX = 0, endY = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int i = 0;

            while ((line = br.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (int j = 0; j < line.length(); j++) {
                    char c = line.charAt(j);
                    row.add(c);

                    if (c == 'S') {
                        startX = i;
                        startY = j;
                    }
                    if (c == 'E') {
                        endX = i;
                        endY = j;
                    }
                }
                grid.add(row);
                i++;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        int n = grid.size(), m = grid.get(0).size();
        int dir = 0, currScore = 0, currRotations = 0;

        long[][] dp = new long[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        dp[startX][startY] = 0;

        long score = getScore(grid, currScore, startX, startY, endX, endY, dir,
                currRotations, startX, startY, dp);

        Map<String, Long> map = new HashMap<>();
        map.put("-1,-1", 0L);

        // long score = getScore1(grid, startX, startY, endX, endY, dir, currRotations,
        // map);

        long start = dp[startX][startY], end = dp[endX][endY];

        System.out.println("Lowest score: " + score);
        System.out.println("Start score: " + start);
        System.out.println("End score: " + end);
    }
}
