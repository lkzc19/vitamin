package org.example.repository;

import org.example.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseModelRepository<T extends BaseModel> extends JpaRepository<T, Long> {

    @Override
    @NonNull
    @Query("select e from #{#entityName} e where e.deletedAt is null")
    List<T> findAll();

    @Override
    @NonNull
    @Query("select e from #{#entityName} e where e.deletedAt is null and e.id = ?1")
    Optional<T> findById(@NonNull Long id);

    @Query("select e from #{#entityName} e where e.deletedAt is not null")
    List<T> findByDeletedAt();

    /**
     * 对于MySQL和MariaDB，你可以使用NOW()函数。
     * 对于PostgreSQL，你可以使用CURRENT_TIMESTAMP。
     * 对于SQL Server，你可以使用GETDATE()。
     * 对于Oracle，你可以使用SYSTIMESTAMP。
     */
    @Override
    @Modifying
    @Query("update #{#entityName} e set e.deletedAt = CURRENT_TIMESTAMP where e.id = ?1")
    void deleteById(@NonNull Long id);
}