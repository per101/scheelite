package org.headstar.scheelite;

import org.omg.SendingContext.RunTime;

/**
 * Created by per on 16/02/14.
 */
public class InvalidStateIdException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidStateIdException(String message) {
        super(message);
    }

}