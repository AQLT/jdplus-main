/*
 * Copyright 2016 National Bank of Belgium
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved 
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and 
 * limitations under the Licence.
 */
package _util.tsproviders;

import demetra.tsprovider.DataSet;
import demetra.tsprovider.DataSource;
import demetra.timeseries.TsInformationType;
import demetra.tsprovider.stream.DataSetTs;
import demetra.tsprovider.util.DataSourcePreconditions;
import java.io.IOException;
import java.util.stream.Stream;
import demetra.tsprovider.stream.HasTsStream;

/**
 *
 * @author Philippe Charles
 */
public final class FailingTsCursorSupport implements HasTsStream {

    private final String providerName;
    private final String message;

    public FailingTsCursorSupport(String providerName, String message) {
        this.providerName = providerName;
        this.message = message;
    }

    @Override
    public Stream<DataSetTs> getData(DataSource dataSource, TsInformationType type) throws IllegalArgumentException, IOException {
        DataSourcePreconditions.checkProvider(providerName, dataSource);
        throw new IOException(message);
    }

    @Override
    public Stream<DataSetTs> getData(DataSet dataSet, TsInformationType type) throws IllegalArgumentException, IOException {
        DataSourcePreconditions.checkProvider(providerName, dataSet);
        throw new IOException(message);
    }
}
