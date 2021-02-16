package org.mik.FirstMaven.export;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XMLElement {

    public String key() default "";
}
