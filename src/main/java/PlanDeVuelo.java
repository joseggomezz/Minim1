import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class PlanDeVuelo {
    private Dron dron;
    private Piloto piloto;
    private LocalDateTime fecha;
    private int duracionHoras;
    private List<Posicion> posiciones;

    public PlanDeVuelo(Dron dron, Piloto piloto, LocalDateTime fecha, int duracionHoras, List<Posicion> posiciones) {
        this.dron = dron;
        this.piloto = piloto;
        this.fecha = fecha;
        this.duracionHoras = duracionHoras;
        this.posiciones = new LinkedList<>(posiciones);
    }

    public Dron getDron() {
        return dron;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getDuracionHoras() {
        return duracionHoras;
    }

    public List<Posicion> getPosiciones() {
        return posiciones;
    }

    // Hora a la que termina el vuelo (para comprobar solapamientos)
    public LocalDateTime getFin() {
        return fecha.plusHours(duracionHoras);
    }
}
