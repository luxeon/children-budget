<#import "layout.ftlh" as layout>
<@layout.page title="Users">
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
					<th scope="col" style="width: 20%">Birthday</th>
					<th scope="col" class="text-end" style="width: 20%">Balance</th>
				</tr>
				</thead>
				<tbody>
				<#if users?has_content>
					<#list users as user>
						<tr>
							<td class="text-muted">${user.id()?c}</td>
							<td class="fw-medium">
								<a href="/users/${user.id()?c}" class="text-decoration-none">${user.name()}</a>
							</td>
							<td>
								<#if user.birthday()?is_date>
									${user.birthday()?string["MMM d, yyyy"]}
								<#else>
									${user.birthday()}
								</#if>
							</td>
							<td class="text-end fw-semibold">${user.balance()?string["0.00"]}</td>
						</tr>
					</#list>
				<#else>
					<tr>
						<td colspan="4" class="text-center py-4 text-muted">No users found.</td>
					</tr>
				</#if>
				</tbody>
			</table>
		</div>
	</div>
</@layout.page>
