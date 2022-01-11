package cristina.jipescu.entities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Oras {
    private JSONParser parser;
    private JSONArray zoneParcare;
    private JSONArray zoneComerciale;
    private ArrayList<ArrayList<String>> parcare1;
    private ArrayList<ArrayList<String>> parcare2;
    private ArrayList<ArrayList<String>> parcare3;
    private final int stanga = 700;
    private final int dreapta = 600;
    private final int sus = 500;
    private final int jos = 600;
    private Masina masina;

    public Oras() {
        parser = new JSONParser();
        zoneParcare = new JSONArray();
        zoneComerciale = new JSONArray();
        parcare1 = new ArrayList<>();
        parcare2 = new ArrayList<>();
        parcare3 = new ArrayList<>();
        masina = new Masina();
    }

    private void verificareCoord() {
        if (masina.getLatN() < 500 || masina.getLatN() > 600) {
            System.out.println("Coordonate in afara orasului!");
            System.exit(-1);
        }
        if (masina.getLatS() < 500 || masina.getLatS() > 600) {
            System.out.println("Coordonate in afara orasului!");
            System.exit(-1);
        }
        if (masina.getLongE() < 600 || masina.getLongE() > 700) {
            System.out.println("Coordonate in afara orasului!");
            System.exit(-1);
        }
        if (masina.getLongV() < 600 || masina.getLongV() > 700) {
            System.out.println("Coordonate in afara orasului!");
            System.exit(-1);
        }
    }

    public void citireZoneParcare() {
        try {
            zoneParcare = (JSONArray) parser.parse(new FileReader("parcari.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Eroare la citire din fisierul parcari.json");
        }
    }

    public void citireZoneComerciale() {
        try{
            zoneComerciale = (JSONArray) parser.parse(new FileReader("comercial.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Eroare la citire din fisierul comercial.json");
        }
    }

    public void citireSituatiiParcari() {
        String[] fisiere = new String[3];
        fisiere[0] = "situatieParcari1_ora10.00.txt";
        fisiere[1] = "situatieParcari2_ora10.00.txt";
        fisiere[2] = "situatieParcari3_ora10.00.txt";
        ArrayList<ArrayList<String>> parcare1 = new ArrayList<>();
        ArrayList<ArrayList<String>> parcare2 = new ArrayList<>();
        ArrayList<ArrayList<String>> parcare3 = new ArrayList<>();

        for (int index = 0; index < fisiere.length; index++) {
            try {
                Scanner scanner = new Scanner(new FileReader(fisiere[index]));
                while (scanner.hasNextLine()) {
                    String[] ore = scanner.nextLine().split(", ");
                    ore[0] = ore[0].substring(ore[0].length() - 5, ore[0].length());

                    ArrayList<String> oreArray = new ArrayList<>();

                    for (String ora : ore) {
                        oreArray.add(ora);
                    }
                    switch (index) {
                        case 0:
                            parcare1.add(oreArray);
                            break;
                        case 1:
                            parcare2.add(oreArray);
                            break;
                        case 3:
                            parcare3.add(oreArray);
                            break;
                        default:
                            break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Eroare la citire din fisierul: " +  fisiere[index]);
            }
            boolean fisierInvalid = false;
            int indexLocuri = 0;
            for (int indexZona = 0; indexZona < zoneParcare.size() && !fisierInvalid; indexZona++) {
                JSONObject zonaParcare = (JSONObject) zoneParcare.get(indexZona);
                JSONArray parcari = (JSONArray) zonaParcare.get("parcari");

                for (int indexParcare = 0; indexParcare < parcari.size() && !fisierInvalid; indexParcare++) {
                    JSONObject parcare = (JSONObject) parcari.get(indexParcare);
                    int nrLocuriParcare = Integer.parseInt(parcare.get("locuri").toString());

                    switch(index) {
                        case 0:
                            if(nrLocuriParcare < parcare1.get(indexLocuri).size()) {
                                fisierInvalid = true;
                            }
                            break;
                        case 1:
                            if(nrLocuriParcare < parcare2.get(indexLocuri).size()) {
                                fisierInvalid = true;
                            }
                            break;
                        case 2:
                            if(nrLocuriParcare < parcare3.get(indexLocuri).size()) {
                                fisierInvalid = true;
                            }
                            break;
                        default:
                            break;
                    }
                    indexLocuri++;
                }
            }
            if(fisierInvalid) {
                System.out.println("Eroare fisierul " + fisiere[index]);
            }else{
                switch(index) {
                    case 0:
                        this.parcare1 = parcare1;
                        break;
                    case 1:
                        this.parcare2 = parcare2;
                        break;
                    case 2:
                        this.parcare3 = parcare3;
                        break;
                    default:
                        break;
                }
            }
        }

    }

    public void selectareFisier(ArrayList<ArrayList<String>> situatieParcari) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selectati un fisier: ");
        if(parcare1.size() != 0) {
            System.out.println("1.sitatuieParcari1_ora10.00.txt");
        }
        if(parcare2.size() != 0) {
            System.out.println("1.sitatuieParcari2_ora10.00.txt");
        }
        if(parcare3.size() != 0) {
            System.out.println("1.sitatuieParcari3_ora10.00.txt");
        }
        int fisier = scanner.nextInt();
        switch(fisier) {
            case 1:
                for (int index = 0; index < parcare1.size(); index++) {
                    situatieParcari.add(parcare1.get(index));
                }
                break;
            case 2:
                for (int index = 0; index < parcare2.size(); index++) {
                    situatieParcari.add(parcare2.get(index));
                }
                break;
            case 3:
                for (int index = 0; index < parcare3.size(); index++) {
                    situatieParcari.add(parcare3.get(index));
                }
                break;
            default:
                System.out.println("Fisier inexistent!");
                break;
        }

    }

    public boolean verificareLocuriLibere(ArrayList<ArrayList<String>> situatieParcari) {
        int indexSituatieParcari = 0;
        for (int index = 0; index < zoneParcare.size(); index++) {
            JSONObject zona = (JSONObject) zoneParcare.get(index);
            JSONArray parcari = (JSONArray) zona.get("parcari");

            for (int indexParcare = 0; indexParcare < parcari.size(); indexParcare++) {
                JSONObject parcare = (JSONObject) parcari.get(indexParcare);
                int nrLocuri = Integer.parseInt(parcare.get("locuri").toString());

                if(situatieParcari.get(indexSituatieParcari).size() - nrLocuri < 0) {
                    return true;
                }
                indexSituatieParcari++;
            }
        }
        return false;
    }

    public double distantaPuncte(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2, 2));
    }
    public void parcareApropiata() {
        ArrayList<ArrayList<String>> situatieParcari = new ArrayList<>();
        selectareFisier(situatieParcari);

        if(verificareLocuriLibere(situatieParcari)) {
             double distantaMinima = Double.MAX_VALUE;
             int indexZonaApropiata = 0;
             int indexParcareApropiata = 0;
             int locuriLibere = 0;
             int indexSituatie = 0;

             for (int index = 0; index < zoneParcare.size(); index++) {
                 JSONObject zonaParcare = (JSONObject) zoneParcare.get(index);
                 JSONArray parcari = (JSONArray) zonaParcare.get("parcari");

                 for (int indexParcare = 0; indexParcare < parcari.size(); indexParcare++) {
                     JSONObject parcare = (JSONObject) parcari.get(indexParcare);
                     ArrayList<Integer> coordParcare = new ArrayList<>();
                     ArrayList<Integer> coordMasina = new ArrayList<>();

                     HashMap<Integer, ArrayList<Integer>> colturiParcare = new HashMap<>();
                     HashMap<Integer, ArrayList<Integer>> colturiMasina = new HashMap<>();

                     coordParcare.add(Integer.parseInt(parcare.get("N").toString()));
                     coordParcare.add(Integer.parseInt(parcare.get("S").toString()));
                     coordParcare.add(Integer.parseInt(parcare.get("V").toString()));
                     coordParcare.add(Integer.parseInt(parcare.get("E").toString()));

                     coordMasina.add(masina.getLatN());
                     coordMasina.add(masina.getLatS());
                     coordMasina.add(masina.getLongV());
                     coordMasina.add(masina.getLongE());

                     int indexColt = 0;
                     for ( int indexCoord = 0; indexCoord < 2; indexCoord++) {
                         ArrayList<Integer> coordonataParcare = new ArrayList<>();
                         coordonataParcare.add(coordParcare.get(indexCoord));
                         coordonataParcare.add(coordParcare.get(2));
                         colturiParcare.put(indexColt, coordonataParcare);

                         ArrayList<Integer> coordonataMasina = new ArrayList<>();
                         coordonataMasina.add(coordMasina.get(indexCoord));
                         coordonataMasina.add(coordMasina.get(2));
                         colturiMasina.put(indexColt, coordMasina);

                         indexColt++;

                         coordonataParcare.clear();
                         coordonataParcare.add(coordParcare.get(indexCoord));
                         coordonataParcare.add(coordParcare.get(3));
                         colturiParcare.put(indexColt, coordonataParcare);

                         coordonataMasina.clear();
                         coordonataMasina.add(coordMasina.get(indexCoord));
                         coordonataMasina.add(coordMasina.get(3));
                         colturiMasina.put(indexColt, coordonataMasina);

                         indexColt++;
                     }
                     double distantaMinCoord = Double.MAX_VALUE;
                     for(int parking = 0; parking < coordParcare.size(); parking++) {
                         for(int car = 0; car < coordMasina.size(); car++) {
                             int parkingVertical = colturiParcare.get(parking).get(0);
                             int parkingOrizontal = colturiParcare.get(parking).get(1);
                             int carVertical = colturiMasina.get(car).get(0);
                             int carOrizontal = colturiMasina.get(car).get(1);

                            double distanta = distantaPuncte(parkingVertical, parkingOrizontal, carVertical, carOrizontal);
                            if(distanta < distantaMinCoord) {
                                distantaMinCoord=distanta;
                            }
                         }
                     }
                     int locuriGoale = Integer.parseInt(parcare.get("locuri").toString()) - situatieParcari.get(indexSituatie).size();

                     if(distantaMinCoord < distantaMinima && locuriGoale != 0) {
                         distantaMinima = distantaMinCoord;
                         indexZonaApropiata = index;
                         indexParcareApropiata = indexParcare;
                         locuriLibere = locuriGoale;
                     }
                     indexSituatie++;
                 }
             }
             JSONObject zonaApropiata = (JSONObject) zoneParcare.get(indexZonaApropiata);
             JSONArray parcari = (JSONArray) zonaApropiata.get("parcari");
             JSONObject parcareApropiata = (JSONObject) parcari.get(indexParcareApropiata);

             System.out.println(zonaApropiata.get("nume".toString()));
             System.out.println(parcareApropiata.get("nume").toString());
             System.out.println("Locuri libere: " + locuriLibere);
             System.out.println("N: " + parcareApropiata.get("N").toString());
             System.out.println("S: " + parcareApropiata.get("S").toString());
             System.out.println("V: " + parcareApropiata.get("V").toString());
             System.out.println("E: " + parcareApropiata.get("E").toString());
        } else {
            Ora oraEliberare = new Ora(23, 59);
            int indexParcareSituatie = 0;
            for(int index = 0; index < situatieParcari.size(); index++) {
                ArrayList<String> parcare = situatieParcari.get(index);
                for( int indexOre = 0; indexOre < parcare.size(); indexOre++) {
                    int oraNoua = Integer.parseInt(parcare.get(indexOre).substring(0,2));
                    int minNou = Integer.parseInt(parcare.get(indexOre).substring(3,5));
                    Ora nouaOra = new Ora(oraNoua, minNou);

                    if(oraEliberare.comparareOra(nouaOra) == 1) {
                        oraEliberare.setOra(nouaOra.getOra());
                        oraEliberare.setMin(nouaOra.getMin());
                        indexParcareSituatie = index;
                    }
                }
            }
            int indexZonaParcare = 0;
            boolean gasit = false;
            for(int index = 0; index < zoneParcare.size() && !gasit; index++) {
                JSONObject zonaParcare = (JSONObject) zoneParcare.get(index);
                JSONArray parcari = (JSONArray) zonaParcare.get("parcari");

                for(int indexParcare = 0; indexParcare < parcari.size() && !gasit; indexParcare++) {
                    if(indexZonaParcare == indexParcareSituatie) {
                        JSONObject parcare = (JSONObject) parcari.get(indexParcare);
                        System.out.println(oraEliberare.toString());
                        System.out.println(zonaParcare.get("nume").toString());
                        System.out.println(parcare.get("N"));
                        System.out.println(parcare.get("S"));
                        System.out.println(parcare.get("V"));
                        System.out.println(parcare.get("E"));
                        gasit = true;
                    }
                    indexZonaParcare++;
                }
            }
        }
    }

    public void parcareEvitareAglomeratie() {
        ArrayList<ArrayList<String>> situatieParcari = new ArrayList<>();

        selectareFisier(situatieParcari);

        if(verificareLocuriLibere(situatieParcari)) {
            double distantaMin = Double.MAX_VALUE;
            int indexZonaLibera = 0;
            int indexParcareLibera = 0;
            int locuriLibere = 0;
            int nrLocuriLibereMax = 0;
            int indexSituatie = 0;

            for(int index = 0; index < zoneParcare.size(); index++) {
                JSONObject zonaParcare = (JSONObject) zoneParcare.get(index);
                JSONArray parcari = (JSONArray) zonaParcare.get("parcari");

                for(int indexParcare = 0; indexParcare < parcari.size(); indexParcare++) {
                    JSONObject parcare = (JSONObject) parcari.get(indexParcare);
                    ArrayList <Integer> coordParcare = new ArrayList<>();
                    ArrayList <Integer> coordMasina = new ArrayList<>();
                    HashMap<Integer, ArrayList<Integer>> colturiParcare = new HashMap();
                    HashMap<Integer, ArrayList<Integer>> colturiMasina = new HashMap();

                    coordParcare.add(Integer.parseInt(parcare.get("N").toString()));
                    coordParcare.add(Integer.parseInt(parcare.get("S").toString()));
                    coordParcare.add(Integer.parseInt(parcare.get("V").toString()));
                    coordParcare.add(Integer.parseInt(parcare.get("E").toString()));

                    coordMasina.add(masina.getLatN());
                    coordMasina.add(masina.getLatS());
                    coordMasina.add(masina.getLongV());
                    coordMasina.add(masina.getLongE());

                    int indexColt = 0;
                    for(int indexCoord = 0; indexCoord < 2; indexCoord++) {
                        ArrayList<Integer> coordonataParcare = new ArrayList<>();
                        coordonataParcare.add(coordParcare.get(indexCoord));
                        coordonataParcare.add(coordParcare.get(2));
                        colturiParcare.put(indexColt, coordonataParcare);

                        ArrayList<Integer> coordonataMasina = new ArrayList<>();
                        coordonataMasina.add(coordMasina.get(indexCoord));
                        coordonataMasina.add(coordMasina.get(2));
                        colturiMasina.put(indexColt, coordonataMasina);

                        indexColt++;

                        coordonataParcare.clear();
                        coordonataParcare.add(coordParcare.get(indexCoord));
                        coordonataParcare.add(coordParcare.get(3));
                        colturiParcare.put(indexColt, coordonataParcare);

                        coordonataMasina.clear();
                        coordonataMasina.add(coordMasina.get(indexCoord));
                        coordonataMasina.add(coordMasina.get(3));
                        colturiMasina.put(indexColt, coordonataMasina);

                        indexColt++;
                    }
                    double distantaMinimaCoordonate = Double.MAX_VALUE;
                    for (int iParcare = 0; iParcare < coordParcare.size(); iParcare++) {
                        for (int iMasina = 0; iMasina < coordMasina.size(); iMasina++) {
                            int verticalParcare = colturiParcare.get(iParcare).get(0);
                            int orizontalParcare = colturiParcare.get(iParcare).get(1);
                            int verticalMasina = colturiMasina.get(iMasina).get(0);
                            int orizontalMasina = colturiMasina.get(iMasina).get(1);

                            double distanta = distantaPuncte(verticalParcare, orizontalParcare, verticalMasina, orizontalMasina);

                            if (distanta < distantaMinimaCoordonate) {
                                distantaMinimaCoordonate = distanta;
                            }
                        }
                    }

                    int locuriDisponibile = Integer.parseInt(parcare.get("locuri").toString()) - situatieParcari.get(indexSituatie).size();
                    if (locuriDisponibile > nrLocuriLibereMax) {
                        distantaMin = distantaMinimaCoordonate;
                        indexZonaLibera = index;
                        indexParcareLibera = indexParcare;
                        locuriLibere = locuriDisponibile;
                        distantaMinimaCoordonate = locuriDisponibile;
                    }
                    indexSituatie++;
                    if (locuriDisponibile == distantaMinimaCoordonate && distantaMin > distantaMinimaCoordonate) {
                        distantaMin = distantaMinimaCoordonate;
                        indexZonaLibera = index;
                        indexParcareLibera = indexParcare;
                        locuriLibere = locuriDisponibile;
                        distantaMinimaCoordonate = locuriDisponibile;
                    }
                }
            }

            JSONObject zonaApropiata = (JSONObject) zoneParcare.get(indexZonaLibera);
            JSONArray parcari = (JSONArray) zonaApropiata.get("parcari");
            JSONObject parcareApropiata = (JSONObject) parcari.get(indexParcareLibera);

            System.out.println(zonaApropiata.get("nume").toString());
            System.out.println(parcareApropiata.get("nume").toString());
            System.out.println("Locuri disponibile: " + locuriLibere);
            System.out.println("N: " + parcareApropiata.get("N").toString());
            System.out.println("S: " + parcareApropiata.get("S").toString());
            System.out.println("V: " + parcareApropiata.get("V").toString());
            System.out.println("E: " + parcareApropiata.get("E").toString());
        }
        else {
            Ora oraEliberare = new Ora(23, 59);
            int indexParcareSituatie = 0;

            for (int index = 0; index < situatieParcari.size(); index++) {

                ArrayList<String> parcare = situatieParcari.get(index);

                for (int indexOre = 0; indexOre < parcare.size(); indexOre++) {
                    int oraNoua = Integer.parseInt(parcare.get(indexOre).substring(0, 2));
                    int minutNou = Integer.parseInt(parcare.get(indexOre).substring(3, 5));
                    Ora oraNouaObiect = new Ora(oraNoua, minutNou);

                    if (oraEliberare.comparareOra(oraNouaObiect) == 1) {
                        oraEliberare.setOra(oraNouaObiect.getOra());
                        oraEliberare.setMin(oraNouaObiect.getMin());
                        indexParcareSituatie = index;
                    }
                }
            }

            int indexParcareZone = 0;
            boolean parcareGasita = false;
            for (int index = 0; index < zoneParcare.size() && !parcareGasita; index++) {
                JSONObject zonaParcare = (JSONObject) zoneParcare.get(index);
                JSONArray parcari = (JSONArray) zonaParcare.get("parcari");

                for (int indexParcare = 0; indexParcare < parcari.size() && !parcareGasita; indexParcare++) {
                    if (indexParcareZone == indexParcareSituatie) {
                        JSONObject parcare = (JSONObject) parcari.get(indexParcare);
                        System.out.println(oraEliberare.toString());
                        System.out.println(zonaParcare.get("nume").toString());
                        System.out.println(parcare.get("nume"));
                        System.out.println(parcare.get("N"));
                        System.out.println(parcare.get("S"));
                        System.out.println(parcare.get("V"));
                        System.out.println(parcare.get("E"));
                        parcareGasita = true;
                    }
                    indexParcareZone++;
                }
            }
        }
    }

    public void farmacieApropiata() {
        double distantaMinima = Double.MAX_VALUE;
        int indexZonaApropiata = 0;
        int indexFarmacieApropiata = 0;

        for (int index = 0; index < zoneComerciale.size(); index++) {
            JSONObject zonaComerciala = (JSONObject) zoneComerciale.get(index);
            JSONArray magazineZona = (JSONArray) zonaComerciala.get("magazine");

            for (int indexMagazin = 0; indexMagazin < magazineZona.size(); indexMagazin++) {

                JSONObject magazin = (JSONObject) magazineZona.get(indexMagazin);

                if (magazin.get("tip").equals("farmacie")) {
                    ArrayList<Integer> coordonateFarmacie = new ArrayList<>();
                    ArrayList<Integer> coordonateMasina = new ArrayList<>();

                    HashMap<Integer, ArrayList<Integer>> colturiFarmacie = new HashMap<>();
                    HashMap<Integer, ArrayList<Integer>> colturiMasina = new HashMap<>();

                    coordonateFarmacie.add(Integer.parseInt(magazin.get("N").toString()));
                    coordonateFarmacie.add(Integer.parseInt(magazin.get("S").toString()));
                    coordonateFarmacie.add(Integer.parseInt(magazin.get("V").toString()));
                    coordonateFarmacie.add(Integer.parseInt(magazin.get("E").toString()));

                    coordonateMasina.add(masina.getLatN());
                    coordonateMasina.add(masina.getLatS());
                    coordonateMasina.add(masina.getLongV());
                    coordonateMasina.add(masina.getLongE());

                    int indexColt = 0;
                    for (int indexCoordonate = 0; indexCoordonate < 2; indexCoordonate++) {
                        ArrayList<Integer> coordonataFarmacie = new ArrayList<>();
                        coordonataFarmacie.add(coordonateFarmacie.get(indexCoordonate));
                        coordonataFarmacie.add(coordonateFarmacie.get(2));
                        colturiFarmacie.put(indexColt, coordonataFarmacie);

                        ArrayList<Integer> coordonataMasina = new ArrayList<>();
                        coordonataMasina.add(coordonateMasina.get(indexCoordonate));
                        coordonataMasina.add(coordonateMasina.get(2));
                        colturiMasina.put(indexColt, coordonataMasina);

                        indexColt++;

                        coordonataFarmacie.clear();
                        coordonataFarmacie.add(coordonateFarmacie.get(indexCoordonate));
                        coordonataFarmacie.add(coordonateFarmacie.get(3));
                        colturiFarmacie.put(indexColt, coordonataFarmacie);

                        coordonataMasina.clear();
                        coordonataMasina.add(coordonateMasina.get(indexCoordonate));
                        coordonataMasina.add(coordonateMasina.get(3));
                        colturiMasina.put(indexColt, coordonataMasina);

                        indexColt++;
                    }

                    double distantaMinimaCoordonate = Double.MAX_VALUE;
                    for (int iFarmacie = 0; iFarmacie < coordonateFarmacie.size(); iFarmacie++) {
                        for (int iMasina = 0; iMasina < coordonateMasina.size(); iMasina++) {
                            int verticalParcare = colturiFarmacie.get(iFarmacie).get(0);
                            int orizontalParcare = colturiFarmacie.get(iFarmacie).get(1);
                            int verticalMasina = colturiMasina.get(iMasina).get(0);
                            int orizontalMasina = colturiMasina.get(iMasina).get(1);

                            double distanta = distantaPuncte(verticalParcare, orizontalParcare, verticalMasina, orizontalMasina);

                            if (distanta < distantaMinimaCoordonate) {
                                distantaMinimaCoordonate = distanta;
                            }
                        }
                    }

                    if (distantaMinimaCoordonate < distantaMinima) {
                        distantaMinima = distantaMinimaCoordonate;
                        indexZonaApropiata = index;
                        indexFarmacieApropiata = indexMagazin;
                    }
                }
            }
        }

        JSONObject zonaApropiata = (JSONObject) zoneComerciale.get(indexZonaApropiata);
        JSONArray magazine = (JSONArray) zonaApropiata.get("magazine");
        JSONObject farmacieApropiata = (JSONObject) magazine.get(indexFarmacieApropiata);

        System.out.println(zonaApropiata.get("nume").toString());
        System.out.println(farmacieApropiata.get("nume").toString());
    }
}
