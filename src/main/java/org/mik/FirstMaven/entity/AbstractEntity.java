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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@XMLSerializable
@JsonSerializable
public abstract class AbstractEntity {
    @JsonElement
    @XMLElement
    private Long id;
    @JsonElement
    @XMLElement
    private String name;
    @JsonElement
    @XMLElement
    private String address;
    @JsonElement
    @XMLElement
    private Long amount = 0L;


    public boolean isValid(){
        return id!=null && name!=null && !name.isEmpty() && address!=null && !address.isEmpty();
    }

    public boolean isPerson(){
        return false;
    }

    public boolean isCompany(){
        return false;
    }


    public static abstract class Builder<T extends AbstractEntity>{
        protected T object;
        protected abstract Builder<T> getBuilder();

        public  abstract Optional<T> build();
        public abstract Builder<T> idNumber(String id);
        public abstract Builder<T> taxNumber(String tax);

        protected Builder(T o){
            this.object = o;
        }

        public Builder<T> id (Long id){
            this.object.setId(id);
            return getBuilder();
        }
        public Builder<T> name (String n){
            this.object.setName(n);
            return getBuilder();
        }

        public Builder<T> address (String a){
            this.object.setAddress(a);
            return getBuilder();
        }

        public Builder<T> amount (Long a){
            this.object.setAmount(a);
            return getBuilder();
        }
    }
}
