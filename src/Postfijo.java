import java.util.Stack;

public class Postfijo {

    public static String[] encabezados = new String[] { "Variable", "Valor" };

    private static String expresionInfijo;
    private static String errorExpresion = "";

    public static void setExpresionInfijo(String expresionInfijo) {
        Postfijo.expresionInfijo = expresionInfijo;
    }

    public static String getErrorExpresion() {
        return errorExpresion;
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
        String expresionPostfijo = "";
        boolean error = false;
        TipoElemento tipo = TipoElemento.OPERADOR;
        int parentesis = 0;
        errorExpresion = "";

        Stack pila = new Stack();

        int i = 0;
        while (i < expresionInfijo.length() && !error) {
            String caracter = expresionInfijo.substring(i, i + 1);
            if (caracter.equals("(")) {
                pila.push(caracter);
                tipo = TipoElemento.PARENTESIS_IZQUIERDO;
                parentesis++;
            } else if (caracter.equals(")")) {
                tipo = TipoElemento.PARENTESIS_DERECHO;
                if (parentesis == 0) {
                    errorExpresion = "Hace falta un paréntesis izquierdo";
                    error = true;
                } else {
                    parentesis--;
                    caracter = (String) pila.peek();
                    while (!pila.isEmpty() && !caracter.equals("(")) {
                        expresionPostfijo += " " + pila.pop();
                        caracter = (String) pila.peek();
                    }
                    pila.pop();
                }
            } else if (esOperador(caracter)) {
                if (tipo.ordinal() < 2) {
                    errorExpresion = "Hace falta un operando antes de " + caracter;
                    error = true;
                } else {
                    tipo = TipoElemento.OPERADOR;
                    expresionPostfijo += " ";
                    while (!pila.isEmpty() && esAntecesor((String) pila.peek(), caracter)) {
                        expresionPostfijo += pila.pop();
                    }
                    pila.push(caracter);
                }
            } else if (esLetra(caracter) || esDigito(caracter)) {
                tipo = TipoElemento.OPERANDO;
                expresionPostfijo += caracter;
            } else {
                errorExpresion = "El caracter " + caracter + " no valido";
                error = true;
            }

            i++;
        }
        if (parentesis > 0) {
            errorExpresion = "Hace falta un paréntesis derecho";
            expresionPostfijo = "";
            error = true;
        } else if (tipo == TipoElemento.OPERADOR) {
            errorExpresion = "Falta operando";
            expresionPostfijo = "";
            error = true;
        } else if (error) {
            expresionPostfijo = "";
        } else {
            expresionPostfijo += " ";
            while (!pila.empty()) {
                expresionPostfijo += pila.pop();
            }
        }

        return expresionPostfijo;
    }

}
