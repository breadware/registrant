package br.com.breadware.model.mapper;

public interface TwoWayMapper<I, O> {

    O mapTo(I input);

    I mapFrom(O output);
}
