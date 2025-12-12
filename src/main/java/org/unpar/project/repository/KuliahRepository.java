package org.unpar.project.repository;

import org.unpar.project.dto.Kuliah;

import java.util.List;

public interface KuliahRepository {
    List<Kuliah> getKuliahList(String id);
}
