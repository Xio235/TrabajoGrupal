public class NodoAgente {
    Agente agente;
    NodoAgente siguiente, anterior;

    public NodoAgente(Agente agente) {
        this.agente = agente;
        siguiente = anterior = null;
    }
}
