package br.com.alura.gerenciador.servlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebFilter("/entrada")
public class AutorizacaoFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	@Override
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String acao = request.getParameter("acao");
		boolean acaoProtegida = !(acao.equals("loginForm") || acao.equals("login"));
		
		HttpSession sessao = ((HttpServletRequest) request).getSession();
		
		boolean usuarioLogado = sessao.getAttribute("usuarioLogado") == null;
		
		if (acaoProtegida && usuarioLogado) {
			((HttpServletResponse) response).sendRedirect("entrada?acao=loginForm");
			return;
		}
		
		System.out.println("Autorização feita");
		chain.doFilter(request, response);
	}

}
