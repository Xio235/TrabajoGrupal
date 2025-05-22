import java.util.Stack;

public class PilaAgentes {
    private Stack<Agente> pila = new Stack<>();

    public void push(Agente agente) {
        pila.push(agente);
    }

    public Agente pop() {
        return pila.isEmpty() ? null : pila.pop();
    }

    public boolean isEmpty() {
        return pila.isEmpty();
    }
}
