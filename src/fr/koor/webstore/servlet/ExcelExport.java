package fr.koor.webstore.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;


@WebServlet( urlPatterns="/export.xls" )
public class ExcelExport extends HttpServlet {

	private static final long serialVersionUID = 8181226250983408872L;

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = getServletContext();
		String databaseDriver = context.getInitParameter( "JDBC_DRIVER" );
		String databaseURL = context.getInitParameter( "JDBC_URL" );
		String databaseLogin = context.getInitParameter( "JDBC_LOGIN" );
		String databasePassword = context.getInitParameter( "JDBC_PASSWORD" );

		try {
			Class.forName( databaseDriver );
			
			try ( HSSFWorkbook workbook = new HSSFWorkbook();
				  Connection connection = DriverManager.getConnection( databaseURL, databaseLogin, databasePassword ) ) {
				
				// --- Define some cell styles ---
				CellStyle headerCellStyle = workbook.createCellStyle();
				HSSFFont font = workbook.createFont();
				font.setBold( true );
				headerCellStyle.setFont( font );
				headerCellStyle.setAlignment( HorizontalAlignment.CENTER );
				
				CellStyle numericCellStyle = workbook.createCellStyle();
				numericCellStyle.setDataFormat( 
						workbook.getCreationHelper().createDataFormat().getFormat( "0.00" ) );
				
				
				// --- Export T_Articles table ---
				HSSFSheet articleSheet = workbook.createSheet( "T_Articles data" );
				HSSFRow row = articleSheet.createRow( 0 );
				HSSFCell cell;
				
				cell = row.createCell( 0 );
				cell.setCellStyle( headerCellStyle );
				cell.setCellValue( "Article identifier" );
				
				cell = row.createCell( 1 );
				cell.setCellStyle( headerCellStyle );
				cell.setCellValue( "Description" );

				cell = row.createCell( 2 );
				cell.setCellStyle( headerCellStyle );
				cell.setCellValue( "Brand" );

				cell = row.createCell( 3 );
				cell.setCellStyle( headerCellStyle );
				cell.setCellValue( "Unitary price" );

				int rowIndex = 1;
				String strSql = "SELECT * FROM T_Articles";
				try ( Statement statement = connection.createStatement() ) {
					try ( ResultSet resultSet = statement.executeQuery( strSql ) ) {
						while( resultSet.next() ) {
							row = articleSheet.createRow( rowIndex++ );
							cell = row.createCell( 0 );
							cell.setCellValue( resultSet.getInt( "idArticle" ) );

							cell = row.createCell( 1 );
							cell.setCellValue( resultSet.getString( "description" ) );

							cell = row.createCell( 2 );
							cell.setCellValue( resultSet.getString( "brand" ) );

							cell = row.createCell( 3 );
							cell.setCellStyle( numericCellStyle );
							cell.setCellValue( resultSet.getDouble( "unitaryPrice" ) );
						}
					}
				}
				
				// --- Auto size each columns of the sheet ---
				for (int i = 0; i < 4; i++) {
					articleSheet.autoSizeColumn( i );
				}

				// --- Export T_Users table ---
				@SuppressWarnings( "unused" )
				HSSFSheet userSheet = workbook.createSheet( "T_Users data" );
				// TODO: terminate the sample
				
				
				// --- Write the office document to the servlet socket ---
				response.setContentType( "application/vnd.ms-excel " );
				try ( OutputStream out = response.getOutputStream() ) {
					workbook.write( out );
				}
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			response.setContentType( "text/html" );
			try ( PrintWriter out = response.getWriter() ) {
				out.println( "An error is produced, please view de servet log for more informations" );
			}
			
		}
		
	}
	
}

