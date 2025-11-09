<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Children Budget · Users</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
	<div class="container">
		<a class="navbar-brand fw-semibold" href="/users">Children Budget</a>
	</div>
</nav>

<main class="container">
	<div class="card shadow-sm">
		<div class="card-header bg-white">
			<h1 class="h4 mb-0">Children</h1>
		</div>
		<div class="card-body p-0">
			<table class="table table-striped table-hover mb-0">
				<thead class="table-light">
				<tr>
					<th scope="col" style="width: 15%">ID</th>
					<th scope="col">Name</th>
				</tr>
				</thead>
				<tbody>
				<#if users?has_content>
					<#list users as user>
						<tr>
							<td class="text-muted">${user.id()?c}</td>
							<td class="fw-medium">${user.name()}</td>
						</tr>
					</#list>
				<#else>
					<tr>
					       <td colspan="2" class="text-center py-4 text-muted">No users found.</td>
					</tr>
				</#if>
				</tbody>
			</table>
		</div>
	</div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>
