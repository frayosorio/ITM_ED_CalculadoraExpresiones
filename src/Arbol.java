import java.util.List;

public class Arbol {

    private Nodo raiz;
    private List<String> variables;
    private List<Double> valores;

    public Arbol() {
        raiz = null;
    }

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public String mostrarInorden() {
        return mostrarInorden(raiz);
    }

    private String mostrarInorden(Nodo nodo) {
        if (nodo != null) {
            return mostrarInorden(nodo.izquierdo) +
                    " " + nodo.getValor() +
                    " " + mostrarInorden(nodo.derecho);
        }
        return "";
    }

    public String mostrarPostorden() {
        return mostrarPostorden(raiz);
    }

    private String mostrarPostorden(Nodo nodo) {
        if (nodo != null) {
            return mostrarPostorden(nodo.izquierdo) +
                    " " + mostrarPostorden(nodo.derecho) +
                    " " + nodo.getValor();
        }
        return "";
    }

    private double getValorVariable(String nombreVariable) {
        var posicion = this.variables.indexOf(nombreVariable);
        if (posicion >= 0) {
            return this.valores.get(posicion);
        }
        return 0;
    }

    private double getValor(Nodo nodo) {
        return nodo.getTipo() == TipoElemento.CONSTANTE_NUMERICA ? nodo.getValorNumerico()
                : getValorVariable(nodo.getValor());
    }

    private double ejecutar(Nodo nodo) {
        if (nodo.izquierdo == null && nodo.derecho == null) {
            return getValor(nodo);
        } else {
            double operando1 = ejecutar(nodo.izquierdo);
            double operando2 = ejecutar(nodo.derecho);
            switch (nodo.getValor()) {
                case "+":
                    return operando1 + operando2;
                case "-":
                    return operando1 - operando2;
                case "*":
                    return operando1 * operando2;
                case "/":
                    return operando2 != 0 ? operando1 / operando2 : 0;
                case "%":
                    return operando2 != 0 ? operando1 % operando2 : 0;
                case "^":
                    return Math.pow(operando1, operando2);
            }
        }
        return 0;
    }

    public double ejecutar(List<String> variables, List<Double> valores) {
        this.variables = variables;
        this.valores = valores;
        return ejecutar(raiz);
    }

}
