package org.mik.FirstMaven.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.mik.FirstMaven.export.XMLElement;
import org.mik.FirstMaven.export.XMLSerializable;
import org.mik.FirstMaven.export.json.JsonElement;
import org.mik.FirstMaven.export.json.JsonSerializable;

import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@XMLSerializable
@JsonSerializable
public class Person extends AbstractEntity{
    @XMLElement(key ="personalIdNumber")
    @JsonElement(key = "personalIdNumber")
    private String idNumber;

    @Override
    public boolean isValid(){
        return super.isValid() && idNumber!=null && !idNumber.isEmpty();
    }

    @Override
    public boolean isPerson(){
        return true;
    }

    public static class Builder extends AbstractEntity.Builder<Person>{
        public Builder(){
            super(new Person());
        }

        @Override
        protected AbstractEntity.Builder<Person> getBuilder() {
            return this;
        }

        @Override
        public Optional<Person> build() {
            return this.object.isValid()
                    ? Optional.of(this.object)
                    : Optional.empty();
        }

        public Builder idNumber(String id){
            this.object.setIdNumber(id);
            return this;
        }

        @Override
        public AbstractEntity.Builder<Person> taxNumber(String tax) {
            throw new UnsupportedOperationException("Person does not have a tax number");
        }
    }
}
