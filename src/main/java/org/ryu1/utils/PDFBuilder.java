package org.ryu1.utils;

import java.io.File;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class PDFBuilder {
	private static Log log = LogFactory.getLog(PDFBuilder.class);

	/**
	 * �w�肵��Path��PDF���o�͂���B
	 * 
	 * @author R.Ishitsuka
	 * @since 2010/10/23
	 * @param pdfPath
	 *            PDF�i�[PATH
	 * @param jasperPath
	 *            Jasper�t�@�C����PATH
	 * @param params
	 *            �p�����[�^
	 * @param connection
	 *            Connection
	 */
	public static void create(final String pdfPath, final String jasperPath, final Map<String, Object> params, final Connection connection) {
		try {
			File path = new File(pdfPath);
			if (!path.getParentFile().exists()) {
				path.getParentFile().mkdirs();
			}

			File reportFile = new File(jasperPath);

			if (!reportFile.exists()) {
				log.error(jasperPath + "�t�@�C�������݂��Ȃ��B");
				throw new RuntimeException(jasperPath + "�t�@�C�������݂��Ȃ��B");
			}

			JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), params, connection);

			// Exporter�̐���
			JRPdfExporter exporter = new JRPdfExporter();

			// �o�̓I�u�W�F�N�g�̐ݒ�
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);

			// �o�͐�̐ݒ�
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, pdfPath);

			// �o�͂̎��s
			exporter.exportReport();
		} catch (JRException e) {
			log.error("PDF�o�͎��s", e);
			throw new RuntimeException(e);
		}
	}
}
