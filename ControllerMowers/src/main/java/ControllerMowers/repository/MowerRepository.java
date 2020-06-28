package ControllerMowers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ControllerMowers.domain.Mower;


@Repository
public interface MowerRepository extends JpaRepository<Mower, Long>
{
    @Query("select l from Mower l where lower(l.status) = lower(:status)")
    List<Mower> findMowersByStatus(@Param("status") String statusMower);
}
