<#import "layout.ftlh" as layout>
<#assign isReadOnly = (readOnly?? && readOnly)>
<@layout.page title=user.name() readOnly=isReadOnly>

    <#if !isReadOnly>
        <div class="mb-3">
            <a href="/users" class="text-decoration-none">&larr; Back to users</a>
        </div>
    </#if>

    <#if !isReadOnly && transactionDeleted?? && transactionDeleted>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Transaction was deleted.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </#if>

	<div class="card shadow-sm mb-4">
        <div class="card-body d-flex flex-column flex-md-row justify-content-between align-items-start align-items-md-center">
            <div class="mb-3 mb-md-0">
                <p class="text-muted mb-1">Balance</p>
                <p class="display-6 mb-0">${user.balance()?string["0.00"]}</p>
            </div>
            <div class="text-md-end">
                <p class="text-muted mb-1">User</p>
                <p class="h4 mb-0">${user.name()}</p>
                <p class="text-muted mb-0">ID ${user.id()?c}</p>
                <p class="text-muted mb-0">
                    Birthday
                    <#if user.birthday()?is_date>
                        ${user.birthday()?string["MMM d, yyyy"]}
                    <#else>
                        ${user.birthday()}
                    </#if>
                </p>
            </div>
        </div>
    </div>
    <#if !isReadOnly>
        <div class="card shadow-sm mb-4">
            <div class="card-header bg-white d-flex flex-column flex-md-row justify-content-between align-items-md-center">
                <h2 class="h5 mb-3 mb-md-0">Add Transaction</h2>
                <p class="text-muted small mb-0">
                    Debit spends money by default. Switch to credit to add funds.
                </p>
            </div>
            <div class="card-body">
                <form method="post" action="/users/${user.id()?c}/transactions" class="row g-3">
                    <div class="col-md-4">
                        <label for="description" class="form-label">Description</label>
                        <input type="text" class="form-control" id="description" name="description" placeholder="Groceries"
                               required>
                    </div>
                    <div class="col-md-3">
                        <label for="amount" class="form-label">Amount</label>
                        <input type="number" class="form-control" id="amount" name="amount" min="0.01" step="0.01"
                               value="1.00" required>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label d-block">Type</label>
                        <div class="btn-group" role="group" aria-label="Transaction type">
                            <input type="radio" class="btn-check" name="type" id="type-debit" value="DEBIT" checked>
                            <label class="btn btn-outline-danger" for="type-debit">Debit (spend)</label>

                            <input type="radio" class="btn-check" name="type" id="type-credit" value="CREDIT">
                            <label class="btn btn-outline-success" for="type-credit">Credit (add)</label>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <label for="occurredOn" class="form-label">Date</label>
                        <input type="date" class="form-control" id="occurredOn" name="occurredOn"
                               value="${.now?date?string["yyyy-MM-dd"]}">
                    </div>
                    <div class="col-12 text-end">
                        <button type="submit" class="btn btn-primary">Add Transaction</button>
                    </div>
                </form>
            </div>
        </div>
    </#if>

    <div class="card shadow-sm">
        <#assign displayedTotalPages = (transactionsPage.totalPages?int > 0)?then(transactionsPage.totalPages?int, 1)>
        <div class="card-header bg-white d-flex justify-content-between align-items-center">
            <h2 class="h5 mb-0">Transactions</h2>
            <span class="text-muted small">
                Page ${(transactionsPage.number?int + 1)?c}
                of ${displayedTotalPages?c}
            </span>
        </div>
        <div class="card-body p-0">
            <#if transactions?has_content>
                <table class="table table-striped table-hover mb-0">
                    <thead class="table-light">
                    <tr>
                        <th scope="col" style="width: 20%">Date</th>
                        <th scope="col">Description</th>
                        <th scope="col" class="text-end" style="width: 20%">Amount</th>
                        <#if !isReadOnly>
                            <th scope="col" style="width: 10%"></th>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <#list transactions as transaction>
                        <#assign amountNumber = transaction.amount()?double>
                        <#if (amountNumber >= 0)>
                            <#assign amountClass = "text-success">
                        <#else>
                            <#assign amountClass = "text-danger">
                        </#if>
                        <#assign occurredOn = transaction.occurredOn()>
                        <tr>
                            <td class="text-muted">
                                <#if occurredOn?is_date>
                                    ${occurredOn?string["MMM d, yyyy"]}
                                <#else>
                                    ${occurredOn}
                                </#if>
                            </td>
                            <td>${transaction.description()}</td>
                            <td class="text-end fw-semibold ${amountClass}">
                                <#if (amountNumber >= 0)>+</#if>${transaction.amount()?string["0.00"]}
                            </td>
                            <#if !isReadOnly>
                                <td class="text-end">
                                    <form method="post" action="/users/${user.id()?c}/transactions/${transaction.id()?c}/delete">
                                        <button type="submit"
                                                class="btn btn-sm btn-outline-danger"
                                                onclick="return confirm('Are you sure you want to remove this transaction?')"
                                                title="Remove transaction">
                                            &times;
                                        </button>
                                    </form>
                                </td>
                            </#if>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            <#else>
                <div class="text-center text-muted py-5">
                    No transactions found for this user.
                </div>
            </#if>
        </div>
        <#if displayedTotalPages gt 1>
            <div class="card-footer bg-white">
                <nav>
                    <ul class="pagination justify-content-end mb-0">
                        <#if transactionsPage.hasPrevious>
                            <li class="page-item">
                                <a class="page-link"
                                   href="/users/${user.id()?c}?page=${transactionsPage.number?int - 1}&size=${transactionsPage.size?int}">Previous</a>
                            </li>
                        <#else>
                            <li class="page-item disabled"><span class="page-link">Previous</span></li>
                        </#if>
                        <#if transactionsPage.hasNext>
                            <li class="page-item">
                                <a class="page-link"
                                   href="/users/${user.id()?c}?page=${transactionsPage.number?int + 1}&size=${transactionsPage.size?int}">Next</a>
                            </li>
                        <#else>
                            <li class="page-item disabled"><span class="page-link">Next</span></li>
                        </#if>
                    </ul>
                </nav>
            </div>
        </#if>
    </div>
</@layout.page>
