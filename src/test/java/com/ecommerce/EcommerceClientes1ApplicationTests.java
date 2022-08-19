package com.ecommerce;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ecommerce.dtos.AuthenticationRequestDto;
import com.ecommerce.dtos.ClienteRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

@SpringBootTest
@AutoConfigureMockMvc
class EcommerceClientesApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void postCliente() throws Exception {
		
		ClienteRequestDto dto = new ClienteRequestDto();
		Faker faker = new Faker(new Locale("pt-BR"));
		
		dto.setNome(faker.name().fullName());
		dto.setEmail(faker.internet().emailAddress());
		dto.setTelefone(faker.number().digits(11));
		dto.setSenha(faker.internet().password(8,12));
		
		mockMvc.perform(
				post("/v1/clientes")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status()
						.isCreated());		
	}
	
	@Test
	public void postAuthentication() throws Exception {
		
		ClienteRequestDto clienteDto = new ClienteRequestDto();
		Faker faker = new Faker(new Locale("pt-BR"));
		
		clienteDto.setNome(faker.name().fullName());
		clienteDto.setEmail(faker.internet().emailAddress());
		clienteDto.setTelefone(faker.number().digits(11));
		clienteDto.setSenha(faker.internet().password(8,12));
		
		mockMvc.perform(
				post("/v1/clientes")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(clienteDto)));
		
		AuthenticationRequestDto authDto = new AuthenticationRequestDto();
		authDto.setEmail(clienteDto.getEmail());
		authDto.setSenha(clienteDto.getSenha());
		
		mockMvc.perform(
				post("/v1/auth")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(authDto)))
				.andExpect(status()
						.isOk());		
	}
	
	@Test
	public void postAccessDenied() throws Exception {
		
		AuthenticationRequestDto authDto = new AuthenticationRequestDto();
		Faker faker = new Faker(new Locale("pt-BR"));
		
		authDto.setEmail(faker.internet().emailAddress());
		authDto.setSenha(faker.internet().password(8,12));
		
		mockMvc.perform(
				post("/v1/auth")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(authDto)))
				.andExpect(status()
						.isUnauthorized());		
	}


}
