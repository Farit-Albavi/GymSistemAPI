package com.timeRegisterAPI.TimeRegisterAPI.services;


import com.timeRegisterAPI.TimeRegisterAPI.models.Client;
import com.timeRegisterAPI.TimeRegisterAPI.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ClientService {


    //¡renombrar el nombre de los metodos!!!!!!!
    @Autowired
    ClientRepository clientRepository;

    //Listar lista de clientes
    public ArrayList<Client> listarClientes() {
        return (ArrayList<Client>) clientRepository.findAll();
    }

    //Agregar cliente
    public Client agregarCliente(Client cl) {
        cl.setFecha_registro(LocalDate.now());
        return clientRepository.save(cl);
    }

    //verificar si el cliente tiene suscripcion expirada
    public String isExpirated(Client cl) {
        LocalDate today = LocalDate.now();
        LocalDate clientDate = cl.getFecha_registro();
        long daysActive = today.toEpochDay() - clientDate.toEpochDay();
        return daysActive >= 30 ? "Suscripcion inactiva con: " + daysActive + " dias activos"
                : "Suscripcion activa con: " + daysActive + " dias activos y les quedan " + (30 - daysActive) + " dias";
    }


    //Retorna cuantos días suscripto está el cliente
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
            cli.setDias_suscripcion((int) (30 - daysOfSus(cli)));
            updatedClients.add(cli);
        }
        return (ArrayList<Client>) clientRepository.saveAll(updatedClients);
    }


    public void renovarSuscripcion(Long id) throws Exception {
        if (clientRepository.existsById(id)) {
            Client cliente = clientRepository.findById(id).orElse(null);
            System.out.println("Fecha actual: " + cliente.getFecha_registro());
            if (cliente != null) {
                cliente.setFecha_registro(LocalDate.now());
                System.out.println("Fecha actual: " + cliente.getFecha_registro());
                clientRepository.save(cliente);
            }
        } else {
            throw new Exception("cliente null");
        }
    }


    //Borrar un cliente por ID
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

}
