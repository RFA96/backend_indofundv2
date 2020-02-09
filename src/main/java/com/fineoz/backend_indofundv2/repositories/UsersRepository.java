package com.fineoz.backend_indofundv2.repositories;

import com.fineoz.backend_indofundv2.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long>
{

}
