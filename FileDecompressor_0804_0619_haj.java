// 代码生成时间: 2025-08-04 06:19:53
package com.example.dropwizard.util;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.Application;
# NOTE: 重要实现细节
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Namespace;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamProvider;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Path("/decompress")
public class FileDecompressor {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileDecompressor.class);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response decompressFile(@FormDataParam("file") InputStream uploadedInputStream,
                                  @FormDataParam("file") FormDataContentDisposition fileMetaData) {
        try {
            // Get the file name from the metadata
            String fileName = fileMetaData.getFileName();
            LOGGER.info("Decompressing file: {}", fileName);

            // Create a temporary file to hold the uploaded file
            File tempFile = File.createTempFile("temp", null);
            tempFile.deleteOnExit();

            // Write the uploaded file to the temporary file
            Files.copy(uploadedInputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Determine the type of the file and decompress accordingly
            if (fileName.endsWith(".zip")) {
                decompressZipFile(tempFile);
            } else if (fileName.endsWith(".tar.bz2")) {
                decompressTarBz2File(tempFile);
            } else {
# 增强安全性
                throw new UnsupportedOperationException("Unsupported file type: " + fileName);
            }

            return Response.status(Response.Status.OK).entity("Decompression complete.").build();
        } catch (IOException | ArchiveException e) {
            LOGGER.error("Error decompressing file", e);
# 扩展功能模块
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error decompressing file").build();
        }
    }

    private void decompressZipFile(File zipFile) throws IOException {
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry = zipIn.getNextEntry();
# FIXME: 处理边界情况
            // Create a buffer to hold the data
            byte[] buffer = new byte[1024];
            while (entry != null) {
                String fileName = entry.getName();
# 增强安全性
                File outputFile = new File(zipFile.getParent(), fileName);
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile))) {
                    int location;
                    while ((location = zipIn.read(buffer)) > 0) {
                        bos.write(buffer, 0, location);
                    }
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }

    private void decompressTarBz2File(File tarBz2File) throws IOException, ArchiveException {
        try (TarArchiveInputStream tarIn = new TarArchiveInputStream(
                new BZip2CompressorInputStream(new FileInputStream(tarBz2File)),
                ArchiveStreamProvider.INSTANCE)) {
            TarArchiveEntry entry;
            while ((entry = tarIn.getNextTarEntry()) != null) {
                String fileName = entry.getName();
                File outputFile = new File(tarBz2File.getParent(), fileName);
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile))) {
                    byte[] buffer = new byte[2048];
                    int len;
                    while ((len = tarIn.read(buffer)) != -1) {
                        bos.write(buffer, 0, len);
                    }
                }
            }
        }
    }
}
