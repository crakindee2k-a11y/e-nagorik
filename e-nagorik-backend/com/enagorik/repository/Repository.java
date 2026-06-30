package com.enagorik.repository;

import java.util.List;
import java.util.Optional;

/**
 * ISP: a small, generic contract — callers depend only on the
 * operations they need, never on storage details.
 */
public interface Repository<T, ID>
{
    void save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
}