/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.kruger.vaccination.dao;

import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bran-
 */
public interface UserRespository extends JpaRepository<User, Integer>{
    
    Optional<User> findByUsername (String username);
    Optional<User> findByEmployee (Employee employee);
}
