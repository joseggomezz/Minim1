import java.util.ArrayList;
import java.util.List;

public class PlanDeVueloDTO {
    private String idDron;
    private String idPiloto;
    private String fecha;
    private int duracionHoras;
    private List<PosicionDTO> posiciones = new ArrayList<>();

    public PlanDeVueloDTO() {
    }

    public String getIdDron() {
        return idDron;
    }

    public void setIdDron(String idDron) {
        this.idDron = idDron;
    }

    public String getIdPiloto() {
        return idPiloto;
    }

    public void setIdPiloto(String idPiloto) {
        this.idPiloto = idPiloto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(int duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public List<PosicionDTO> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(List<PosicionDTO> posiciones) {
        this.posiciones = posiciones;
    }
}
