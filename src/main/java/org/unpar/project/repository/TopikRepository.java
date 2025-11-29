package org.unpar.project.repository;

import org.unpar.project.model.Topik;

import java.util.List;

public interface TopikRepository {
    String getJudulTopik(String kodeTopik);
    List<Topik> findAllTopikByDosen(String id);
}
