package calculadoreexpresiones;

import java.util.List;

public class ArbolBinario {

    private Nodo raiz;

    private List<String> variables;
    private List<Double> valores;

    public ArbolBinario() {
        raiz = null;
    }

    public ArbolBinario(Nodo n) {
        raiz = n;
    }

    //Recorrido del arbol en INDORDEN
    public String mostrarInorden() {
        return mostrarInorden(raiz);
    }

    //metodo recursivo para INORDEN
    private String mostrarInorden(Nodo n) {
        if (n != null) {
            return mostrarInorden(n.izquierdo) + " "
                    + n.getValor() + " "
                    + mostrarInorden(n.derecho);
        }
        return "";
    }

    //metodo recursivo para POSTORDEN
    public String mostrarPostorden() {
        return mostrarPostorden(raiz);
    }

    //metodo recursivo para POSTORDEN
    private String mostrarPostorden(Nodo n) {
        if (n != null) {
            return mostrarPostorden(n.izquierdo) + " "
                    + mostrarPostorden(n.derecho) + " "
                    + n.getValor();
        }
        return "";
    }

    //metodo recursivo para PREORDEN
    public String mostrarPreorden() {
        return mostrarPreorden(raiz);
    }

    //metodo recursivo para PREORDEN
    private String mostrarPreorden(Nodo n) {
        if (n != null) {
            return n.getValor() + " "
                    + mostrarPreorden(n.izquierdo) + " "
                    + mostrarPreorden(n.derecho);
        }
        return "";
    }

    //********** Ejecución de la expresion aritmetica
    private double getValorVariable(String variable) {
        double valor = 0;
        if (this.variables.size() > 0) {
            int p = this.variables.indexOf(variable);
            if (p >= 0 && p < this.valores.size()) {
                valor = this.valores.get(p);
            }
        }
        return valor;
    }

    private double getValorNodo(Nodo n) {
        return n.getTipo() == TipoOperando.CONSTANTE ? n.getValorNumerico() : getValorVariable(n.getValor());
    }

    private double ejecutarNodo(Nodo n) {
        if (n.izquierdo == null && n.derecho == null) {
            return getValorNodo(n);
        } else {
            double operando1 = ejecutarNodo(n.izquierdo);
            double operando2 = ejecutarNodo(n.derecho);
            switch (n.getValor()) {
                case "+":
                    return operando1 + operando2;
                case "-":
                    return operando1 - operando2;
                case "*":
                    return operando1 * operando2;
                case "/":
                    return operando1 / operando2;
                case "%":
                    return operando1 % operando2;
                case "^":
                    return Math.pow(operando1, operando2);
            }
        }
        return 0;
    }

    public double ejecutar(List<String> variables, List<Double> valores) {
        this.variables = variables;
        this.valores = valores;
        return ejecutarNodo(raiz);
    }

}
