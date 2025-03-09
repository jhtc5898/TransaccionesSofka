package com.sofka.movimientos.Utils;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class AccountNumberGenerator {


    public static String generarNumeroCuenta() {

        SecureRandom secureRandom = new SecureRandom();
        int numero = secureRandom.nextInt(90000);
        numero += 10000;
        return String.format("%05d", numero);
    }
}
