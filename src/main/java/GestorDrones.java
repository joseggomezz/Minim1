import java.time.LocalDateTime;
import java.util.List;

public interface GestorDrones {
    void anadirDron(String id, String nombre, String fabricante, String modelo);
    void anadirPiloto(String id, String nombre, String apellidos);
    List<Dron> listarDronesPorHoras();
    List<Piloto> listarPilotosPorHoras();
    void guardarDron(String idDron);
    Dron repararDron();
    PlanDeVuelo crearPlanVuelo(String idDron, String idPiloto, LocalDateTime fecha,
                               int duracionHoras, List<Posicion> posiciones)
            throws DroneNotAvailableException, FlightPlanConflictException;
    List<PlanDeVuelo> planesDePiloto(String idPiloto);
    List<PlanDeVuelo> planesDeDron(String idDron);
}