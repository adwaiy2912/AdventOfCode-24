import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 {
    private static int[][] dirs = new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    private static int getScore(List<List<Integer>> grid, int i, int j, int n, int m, Set<String> vis) {
        if (i < 0 || i >= n || j < 0 || j >= m) {
            return 0;
        }

        String pos = i + "," + j;
        if (grid.get(i).get(j) == 9 && !vis.contains(pos)) {
            vis.add(pos);
            return 1;
        }

        int score = 0;
        for (int[] dir : dirs) {
            int new_i = i + dir[0], new_j = j + dir[1];
            if (new_i >= 0 && new_i < n && new_j >= 0 && new_j < m
                    && grid.get(i).get(j) + 1 == grid.get(new_i).get(new_j)) {
                score += getScore(grid, new_i, new_j, n, m, vis);
            }
        }
        return score;
    }

    private static int getScore2(List<List<Integer>> grid, int i, int j, int n, int m) {
        if (i < 0 || i >= n || j < 0 || j >= m) {
            return 0;
        }

        if (grid.get(i).get(j) == 9) {
            return 1;
        }

        int score = 0;
        for (int[] dir : dirs) {
            int new_i = i + dir[0], new_j = j + dir[1];
            if (new_i >= 0 && new_i < n && new_j >= 0 && new_j < m
                    && grid.get(i).get(j) + 1 == grid.get(new_i).get(new_j)) {
                score += getScore2(grid, new_i, new_j, n, m);
            }
        }
        return score;
    }

    public static void main(String[] args) {
        List<List<Integer>> grid = new ArrayList<>();

        String filePath = "input/inp10.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                List<Integer> row = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    row.add(Integer.parseInt(line.charAt(i) + ""));
                }
                grid.add(row);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        int n = grid.size(), m = grid.get(0).size();
        int totalScore = 0, totalScore2 = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid.get(i).get(j) == 0) {
                    Set<String> vis = new HashSet<>();
                    totalScore += getScore(grid, i, j, n, m, vis);
                    totalScore2 += getScore2(grid, i, j, n, m);
                }
            }
        }

        System.out.println("Total score: " + totalScore);
        System.out.println("Total score 2: " + totalScore2);
    }
}
