/*
 * Copyright 2022 National Bank of Belgium
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved 
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and 
 * limitations under the Licence.
 */
package demetra.desktop.tramoseats.diagnostics.impl;

import jdplus.sa.desktop.plugin.diagnostics.ResidualTradingDaysDiagnosticsBuddy;
import demetra.desktop.tramoseats.diagnostics.TramoSeatsDiagnosticsFactoryBuddy;
import jdplus.sa.base.core.diagnostics.ResidualTradingDaysDiagnosticsConfiguration;
import jdplus.sa.base.core.diagnostics.ResidualTradingDaysDiagnosticsFactory;
import jdplus.tramoseats.base.core.tramoseats.TramoSeatsResults;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author palatej
 */
@ServiceProvider(service = TramoSeatsDiagnosticsFactoryBuddy.class, position = 1230)
public final class TramoSeatsResidualTradingDaysDiagnosticsBuddy extends ResidualTradingDaysDiagnosticsBuddy implements TramoSeatsDiagnosticsFactoryBuddy<ResidualTradingDaysDiagnosticsConfiguration> {

    public TramoSeatsResidualTradingDaysDiagnosticsBuddy(){
        this.setActiveDiagnosticsConfiguration(ResidualTradingDaysDiagnosticsConfiguration.getDefault());
    }
    
    @Override
    public ResidualTradingDaysDiagnosticsFactory createFactory() {
        return new ResidualTradingDaysDiagnosticsFactory<>(this.getActiveDiagnosticsConfiguration(),
                (TramoSeatsResults r) -> r.getDiagnostics().getGenericDiagnostics().residualTradingDaysTests());
    }

}
