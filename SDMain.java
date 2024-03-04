/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inferencia;

import java.util.ArrayList;
import java.util.Scanner;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class SDMain {



    public static void main(String[] args) {

        fis_agricultura p = new fis_agricultura();
        p.setVisible(true);

    }


    public String[] evaluar(double Humedad_del_suelo, double Temperatura, double Indice_de_Nutrientes_del_Suelo) {

        // Carga el archivo de lenguaje de control difuso 'FCL'
        String fileName = "src/inferencia/FIS.fcl";
        FIS fis = FIS.load(fileName, true);

        // En caso de error
        if (fis == null) {
            System.err.println("No se puede cargar el archivo: '" + fileName + "'");
            return new String[0];
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
        JFuzzyChart.get().chart(tip1, tip1.getDefuzzifier(), true);

        Variable tip2 = fis.getVariable("Tiempo_de_Riego");
        JFuzzyChart.get().chart(tip2, tip2.getDefuzzifier(), true);


        // Imprime el valor concreto de salida del sistema
        double salida1 = fis.getVariable("Cantidad_de_Agua_de_Riego").getLatestDefuzzifiedValue();
        double salida2 = fis.getVariable("Tiempo_de_Riego").getLatestDefuzzifiedValue();

        // Muestra cuanto peso tiene la variable de salida en cada CD de salida
        double pertenenciamuyBaja = fis.getVariable("Cantidad_de_Agua_de_Riego").getMembership("Muy_baja");
        double pertenenciaBaja = fis.getVariable("Cantidad_de_Agua_de_Riego").getMembership("Baja");
        double pertenenciaMedia = fis.getVariable("Cantidad_de_Agua_de_Riego").getMembership("Media");
        double pertenenciaAlta = fis.getVariable("Cantidad_de_Agua_de_Riego").getMembership("Alta");

        double pertenenciaCorto = fis.getVariable("Tiempo_de_Riego").getMembership("Corto");
        double pertenenciaModerado = fis.getVariable("Tiempo_de_Riego").getMembership("Moderado");
        double pertenenciaLargo = fis.getVariable("Tiempo_de_Riego").getMembership("Largo");
        double pertenenciaMuy_largo = fis.getVariable("Tiempo_de_Riego").getMembership("Muy_largo");

        String recomendacion1 = "";
        String recomendacion2 = "";

        if (pertenenciamuyBaja >= pertenenciaBaja &&
                pertenenciamuyBaja >= pertenenciaMedia &&
                pertenenciamuyBaja >= pertenenciaAlta) {
            recomendacion1 = "Muy_baja";
        }
        else if (pertenenciaBaja >= pertenenciamuyBaja &&
                pertenenciaBaja >= pertenenciaMedia &&
                pertenenciaBaja >= pertenenciaAlta) {
            recomendacion1 = "Baja";
        }
        else if (pertenenciaMedia >= pertenenciamuyBaja &&
                pertenenciaMedia >= pertenenciaBaja &&
                pertenenciaMedia >= pertenenciaAlta) {
            recomendacion1 = "Media";
        }
        else if (pertenenciaAlta >= pertenenciamuyBaja &&
                pertenenciaAlta >= pertenenciaBaja &&
                pertenenciaAlta >= pertenenciaMedia) {
            recomendacion1 = "Alta";
        }



        if (pertenenciaCorto >= pertenenciaModerado &&
                pertenenciaCorto >= pertenenciaLargo &&
                pertenenciaCorto >= pertenenciaMuy_largo) {
            recomendacion2 = "Corto";
        } else if (pertenenciaModerado >= pertenenciaCorto &&
                pertenenciaModerado >= pertenenciaLargo &&
                pertenenciaModerado >= pertenenciaMuy_largo) {
            recomendacion2 = "Moderado";
        } else if (pertenenciaLargo >= pertenenciaCorto &&
                pertenenciaLargo >= pertenenciaModerado &&
                pertenenciaLargo >= pertenenciaMuy_largo) {
            recomendacion2 = "Largo";
        } else if (pertenenciaMuy_largo >= pertenenciaCorto &&
                pertenenciaMuy_largo >= pertenenciaModerado &&
                pertenenciaMuy_largo >= pertenenciaLargo) {
            recomendacion2 = "Muy_largo";
        }
        
        String[] resultados = new String[2];
        
        resultados[0] = "Cantidad de agua de riego: " + String.format("%.1f", salida1) + " litros por día. Se recomienda utilizar una cantidad de agua de riego " + recomendacion1;
        resultados[1] = "Tiempo de riego: " + String.format("%.1f", salida2) + " minutos por día. Se recomienda realizar el riego por un tiempo " + recomendacion2;
        System.out.println("ll" + recomendacion1);
        System.out.println("33" + recomendacion2);
        return resultados;
    }
}