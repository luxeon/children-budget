<#import "layout.ftlh" as layout>
<@layout.page title=user.name()>
    <div class="mb-3">
        <a href="/users" class="text-decoration-none">&larr; Back to users</a>
    </div>

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
            </div>
        </div>
    </div>

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
                    </tr>
                    </thead>
                    <tbody>
                    <#list transactions as transaction>
                        <#assign amountNumber = transaction.amount()?double>
                        <#assign amountClass = (amountNumber >= 0)?then("text-success", "text-danger")>
                        <tr>
                            <td class="text-muted">${transaction.occurredOn()?string["MMM d, yyyy"]}</td>
                            <td>${transaction.description()}</td>
                            <td class="text-end fw-semibold ${amountClass}">
                                <#if amountNumber >= 0>+</#if>${transaction.amount()?string["0.00"]}
                            </td>
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
