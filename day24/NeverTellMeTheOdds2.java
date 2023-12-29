import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NeverTellMeTheOdds2 {

    private static List<Particle> particles = new ArrayList<>();

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        calculateCoordinates();
    }

    private static void readInput(File file, String filePath) {
        
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] splitLine = line.split(" @ ");
                Particle newParticle = new Particle(parseCoordinates(splitLine[0]), parseCoordinates(splitLine[1]));
                particles.add(newParticle);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }    
    }

    private static long[] parseCoordinates(String coordinateString) {
        return Arrays.stream(coordinateString.split(", ")).mapToLong(str -> Long.parseLong(str.strip())).toArray();
    }

    private static void calculateCoordinates() {
        double[][] coeficients = new double[4][4];
        double[] results = new double[4];
        
        
        for (int i = 0; i < 4; i++) {
            Particle particle = particles.get(i);
            Particle particle2 = particles.get(i + 1);
            
            coeficients[i][0] = (double) (particle2.velocity[1] - particle.velocity[1]);
            coeficients[i][1] = (double) (particle.velocity[0] - particle2.velocity[0]);
            coeficients[i][2] = (double) (particle.position[1] - particle2.position[1]);
            coeficients[i][3] = (double) (particle2.position[0] - particle.position[0]);
            
            results[i] = (double) (particle2.position[0] * particle2.velocity[1] - particle2.position[1] * particle2.velocity[0] - particle.position[0] * particle.velocity[1] + particle.position[1] * particle.velocity[0]);
        }

        guassianElimination(coeficients, results);

        long x = Math.round(results[0]);
        long y = Math.round(results[1]);

        for (int i = 0; i < 4; i++) {
            Particle particle = particles.get(i);
            Particle particle2 = particles.get(i + 1);
            
            coeficients[i][0] = (double) (particle2.velocity[1] - particle.velocity[1]);
            coeficients[i][1] = (double) (particle.velocity[2] - particle2.velocity[2]);
            coeficients[i][2] = (double) (particle.position[1] - particle2.position[1]);
            coeficients[i][3] = (double) (particle2.position[2] - particle.position[2]);
            
            results[i] = (double) (particle2.position[2] * particle2.velocity[1] - particle2.position[1] * particle2.velocity[2] - particle.position[2] * particle.velocity[1] + particle.position[1] * particle.velocity[2]);
        }

        guassianElimination(coeficients, results);

        long z = Math.round(results[0]);

        System.out.println(x + y + z);
    }

    private static void guassianElimination(double[][] coeficients, double[] results) {
        int nVariables = coeficients.length;

        for (int i = 0; i < nVariables; i++) {
            double pivot = coeficients[i][i];

            for (int j = 0; j < nVariables; j++) {
                coeficients[i][j] /= pivot;
            }

            results[i] /= pivot;

            for (int k = 0; k < nVariables; k++) {
                if (k != i) {
                    double factor = coeficients[k][i];

                    for (int j = 0; j < nVariables; j++) {
                        coeficients[k][j] -= factor * coeficients[i][j];
                    }

                    results[k] -= factor * results[i];
                }
            }
        }
    }
}
