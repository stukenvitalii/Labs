import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.*;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XMLtoPDFReporter {
    static String outputFilePath = "./reports/report.pdf";
    private static final Logger logger = LogManager.getLogger("mainLogger");


    public static void createReport(String dataFilePath) {
        try {
            JRXmlDataSource dataSource = new JRXmlDataSource(dataFilePath, "/groups/group");
            JasperReport jasperReport = JasperCompileManager.compileReport("./jrxml/report4.jrxml");
            JasperPrint print = JasperFillManager.fillReport(jasperReport, null, dataSource);
            if (outputFilePath.toLowerCase().endsWith("pdf")) {
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(print));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFilePath));
                exporter.setConfiguration(new SimplePdfReportConfiguration());
                exporter.setConfiguration(new SimplePdfExporterConfiguration());
                exporter.exportReport();
            } else {
                HtmlExporter exporter = new HtmlExporter();
                exporter.setExporterInput(new SimpleExporterInput(print));
                exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputFilePath));
                exporter.setConfiguration(new SimpleHtmlReportConfiguration());
                exporter.setConfiguration(new SimpleHtmlExporterConfiguration());
                exporter.exportReport();
            }
            JasperViewer.viewReport(print, false);
            logger.info("Report {} successfully created", outputFilePath);
        } catch (JRException e) {
            logger.error(e.getMessage(),e);
        }
    }
}
