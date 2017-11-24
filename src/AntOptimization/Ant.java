package AntOptimization;

import java.util.ArrayList;
import java.util.Random;


public class Ant {
    private static ArrayList<Ant> listAnt = new ArrayList<>();
    private int[] antWay;
    private double lengthWay;

    Ant() {
        Random random = new Random();
        new Ant(random.nextInt(AntColony.getCountColony()));
    }

    Ant(int firstColony) {
        antWay = new int[AntColony.getCountColony()];
        for(int j = 0; j< antWay.length; antWay[j] = j++) ;
        swapValueInArrayWay(antWay.length-1, firstColony);
        changeWay();
        calculationLengthWay();
    }

    public static void addAnt (Ant ant) {
        listAnt.add(ant);
    }

    public static ArrayList<Ant> getListAnt() {
        return listAnt;
    }

    public int[] getAntWay() {
        return antWay;
    }

    public double getLengthWay() {
        return lengthWay;
    }

    public void changeWay() {
        double ProbabilityTransitionAnt;
        int IndexNextColony;
        boolean Condition;
        double SummProbabiliry;
        swapValueInArrayWay(0, antWay.length-1);
        int i = 1;

        do {
            double SummArrayProbabilityTransitionAnt = 0;
            for (int j = i; j < antWay.length; j++){
                SummArrayProbabilityTransitionAnt +=  ParameterAntOptimization.getProbabilitiTransitionInColony()[antWay[i-1]][antWay[j]];
            }

            ProbabilityTransitionAnt = AntMath.randomDouble(SummArrayProbabilityTransitionAnt);
            Condition = false;
            SummProbabiliry = 0;
            IndexNextColony = i-1;

            do {
                SummProbabiliry +=  ParameterAntOptimization.getProbabilitiTransitionInColony()[antWay[i-1]][antWay[++IndexNextColony]];
                if ((ProbabilityTransitionAnt < SummProbabiliry)|(IndexNextColony == antWay.length-1)) {
                    Condition = true;
                }
            } while (!Condition);
            swapValueInArrayWay(i, IndexNextColony);
        } while (++i < antWay.length-1);

        calculationLengthWay();
    }

    private void swapValueInArrayWay(int FirstIndex, int SecondIndex) {
        int Buffer;
        Buffer = antWay[FirstIndex];
        antWay[FirstIndex] = antWay[SecondIndex];
        antWay[SecondIndex] = Buffer;
    }

    private void calculationLengthWay(){
        lengthWay = 0;
        for (int i = 1; i < antWay.length; i++) {
            lengthWay += AntColony.getDistanceBetweenColony()[antWay[i-1]][antWay[i]];
        }
    }

    public static int getIngexMinimalLengthWay(ArrayList<Ant> ants) {
        double min = ants.get(0).getLengthWay();
        int minIndex = 0;

        for (Ant ant : ants){
            if (ant.getLengthWay() < min) {
                min = ant.getLengthWay();
                minIndex = ants.indexOf(ant);
            }
        }

        return minIndex;
    }

}
