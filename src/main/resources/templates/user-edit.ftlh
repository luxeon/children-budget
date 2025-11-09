<#import "layout.ftlh" as layout>
<@layout.page title="Edit ${user.name()}">
    <div class="mb-3">
        <a href="/users/${user.id()?c}" class="text-decoration-none">&larr; Back to user details</a>
    </div>

    <div class="card shadow-sm">
        <div class="card-header bg-white d-flex justify-content-between align-items-center">
            <h1 class="h5 mb-0">Edit User</h1>
            <span class="text-muted small">ID ${user.id()?c}</span>
        </div>
        <div class="card-body">
            <form method="post" action="/users/${user.id()?c}" class="row g-3">
                <div class="col-md-6">
                    <label for="name" class="form-label">Name</label>
                    <input type="text"
                           id="name"
                           name="name"
                           class="form-control"
                           value="${user.name()}"
                           required maxlength="255">
                </div>
                <div class="col-md-6">
                    <label for="birthday" class="form-label">Birthday</label>
                    <#assign birthdayValue = (user.birthday()?is_date)?then(user.birthday()?string["yyyy-MM-dd"], user.birthday())>
                    <input type="date"
                           id="birthday"
                           name="birthday"
                           class="form-control"
                           value="${birthdayValue}"
                           required>
                </div>
                <div class="col-12 d-flex justify-content-end gap-2">
                    <a href="/users/${user.id()?c}" class="btn btn-outline-secondary">Cancel</a>
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </div>
            </form>
        </div>
    </div>
</@layout.page>
