<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- a taglib "form" serve para trabalhar com formulário de cadastro -->
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cadastro de Usuário</title>
    <spring:url var="css" value="/static/css/bootstrap.css"/>
    <link type="text/css" rel="stylesheet" href="${css }"/>
</head>
<body>
<div class="container">
    <h1>Cadastro de Usuários</h1>
    <hr>
    <div>

		<spring:url value="/usuario/todos" var="home"/>
		<a class="btn btn-default" href="${home }">Home</a>

		</div>
    <hr>
    <div>
    <!--  usa a mesma variavel para salvar ou atualizar o que  muda é se tem id ou não -->
    	<spring:url value="${usuario.id == null ? '/usuario/save' : '/usuario/update'}" var="save"/>
       
       <!--  no "modelAttribute" passa uma instrução que vai ser referente à classe de dominio que vai trabalhar no formulário "usuario" que serve como variavel para 
        indicar "nome" faz parte da classe "usuario", que sobrenome faz parte da classe "usuario" e assim por diante
        a "action" vai ser a URL de acesso, o JSTL e o Spring Framework pode usar uma tag spring:url 
        a linguagem de expressão "save" esse valor dessa tag é URL-->
        <form:form modelAttribute="usuario" action="${save }" method="post">
        	<!-- usa isso por causa do update pois precisa de id -->
        	<form:hidden path="id"/>
            <div class="form-group">
                <label for="nome">Nome: </label>
                <!-- o atributo path faz conexão entre o formulario e objeto dominio, caso coloque algo que não exista na classe de 
			dominio(model) ou seja uma variavel que lá não existe ele vai lançar uma execeção assim que aplicação for rodada 
			porque existe uma conexão no Model View Controller onde a view de alguma maneira vai consegui enxergar que tem no seu Model
			-->
                <form:input path="nome" class="form-control"/>   
                <form:errors path="nome" cssClass="label label-danger"/>             
            </div>
            <div class="form-group">
                <label for="sobrenome">Sobrenome: </label>
                <form:input path="sobrenome"  class="form-control"/>    
                <form:errors path="sobrenome" cssClass="label label-danger"/>          
            </div>
            <div class="form-group">
                <label for="sexo">Sexo: </label>
                <form:select path="sexo" class="form-control">
                	<form:options items="${sexos }" itemLabel="desc"/>
                </form:select>
            </div>          
            <div class="form-group">
                <label for="dtNascimento">Data Nascimento: </label>
                <form:input path="dtNascimento"  class="form-control" type="date"/>  
                <form:errors path="dtNascimento" cssClass="label label-danger"/>    
            </div> 
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Confirmar</button>
            </div>
        </form:form>
    </div>
    <hr>
    <footer class="footer">
        <p>&copy; 2017 DevMedia</p> 
    </footer>
</div>
</body>
</html>