package jpchs.spring_app.converter;

public interface StringConverter<T> {

    T convert(String source);
}
