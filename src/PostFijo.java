import java.util.Stack;

public class PostFijo {

    private static String expresionInfijo;
    private static String expresionPostfijo;
    private static String errorExpresion;

    public static void setExpresionInfijo(String expresionInfijo) {
        PostFijo.expresionInfijo = expresionInfijo;
    }

    public static String getExpresionPostfijo() {
        expresionPostfijo = "";
        expresionInfijo = expresionInfijo.replace(" ", "");
        errorExpresion = "";

        Stack pila = new Stack();
        boolean error = false;
        int i = 0;
        int parentesis=0;
        while (i < expresionInfijo.length() && !error) {
            String caracter = expresionInfijo.substring(i, i + 1);

            if (caracter.equals("(")) {
                pila.push(caracter);
                parentesis++;
            } else if (caracter.equals(")")) {

            } else if (esOperador(caracter)) {

            } else if (esLetra(caracter) || esDigito(caracter)) {

            } else {
                error = true;
                errorExpresion = "el caracter '" + caracter + "' no válido";
            }
            i++;
        }

        return expresionPostfijo;
    }

    public static boolean esLetra(String caracter) {
        return caracter.matches("[a-zA-Z]");
    }

    public static boolean esDigito(String caracter) {
        return caracter.matches("[0-9]");
    }

    public static boolean esOperador(String caracter) {
        String caracteres = "+-*/%^";
        return caracteres.contains(caracter);
    }

    public static boolean esPredecesor(String operador1, String operador2) {
        boolean p = false;
        if (operador1.equals("^"))
            p = true;
        else if (operador1.equals("%")) {
            if (!operador2.equals("^"))
                p = true;
        } else if (operador1.equals("/") || operador1.equals("*")) {
            if (!operador2.equals("^") && !operador2.equals("%"))
                p = true;
        } else {
            if (operador2.equals("+") || operador2.equals("-"))
                p = true;
        }
        return p;
    }

}
