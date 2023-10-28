package calculadoreexpresiones;

public class Nodo {

    private String valor;

    Nodo izquierdo;
    Nodo derecho;

    private TipoOperando tipo;

    public Nodo() {
        valor = "";
        izquierdo = null;
        derecho = null;
    }

    public Nodo(String valor, TipoOperando tipo) {
        this.valor = valor;
        this.tipo = tipo;
        izquierdo = null;
        derecho = null;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public TipoOperando getTipo() {
        return tipo;
    }

    public double getValorNumerico() {
        try {
            if (tipo == TipoOperando.CONSTANTE) {
                return Double.parseDouble(this.valor);
            }
        } catch (Exception e) {

        }
        return 0;
    }
}
