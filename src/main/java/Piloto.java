import java.util.LinkedList;
import java.util.List;

public class Piloto {
    private String id;
    private String nombre;
    private String apellidos;
    private int horasVuelo;
    private List<PlanDeVuelo> planes;

    public Piloto(String id, String nombre, String apellidos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.horasVuelo = 0;
        this.planes = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getHorasVuelo() {
        return horasVuelo;
    }

    public void sumarHoras(int horas) {
        this.horasVuelo = this.horasVuelo + horas;
    }

    public List<PlanDeVuelo> getPlanes() {
        return planes;
    }

    public void addPlan(PlanDeVuelo plan) {
        this.planes.add(plan);
    }
}