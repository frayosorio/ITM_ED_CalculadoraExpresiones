public class Postfijo {

    public static String[] encabezados = new String[] { "Variable", "Valor" };

    private static String expresionInfijo;

    public static void setExpresionInfijo(String expresionInfijo) {
        Postfijo.expresionInfijo = expresionInfijo;
    }

    private static boolean esLetra(String caracter) {
        return (caracter.compareTo("A") >= 0 && caracter.compareTo("Z") <= 0)
                || (caracter.compareTo("a") >= 0 && caracter.compareTo("z") <= 0);
    }

    private static boolean esDigito(String caracter) {
        return (caracter.compareTo("0") >= 0 && caracter.compareTo("9") <= 0);
    }

    private static boolean esOperador(String caracter) {
        String operadores = "+-*/%^";
        return operadores.contains(caracter);
    }

    private static boolean esAntecesor(String operador1, String operador2) {
        boolean antecesor = false;
        if (operador1.equals("^")) {
            antecesor = true;
        } else if (operador1.equals("%")) {
            if (!operador2.equals("^")) {
                antecesor = true;
            }
        } else if (operador1.equals("/") || operador1.equals("*")) {
            if (!operador2.equals("^") || !operador2.equals("%")) {
                antecesor = true;
            }
        } else if (operador1.equals("+") || operador1.equals("-")) {
            if (operador2.equals("+") || operador2.equals("-")) {
                return true;
            }
        }
        return antecesor;
    }

    public static String getExpresionPostfijo() {
        String postFijo = "";
        boolean error = false;

        int i = 0;
        while (i < expresionInfijo.length() && !error) {
            String caracter = expresionInfijo.substring(i, i + 1);

            System.out.println(caracter + ": es digito?" + esDigito(caracter) 
            + ": es caracter?" + esLetra(caracter)
            + ": esOperador?" + esOperador(caracter));

            i++;
        }

        return postFijo;
    }

}
