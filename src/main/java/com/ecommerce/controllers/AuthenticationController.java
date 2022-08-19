package com.ecommerce.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dtos.AuthenticationRequestDto;
import com.ecommerce.dtos.AuthenticationResponseDto;
import com.ecommerce.models.Cliente;
import com.ecommerce.security.TokenAuthenticationService;
import com.ecommerce.services.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Autenticação")
@RestController
public class AuthenticationController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@ApiOperation("Serviço para autenticação de clientes")
	@PostMapping("/v1/auth")
	public ResponseEntity<AuthenticationResponseDto> post(@Valid @RequestBody AuthenticationRequestDto dto) {

		AuthenticationResponseDto authResponse = new AuthenticationResponseDto();
		HttpStatus status = null;

		try {

			Cliente cliente = clienteService.get(dto.getEmail(), dto.getSenha());

			authResponse.setMessage("Cliente obtido com sucesso!");
			authResponse.setAccessToken(tokenAuthenticationService.generateToken(cliente.getEmail()));
			authResponse.setData(cliente);

			status = HttpStatus.OK;

		} catch (IllegalArgumentException e) {

			authResponse.setMessage(e.getMessage());
			status = HttpStatus.UNAUTHORIZED;

		}

		return ResponseEntity.status(status).body(authResponse);
	}
}
