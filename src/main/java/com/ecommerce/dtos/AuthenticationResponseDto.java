package com.ecommerce.dtos;

import com.ecommerce.models.Cliente;

import lombok.Data;

@Data
public class AuthenticationResponseDto {
	
	private String message;
	private String accessToken;
	private Cliente data;
	

}
