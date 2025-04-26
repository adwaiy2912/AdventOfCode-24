import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day12 {
    private static int getPerimeter(List<List<Character>> grid, int i, int j, int n, int m, char ch, boolean[][] vis) {
        if (i < 0 || i >= n || j < 0 || j >= m || vis[i][j] || grid.get(i).get(j) != ch) {
            return 0;
        }
        vis[i][j] = true;

        int perimeter = 0;

        perimeter = i == 0 || grid.get(i - 1).get(j) != ch ? perimeter + 1
                : perimeter + getPerimeter(grid, i - 1, j, n, m, ch, vis);
        perimeter = i == n - 1 || grid.get(i + 1).get(j) != ch ? perimeter + 1
                : perimeter + getPerimeter(grid, i + 1, j, n, m, ch, vis);
        perimeter = j == 0 || grid.get(i).get(j - 1) != ch ? perimeter + 1
                : perimeter + getPerimeter(grid, i, j - 1, n, m, ch, vis);
        perimeter = j == m - 1 || grid.get(i).get(j + 1) != ch ? perimeter + 1
                : perimeter + getPerimeter(grid, i, j + 1, n, m, ch, vis);

        return perimeter;
    }

    private static int getArea(List<List<Character>> grid, int i, int j, int n, int m, char ch, boolean[][] vis) {
        if (i < 0 || i >= n || j < 0 || j >= m || vis[i][j] || grid.get(i).get(j) != ch) {
            return 0;
        }
        vis[i][j] = true;

        int area = 1;

        area += getArea(grid, i - 1, j, n, m, ch, vis);
        area += getArea(grid, i + 1, j, n, m, ch, vis);
        area += getArea(grid, i, j - 1, n, m, ch, vis);
        area += getArea(grid, i, j + 1, n, m, ch, vis);

        return area;
    }

    public static void main(String[] args) {
        List<List<Character>> grid = new ArrayList<>();

        String filePath = "input/inp12.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    row.add(c);
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

        long totalPrice = 0;
        boolean[][] vis = new boolean[n][m];
        boolean[][] vis2 = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!vis[i][j]) {
                    char ch = grid.get(i).get(j);
                    totalPrice += getPerimeter(grid, i, j, n, m, ch, vis) * getArea(grid, i, j, n, m, ch, vis2);
                }
            }
        }

        System.out.println("Total price: " + totalPrice);
    }
}
