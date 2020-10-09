package com.bank;

import com.bank.repository.utils.DBUtils;
import com.bank.utils.HttpServerUtils;
import org.h2.tools.RunScript;

import java.io.FileReader;

/**
 * Main class.
 */
public class Main {

    /**
     * Main method.
     *
     * @param args - scripts
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 0) {
            for (String arg : args) {
                RunScript.execute(DBUtils.getConnection(), new FileReader(arg));
            }
        } else {

            System.out.println("Starting server with example scripts");
        }
        HttpServerUtils.startService();
    }
    //HELLO from alex
}

