package kg.transparency.dt4ce.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.SneakyThrows;
import java.io.ByteArrayOutputStream;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class QRCodeGenerator {
    @SneakyThrows
    public static ResponseEntity<byte[]> generateQR(Long id){
        String data = "{\"initiativeId\":" + id + "}";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int width = 400;
        int height = 400;

        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        byte[] qrCodeBytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(qrCodeBytes, headers, HttpStatus.OK);
    }
}
