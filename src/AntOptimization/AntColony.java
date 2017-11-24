package AntOptimization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AntColony {
    private static int countColony;
    private static int countAntsInOneColony;
    private static double[][] DistanceBetweenColony;

    public static  void createAntColony() throws IOException {
        assingValueVariableColonyFromConsole();
        setDistanceBetweenColony();
    }

    public static  void createAntColony(String fileName) throws IOException {
        countColony = (int) WriteIntoExcel.ReadFromExcell.ReadValue(fileName +".xls", "Параметры алгоритма", 1, 1);
        countAntsInOneColony = (int) WriteIntoExcel.ReadFromExcell.ReadValue(fileName +".xls", "Параметры алгоритма", 2, 1);
        DistanceBetweenColony = new double[countColony][countColony];
        DistanceBetweenColony = WriteIntoExcel.ReadFromExcell.LoadDoubleArray(countColony, countColony);
    }

    public static double[][] getDistanceBetweenColony() {
        return DistanceBetweenColony;
    }

    public static void setDistanceBetweenColony(double[][] distanceBetweenColony) {
        DistanceBetweenColony = distanceBetweenColony;
    }

    public static int getCountColony() {
        return countColony;
    }

    public static void setCountColony(int countColony) {
        countColony = countColony;
    }

    public static int getCountAntsInOneColony() {
        return countAntsInOneColony;
    }

    public static void setCountAntsInOneColony(int countAntsInOneColony) {
        countAntsInOneColony = countAntsInOneColony;
    }

    private static void assingValueVariableColonyFromConsole() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите количество муравейников");
        countColony = Integer.parseInt(br.readLine().trim());
        System.out.println("Введите количество муравьёв в каждом муравейнике");
        countAntsInOneColony = Integer.parseInt(br.readLine().trim());
    }

    private static void setDistanceBetweenColony() throws IOException {
        System.out.println("Задать расстоянмя автоматически? Y/N ?");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        switch (br.readLine().trim().toLowerCase()) {
            case "y":
            {
                setAutoDistanceBetweenColony();
                break;
            }
            case "n":
            {
                setCustomDistanceBetweenColony();
                break;
            }
        }

    }

    private static void setCustomDistanceBetweenColony() throws IOException  {
        System.out.println("В ручном вводе");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        DistanceBetweenColony = new double[countColony][countColony];
        for (int i = 0; i < countColony; i++) {
            for (int j = 0; j < countColony; j++) {
                if (i!=j) {
                    {
                        System.out.println("Введите расстояние между " + (i+1) + "-м и " + (j+1) + "-м муравейниками");
                        DistanceBetweenColony[i][j] = Double.parseDouble(br.readLine().trim());
                    }
                }
                else {
                    DistanceBetweenColony[i][j] = 0;
                }
            }
        }
    }

    private static void setAutoDistanceBetweenColony() {
        DistanceBetweenColony = new double[countColony][countColony];
        for (int i = 0; i < countColony; i++) {
            for (int j = 0; j < countColony; j++) {
                if (i!=j) {
                    DistanceBetweenColony[i][j] = 1 + AntMath.roundTo2Decimal(AntMath.randomDouble(99.0));
                }
                else {
                    DistanceBetweenColony[i][j] = 0;
                }
            }
        }
    }

}
