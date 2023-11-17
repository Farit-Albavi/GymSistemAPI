package com.timeRegisterAPI.TimeRegisterAPI.repositories;

import com.timeRegisterAPI.TimeRegisterAPI.models.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
