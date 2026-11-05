import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Path("/flota")
public class ServicioDrones {

    private GestorDrones gestor;

    public ServicioDrones() {
        gestor = GestorDronesImpl.getInstance();
        if (gestor.listarDronesPorHoras().isEmpty()) {
            gestor.anadirDron("D1", "Phantom", "DJI", "P4");
            gestor.anadirDron("D2", "Mavic", "DJI", "M3");
            gestor.anadirPiloto("P1", "Ana", "Lopez Perez");
            gestor.anadirPiloto("P2", "Luis", "Garcia Ruiz");
        }
    }

    @POST
    @Path("/planes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearPlan(PlanDeVueloDTO dto) {
        if (dto == null || dto.getIdDron() == null || dto.getIdPiloto() == null
                || dto.getFecha() == null || dto.getPosiciones() == null) {
            return Response.status(400).build();
        }

        LocalDateTime fecha;
        try {
            fecha = LocalDateTime.parse(dto.getFecha());
        } catch (DateTimeParseException e) {
            return Response.status(400).build();
        }

        List<Posicion> posiciones = new ArrayList<>();
        for (PosicionDTO p : dto.getPosiciones()) {
            posiciones.add(new Posicion(p.getLatitud(), p.getLongitud()));
        }

        try {
            PlanDeVuelo plan = gestor.crearPlanVuelo(dto.getIdDron(), dto.getIdPiloto(),
                    fecha, dto.getDuracionHoras(), posiciones);
            return Response.status(201).entity(convertirPlan(plan)).build();
        } catch (DroneNotAvailableException | FlightPlanConflictException e) {
            return Response.status(409).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
    }

    @GET
    @Path("/drones/horas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dronesPorHoras() {
        List<Dron> drones = gestor.listarDronesPorHoras();
        List<DronDTO> lista = new ArrayList<>();
        for (Dron d : drones) {
            lista.add(new DronDTO(d.getId(), d.getNombre(), d.getFabricante(),
                    d.getModelo(), d.getHorasVuelo(), d.isEnAlmacen()));
        }
        GenericEntity<List<DronDTO>> entity = new GenericEntity<List<DronDTO>>(lista) {
        };
        return Response.status(200).entity(entity).build();
    }

    @PUT
    @Path("/almacen/{idDron}")
    public Response guardarDron(@PathParam("idDron") String idDron) {
        gestor.guardarDron(idDron);
        return Response.status(204).build();
    }

    @GET
    @Path("/pilotos/{idPiloto}/planes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response planesDePiloto(@PathParam("idPiloto") String idPiloto) {
        List<PlanDeVuelo> planes = gestor.planesDePiloto(idPiloto);
        List<PlanDeVueloDTO> lista = new ArrayList<>();
        for (PlanDeVuelo p : planes) {
            lista.add(convertirPlan(p));
        }
        GenericEntity<List<PlanDeVueloDTO>> entity = new GenericEntity<List<PlanDeVueloDTO>>(lista) {
        };
        return Response.status(200).entity(entity).build();
    }

    private PlanDeVueloDTO convertirPlan(PlanDeVuelo plan) {
        PlanDeVueloDTO dto = new PlanDeVueloDTO();
        dto.setIdDron(plan.getDron().getId());
        dto.setIdPiloto(plan.getPiloto().getId());
        dto.setFecha(plan.getFecha().toString());
        dto.setDuracionHoras(plan.getDuracionHoras());
        List<PosicionDTO> posiciones = new ArrayList<>();
        for (Posicion p : plan.getPosiciones()) {
            posiciones.add(new PosicionDTO(p.getLatitud(), p.getLongitud()));
        }
        dto.setPosiciones(posiciones);
        return dto;
    }
}
