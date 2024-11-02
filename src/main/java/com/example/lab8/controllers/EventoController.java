package com.example.lab8.controllers;



import com.example.lab8.entity.Evento;
import com.example.lab8.entity.Reserva;
import com.example.lab8.repositories.EventoRepository;
import com.example.lab8.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
public class EventoController {

    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    ReservaRepository reservaRepository;

    //Evento
    @ResponseBody
    @GetMapping(value="/evento",produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public List<Evento> listaEvento() {
        return eventoRepository.findAllByOrderByFecha();
    }

    @GetMapping(value = "/evento/{id}")
    public ResponseEntity<HashMap<String, Object>> obtenerEventoPorId(@PathVariable("id") String dateStr) {
        HashMap<String, Object> responseJson = new HashMap<>();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date fecha = formato.parse(dateStr);

            Optional<Evento> optTarjeta = eventoRepository.findByFecha(fecha);
            if (optTarjeta.isPresent()) {
                responseJson.put("result", "success");
                responseJson.put("tarjeta", optTarjeta.get());
                return ResponseEntity.ok(responseJson);
            } else {
                responseJson.put("msg", "Evento no encontrado");
            }
        } catch (ParseException e) {
            responseJson.put("msg", "Formato de fecha inválido. Utiliza el formato yyyy-MM-dd");
        }
        responseJson.put("result", "failure");
        return ResponseEntity.badRequest().body(responseJson);
    }

    @PostMapping(value = "/evento")
    public ResponseEntity<HashMap<String, Object>> guardarProducto(
            @RequestBody Evento evento,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseMap = new HashMap<>();

        eventoRepository.save(evento);
        if (fetchId) {
            responseMap.put("id", evento.getId());
        }
        responseMap.put("estado", "creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }

    //Reserva
    @ResponseBody
    @GetMapping(value="/reserva",produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    @PostMapping(value = "/reserva")
    public ResponseEntity<HashMap<String, Object>> guardarReserva(@RequestBody Reserva reserva) {

        HashMap<String, Object> responseMap = new HashMap<>();

        int idEvento = reserva.getEvento().getId();
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);

        if (eventoOpt.isPresent()) {
            Evento evento = eventoOpt.get();

            Integer reservasActuales = evento.getReservas();
            Integer capacidadMaxima = evento.getCapacidad();

            if (reservasActuales + reserva.getCupos() <= capacidadMaxima) {
                reserva.setEvento(evento);
                reservaRepository.save(reserva);

                evento.setReservas(reservasActuales + reserva.getCupos());
                eventoRepository.save(evento);

                responseMap.put("estado", "reserva creada");
                responseMap.put("idReserva", reserva.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("mensaje", "No hay suficientes cupos disponibles");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }
        } else {
            responseMap.put("estado", "error");
            responseMap.put("mensaje", "Evento no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
        }
    }


    @DeleteMapping(value = "/reserva/{id}")
    public ResponseEntity<HashMap<String, Object>> borrarReserva(@PathVariable("id") int idReserva) {
        HashMap<String, Object> responseMap = new HashMap<>();

        Optional<Reserva> reservaOpt = reservaRepository.findById(idReserva);
        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            Evento evento = reserva.getEvento();
            reservaRepository.delete(reserva);

            int reservasActuales = evento.getReservas() - reserva.getCupos();
            evento.setReservas(reservasActuales);
            eventoRepository.save(evento);

            responseMap.put("estado", "borrado exitoso");
            return ResponseEntity.ok(responseMap);
        } else {
            responseMap.put("estado", "error");
            responseMap.put("mensaje", "Reserva no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
        }
    }



}
