package fr.koor.webstore.ihm.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.koor.webstore.business.Article;
import fr.koor.webstore.dao.ArticleDAO;

@WebServlet( "/admin/editArticle" )
public class EditArticle extends HttpServlet {

	private static final long serialVersionUID = 550038282401302959L;


	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		HttpSession session = request.getSession( true );
		if ( session.getAttribute( "connectedUser" ) == null ) {
			response.sendRedirect( "../login" );
			return;
		}
		
		// Le choix imposé de l'article 4 est juste pour la démo !!!
		// Vous pouvez, bien entendu, modifier la page pour permettre la modification de n'importe quel article !!! (Bon TP)
		session.setAttribute( "currentArticle", ArticleDAO.getArticleById( 4 ) );
		request.getRequestDispatcher( "/admin/editArticle.jsp" ).forward( request, response );
	}
	
	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		HttpSession session = request.getSession( true );
		if ( session.getAttribute( "connectedUser" ) == null ) {
			response.sendRedirect( "../login" );
			return;
		}

		Article article = (Article) session.getAttribute( "currentArticle" );
		article.setDescription( request.getParameter( "description" ) );
		article.setBrand( request.getParameter( "brand" ) );
		article.setUnitaryPrice( Double.parseDouble( request.getParameter( "unitaryPrice" ) ) );
		ArticleDAO.updateArticle( article );		// On envoie les modifications en base
		
		request.getRequestDispatcher( "/admin/editArticle.jsp" ).forward( request, response );
	}
	
}
