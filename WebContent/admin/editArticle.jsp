<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Edit article</title>
		<link rel="stylesheet" type="text/css" href="../styles.css" />
	</head>
	<body>
        <h1>Edit article - Admin part</h1>
        
	    <br/>
	   
        <form action="editArticle" method="post">
        
	        Identifier: <input name="idArticle" value="${currentArticle.idArticle}" /> <br/>
	        Brand: <input name="brand" value="${currentArticle.brand}" /> <br/> 
	        Description: <input name="description" value="${currentArticle.description}" /> <br/>
	        Unitary price: <input name="unitaryPrice" value="${currentArticle.unitaryPrice}" type="number" /> <br/>
	        <br/>
	        
            <input name="btnUpdate" type="submit" value="Update article" />
            
        </form>    
	</body>
</html>