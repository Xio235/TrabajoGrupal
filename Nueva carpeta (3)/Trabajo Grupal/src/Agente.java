public class Agente {
    private String id;
    private String nombre;
    private String mision;
    private int peligrosidad;
    private double pagoMensual;

    public Agente(String id, String nombre, String mision, int peligrosidad, double pagoMensual) {
        this.id = id;
        this.nombre = nombre;
        this.mision = mision;
        this.peligrosidad = peligrosidad;
        this.pagoMensual = pagoMensual;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getMision() { return mision; }
    public int getPeligrosidad() { return peligrosidad; }
    public double getPagoMensual() { return pagoMensual; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMision(String mision) { this.mision = mision; }
    public void setPeligrosidad(int peligrosidad) { this.peligrosidad = peligrosidad; }
    public void setPagoMensual(double pagoMensual) { this.pagoMensual = pagoMensual; }

    public double getAporteFondo() {
        return pagoMensual * 0.08;
    }

    public double getImpuestoAnual() {
        double anual = pagoMensual * 12;
        if (anual <= 5000) return 0;
        else if (anual <= 10000) return (anual - 5000) * 0.10;
        else if (anual <= 20000) return (anual - 10000) * 0.20 + 500;
        else return (anual - 20000) * 0.30 + 2500;
    }

    public double getPagoNeto() {
        return pagoMensual - getAporteFondo() - (getImpuestoAnual() / 12);
    }

    public String getResumen() {
        return "Nombre: " + nombre +
                "\nPago mensual: $" + pagoMensual +
                "\nAporte al Fondo: $" + String.format("%.2f", getAporteFondo()) +
                "\nImpuesto mensual: $" + String.format("%.2f", getImpuestoAnual()/12) +
                "\nPago Neto: $" + String.format("%.2f", getPagoNeto()) + "\n";
    }
}


