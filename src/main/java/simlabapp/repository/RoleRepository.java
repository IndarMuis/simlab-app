package simlabapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simlabapp.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
