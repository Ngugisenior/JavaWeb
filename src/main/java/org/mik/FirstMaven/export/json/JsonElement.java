package org.mik.FirstMaven.export.json;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonElement {
    public String key() default "";
}
