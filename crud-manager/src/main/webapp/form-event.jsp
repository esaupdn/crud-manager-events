<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<%@include file="base-head.jsp"%>
<title>CRUD Manager - Inserir Evento</title>
</head>
<body>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<h3 class="page-header">${action eq "insert" ? "Adicionar" : "Editar"}
			Evento</h3>

		<form action="${pageContext.request.contextPath}/event/${action}"
			method="POST">

			<input type="hidden" name="event_id" value="${eventToEdit.getId()}">

			<div class="row">
				<div class="form-group col-md-4">
					<label for="content">Nome</label> <input type="text"
						class="form-control" id="event_name" name="event_name"
						autofocus="autofocus" placeholder="Nome do Evento" required
						oninvalid="this.setCustomValidity('Informe o Nome do Evento.')"
						oninput="setCustomValidity('')" value="${eventToEdit.getName()}">
				</div>

				<div class="form-group col-md-4">
					<label for="content">Localização do Evento</label> <input type="text"
						class="form-control" id="event_location" name="event_location"
						autofocus="autofocus" placeholder="Localização do Evento" required
						oninvalid="this.setCustomValidity('Informe a Localização do Evento.')"
						oninput="setCustomValidity('')" value="${eventToEdit.getLocation()}">
				</div>
				

				<div class="form-group col-md-4">
					<label for="content">Descrição</label> <input type="text"
						class="form-control" id="event_description" name="event_description"
						autofocus="autofocus" placeholder="Descrição do Evento" required
						oninvalid="this.setCustomValidity('Informe a Descrição.')"
						oninput="setCustomValidity('')" value="${eventToEdit.getDescription()}">
				</div>

				<div class="form-group col-md-4">
					<label for="content">Email</label> <input type="email"
						class="form-control" id="event_email" name="event_email"
						autofocus="autofocus" placeholder="Email do Organizador do Evento" required
						oninvalid="this.setCustomValidity('Por favor, informe o Email do Organizador')"
						oninput="setCustomValidity('')" value="${eventToEdit.getEmail()}">
				</div>

				<div class="form-group col-md-6">
					<label for="seller_company">Empresa</label> <select
						id="event_company" class="form-control selectpicker"
						name="event_company" required
						oninvalid="this.setCustomValidity('Por favor, informe a Empresa Organizadora.')"
						oninput="setCustomValidity('')">
						<option value=""disabled ${notemptyeventsToEdit ? "" : "selected"}>Selecione
							uma empresa</option>
						<c:forEach var="company" items="${companies}">
							<option value="${company.getId()}"
								${eventToEdit.getCompany().getId() eq company.getId() ? "selected" : ""}>
								${company.getName()}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<hr />

			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/sellers"
						class="btn btn-default">Cancelar</a>
					<button type="submit" class="btn btn-primary">${action eq "insert" ? "Criar" : "Editar"}
						Evento</button>
				</div>
			</div>

		</form>
	</div>
</body>
</html>