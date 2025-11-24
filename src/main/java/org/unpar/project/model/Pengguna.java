package main.java.org.unpar.project.model;

import lombok.Data;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  
public class Pengguna {
    private String idPengguna;
    private String nama;
    private String password;
    private String email;
}
