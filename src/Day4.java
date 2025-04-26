import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day4 {

    static int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };
    static String xmas = "XMAS";

    private static int getXmasCount(List<String> grid, int i, int j, int n, int m) {
        int count = 0;

        for (int[] dir : dirs) {
            int x = i, y = j, pos = 0;
            int dx = dir[0], dy = dir[1];
            for (int k = 0; k < xmas.length(); k++) {
                if (x < 0 || x >= n || y < 0 || y >= m || grid.get(x).charAt(y) != xmas.charAt(pos)) {
                    break;
                }

                x += dx;
                y += dy;
                pos++;
            }

            if (pos == xmas.length()) {
                count++;
            }
        }

        return count;
    }

    private static boolean is_X_Mas(List<String> grid, int i, int j) {
        if (i == 0 || j == 0 || i == grid.size() - 1 || j == grid.get(i).length() - 1) {
            return false;
        }

        boolean diag1 = (grid.get(i - 1).charAt(j - 1) == 'M' && grid.get(i + 1).charAt(j + 1) == 'S') ||
                (grid.get(i - 1).charAt(j - 1) == 'S' && grid.get(i + 1).charAt(j + 1) == 'M');
        boolean diag2 = (grid.get(i - 1).charAt(j + 1) == 'M' && grid.get(i + 1).charAt(j - 1) == 'S') ||
                (grid.get(i - 1).charAt(j + 1) == 'S' && grid.get(i + 1).charAt(j - 1) == 'M');

        return diag1 && diag2;
    }

    public static void main(String[] args) {
        List<String> grid = new ArrayList<>();

        String filePath = "input/inp4.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                grid.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        int xmasCount1 = 0;
        int xmasCount2 = 0;

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).length(); j++) {

                // Part 1

                if (grid.get(i).charAt(j) == 'X') {
                    xmasCount1 += getXmasCount(grid, i, j, grid.size(), grid.get(i).length());
                }

                // Part 2

                if (grid.get(i).charAt(j) == 'A' && is_X_Mas(grid, i, j)) {
                    xmasCount2++;
                }
            }
        }

        System.out.println("Xmas count: " + xmasCount1);
        System.out.println("Xmas count 2: " + xmasCount2);

        // Xmas count: 2718
        // Xmas count 2: 2046
    }
}
