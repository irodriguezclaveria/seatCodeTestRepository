package ControllerMowers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ControllerMowers.domain.Position;


@Repository
public interface PositionRepository extends JpaRepository<Position, Long>
{

}
