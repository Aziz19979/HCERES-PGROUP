package org.centrale.hceres.items;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.Data;

/**
 * TODO replace admin table to role table having:
 * 1 -> ADMIN
 * 2 -> USER
 *
 * and add column role_id in researcher table
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Long id;
    private String name;
}
