package cristina.jipescu.entities;

import java.util.Scanner;

public class Masina {
    private int latN;
    private int latS;
    private int longV;
    private int longE;
    private Scanner scanner;

    public Masina() {
        scanner = new Scanner(System.in);
        latN = scanner.nextInt();
        latS = scanner.nextInt();
        longV = scanner.nextInt();
        longE = scanner.nextInt();
    }

    public int getLatN() {
        return latN;
    }

    public void setLatN(int latN) {
        this.latN = latN;
    }

    public int getLatS() {
        return latS;
    }

    public void setLatS(int latS) {
        this.latS = latS;
    }

    public int getLongV() {
        return longV;
    }

    public void setLongV(int longV) {
        this.longV = longV;
    }

    public int getLongE() {
        return longE;
    }

    public void setLongE(int longE) {
        this.longE = longE;
    }
}
