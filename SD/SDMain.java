package SD;

import tipper_fis.FIS_Controlador;

import java.util.Scanner;

public class SDMain {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        SDController fis = new SDController();
        System.out.println("Ingrese el porcentaje de Humedad: ");
        double humedad = scan.nextDouble();
        System.out.println("Ingrese la Temperatura: ");
        double Temperatura = scan.nextDouble();
        System.out.println("Ingrese el indice: ");
        double indice = scan.nextDouble();


        double[] result = fis.evaluar(humedad, Temperatura, indice);
        System.out.println("La Cantidad de agua de riego que se recomienda es: " + result[0]);
        System.out.println("El tiempo de riego que se recomienda es: " + result[1]);
        System.out.println(fis.get_CD());


    }
}
