import java.util.LinkedList;
import java.util.List;

public class Dron {
    private String id;
    private String nombre;
    private String fabricante;
    private String modelo;
    private int horasVuelo;
    private boolean enAlmacen;
    private List<PlanDeVuelo> planes;

    public Dron(String id, String nombre, String fabricante, String modelo) {
        this.id = id;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.horasVuelo = 0;
        this.enAlmacen = false;
        this.planes = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public int getHorasVuelo() {
        return horasVuelo;
    }

    public void sumarHoras(int horas) {
        this.horasVuelo = this.horasVuelo + horas;
    }

    public boolean isEnAlmacen() {
        return enAlmacen;
    }

    public void setEnAlmacen(boolean enAlmacen) {
        this.enAlmacen = enAlmacen;
    }

    public List<PlanDeVuelo> getPlanes() {
        return planes;
    }

    public void addPlan(PlanDeVuelo plan) {
        this.planes.add(plan);
    }
}
