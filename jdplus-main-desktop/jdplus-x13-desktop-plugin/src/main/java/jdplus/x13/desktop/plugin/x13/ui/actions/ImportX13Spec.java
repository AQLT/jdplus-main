/*
 * Copyright 2016 National Bank of Belgium
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved 
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
package jdplus.x13.desktop.plugin.x13.ui.actions;

import jdplus.toolkit.base.api.information.InformationSet;
import jdplus.toolkit.base.xml.information.XmlInformationSet;
import jdplus.toolkit.desktop.plugin.Config;
import jdplus.toolkit.desktop.plugin.interchange.Importable;
import jdplus.toolkit.desktop.plugin.interchange.InterchangeManager;
import jdplus.toolkit.desktop.plugin.nodes.SingleNodeAction;
import jdplus.toolkit.desktop.plugin.workspace.WorkspaceFactory;
import jdplus.toolkit.desktop.plugin.workspace.WorkspaceItem;
import jdplus.x13.base.api.x13.X13Spec;
import jdplus.x13.base.information.X13SpecMapping;
import jdplus.x13.desktop.plugin.x13.documents.X13SpecManager;
import nbbrd.design.ClassNameConstant;
import nbbrd.io.text.Parser;
import nbbrd.io.xml.bind.Jaxb;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.Presenter;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Action on Tramo specification workspace node allowing the import
 *
 * @author Mats Maggi
 */
@ActionID(category = "Edit", id = ImportX13Spec.ID)
@ActionRegistration(displayName = "#CTL_ImportX13Spec", lazy = false)
@ActionReferences({
    @ActionReference(path = X13SpecManager.PATH, position = 1000)
})
@Messages("CTL_ImportX13Spec=Import from")
public class ImportX13Spec extends SingleNodeAction<Node> implements Presenter.Popup {

    @ClassNameConstant
    public static final String ID = "jdplus.x13.desktop.plugin.x13.ui.actions.ImportX13Spec";

    public ImportX13Spec() {
        super(Node.class);
    }

    @Override
    protected void performAction(Node activatedNode) {

    }

    @Override
    public JMenuItem getPopupPresenter() {
        JMenuItem result = InterchangeManager.get().newImportMenu(getImportables());
        result.setText(Bundle.CTL_ImportX13Spec());
        return result;
    }

    @Override
    protected boolean enable(Node activatedNode) {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    private List<Importable> getImportables() {
        return Collections.singletonList(new Importable() {

            @Override
            public String getDomain() {
                return X13Spec.class.getName();
            }

            @Override
            public void importConfig(Config config) throws IllegalArgumentException {
                X13Spec spec = fromConfig(config);
                if (spec != null) {
                    WorkspaceItem<X13Spec> ndoc = WorkspaceItem.newItem(X13SpecManager.ID, config.getName(), spec);
                    WorkspaceFactory.getInstance().getActiveWorkspace().add(ndoc);
                }
            }
        });
    }

    private static X13Spec fromConfig(@NonNull Config config) throws IllegalArgumentException {
        if (!X13Spec.class.getName().equals(config.getDomain())) {
            throw new IllegalArgumentException("Invalid config");
        }
        return Optional.ofNullable(config.getParameter("specification"))
                .map(INFORMATIONPARSER::parse)
                .map(X13SpecMapping.SERIALIZER_V3::read)
                .orElse(null);
    }
    
    private static final Parser<InformationSet> INFORMATIONPARSER = Jaxb.Parser.of(XmlInformationSet.class).asParser().andThen(XmlInformationSet::create);
}
