package com.webapp.arvand.coreback.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "TBL_ROLE")
@Data
public class RoleEntity {
    @Id
    @Column(name = "FLD_ROLE_ID")
    private Long roleId;

    @Column(name = "FLD_ROLE_NAME",unique = true)
    private String roleName;
}
