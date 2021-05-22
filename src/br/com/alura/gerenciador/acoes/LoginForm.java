package br.com.alura.gerenciador.acoes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginForm implements Acao {

	@Override
	public String executa(HttpServletRequest request, HttpServletResponse response) {
		return "forward:formLogin.jsp";
	}

}
