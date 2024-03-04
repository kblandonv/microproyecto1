package SD;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

public class SDController{

    final private String path_FIS = "src/SD/FIS.fcl";
    final private FIS fis_var;

    public SDController() {
        this.fis_var = FIS.load(this.path_FIS, true);
        // En caso de error
        if (this.fis_var == null) {
            System.err.println("No se puede cargar el archivo: '" + "'");
        }
    }

    public double[] evaluar(double Humedad_del_suelo, double Temperatura, double Indice_de_Nutrientes_del_Suelo) {
        this.fis_var.setVariable("Humedad_del_suelo", Humedad_del_suelo);
        this.fis_var.setVariable("Temperatura", Temperatura);
        this.fis_var.setVariable("Indice_de_Nutrientes_del_Suelo", Indice_de_Nutrientes_del_Suelo);
        this.fis_var.evaluate();
        JFuzzyChart.get().chart(this.fis_var.getFunctionBlock("SistemaDifuso"));
        double[] resultados = new double[2];
        resultados[0] = this.fis_var.getVariable("Cantidad_de_Agua_de_Riego").getLatestDefuzzifiedValue();
        resultados[1] = this.fis_var.getVariable("Tiempo_de_Riego").getLatestDefuzzifiedValue();

        return resultados;
    }

    public String get_CD() {
        String[] CD_salida = {"Muy_baja","Baja", "Media", "Alta"};
        String[] CD_salida1 = {"Corto","Moderado", "Largo", "Muy_largo"};
        String result = "";
        String result1 = "";
        for (String vl : CD_salida) {
            if (this.fis_var.getVariable("Cantidad_de_Agua_de_Riego").getMembership(vl) > 0) {
                result += vl + ", ";
            }
        }
        for (String vl : CD_salida1) {
            if (this.fis_var.getVariable("Tiempo_de_Riego").getMembership(vl) > 0) {
                result1 += vl + ", ";
            }
        }



        result = result.strip();
        result1 = result1.strip();
        result = result.substring(0, result.length() - 1);
        result1 = result1.substring(0, result1.length() - 1);
        return  result + "\n" + result1;
    }
}

