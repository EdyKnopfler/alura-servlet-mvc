package br.com.alura.gerenciador.acoes;

import java.util.List;

import br.com.alura.gerenciador.modelo.Banco;
import br.com.alura.gerenciador.modelo.Empresa;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListaEmpresas implements Acao {
	
	public String executa(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Listando empresas");

		Banco banco = new Banco();
		List<Empresa> lista = banco.getEmpresas();
		
		request.setAttribute("empresas", lista);
		return "forward:listaEmpresas.jsp";
	}

}
