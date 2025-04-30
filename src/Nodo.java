public class Nodo {
    private String valor;
    private TipoElemento tipo;

    public Nodo izquierdo;
    public Nodo derecho;

    public Nodo() {
    }

    public Nodo(String valor, TipoElemento tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public TipoElemento getTipo() {
        return tipo;
    }

    public double getValorNumerico() {
        if (tipo == TipoElemento.CONSTANTE_NUMERICA) {
            return Double.parseDouble(valor);
        }
        return 0;
    }

}
