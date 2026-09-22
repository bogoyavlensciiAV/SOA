package jpchs.spring_app.dto.inner;


import jpchs.spring_app.enm.Errors;

public record ErrorItem (
    Errors code,
    String message
) {
}
