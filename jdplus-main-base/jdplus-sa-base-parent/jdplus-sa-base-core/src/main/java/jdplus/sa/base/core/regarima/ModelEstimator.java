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
package jdplus.sa.base.core.regarima;

import jdplus.toolkit.base.core.math.functions.levmar.LevenbergMarquardtMinimizer;
import jdplus.toolkit.base.core.regsarima.RegSarimaComputer;
import jdplus.toolkit.base.core.regsarima.regular.IModelEstimator;
import jdplus.toolkit.base.core.regsarima.regular.RegSarimaModelling;
import nbbrd.design.BuilderPattern;
import nbbrd.design.Development;

/**
 *
 * @author palatej
 */
@Development(status = Development.Status.Preliminary)
public class ModelEstimator implements IModelEstimator {

    public static Builder builder() {
        return new Builder();
    }

    @BuilderPattern(ModelEstimator.class)
    public static class Builder {

        private double epsilon = .00001;
        private RegSarimaComputer.StartingPoint starting = RegSarimaComputer.StartingPoint.Multiple;
        private boolean exactDerivatives = true;

        public Builder precision(double precision) {
            this.epsilon = precision;
            return this;
        }

        public Builder exactDerivatives(boolean exactDerivatives) {
            this.exactDerivatives = exactDerivatives;
            return this;
        }

        public Builder startingPoint(RegSarimaComputer.StartingPoint starting) {
            this.starting = starting;
            return this;
        }

        public ModelEstimator build() {
            return new ModelEstimator(epsilon, starting, exactDerivatives);
        }

    }
    private final double eps;
    private final RegSarimaComputer.StartingPoint starting;
    private final boolean exactDerivatives;

    private ModelEstimator(double eps, RegSarimaComputer.StartingPoint starting, boolean exactDerivatives) {
        this.eps = eps;
        this.starting = starting;
        this.exactDerivatives = exactDerivatives;
    }

    @Override
    public boolean estimate(RegSarimaModelling context) {

        try {
            RegSarimaComputer processor = RegSarimaComputer.builder()
                    .minimizer(LevenbergMarquardtMinimizer.builder())
                    .precision(eps)
                    .startingPoint(starting)
                    .computeExactFinalDerivatives(exactDerivatives)
                    .build();
            context.getDescription().freeArimaParameters();
            context.estimate(processor);
            return true;
        } catch (RuntimeException err) {
            return false;
        }
    }
}
