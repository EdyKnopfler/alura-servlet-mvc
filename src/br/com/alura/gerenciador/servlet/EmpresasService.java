package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;

import br.com.alura.gerenciador.modelo.Banco;
import br.com.alura.gerenciador.modelo.Empresa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/empresas")
public class EmpresasService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String mimeJson = "application/json";
	private static final String mimeXml = "application/xml";
	private static final String dateIso = "yyyy-MM-dd'T'HH:mm:ssXXX";
	
	private Gson gson;
	private XStream xstream;
	
	
	public EmpresasService() {
		gson = new GsonBuilder().setDateFormat(dateIso).create();
		
		DateConverter dateConverter = new DateConverter(dateIso, null);
		xstream = new XStream();
		xstream.registerConverter(dateConverter);
		xstream.alias("empresa", Empresa.class);
		xstream.alias("empresas", List.class);
	}


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Banco banco = new Banco();
		List<Empresa> empresas = banco.getEmpresas();
		String accept = request.getHeader("Accept");
		
		if (accept != null && accept.contains(mimeXml) && !accept.contains(mimeJson)) {
			String xml = xstream.toXML(empresas);
			response.setContentType(mimeXml);
			response.getWriter().print(xml);
		}
		else {
			String json = gson.toJson(empresas);
			response.setContentType(mimeJson);
			response.getWriter().print(json);
		}
	}

}
