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

}
