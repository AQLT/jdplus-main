/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.desktop.ui.processing;

import demetra.processing.ProcDocument;
import javax.swing.JComponent;

/**
 *
 * @author Jean Palate
 * @param <D>
 */
public class ContextualTableUI<D extends ProcDocument> implements ItemUI<ContextualIds<D>> {

    private final boolean fullNames;

    public ContextualTableUI(boolean fullNames) {
         this.fullNames = fullNames;
    }

    @Override
    public JComponent getView(ContextualIds<D> info) {

        return TsViewToolkit.getGrid(info.makeAllTs(fullNames));
    }

}
