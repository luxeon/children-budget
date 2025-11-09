<#import "layout.ftlh" as layout>
<@layout.page title="Add User">
    <div class="mb-3">
        <a href="/users" class="text-decoration-none">&larr; Back to users</a>
    </div>

    <div class="card shadow-sm">
        <div class="card-header bg-white d-flex justify-content-between align-items-center">
            <h1 class="h5 mb-0">Add New User</h1>
        </div>
        <div class="card-body">
            <form method="post" action="/users" class="row g-3">
                <div class="col-md-6">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" id="name" name="name" class="form-control" required maxlength="255" placeholder="Child name">
                </div>
                <div class="col-md-6">
                    <label for="birthday" class="form-label">Birthday</label>
                    <#assign defaultDate = (today?? && today?is_date)?then(today?string["yyyy-MM-dd"], .now?date?string["yyyy-MM-dd"])>
                    <input type="date" id="birthday" name="birthday" class="form-control" value="${defaultDate}" required>
                </div>
                <div class="col-md-4">
                    <label for="initialBalance" class="form-label">Initial Balance</label>
                    <input type="number" id="initialBalance" name="initialBalance" class="form-control" value="0.00" step="0.01" min="0">
                </div>
                <div class="col-12 d-flex justify-content-end gap-2">
                    <a href="/users" class="btn btn-outline-secondary">Cancel</a>
                    <button type="submit" class="btn btn-primary">Create User</button>
                </div>
            </form>
        </div>
    </div>
</@layout.page>
