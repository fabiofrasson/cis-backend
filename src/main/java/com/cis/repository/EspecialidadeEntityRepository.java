package com.cis.repository;

public interface EspecialidadeEntityRepository extends JpaRepository<EspecialidadeEntity, Long> {

    Optional<EspecialidadeEntity> findByEspecialidadeNameIgnoreCase(String name);
}