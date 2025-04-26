import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day8 {
    private static void helper1(Set<String> vis, int x, int y, int x_alt, int y_alt, int n, int m) {
        int px = (2 * x) - x_alt;
        int py = (2 * y) - y_alt;

        if (px < 0 || py < 0 || px >= n || py >= m) {
            return;
        }

        vis.add(px + "," + py);
    }

    private static void helper2(Set<String> vis, int x, int y, int x_alt, int y_alt, int n, int m) {
        int dx = x - x_alt, dy = y - y_alt;

        while (true) {
            x += dx;
            y += dy;

            if (x < 0 || y < 0 || x >= n || y >= m) {
                break;
            }

            vis.add(x + "," + y);
        }
    }

    public static void main(String[] args) {
        List<List<Character>> grid = new ArrayList<>();
        Map<Character, List<String>> map = new HashMap<>();

        String filePath = "input/inp8.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int idx = 0;

            while ((line = br.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    row.add(line.charAt(i));

                    if (line.charAt(i) != '.') {
                        map.putIfAbsent(line.charAt(i), new ArrayList<>());
                        map.get(line.charAt(i)).add(idx + "," + i);
                    }
                }
                grid.add(row);
                idx++;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        int n = grid.size(), m = grid.get(0).size();

        Set<String> vis = new HashSet<>();
        Set<String> vis2 = new HashSet<>();

        for (char ch : map.keySet()) {
            List<String> lst = map.get(ch);

            for (int i = 0; i < lst.size(); i++) {
                for (int j = i + 1; j < lst.size(); j++) {
                    String[] s1 = lst.get(i).split(",");
                    String[] s2 = lst.get(j).split(",");

                    int x1 = Integer.parseInt(s1[0]), y1 = Integer.parseInt(s1[1]);
                    int x2 = Integer.parseInt(s2[0]), y2 = Integer.parseInt(s2[1]);

                    // Part 1

                    helper1(vis, x1, y1, x2, y2, n, m);
                    helper1(vis, x2, y2, x1, y1, n, m);

                    // Part 2

                    helper2(vis2, x1, y1, x2, y2, n, m);
                    helper2(vis2, x2, y2, x1, y1, n, m);
                }
            }
        }

        System.out.println("Unique antinode locations: " + vis.size());
        System.out.println("Unique antinode locations2: " + vis2.size());
    }
}
