package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.model.Book;

import java.util.List;

@Mapper
public interface BookMapper {

    int insert(Book book);

    int deleteById(@Param("id") long id);

    int updateById(Book book);

    List<Book> listAll();
}
