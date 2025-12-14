package org.unpar.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DosenEditDTO {
    private String idDosen;
    private String nama;
    private String email;
}
