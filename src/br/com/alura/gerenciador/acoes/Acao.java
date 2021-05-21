package br.com.alura.gerenciador.acoes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Acao {

	String executa(HttpServletRequest request, HttpServletResponse response);

}
