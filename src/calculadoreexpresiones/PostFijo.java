package calculadoreexpresiones;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PostFijo {

    private static String expresionInfijo;
    private static String expresionPostfijo;
    private static String errorExpresion;

    public static void setExpresionInfijo(String expresion) {
        expresionInfijo = expresion;
    }

    public static String getExpresionPostfijo() {
        return expresionPostfijo;
    }

    public static String getErrorExpresion() {
        return errorExpresion;
    }

    private static boolean esLetra(String caracter) {
        return ((caracter.compareTo("a") >= 0 && caracter.compareTo("z") <= 0)
                || (caracter.compareTo("A") >= 0 && caracter.compareTo("Z") <= 0));
    }

    private static boolean esDigito(String caracter) {
        return (caracter.compareTo("0") >= 0 && caracter.compareTo("9") <= 0);
    }

    private static boolean esOperador(String caracter) {
        return (caracter.equals("+") || caracter.equals("-") || caracter.equals("*") || caracter.equals("/") || caracter.equals("^"));
    }

    private static boolean esPredecesor(String operador1, String operador2) {
        if (operador1.equals("^")) {
            return true;
        } else if (operador1.equals("/") || operador1.equals("*")) {
            if (!operador2.equals("^")) {
                return true;
            }
        } else if (operador1.equals("-") || operador1.equals("+")) {
            if (operador2.equals("-") || operador2.equals("+")) {
                return true;
            }
        }
        return false;
    }

    public static String obtenerPostFijo() {
        expresionPostfijo = "";
        errorExpresion = "";

        Stack p = new Stack();
        int noOperador = 0; // 0: no es operador, 1: es parentesis (, 2: es parentesis ), 3: es operando
        boolean error = false;
        int parentesis = 0;
        int i = 0;
        //Recorrer cada uno de los caracteres
        while (i < expresionInfijo.length() && !error) {
            String caracter = expresionInfijo.substring(i, i + 1);
            if (caracter.equals("(")) {
                noOperador = 1;
                parentesis++;
                p.push(caracter);
            } else if (caracter.equals(")")) {
                noOperador = 2;
                if (parentesis > 0) {
                    parentesis--;
                    caracter = (String) p.peek();
                    while (!p.empty() && !caracter.equals("(")) {
                        expresionPostfijo += " " + (String) p.pop();
                        caracter = (String) p.peek();
                    }
                    p.pop();
                } else {
                    error = true;
                    errorExpresion = "Hace falta parentesis izquierdo";
                }
            } else if (esOperador(caracter)) {
                if (noOperador < 2) {
                    error = true;
                    errorExpresion = "Hace falta operando antes de " + caracter;
                } else {
                    noOperador = 0;
                    expresionPostfijo = expresionPostfijo + " ";
                    while (!p.empty() && esPredecesor((String) p.peek(), caracter)) {
                        expresionPostfijo = expresionPostfijo + (String) p.pop();
                    }
                    p.push(caracter);
                }
            } else if (esLetra(caracter) || esDigito(caracter)) {
                noOperador = 3;
                expresionPostfijo = expresionPostfijo + caracter;
            } else {
                error = true;
                errorExpresion = "Simbolo '" + caracter + "' indefinido ";
            }
            i++;
        }
        //Verificar errores
        if (parentesis > 0) {
            errorExpresion = "Error convirtiendo: Hace falta parentesis derecho";
            expresionPostfijo = "";
        } else if (error && !errorExpresion.equals("")) {
            errorExpresion = "Error convirtiendo: " + errorExpresion;
            expresionPostfijo = "";
        } else if (error || i == 0 || noOperador == 0) {
            errorExpresion = "Error convirtiendo: No hay expresión o falta operandos";
            expresionPostfijo = "";
        } else {
            //Terminar de construir la expresión POSTFIJO
            expresionPostfijo = expresionPostfijo + " ";
            while (!p.empty()) {
                expresionPostfijo = expresionPostfijo + (String) p.pop();
            }
        }
        return expresionPostfijo;
    }

    public static List<String> obtenerVariables() {
        List<String> variables = new ArrayList<>();
        errorExpresion = "";

        boolean error = false;
        int tipoOperando = 0; //0: no es operando, 1: es variable, 2: es constante numerica
        String texto = "";
        int i = 0;
        //Recorrer cada uno de los caracteres
        while (i < expresionPostfijo.length() && !error) {
            String caracter = expresionPostfijo.substring(i, i + 1);
            if (esLetra(caracter) && tipoOperando == 2) {
                error = true;
            } else if ((esLetra(caracter) && tipoOperando < 2)
                    || (esDigito(caracter) && tipoOperando == 1)) {
                tipoOperando = 1;
                texto += caracter;
            } else if (esDigito(caracter) && tipoOperando != 1) {
                tipoOperando = 2;
                texto += caracter;
            } else if (caracter.equals(" ") && tipoOperando == 1) {
                //no permitir variables repetidas
                if (!variables.contains(texto)) {
                    variables.add(texto);
                }
                texto = "";
                tipoOperando = 0;
            } else if (caracter.equals(" ") && tipoOperando == 2) {
                texto = "";
                tipoOperando = 0;
            }
            i++;
        }
        if (!error) {
            return variables;
        } else {
            errorExpresion = "Error obteniendo variables";
            return null;
        }

    }

    public static void mostrarVariables(JTable tbl) {
        List<String> variables = PostFijo.obtenerVariables();
        String[] encabezados = new String[]{"variable", "valor"};
        String[][] datos = null;

        if (variables != null) {
            datos = new String[variables.size()][2];
            for (int i = 0; i < variables.size(); i++) {
                datos[i][0] = variables.get(i);
            }
        }
        DefaultTableModel dtm = new DefaultTableModel(datos, encabezados);
        tbl.setModel(dtm);
    }

    public static ArbolBinario obtenerArbol() {
        errorExpresion = "";
        Stack p = new Stack();
        TipoOperando tipo = TipoOperando.NINGUNO;

        String texto = "";
        int i = 0;
        //Recorrer cada uno de los caracteres
        while (i < expresionPostfijo.length() && errorExpresion.equals("")) {
            String caracter = expresionPostfijo.substring(i, i + 1);
            if (esLetra(caracter) && tipo == TipoOperando.CONSTANTE) {
                errorExpresion = "Una constante numérica no puede tener letras";
            } else if ((esLetra(caracter) && tipo != TipoOperando.CONSTANTE)
                    || (esDigito(caracter) && tipo == TipoOperando.VARIABLE)) {
                tipo = TipoOperando.VARIABLE;
                texto += caracter;
            } else if (esDigito(caracter) && tipo != TipoOperando.VARIABLE) {
                tipo = TipoOperando.CONSTANTE;
                texto += caracter;
            } else if (caracter.equals(" ") && tipo != TipoOperando.NINGUNO) {
                //no permitir variables repetidas
                Nodo nOperando = new Nodo(texto, tipo);
                p.push(nOperando);
                texto = "";
                tipo = TipoOperando.NINGUNO;
            } else {
                caracter = expresionPostfijo.substring(i, i + 1);
                if (PostFijo.esOperador(caracter)) {
                    Nodo nOperador = new Nodo(caracter, TipoOperando.NINGUNO);
                    Nodo nDerecho = (Nodo) p.pop();
                    Nodo nIzquierdo = (Nodo) p.pop();
                    nOperador.izquierdo = nIzquierdo;
                    nOperador.derecho = nDerecho;
                    p.push(nOperador);
                }
            }
            i++;
        }
        return errorExpresion.equals("") ? new ArbolBinario((Nodo) p.pop()) : null;
    }

}
