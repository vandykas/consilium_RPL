package org.unpar.project.model;

import lombok.Data;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  
public class Pengguna {
    private String idPengguna;
    private String nama;
    private String email;
    private String password;
    private String role;
}
