import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
    static int[][] dirs = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

    public static void main(String[] args) {
        List<List<Character>> grid = new ArrayList<>();

        String filePath = "input/inp6.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    row.add(line.charAt(i));
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

        int x = 0, y = 0, currDir = 0;
        int posCount = 1, overlapCount = 0;

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j) == '^') {
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        while (true) {
            int dx = dirs[currDir][0], dy = dirs[currDir][1];

            int new_x = x + dx, new_y = y + dy;

            if (new_x < 0 || new_y < 0 || new_x >= grid.size() || new_y >= grid.get(new_x).size()) {
                break;
            }

            if (grid.get(new_x).get(new_y) == 'X') {
                overlapCount++;
            }

            if (grid.get(new_x).get(new_y) == '.') {
                posCount++;
                grid.get(new_x).set(new_y, 'X');
            }

            if (grid.get(new_x).get(new_y) == '#') {
                currDir = (currDir + 1) % 4;
            } else {
                x = new_x;
                y = new_y;
            }
        }

        System.out.println("Distinct positions count: " + posCount);
        System.out.println("Overlapping positions count: " + overlapCount);
    }

}
