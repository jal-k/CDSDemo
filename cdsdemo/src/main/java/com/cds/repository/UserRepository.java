/**
 * 
 */
package com.cds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cds.model.User;

/**
 * @author Jalpa.Kholiya
 * This is an example of SpringBoot with CRUD
 * interface UserRepository - extending CrudRepository - Includes one example of custom query
 */

public interface UserRepository extends CrudRepository<User, Integer>{

	@Query(value="SELECT * FROM cds.cds_users cu WHERE cu.salary between ?1 and ?2", nativeQuery = true)
    public List<User> findUsersForSalaryRange( double salarylow, double salaryhigh);

}
