package AntOptimization;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AntAlgoritm {
    static DataOptimization dataOut;

    public static DataOptimization Algoritm() throws IOException {
        dataOut = new DataOptimization();

        inizializeAnts();
        getOptimaWay();
        antColonyAlgorithm();
        outInConsoleDataAntOptimization();
        System.gc();

        return dataOut;
    }

    private static void inizializeAnts() {
        int summCountAnts = AntColony.getCountColony() * AntColony.getCountAntsInOneColony();
        for (int countAnts = 0; countAnts < summCountAnts; countAnts++) {
            for (int antsInOneColony = 0; antsInOneColony < AntColony.getCountAntsInOneColony(); antsInOneColony++){
                for (int IndexFirstColonyInWay = 0; IndexFirstColonyInWay < AntColony.getCountColony(); IndexFirstColonyInWay++){
                    Ant.addAnt(new Ant(IndexFirstColonyInWay));
                }
            }
        }
    }

    private static void antColonyAlgorithm() throws IOException  {
        int CurrentEra = 1;
        int NotChangeMinWay = 0;
        Date date = new Date();
        long TimeWork = date.getTime();
        StringBuffer sb = new StringBuffer("");

        do {
            sb.delete( 0, sb.length() );
            sb.append("Эпоха № ").append(CurrentEra).append(" ");

            bodyAntColonyAlgorithm();

            dataOut.listOptimaWay.add( Ant.getListAnt().get(Ant.getIngexMinimalLengthWay(Ant.getListAnt())).getAntWay() );
            dataOut.listLengthOptimaWay.add( Ant.getListAnt().get(Ant.getIngexMinimalLengthWay(Ant.getListAnt())).getLengthWay() );

            if (CurrentEra > 1){

                if ( dataOut.listLengthOptimaWay.get(CurrentEra-2) == dataOut.listLengthOptimaWay.get(CurrentEra-1) ) {
                    NotChangeMinWay++;
                }
                else { NotChangeMinWay = 0;}

            }

//            sb.append( Ant.getListAnt().get(Ant.getIngexMinimalLengthWay(Ant.getListAnt())).getLengthWay() ).append(" ");
            sb.append(dataOut.listLengthOptimaWay.get(CurrentEra-1));
            sb.append(Arrays.toString(dataOut.listOptimaWay.get(CurrentEra-1)));
            System.out.println(sb);

        } while ((++CurrentEra <= ParameterAntOptimization.getMaxCountEra())&(NotChangeMinWay < 5000));


        Date date2 = new Date();
        TimeWork = TimeWork - date2.getTime();
        dataOut.timeOptimization = TimeWork;
        dataOut.eraOptimization = --CurrentEra;
        System.out.println("Алгоритм работал:  " + (TimeWork * (-1)) + " мс");
        System.out.println("Эпох пройдено : " + CurrentEra);
        if (NotChangeMinWay == 5000){
            System.out.println("На протяжении 5000 эпох путь не улучшался, алгоритм закончен");
        }
        else{
            System.out.println("Домтигнут лимит эпох, алгоритм закончен");
        }
    }

    private static void bodyAntColonyAlgorithm() {
        ParameterAntOptimization.calculationChangePferomoneOnWay();
        ParameterAntOptimization.calculationProbabityTransitionInColony();
        for (int i = 0; i < Ant.getListAnt().size(); Ant.getListAnt().get(i++).changeWay() );
        changeOptimaWay();
    }

    private static void changeOptimaWay(){
        if (dataOut.lengthOptimaWay > Ant.getListAnt().get(Ant.getIngexMinimalLengthWay(Ant.getListAnt())).getLengthWay()) {
            getOptimaWay();
        }
    }

    private static void getOptimaWay(){
        int minWayIndex = Ant.getIngexMinimalLengthWay(Ant.getListAnt());
        dataOut.optimaWay = Ant.getListAnt().get(minWayIndex).getAntWay();
        dataOut.lengthOptimaWay =  Ant.getListAnt().get(minWayIndex).getLengthWay();
    }

    private static void outInConsoleDataAntOptimization() throws IOException {
        System.out.println();
        System.out.println("Вывод");
        System.out.println("Количество муравейников равно");
        System.out.println(AntColony.getCountColony());
        System.out.println("Количество муравьёв в каждом муравейнике равно: ");
        System.out.println(AntColony.getCountAntsInOneColony());
        System.out.println("Матрица расстояний между муравейниками равна: ");
        System.out.println(Arrays.toString(AntColony.getDistanceBetweenColony()));

        System.out.println();
        System.out.println("Параметры алгоритма:");
        System.out.println("Коэфициент влияния феромона: ");
        System.out.println( ParameterAntOptimization.getDegreeInfluencePheromone());
        System.out.println("Коэфициент влияния расстояния между муравейниками");
        System.out.println(ParameterAntOptimization.getDegreeInfluenceDistance());
        System.out.println("Коэфициент учитывающий испарение феромона:");
        System.out.println( ParameterAntOptimization.getEvaporationPheromone());
        System.out.println();
        System.out.println("Переменные алгоритма:");
        System.out.println("Среднее расстояние между муравейниками равно: ");
        System.out.println( ParameterAntOptimization.getAverangDistant() );
        System.out.println("Параметр видимости муравейника: ");
        System.out.println(Arrays.toString( ParameterAntOptimization.getArrayVisibilityColony() ));

        System.out.println("Количество феромона на пути из i-го в j-й муравейник");
        System.out.println(Arrays.toString( ParameterAntOptimization.getArrayAmountPheromoneOnWay() ));

        System.out.println("Вероятность перехода из i-го в j-й муравейник");
        System.out.println(Arrays.toString( ParameterAntOptimization.getProbabilitiTransitionInColony() ));

        System.out.println("Общее количество муравьев:");
        System.out.println(Ant.getListAnt().size());

        System.out.println();
        System.out.println("Минимальный путь: ");

        System.out.print("Длина минимального пути равна: ");
        System.out.println(Ant.getListAnt().get(Ant.getIngexMinimalLengthWay(Ant.getListAnt())).getLengthWay());

        System.out.println("Лучший путь за все эпохи: ");
        System.out.println(Arrays.toString(dataOut.optimaWay));

        System.out.print("Длина оптимального пути за все эпохи: ");
        System.out.println(dataOut.lengthOptimaWay);
        System.out.println("Матрица путей по эпохам: ");

       // System.out.println(Arrays.toString(dataOut.listLengthOptimaWay.toArray()));
    }

    public static class DataOptimization {
        int countColony;
        int countAntInOneColony;
        int countAnts;
        double lengthOptimaWay;
        int maxCountEra;
        double degreeInfluencePheromone;
        double degreeInfluenceDistance;
        double evaporationPheromone;
        int[] optimaWay;
        ArrayList<Double> listLengthOptimaWay = new ArrayList<>();
        ArrayList<int[]> listOptimaWay = new ArrayList<>();

        long timeOptimization;
        int eraOptimization;

        DataOptimization() {
            countColony = AntColony.getCountColony();
            countAntInOneColony = AntColony.getCountAntsInOneColony();
            countAnts = countColony * countAntInOneColony;
            lengthOptimaWay = 0;
            maxCountEra = ParameterAntOptimization.getMaxCountEra() ;
            degreeInfluencePheromone =  ParameterAntOptimization.getDegreeInfluencePheromone() ;
            degreeInfluenceDistance =  ParameterAntOptimization.getDegreeInfluenceDistance() ;
            evaporationPheromone = ParameterAntOptimization.getEvaporationPheromone() ;
            optimaWay = new int[countColony];
        }

    }


}
