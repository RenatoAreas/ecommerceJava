package com.ecommerce.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dtos.ClienteRequestDto;
import com.ecommerce.dtos.ClienteResponseDto;
import com.ecommerce.models.Cliente;
import com.ecommerce.services.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Clientes")
@RestController
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@ApiOperation("Serviço para cadastro de clientes.")
	@PostMapping("/v1/clientes")
	public ResponseEntity<ClienteResponseDto> post(@Valid @RequestBody ClienteRequestDto dto) {

		ClienteResponseDto clienteResponse = new ClienteResponseDto();
		HttpStatus status = null;

		try {

			ModelMapper modelMapper = new ModelMapper();
			Cliente cliente = clienteService.save(modelMapper.map(dto, Cliente.class));

			clienteResponse.setMessage("Cliente cadastrado com sucesso.");
			clienteResponse.setData(cliente);

			status = HttpStatus.CREATED;
		} catch (IllegalArgumentException e) {

			clienteResponse.setMessage(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}

		return ResponseEntity.status(status).body(clienteResponse);
	}
}
