public class NBody {
    private static String backgroundImage = "./images/starfield.jpg";
    private static final String backgroundMusic = "./audio/2001.mid";

    /** read the Radius from input file */
    public static double readRadius(String filename) {
        In in = new In(filename);
        in.readInt();
        return in.readDouble();
    }
    /** read Planet info and place them into an Planet array */
    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        in.readDouble();
        Planet[] allPlanets = new Planet[n];
        for (int i = 0; i < n; i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            allPlanets[i] = new Planet(xP, yP, xV, yV, m, img);
        }
        return allPlanets;
    }
    /** main function */
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]); //Read command line arguments
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = readRadius(filename); //Read Planets and the universe radius defined in filename
        Planet[] allPlanets = readPlanets(filename);
        StdDraw.enableDoubleBuffering(); //Enables double buffering
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdAudio.play(backgroundMusic);

        double t = 0.0; // time variable
        int n = allPlanets.length;
        double[] xForces = new double[n];
        double[] yForces = new double[n];
        while (t < T) {
            for (int i = 0; i < n; i++) { //Calculate net forces for each Planet
                double fX = allPlanets[i].calcNetForceExertedByX(allPlanets);
                double fY = allPlanets[i].calcNetForceExertedByY(allPlanets);
                xForces[i] = fX;
                yForces[i] = fY;
            }
            for (int i = 0; i < n; i++) { //Update each Planet's members
                allPlanets[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.picture(0, 0, NBody.backgroundImage); //Show the background

            for (Planet p : allPlanets) { //Draw all of the Planets
                p.draw();
            }
            StdDraw.show(); //Shows the drawing to the screen
            StdDraw.pause(10);
            t += dt;
        }

        StdOut.printf("%d\n", n); //Outputs the final states of each Planet for autograder to work correctly
        StdOut.printf("%.2e\n", radius);
        for (Planet p : allPlanets) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
        }
    }
}