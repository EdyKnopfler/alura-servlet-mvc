package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import br.com.alura.gerenciador.acoes.Acao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/entrada")
public class UnicaEntradaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeAcao = request.getParameter("acao");
		
		if (nomeAcao == null) 
			throw new ServletException("Nenhuma ação definida!");
		
		String classe = "br.com.alura.gerenciador.acoes." + 
						nomeAcao.substring(0, 1).toUpperCase() +
						nomeAcao.substring(1);
		
		Acao acao = null;
		
		try {
			acao = (Acao) Class.forName(classe).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
		String nome = acao.executa(request, response);
		String[] split = nome.split(":");
		String tipo = split[0];
		String endereco = split[1];
		
		if (tipo.equals("forward")) {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/" + endereco);
			rd.forward(request, response);		
		}
		else {
			response.sendRedirect(endereco);
		}
	}

}
