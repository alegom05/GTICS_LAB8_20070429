package com.example.lab8.repositories;

import com.example.lab8.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    Optional<Evento> findByFecha(Date date);

    List<Evento> findAllByOrderByFecha();
}
