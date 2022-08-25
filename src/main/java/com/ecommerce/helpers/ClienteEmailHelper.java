package com.ecommerce.helpers;

import com.ecommerce.dtos.EmailMessageDto;
import com.ecommerce.models.Cliente;

public class ClienteEmailHelper {
	
	public static EmailMessageDto gerarMensagemDeCriacaoDeConta(Cliente cliente) {
		
		EmailMessageDto dto = new EmailMessageDto();
		
		String to = cliente.getEmail();
		String subject = "Parabéns, sua conta foi criada com sucesso!";
		String body = "Olá" + cliente.getNome()
		+ "\n\n"
		+ "Sua conta de cliente foi cadastrada com sucesso na nossa loja"
		+ "\n"
		+"Seus dados são:"
		+ "\nNome: " + cliente.getNome()
		+ "\nEmail: " + cliente.getEmail()
		+ "\nTelefone: " + cliente.getTelefone()
		+ "\nAtenciosamente"
		+ "\nEquipe Ecommercer";
		
		dto.setTo(to);
		dto.setSubject(subject);
		dto.setBody(body);
		
		return dto;		
		
	}

}
