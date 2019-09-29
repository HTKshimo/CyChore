package com.example.startspring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.util.Base64;

@RestController
@RequestMapping("/home")
public class home{

    @GetMapping
    public String getHome() throws IOException {
        System.out.println("user logged in");
        return "Blobfish!!!!";
    }

    @PostMapping
    public String testPost(HttpServletRequest request) throws IOException {
        System.out.println("Hellooooooooooooooooo");
        System.out.println("test"+request.getParameter("name") );

        String secretKey = "boooooooooom!!!!";
        String salt = "ssshhhhhhhhhhh!!!!";

        try
        {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec ssecretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, ssecretKey, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(request.getParameter("name").getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return "Hello World!";
    }
}


