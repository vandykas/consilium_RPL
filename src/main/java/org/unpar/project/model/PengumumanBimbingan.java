package org.unpar.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PengumumanBimbingan {

    private String idMahasiswa;
    private String npm;
    private String nama;
    private String topik;
    private int sebelumUts;
    private int setelahUts;
    private boolean memenuhiMinimal;
}
