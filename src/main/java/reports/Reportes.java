package reports;

import bga.com.fe.exceptions.FeException;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 *
 * @author bgarita, 25/12/2022
 */

/*
This class uses the forward slash instead of back slash regardless the OS
because:
1) Windows understands it
2) It is needed for parameters in page URLs
 */
public class Reportes {

    private JasperReport masterReport;

    private static final String REPORTS_BASE_DIR = "reports";
    private static final String PDF_FILES = REPORTS_BASE_DIR + "/pdf";
    public static final String PDF = "pdf";
    public static final String XLSX = "xlsx";

    private final Connection conn;

    /**
     *
     * @param conn
     */
    public Reportes(Connection conn) {
        this.masterReport = null;
        setWorkFolder(REPORTS_BASE_DIR);
        setWorkFolder(PDF_FILES);
        this.conn = conn;
    } // end Constructor

    public JasperReport getMasterReport() {
        return masterReport;
    }

    public void setMasterReport(JasperReport masterReport) {
        this.masterReport = masterReport;
    }

    public String getReportsBaseDir() {
        return REPORTS_BASE_DIR;
    }

    private void setWorkFolder(String path) {
        File file = new File(path);
        if (file.exists()) {
            return;
        }
        file.mkdir();
    }

    public String createDocument(String jasperForm, String format, int year, int month) throws FeException{
        File f = new File(REPORTS_BASE_DIR + "/" + jasperForm);
        File pdf = new File(PDF_FILES);
        String archivo = pdf + "/Doc";

        try {
            if (!f.exists() || f.isDirectory()) {
                throw new FeException(this.getClass().getName(), "createDocument()",
                        """
                        No encuentro el archivo de impresi\u00f3n. 
                        Deber\u00eda estar en: """ + f.getAbsolutePath());
            } // end if

            masterReport = (JasperReport) JRLoader.loadObject(f);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("p_year", year);
            parameters.put("p_month", month);

            JasperPrint jasperPrint
                    = JasperFillManager.fillReport(f.getAbsolutePath(), parameters, this.conn);

            if (format.equals(PDF)) {
                // Export to PDF
                String pdfFile = archivo + ".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile);
                archivo = pdfFile;
            } else if (format.equals(XLSX)) {
                // Export to xlsx
                String xlsxFile = archivo + ".xlsx";
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsxFile));
                SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                configuration.setOnePagePerSheet(true);
                configuration.setFreezeRow(7);
                exporter.setConfiguration(configuration);
                exporter.exportReport();
                archivo = xlsxFile;
            }

        } catch (FeException | JRException ex) {
            throw new FeException(this.getClass().getName(), "createDocument()",
                    ex.getMessage());
        } // end try-catch
        return archivo.replace("\\","/");
    }
}
