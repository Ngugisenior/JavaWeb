package org.mik.FirstMaven.export;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface XMLSerializable {
    public String key() default "";
}
