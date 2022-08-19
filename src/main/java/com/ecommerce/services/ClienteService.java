package com.ecommerce.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.models.Cliente;
import com.ecommerce.repositories.IClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private IClienteRepository clienteRepository;

	public Cliente save(Cliente cliente) {

		if (clienteRepository.findByEmail(cliente.getEmail()).isPresent())
			throw new IllegalArgumentException("Email já cadastrado, tente outro.");

		if (clienteRepository.findByTelefone(cliente.getTelefone()).isPresent())
			throw new IllegalArgumentException("Telefone já cadastrado, tente outro.");

		cliente.setSenha(getHashMd5(cliente.getSenha()));
		cliente.setCadastradoEm(Instant.now());
		cliente.setAtualizadoEm(Instant.now());

		clienteRepository.save(cliente);
		return cliente;
	}

	public Cliente get(String email, String senha) {

		Optional<Cliente> optional = clienteRepository.findByEmailAndSenha(email, getHashMd5(senha));

		if (optional.isEmpty())
			throw new IllegalArgumentException("Dados inválidos, Cliente não encontrado");

		Cliente cliente = optional.get();
		return cliente;
	}

	private static String getHashMd5(String value) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		BigInteger hash = new BigInteger(1, md.digest(value.getBytes()));
		return hash.toString(16);
	}

}
