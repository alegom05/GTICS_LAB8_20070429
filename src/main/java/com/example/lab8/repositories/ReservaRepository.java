package com.example.lab8.repositories;

import com.example.lab8.entity.Evento;
import com.example.lab8.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    Integer countById(Integer idEvento);
}
