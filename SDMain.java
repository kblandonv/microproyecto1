package SD;

import tipper_fis.FIS_Controlador;

import java.util.Scanner;

public class SDMain {



    public static void main(String[] args) {

        fis_agricultura p = new fis_agricultura();
        p.setVisible(true);

    }


    public double[] evaluar(double Humedad_del_suelo, double Temperatura, double Indice_de_Nutrientes_del_Suelo) {

        // Carga el archivo de lenguaje de control difuso 'FCL'
        String fileName = "src/SD/FIS.fcl";
        FIS fis = FIS.load(fileName, true);

        // En caso de error
        if (fis == null) {
            System.err.println("No se puede cargar el archivo: '" + fileName + "'");
            return "";
        }

        // Establecer las entradas del sistema
        fis.setVariable("Humedad_del_suelo", Humedad_del_suelo);
        fis.setVariable("Temperatura", Temperatura);
        fis.setVariable("Indice_de_Nutrientes_del_Suelo", Indice_de_Nutrientes_del_Suelo);

        // Inicia el funcionamiento del sistema
        fis.evaluate();

        // Muestra los gráficos de las variables de entrada y salida
        JFuzzyChart.get().chart(fis.getFunctionBlock("SistemaDifuso"));


        // Muestra el conjunto difuso sobre el que se calcula el COG
        Variable tip1 = fis.getVariable("Cantidad_de_Agua_de_Riego");
        JFuzzyChart.get().chart(tip, tip1.getDefuzzifier(), true);

        Variable tip2 = fis.getVariable("Tiempo_de_Riego");
        JFuzzyChart.get().chart(tip, tip2.getDefuzzifier(), true);


        // Imprime el valor concreto de salida del sistema
        double salida1 = fis.getVariable("Cantidad_de_Agua_de_Riego").getLatestDefuzzifiedValue();
        double salida2 = fis.getVariable("Tiempo_de_Riego").getLatestDefuzzifiedValue();

        // Muestra cuanto peso tiene la variable de salida en cada CD de salida
        double pertenenciamuyBaja = fis.getVariable("Cantidad_de_Agua_de_Riego").getMembership("Muy_baja");
        double pertenenciaBaja = fis.getVariable("Cantidad_de_Agua_de_Riego").getMembership("baja");
        double pertenenciaMedia = fis.getVariable("Cantidad_de_Agua_de_Riego").getMembership("Media");
        double pertenenciaAlta = fis.getVariable("Cantidad_de_Agua_de_Riego").getMembership("Alta");

        double pertenenciaCorto = fis.getVariable("Tiempo_de_Riego").getMembership("Corto");
        double pertenenciaModerado = fis.getVariable("Tiempo_de_Riego").getMembership("Moderado");
        double pertenenciaLargo = fis.getVariable("Tiempo_de_Riego").getMembership("Largo");
        double pertenenciaMuy_largo = fis.getVariable("Tiempo_de_Riego").getMembership("Muy_largo");

        String recomendacion1 = "";
        String recomendacion2 = "";

        if (pertenenciamuyBaja <= pertenenciaBaja &&
                pertenenciamuyBaja <= pertenenciaMedia &&
                pertenenciamuyBaja <= pertenenciaAlta) {
            recomendacion1 = "Muy_baja";
        }
        else if (pertenenciaBaja >= pertenenciamuyBaja &&
                pertenenciaBaja <= pertenenciaMedia &&
                pertenenciaBaja <= pertenenciaAlta) {
            recomendacion1 = "baja";
        }
        else if (pertenenciaMedia >= pertenenciamuyBaja &&
                pertenenciaMedia >= pertenenciaBaja &&
                pertenenciaMedia <= pertenenciaAlta) {
            recomendacion1 = "Media";
        }
        else if (pertenenciaAlta >= pertenenciamuyBaja &&
                pertenenciaAlta >= pertenenciaBaja &&
                pertenenciaAlta >= pertenenciaMedia) {
            recomendacion1 = "Alta";
        }



        if (pertenenciaCorto <= pertenenciaModerado &&
                pertenenciaCorto <= pertenenciaLargo &&
                pertenenciaCorto <= pertenenciaMuy_largo) {
            recomendacion2 = "Corto";
        } else if (pertenenciaModerado >= pertenenciaCorto &&
                pertenenciaModerado <= pertenenciaLargo &&
                pertenenciaModerado <= pertenenciaMuy_largo) {
            recomendacion2 = "Moderado";
        } else if (pertenenciaLargo >= pertenenciaCorto &&
                pertenenciaLargo >= pertenenciaModerado &&
                pertenenciaLargo <= pertenenciaMuy_largo) {
            recomendacion2 = "Largo";
        } else if (pertenenciaMuy_largo >= pertenenciaCorto &&
                pertenenciaMuy_largo >= pertenenciaModerado &&
                pertenenciaMuy_largo >= pertenenciaLargo) {
            recomendacion2 = "Muy_largo";
        }




        return ("Cantida de agua de riego: " + String.format("%.1d", salida1) + "litros por día" +
                "Tiempo de riego: " + String.format("%.1d", salida2) + "minutos por día" +
                "\n\n" + "Se recomienda utilizar una cantidad de agua de riego " + recomendacion1 +
                "\n\n" + "Se recomienda realizar el riego por un tiempo " + recomendacion2 +
                "\n\n" );
    }
}































































