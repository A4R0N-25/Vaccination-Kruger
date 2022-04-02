/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.kruger.vaccination.dao;

import ec.com.kruger.vaccination.model.VaccineType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bran-
 */
public interface VaccineTypeRepository extends JpaRepository<VaccineType, Integer>{
    
    Optional<VaccineType> findByName (String name);
    
}
