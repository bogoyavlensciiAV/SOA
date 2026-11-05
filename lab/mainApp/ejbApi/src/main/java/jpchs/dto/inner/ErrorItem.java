package jpchs.dto.inner;

import jpchs.enm.Errors;

public record ErrorItem (
    Errors code,
    String message
) {
}
