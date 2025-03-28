import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.zip.*;

public class BaixarECompactar {
    public static void main(String[] args) {
        try {
            // URLs dos arquivos PDF para download
            String[] urlsArquivos = {
                "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf",
                "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf"
            };

            // Define o diretório para salvar os arquivos baixados
            String dirDownload = "downloads";
            Files.createDirectories(Paths.get(dirDownload));

            // Baixar os arquivos
            for (String urlArquivo : urlsArquivos) {
                try {
                    baixarArquivo(urlArquivo, dirDownload);
                } catch (FileNotFoundException e) {
                    System.err.println("Arquivo não encontrado: " + urlArquivo);
                }
            }

            // Compactar os arquivos baixados em ZIP
            String nomeArquivoZip = "anexos.zip";
            compactarArquivos(dirDownload, nomeArquivoZip);

            System.out.println("Download e compactação concluídos com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void baixarArquivo(String urlArquivo, String dirDownload) throws IOException {
        URL url = new URL(urlArquivo);
        String nomeArquivo = urlArquivo.substring(urlArquivo.lastIndexOf('/') + 1);
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(dirDownload, nomeArquivo), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void compactarArquivos(String dirOrigem, String nomeArquivoZip) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(nomeArquivoZip);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            Path caminhoOrigem = Paths.get(dirOrigem);
            Files.walk(caminhoOrigem)
                .filter(path -> !Files.isDirectory(path))
                .forEach(path -> {
                    ZipEntry zipEntry = new ZipEntry(caminhoOrigem.relativize(path).toString());
                    try {
                        zos.putNextEntry(zipEntry);
                        Files.copy(path, zos);
                        zos.closeEntry();
                    } catch (IOException e) {
                        System.err.println("Falha ao compactar o arquivo: " + path);
                        e.printStackTrace();
                    }
                });
        }
    }
}