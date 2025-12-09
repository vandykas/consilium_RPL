package org.unpar.project.repository;

import org.unpar.project.model.Ruangan;

import java.util.List;

public interface RuanganRepository {
    List<Ruangan> findAll();
}
