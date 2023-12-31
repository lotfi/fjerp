package org.forma.repo;

import org.forma.model.Codif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodifRepository extends JpaRepository<Codif, String>
{
}