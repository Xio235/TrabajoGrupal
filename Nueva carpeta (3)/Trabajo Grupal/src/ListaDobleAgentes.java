import java.util.ArrayList;

public class ListaDobleAgentes {
    private NodoAgente cabeza;

    public void agregarAgente(Agente agente) {
        NodoAgente nuevo = new NodoAgente(agente);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoAgente temp = cabeza;
            while (temp.siguiente != null) temp = temp.siguiente;
            temp.siguiente = nuevo;
            nuevo.anterior = temp;
        }
    }

    public boolean modificarAgente(String id, Agente datosNuevos) {
        NodoAgente temp = cabeza;
        while (temp != null) {
            if (temp.agente.getId().equals(id)) {
                temp.agente.setNombre(datosNuevos.getNombre());
                temp.agente.setMision(datosNuevos.getMision());
                temp.agente.setPeligrosidad(datosNuevos.getPeligrosidad());
                temp.agente.setPagoMensual(datosNuevos.getPagoMensual());
                return true;
            }
            temp = temp.siguiente;
        }
        return false;
    }

    public ArrayList<Agente> listar() {
        ArrayList<Agente> lista = new ArrayList<>();
        NodoAgente temp = cabeza;
        while (temp != null) {
            lista.add(temp.agente);
            temp = temp.siguiente;
        }
        return lista;
    }
}
