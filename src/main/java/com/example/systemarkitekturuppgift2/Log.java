package com.example.systemarkitekturuppgift2;

import jakarta.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@InterceptorBinding
@Target({TYPE, METHOD}) // Får användas överallt typ.
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
}
