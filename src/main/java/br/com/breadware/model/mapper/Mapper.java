package br.com.breadware.model.mapper;

public interface Mapper<I, O> {

    O map(I input);
}
