package com.example.lab8.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity (name="eventos")
@Getter
@Setter
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ideventos")
    private Integer id;

    private String nombre;

    private Date fecha;

    private String categoria;

    private Integer capacidad;

    private Integer reservas;

}
