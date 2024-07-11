package dev.wolfieboy09.singularity.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import org.jetbrains.annotations.NotNull;

/**
 * Interface to declare that all fields, methods, and parameters in a class are {@link NotNull}
 */
@NotNull
@Nonnull
@TypeQualifierDefault({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.CLASS)
public @interface NothingNullByDefault {}