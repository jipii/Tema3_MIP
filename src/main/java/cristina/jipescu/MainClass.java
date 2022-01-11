package cristina.jipescu;

import cristina.jipescu.entities.Oras;

import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        Oras oras = new Oras();
        oras.citireZoneParcare();
        oras.citireSituatiiParcari();
        oras.citireZoneComerciale();

        Scanner scanner = new Scanner(System.in);

        System.out.println("1.Afisare parcare apropiata");
        System.out.println("2.Afisare parcare cu cele mai multe locuri disponibile");
        System.out.println("3.Afisare farmacie");

        int optiune = scanner.nextInt();
        switch (optiune) {
            case 1:
                oras.parcareApropiata();
                break;

            case 2:
                oras.parcareEvitareAglomeratie();
                break;

            case 3:
                oras.farmacieApropiata();
                break;

            default:
                break;
        }
        scanner.close();
    }
}
