package com.ecommerce.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

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

	public static String getHashMd5(String value) {
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
