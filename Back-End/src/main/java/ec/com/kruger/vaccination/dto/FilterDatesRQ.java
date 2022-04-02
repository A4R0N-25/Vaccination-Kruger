/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.kruger.vaccination.dto;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author bran-
 */
@Data
public class FilterDatesRQ {
    
    private Date start;
    private Date end;
    
}
