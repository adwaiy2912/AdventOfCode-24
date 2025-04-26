import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day15 {
    static int[][] dirs = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    // up, down, left, right

    private static int getDir(char c) {
        switch (c) {
            case '^':
                return 0;
            case 'v':
                return 1;
            case '<':
                return 2;
            case '>':
                return 3;
            default:
                return -1;
        }
    }

    private static boolean canUpdate(List<List<Character>> grid, int x, int y, int dir) {
        int newX = x + dirs[dir][0], newY = y + dirs[dir][1];
        char next = grid.get(newX).get(newY);

        if (next == '#') {
            return false;
        }
        if (next == 'O') {
            return canUpdate(grid, newX, newY, dir);
        }
        return true;
    }

    private static void updateGrid(List<List<Character>> grid, int x, int y, int dir, char prev) {
        int newX = x + dirs[dir][0], newY = y + dirs[dir][1];
        char curr = grid.get(x).get(y), next = grid.get(newX).get(newY);

        if (next == '#') {
            return;
        }
        if (next == 'O') {
            updateGrid(grid, newX, newY, dir, curr);
        }
        grid.get(x).set(y, prev);
        grid.get(newX).set(newY, curr);
    }

    private static boolean canUpdate2LR(List<List<Character>> grid, int x, int y, int dir) {
        int newX = x + dirs[dir][0], newY = y + dirs[dir][1];
        char next = grid.get(newX).get(newY);

        if (next == '#') {
            return false;
        }
        if (next == ']' || next == '[') {
            return canUpdate2LR(grid, newX, newY, dir);
        }
        return true;
    }

    private static void updateGridLR(List<List<Character>> grid, int x, int y, int dir, char prev) {
        int newX = x + dirs[dir][0], newY = y + dirs[dir][1];
        char curr = grid.get(x).get(y), next = grid.get(newX).get(newY);

        if (next == '#') {
            return;
        }
        if (next == ']' || next == '[') {
            updateGridLR(grid, newX, newY, dir, curr);
        }
        grid.get(x).set(y, prev);
        grid.get(newX).set(newY, curr);
    }

    private static boolean canUpdate2UD(List<List<Character>> grid, int x, int y, int dir) {
        int newX = x + dirs[dir][0], newY = y + dirs[dir][1];
        char next = grid.get(newX).get(newY);

        if (next == '#') {
            return false;
        }
        if (next == ']') {
            return canUpdate2UD(grid, newX, newY, dir) && canUpdate2UD(grid, newX, newY - 1, dir);
        }
        if (next == '[') {
            return canUpdate2UD(grid, newX, newY, dir) && canUpdate2UD(grid, newX, newY + 1, dir);
        }
        return true;
    }

    private static void updateGridUD(List<List<Character>> grid, int x, int y, int dir, char prev) {
        int newX = x + dirs[dir][0], newY = y + dirs[dir][1];
        char curr = grid.get(x).get(y), next = grid.get(newX).get(newY);

        if (next == '#') {
            return;
        }
        if (next == ']') {
            updateGridUD(grid, newX, newY, dir, curr);
            updateGridUD(grid, newX, newY - 1, dir, '.');
        }
        if (next == '[') {
            updateGridUD(grid, newX, newY, dir, curr);
            updateGridUD(grid, newX, newY + 1, dir, '.');
        }
        grid.get(x).set(y, prev);
        grid.get(newX).set(newY, curr);
    }

    public static void main(String[] args) {
        List<List<Character>> grid = new ArrayList<>();
        List<List<Character>> grid2 = new ArrayList<>();
        List<String> moves = new ArrayList<>();

        String filePath = "input/inp15.0.txt";
        String movesFilePath = "input/inp15.1.txt";

        int x = 0, y = 0;
        int x2 = 0, y2 = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int i = 0;

            while ((line = br.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (int j = 0; j < line.length(); j++) {
                    char c = line.charAt(j);

                    if (c == '@') {
                        x = i;
                        y = j;
                    }
                    row.add(c);
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

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int i = 0;

            while ((line = br.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (int j = 0; j < line.length(); j++) {
                    char c = line.charAt(j);

                    if (c == '@') {
                        x2 = i;
                        y2 = 2 * j;
                        row.add(c);
                        row.add('.');
                    } else if (c == 'O') {
                        row.add('[');
                        row.add(']');
                    } else {
                        row.add(c);
                        row.add(c);
                    }
                }
                grid2.add(row);
                i++;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(movesFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                moves.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        for (String move : moves) {
            for (char c : move.toCharArray()) {
                int dir = getDir(c);

                if (canUpdate(grid, x, y, dir)) {
                    updateGrid(grid, x, y, dir, '.');

                    x += dirs[dir][0];
                    y += dirs[dir][1];
                }
            }
        }

        for (String move : moves) {
            for (char c : move.toCharArray()) {
                int dir = getDir(c);

                if ((dir == 2 || dir == 3) && canUpdate2LR(grid2, x2, y2, dir)) {
                    updateGridLR(grid2, x2, y2, dir, '.');

                    x2 += dirs[dir][0];
                    y2 += dirs[dir][1];
                }
                if ((dir == 0 || dir == 1) && canUpdate2UD(grid2, x2, y2, dir)) {
                    updateGridUD(grid2, x2, y2, dir, '.');

                    x2 += dirs[dir][0];
                    y2 += dirs[dir][1];
                }
            }
        }

        long GPScoord = 0;
        long GPScoord2 = 0;

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j) == 'O') {
                    GPScoord += (i * 100) + j;
                }
            }
        }

        for (int i = 0; i < grid2.size(); i++) {
            for (int j = 0; j < grid2.get(i).size(); j++) {
                if (grid2.get(i).get(j) == '[') {
                    GPScoord2 += (i * 100) + j;
                }
            }
        }

        System.out.println("GPS coordinates: " + GPScoord);
        System.out.println("GPS coordinates 2: " + GPScoord2);
    }
}