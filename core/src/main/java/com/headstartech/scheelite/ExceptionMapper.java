package com.headstartech.scheelite;

/**
 * Maps an {@link java.lang.Exception} to a {@link java.lang.RuntimeException}.
 */
public interface ExceptionMapper {

    RuntimeException mapException(Exception e);
}
