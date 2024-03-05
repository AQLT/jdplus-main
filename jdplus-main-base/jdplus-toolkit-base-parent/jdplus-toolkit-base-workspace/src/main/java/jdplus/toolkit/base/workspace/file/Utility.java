/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdplus.toolkit.base.workspace.file;

import internal.toolkit.base.workspace.file.xml.util.XmlCalendars;
import internal.toolkit.base.workspace.file.xml.util.XmlTsVariables;
import java.io.File;
import java.io.IOException;
import jdplus.toolkit.base.api.timeseries.calendars.CalendarManager;
import jdplus.toolkit.base.api.timeseries.regression.TsDataSuppliers;
import nbbrd.io.xml.Xml;
import nbbrd.io.xml.bind.Jaxb;

/**
 *
 * @author palatej
 */
@lombok.experimental.UtilityClass
public class Utility {

    private static final Xml.Parser<CalendarManager> CAL_R = Jaxb.Parser.of(XmlCalendars.class).andThen(x -> x.create());
    private static final Xml.Parser<TsDataSuppliers> VARS_R = Jaxb.Parser.of(XmlTsVariables.class).andThen(x -> x.create());
    private static final Xml.Formatter<CalendarManager> CAL_W = Jaxb.Formatter.of(XmlCalendars.class)
            .withFormatted(true)
            .compose(v -> {
                XmlCalendars x = new XmlCalendars();
                x.copy(v);
                return x;
            });
    private static final Xml.Formatter<TsDataSuppliers> VARS_W = Jaxb.Formatter.of(XmlTsVariables.class)
            .withFormatted(true)
            .compose(v -> {
                XmlTsVariables x = new XmlTsVariables();
                x.copy(v);
                return x;
            });

    public CalendarManager readCalendars(String sfile) throws IOException {
        File file = new File(sfile);
        return CAL_R.parseFile(file);
    }

    public TsDataSuppliers readData(String sfile) throws IOException {
        File file = new File(sfile);
        return VARS_R.parseFile(file);
    }

    public void writeCalendars(CalendarManager cal, String sfile) throws IOException {
        File file = new File(sfile);
        CAL_W.formatFile(cal, file);
    }

    public void writeData(TsDataSuppliers data, String sfile) throws IOException {
        File file = new File(sfile);
        VARS_W.formatFile(data, file);
    }

}
