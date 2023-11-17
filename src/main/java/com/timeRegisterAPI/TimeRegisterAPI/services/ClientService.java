package com.timeRegisterAPI.TimeRegisterAPI.services;


import com.timeRegisterAPI.TimeRegisterAPI.models.Client;
import com.timeRegisterAPI.TimeRegisterAPI.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public ArrayList<Client> listarClientes() {
        return (ArrayList<Client>) clientRepository.findAll();
    }

    //Agregar cliente
    public Client agregarCliente(Client cl) {
        cl.setFecha_registro(LocalDate.now());
        return clientRepository.save(cl);
    }

    public String isExpirated(Client cl) {
        LocalDate today = LocalDate.now();
        LocalDate clientDate = cl.getFecha_registro();
        long daysActive = today.toEpochDay() - clientDate.toEpochDay();
        return daysActive >= 30 ? "Suscripcion inactiva con: " + daysActive + " dias activos" : "Suscripcion activa con: " + daysActive + " dias activos";
    }


    public long daysOfSus(Client cl) {
        LocalDate today = LocalDate.now();
        LocalDate clientDate = cl.getFecha_registro();
        return today.toEpochDay() - clientDate.toEpochDay();
    }


    //Actualiza la tabla en función si una subscription está activa o no.
    public ArrayList<Client> updateClient() {
        ArrayList<Client> updatedClients = new ArrayList<>();
        ArrayList<Client> clientes = (ArrayList<Client>) clientRepository.findAll();

        for (Client cli : clientes) {
            // Si la suscripción ha expirado
            if (daysOfSus(cli) >= 30) {
                cli.setExpirated(true);
                updatedClients.add(cli);
            } else {
                cli.setExpirated(false);
                updatedClients.add(cli);
            }
        }
        return (ArrayList<Client>) clientRepository.saveAll(updatedClients);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

}
