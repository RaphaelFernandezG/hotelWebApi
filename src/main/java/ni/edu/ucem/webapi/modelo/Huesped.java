/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.ucem.webapi.modelo;

import javax.validation.constraints.NotNull;

/**
 *
 * @author dbojorge
 */
public class Huesped {
    private int id;
    @NotNull(message="Nombre Huesped Requerido")
    private String nombre;
    private String email;
    @NotNull(message="Teléfono Huesped Requerido")
    private String telefono;

    public Huesped() {
    }

    public Huesped(int id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
