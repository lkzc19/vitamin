package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.Foo;

@Mapper
public interface FooMapper {

    int insert(Foo foo);
}
