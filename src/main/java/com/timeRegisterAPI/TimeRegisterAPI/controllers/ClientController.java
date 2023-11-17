package com.timeRegisterAPI.TimeRegisterAPI.controllers;

import com.timeRegisterAPI.TimeRegisterAPI.models.Client;
import com.timeRegisterAPI.TimeRegisterAPI.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping(path = "/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    //Listar tabla
    @CrossOrigin
    @GetMapping
    public ArrayList<Client> getListaClientes() {
        return clientService.listarClientes();
    }

    //Agregar cliente a la tabla
    @CrossOrigin
    @PostMapping
    public boolean agregarCliente(@RequestBody Client cl) {
        try {
            clientService.agregarCliente(cl);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    //Verificar si un cliente en específico está activo
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping(path = "/isExpirated") 
    public String isExpirated(@RequestBody Client cl) {
        return clientService.isExpirated(cl);
    }

    //Lista de clientes actualizados según subscription activa
    @CrossOrigin
    @GetMapping(path = "/updateClients")
    public ArrayList<Client> updateStatus() {
        return clientService.updateClient();
    }

    @CrossOrigin()
    @DeleteMapping(path = "/delete={id}")
    public String deleteById(@PathVariable Long id) {
        try {
            clientService.deleteById(id);
            return "Se borro el cliente con id: " + id + " con éxito";
        } catch (Exception exception) {
            return "Error en borrar cliente: " + exception;
        }
    }

}
