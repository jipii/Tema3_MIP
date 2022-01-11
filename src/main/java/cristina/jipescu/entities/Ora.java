package cristina.jipescu.entities;

public class Ora {
    private int ora;
    private int minut;

    public Ora(int ora, int minut) {
        this.ora = ora;
        this.minut = minut;
    }

    public int getOra() {
        return ora;
    }

    public void setOra(int ora) {
        this.ora = ora;
    }

    public int getMin() {
        return minut;
    }

    public void setMin(int minut) {
        this.minut = minut;
    }

    public int comparareOra(Ora oraDiferita) {
        if (ora < oraDiferita.getOra()) {
            return -1;
        }
        if (ora > oraDiferita.getOra()) {
            return 1;
        }
        if (minut < oraDiferita.getMin()) {
            return -1;
        }
        if (minut > oraDiferita.getMin()) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        if (minut > 9) {
            return ora + ":" + minut;
        }
        return ora + ":0" + minut;
    }
}
