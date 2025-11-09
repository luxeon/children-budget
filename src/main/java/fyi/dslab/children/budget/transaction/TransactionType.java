package fyi.dslab.children.budget.transaction;

import java.math.BigDecimal;

public enum TransactionType {
    DEBIT {
        @Override
        public BigDecimal applySign(BigDecimal amount) {
            return amount.negate();
        }
    },
    CREDIT {
        @Override
        public BigDecimal applySign(BigDecimal amount) {
            return amount;
        }
    };

    public abstract BigDecimal applySign(BigDecimal amount);
}
