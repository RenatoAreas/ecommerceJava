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
import com.ecommerce.dtos.EmailMessageDto;
import com.ecommerce.helpers.ClienteEmailHelper;
import com.ecommerce.models.Cliente;
import com.ecommerce.producers.EmailMessageProducer;
import com.ecommerce.services.ClienteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Clientes")
@RestController
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailMessageProducer emailMessageProducer;

	@Autowired
	private ObjectMapper objectMapper;

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

			// enviando mensagem
			EmailMessageDto emailMessage = ClienteEmailHelper.gerarMensagemDeCriacaoDeConta(cliente);
			String message = objectMapper.writeValueAsString(emailMessage);
			emailMessageProducer.send(message);

		} catch (IllegalArgumentException e) {

			clienteResponse.setMessage(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		} catch (JsonProcessingException e) {

			e.printStackTrace();
			clienteResponse.setMessage(e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return ResponseEntity.status(status).body(clienteResponse);
	}
}
