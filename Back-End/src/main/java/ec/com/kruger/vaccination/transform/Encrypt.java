/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.kruger.vaccination.transform;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author bran-
 */
@Slf4j
public class Encrypt {

    public static String encryptPassword(String password) throws Exception {
            SecureRandom random = new SecureRandom();
            byte[] salt = {-22, -35, -117, 88, 27, 111, -25, 115, -55, 4, 67, -31, 95, -66, 60, -42};
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return new String(factory.generateSecret(spec).getEncoded());
    }

}
