package de.rwth.idsg.steve.web.dto.ocpp15;

import de.rwth.idsg.steve.web.dto.common.MultipleChargePointSelect;
import lombok.Getter;
import lombok.Setter;
import ocpp.cp._2012._06.ResetType;

import javax.validation.constraints.NotNull;

/**
 * @author Sevket Goekay <goekay@dbis.rwth-aachen.de>
 * @since 01.01.2015
 */
@Getter
@Setter
public class ResetParams extends MultipleChargePointSelect {

    @NotNull(message = "Reset Type is required")
    private ResetType resetType;
}
