package org.siemac.metamac.rest.notifications.v1_0.domain.enume;

import java.io.Serializable;

/**
 * Enum for MetamacApplicationsEnum
 */
public enum MetamacApplicationsEnum implements Serializable {
    GESTOR_INDICADORES,
    GESTOR_RECURSOS_ESTRUCTURALES,
    GESTOR_RECURSOS_ESTADISTICOS,
    GESTOR_NOTIFICACIONES,
    GESTOR_AYUDAS,
    GESTOR_METADATOS_COMUNES,
    GESTOR_ACCESOS,
    GESTOR_OPERACIONES;

    /**
     */
    private MetamacApplicationsEnum() {
    }

    public String getName() {
        return name();
    }
}