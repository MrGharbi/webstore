package fr.koor.webstore.ihm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.koor.webstore.business.CatalogBrowser;

@WebServlet( "/viewArticle" )
public class ViewArticle extends HttpServlet {

	private static final long serialVersionUID = 550038282401302959L;


	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		HttpSession session = request.getSession( true );
		if ( session.getAttribute( "connectedUser" ) == null ) {
			response.sendRedirect( "login" );
			return;
		}
		
		request.getRequestDispatcher( "/viewArticle.jsp" ).forward( request, response );
	}
	
	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		HttpSession session = request.getSession( true );
		if ( session.getAttribute( "connectedUser" ) == null ) {
			response.sendRedirect( "login" );
			return;
		}

		CatalogBrowser browser = (CatalogBrowser) session.getAttribute( "catalogBrowser" );
		
		if ( request.getParameter( "btnPrevious" ) != null ) {
			browser.goPrevious();
		} else if ( request.getParameter( "btnNext" ) != null ) {
			browser.goNext();
		} else if ( request.getParameter( "btnAdd" ) != null ) {
			browser.addCurrentArticle();
		}
		
		request.getRequestDispatcher( "/viewArticle.jsp" ).forward( request, response );
	}
	
}
