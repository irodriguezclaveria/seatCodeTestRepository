package ControllerMowers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ControllerMowers.domain.Plateau;


@Repository
public interface PlateauRepository extends JpaRepository<Plateau, String>
{

}
