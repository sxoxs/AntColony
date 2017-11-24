package AntOptimization;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class Main {
    static AntAlgoritm.DataOptimization outData;
    protected static String fileNameForLoad;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String AGRUMENT;

        System.out.println("Добро пожаловать в муравьиный алгоритм");
        System.out.println("Загрузить предыдущие настройки?");
        System.out.println("Да - Y / Нет - N");

        if ( br.readLine().trim().toLowerCase().equals("y") ) {
            System.out.println("Введите название файла для загрузки настроек");
            fileNameForLoad = br.readLine().trim().toLowerCase();
            AntColony.createAntColony(fileNameForLoad);
        }
        else {
            AntColony.createAntColony();
        }

        System.out.println("Загрузить предыдущие настройки алгоритма?");
        System.out.println("Да - Y / Нет - N");

        fileNameForLoad = "";
        if ( br.readLine().trim().toLowerCase().equals("y") ) {
            System.out.println("Введите название файла для загрузки настроек");
            fileNameForLoad = br.readLine().trim().toLowerCase();
        }


        //агоритм
        ParameterAntOptimization.createParameterAntOptimization(fileNameForLoad);
        outData = AntAlgoritm.Algoritm();
        //



        Date date = new Date();
        date.getTime();
        System.out.println("Введите название файла для сохранения настроек");
        StringBuffer sb = new StringBuffer(br.readLine().trim().toLowerCase());

        sb.append("_").append(Integer.toString(date.getDate())).append('.');
        sb.append(Integer.toString(date.getMonth())).append('.').append( Integer.toString(date.getYear()));
        sb.append("_").append(Integer.toString(date.getHours())).append(".");
        sb.append(Integer.toString(date.getMinutes()));

        WriteIntoExcel.setFileNameForSave(sb.toString());


        WriteIntoExcel.WriteEraLengthWay(outData.listOptimaWay, outData.listLengthOptimaWay);

        WriteIntoExcel.SaveConficDistance();
        WriteIntoExcel.SaveConfig(outData);
    }


}


