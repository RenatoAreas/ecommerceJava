package com.ecommerce.dtos;

import com.ecommerce.models.Cliente;

import lombok.Data;

@Data
public class ClienteResponseDto {
	private String message;
	private Cliente data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Cliente getData() {
		return data;
	}

	public void setData(Cliente data) {
		this.data = data;
	}

}
