package org.mik.FirstMaven.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Company extends AbstractEntity{
    private String taxNumber;

    public boolean isValid(){
        return super.isValid() && taxNumber!=null && !taxNumber.isEmpty();
    }
    @Override
    public boolean isCompany(){
        return true;
    }
    public static class Builder extends AbstractEntity.Builder<Company>{
        public Builder(){
            super(new Company());
        }

        @Override
        protected AbstractEntity.Builder<Company> getBuilder() {
            return this;
        }

        @Override
        public Optional<Company> build() {
            return this.object.isValid()
            ? Optional.of(this.object)
            : Optional.empty();
        }

        @Override
        public AbstractEntity.Builder<Company> idNumber(String id) {
            throw new UnsupportedOperationException("A company does not have id number");
        }

        @Override
        public AbstractEntity.Builder<Company> taxNumber(String tax) {
            this.object.setTaxNumber(tax);
            return this;
        }
    }
}
