package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import br.com.alura.gerenciador.acoes.Acao;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControladorFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
	
		HttpServletRequest request = ((HttpServletRequest) req);
		HttpServletResponse response = ((HttpServletResponse) res);
		
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
			response.sendRedirect("entrada?acao=" + endereco);
		}
	}

}
