package com.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.models.Cliente;

@Repository
public interface IClienteRepository extends MongoRepository<Cliente, String> {

	@Query("{email : ?0}")
	Optional<Cliente> findByEmail(String email);

	@Query("{telefone : ?0}")
	Optional<Cliente> findByTelefone(String telefone);
}

