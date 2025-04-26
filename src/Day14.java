import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day14 {
    public static void main(String[] args) {
        List<int[]> pos = new ArrayList<>();
        List<int[]> speed = new ArrayList<>();

        String filePath = "input/inp14.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");

                String[] pValues = parts[0].split("[=,]");
                int p1 = Integer.parseInt(pValues[1]);
                int p2 = Integer.parseInt(pValues[2]);

                String[] vValues = parts[1].split("[=,]");
                int v1 = Integer.parseInt(vValues[1]);
                int v2 = Integer.parseInt(vValues[2]);

                pos.add(new int[] { p1, p2 });
                speed.add(new int[] { v1, v2 });
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        int width = 101, height = 103;
        int time = 100;
        int n = pos.size();

        // Part 1

        int[] quadrant = new int[4];

        for (int i = 0; i < n; i++) {
            int[] p = pos.get(i);
            int[] v = speed.get(i);

            p[0] += v[0] * time;
            p[1] += v[1] * time;

            int x = p[0] % width < 0 ? (p[0] % width) + width : p[0] % width;
            int y = p[1] % height < 0 ? (p[1] % height) + height : p[1] % height;

            if (x < width / 2 && y < height / 2) {
                quadrant[0]++;
            }
            if (x > width / 2 && y < height / 2) {
                quadrant[1]++;
            }
            if (x < width / 2 && y > height / 2) {
                quadrant[2]++;
            }
            if (x > width / 2 && y > height / 2) {
                quadrant[3]++;
            }
        }

        long safetyFactor = quadrant[0] * quadrant[1] * quadrant[2] * quadrant[3];
        System.out.println("Safety factor: " + safetyFactor);

        // Part 2

        String outFilePath = "input/inp14.out.txt";
        Set<String> set;

        for (int i = 0; i <= 50000; i++) {
            set = new HashSet<>();

            for (int j = 0; j < n; j++) {
                pos.get(j)[0] += speed.get(j)[0];
                pos.get(j)[0] = pos.get(j)[0] % width < 0 ? (pos.get(j)[0] % width) + width : pos.get(j)[0] % width;

                pos.get(j)[1] += speed.get(j)[1];
                pos.get(j)[1] = pos.get(j)[1] % height < 0 ? (pos.get(j)[1] % height) + height : pos.get(j)[1] % height;

                set.add(pos.get(j)[0] + "," + pos.get(j)[1]);
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFilePath, true))) {
                bw.write("Time: " + i + "\n");
                for (int j = 0; j < height; j++) {
                    for (int k = 0; k < width; k++) {
                        bw.write(set.contains(k + "," + j) ? "#" : ".");
                    }
                    bw.write("\n");
                }
                bw.write("\n");
            } catch (IOException e) {
                System.err.println("Error writing to the file: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
