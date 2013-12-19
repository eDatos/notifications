package org.siemac.metamac.rest.notifications.v1_0.domain.enume;

import java.io.Serializable;

/**
 * Enum for MetamacRolesEnum
 */
public enum MetamacRolesEnum implements Serializable {
    ADMINISTRADOR,
    TECNICO_SISTEMA_INDICADORES,
    TECNICO_APOYO_PRODUCCION,
    TECNICO_PRODUCCION,
    JEFE_PRODUCCION,
    TECNICO_APOYO_DIFUSION,
    TECNICO_DIFUSION,
    TECNICO_PLANIFICACION,
    TECNICO_APOYO_PLANIFICACION,
    JEFE_NORMALIZACION,
    TECNICO_NORMALIZACION,
    TECNICO_APOYO_NORMALIZACION;

    /**
     */
    private MetamacRolesEnum() {
    }

    public String getName() {
        return name();
    }
}
