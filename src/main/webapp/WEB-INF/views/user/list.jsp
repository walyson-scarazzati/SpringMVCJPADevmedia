<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- quando trabalha com JSTL precisa importar para JSP os recursos que a JSTL vão fornecer
     usa a taglib para fazer isso
     A URI é da onde você faz o import da sua taglib
  	 para utilizar esses recursos dentro das páginas usa o prefixo
  	 exemplo sempre que digitar o prefixo spring vai ter acesso aos recursos da taglib "tags" do Spring Framework fornece  -->
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Lista de Ususarios</title>
    <spring:url var="css" value="/static/css/bootstrap.css"/>
    <link type="text/css" rel="stylesheet" href="${css }"/>
</head>
<body>
<div class="container">
    <h1>Lista de Usuários</h1>
    <hr>
    <div>

		<spring:url value="/usuario/cadastro" var="cadastro"/>
        <a class="btn btn-default" href="${cadastro }">Novo Usuário</a>
    </div>
    <hr>
    		<div>
				<div>
					<spring:url var="act_sexo" value="/usuario/sexo" />
					<form action="${act_sexo}" method="get">
						<div class="form-group">
							<label for="tipoSexo">Busca por Sexo</label> <select
								name="tipoSexo" class="form-control">
								<c:forEach var="sexo" items="${sexos}">
									<option value="${sexo.desc}">${sexo.desc}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-default">Localizar</button>
						</div>
					</form>
				</div>

				<div>
					<spring:url var="act_nome" value="/usuario/nome" />
					<form action="${act_nome}" method="get">
						<div class="form-group">
							<label for="nome">Busca por Nome ou Sobrenome</label> <input
								name="nome" type="text" class="form-control">
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-default">Localizar</button>
						</div>
					</form>
				</div>
			</div>

    <div class="${message == null ? 'panel-default' : 'panel-success'}">

        <div class="panel-heading">
            <span>${message == null ? '&nbsp;' : message}</span>
        </div>

        <table class="table table-striped table-condensed">
            <thead>
            <tr>
                <th>ID</th>
                <th>NOME</th>
                <th>DATA NASCIMENTO</th>
                <th>TIPO SEXO</th>
                <th>AÇÃO</th>
            </tr>
            </thead>
            <tbody>
            <!-- a propriedade "items" diz o que via percorrer ou qual lista que vou percorrer o nome deve ser o mesmo que está no controller adicionando uma variável usuarios contendo objeto 
             a propriedade "var" onde define um nome que vai ser a variavel -->
            <c:forEach var="usuario" items="${usuarios }">
                <tr>
                <!--  sempre pega somente o nome atributo exemplo id, não o nome do método do atributo ex getId -->
                    <td>${usuario.id }</td>
                    <td>${usuario.nome }&nbsp;${usuario.sobrenome }</td>
                    <td>
                    <!--  se usa date ou calendar não precisa fazer o parcer mas nesse caso precisa pois está trabalhando com LocalDate -->
                    	<f:parseDate var="date" value="${usuario.dtNascimento }" pattern="yyyy-MM-dd" type="date"/>
                    	<!-- como quero mostrar a data -->
                    	<f:formatDate value="${date }" pattern="dd/MM/yyyy" type="date"/>
                    </td>
                    <td>${usuario.sexo.desc }</td>
                    <td>                        
                    	<spring:url value="/usuario/update/${usuario.id }" var="update"/>
                        <a class="btn btn-info" href="${update }" >Editar</a>
                        <spring:url value="/usuario/delete/${usuario.id }" var="delete"/>
                        <a class="btn btn-danger" href="${delete }" >Excluir</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <hr>
    <footer class="footer">
        <p>&copy; 2017 DevMedia</p>
    </footer>
</div>
</body>
</html>