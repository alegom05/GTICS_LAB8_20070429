package com.example.lab8.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity (name="reservas")
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreservas")
    private Integer id;

    private String nombre;

    private String correo;

    private Integer cupos;

    @ManyToOne
    @JoinColumn(name = "ideventos")
    private Evento evento;


}
