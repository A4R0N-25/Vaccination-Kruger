/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.kruger.vaccination.dao;

import ec.com.kruger.vaccination.model.VaccinationDetail;
import ec.com.kruger.vaccination.model.VaccineType;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bran-
 */
public interface VaccinationDetailRepository extends JpaRepository<VaccinationDetail, Integer>{
    
    List<VaccinationDetail> findByVaccineType (VaccineType type);
    
    List<VaccinationDetail> findByVaccinationDateBetween (Date start, Date end);
    
}
