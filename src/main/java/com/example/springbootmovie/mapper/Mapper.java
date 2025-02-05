package com.example.springbootmovie.mapper;

public interface Mapper<E,D> {

    E toEntity(D d);

    D toDto(E e);

}
