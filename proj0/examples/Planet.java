/** creating a Planet class */
public class Planet {
    private static final double G = 6.67 * 1e-11;
    /** instance variables */
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    /** Constructor */
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
    /** calculate distance */
    public double calcDistance(Planet p) {
        double diffX = p.xxPos - xxPos;
        double diffY = p.yyPos - yyPos;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }
    /** calculate force */
    public double calcForceExertedBy(Planet p) {
        double r = calcDistance(p);
        return G * mass * p.mass / (r * r);
    }
    public double calcForceExertedByX(Planet p) {
        double F = calcForceExertedBy(p);
        double r = calcDistance(p);
        double diffX = p.xxPos - xxPos;
        return F * diffX / r;
    }
    public double calcForceExertedByY(Planet p) {
        double F = calcForceExertedBy(p);
        double r = calcDistance(p);
        double diffY = p.yyPos - yyPos;
        return F * diffY / r;
    }
    /** calculate net force */
    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double fXNet = 0.0;
        for (Planet p: allPlanets) {
            if (!equals(p)) {
                fXNet += calcForceExertedByX(p);
            }
        }
        return fXNet;
    }
    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double fYNet = 0.0;
        for (Planet p: allPlanets) {
            if (!equals(p)) {
                fYNet += calcForceExertedByY(p);
            }
        }
        return fYNet;
    }
    public void update(double dt, double fX, double fY) {
        double aXNet = fX / this.mass;
        double aYNet = fY / this.mass;

        this.xxVel += dt * aXNet;
        this.yyVel += dt * aYNet;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }

    /** Draws this.Planet at its position */
    public void draw() {
        StdDraw.picture(xxPos, yyPos, "./images/" + imgFileName);
    }
}