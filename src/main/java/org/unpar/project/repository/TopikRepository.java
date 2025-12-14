package org.unpar.project.repository;

import java.util.List;

import org.unpar.project.model.Topik;

public interface TopikRepository {
    String getJudulTopik(String kodeTopik);
    List<Topik> findAllTopikByDosen(String id);
    List<Topik> findAllTopik();

    void saveTopik(String id, String judulTopik);

    void savePembukaTopik(String idDosen, String idTopik);
}
