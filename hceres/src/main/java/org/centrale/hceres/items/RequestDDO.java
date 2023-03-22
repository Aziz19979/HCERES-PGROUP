package org.centrale.hceres.items;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDDO {
    
    Integer researcherId ;
    String ScientificExpertiseTypeName ;
    String ScientificExpertiseDescription ;
    Date ScientificExpertiseStartDate ;
    Date ScientificExpertiseEndDate;
}
