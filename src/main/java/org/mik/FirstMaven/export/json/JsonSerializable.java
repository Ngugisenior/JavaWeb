package org.mik.FirstMaven.export.json;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonSerializable {
    public String key() default "";
}
