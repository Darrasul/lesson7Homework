package com.buzas.lesson7homework.entities.students;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional(Transactional.TxType.SUPPORTS)
public interface StudentRepository extends JpaRepository<Student, Long>, QuerydslPredicateExecutor<Student> {
    Optional<Student> findById(Long id);

    @Query(value = """
    select * from students s
""", countQuery = """
    select count(*) from students s
""",
            nativeQuery = true)
    Page<Student> findByPage(Pageable pageable);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
    insert into students(name, age)
    values (:name, :age)
""",
            nativeQuery = true)
    void addStudent(String name, int age);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
    update students s
    set
    s.name = :name,
    s.age = :age
    where s.id = :id
""",
            nativeQuery = true)
    void updateStudent(Long id, String name, int age);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
    delete from students s
    where s.id = :id
""",
            nativeQuery = true)
    void deleteById(Long id);
}
