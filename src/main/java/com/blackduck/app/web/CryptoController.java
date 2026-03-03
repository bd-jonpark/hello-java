package com.blackduck.app.web;

import org.springframework.web.bind.annotation.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.*;

// A02:2021 - Cryptographic Failures (Weak Crypto)
// Test: GET /api/encrypt?data=hello
@RestController
public class CryptoController {

    @GetMapping("/api/encrypt")
    public Map<String, String> encrypt(@RequestParam String data) throws Exception {
        Map<String, String> result = new LinkedHashMap<>();

        // VULNERABLE: MD5 is broken for security purposes
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] md5Hash = md5.digest(data.getBytes());
        result.put("md5", HexFormat.of().formatHex(md5Hash));

        // VULNERABLE: DES is insecure (56-bit key)
        String desKey = "12345678";  // hardcoded + weak key
        SecretKeySpec keySpec = new SecretKeySpec(desKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        result.put("des", Base64.getEncoder().encodeToString(encrypted));

        return result;
    }
}
