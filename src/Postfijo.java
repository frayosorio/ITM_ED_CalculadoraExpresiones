import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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

    public static List<String> getVariables() {
        String expresionPostfijo = getExpresionPostfijo();

        List<String> variables = new ArrayList<>();
        boolean error = false;
        int i = 0;

        TipoElemento tipo = TipoElemento.OPERADOR;
        String texto = "";
        while (i < expresionPostfijo.length() && !error) {
            String caracter = expresionPostfijo.substring(i, i + 1);

            if (esLetra(caracter) && tipo == TipoElemento.CONSTANTE_NUMERICA) {
                errorExpresion = "Caracter inválido para una constante numérica";
                error = true;
            } else if ((esLetra(caracter) && tipo.ordinal() < TipoElemento.CONSTANTE_NUMERICA.ordinal())
                    || (esDigito(caracter) && tipo == TipoElemento.VARIABLE)) {
                texto += caracter;
                tipo = TipoElemento.VARIABLE;
            } else if (esDigito(caracter) && tipo != TipoElemento.VARIABLE) {
                texto += caracter;
                tipo = TipoElemento.CONSTANTE_NUMERICA;
            } else if (caracter.equals(" ") && tipo == TipoElemento.VARIABLE) {
                if (!variables.contains(texto)) {
                    variables.add(texto);
                }
                texto = "";
                tipo = TipoElemento.OPERADOR;
            } else if (caracter.equals(" ") && tipo == TipoElemento.CONSTANTE_NUMERICA) {
                texto = "";
                tipo = TipoElemento.OPERADOR;
            }
            i++;
        }
        return !error ? variables : null;
    }

    public static void mostrarVariables(JTable tbl) {
        DefaultTableModel dtm = new DefaultTableModel(null, encabezados);
        var variables = getVariables();
        if (variables != null) {
            String[][] datos = new String[variables.size()][2];
            int fila = 0;
            for (String variable : variables) {
                datos[fila][0] = variable;
                fila++;
            }
            dtm = new DefaultTableModel(datos, encabezados);

        }
        tbl.setModel(dtm);
    }

}
