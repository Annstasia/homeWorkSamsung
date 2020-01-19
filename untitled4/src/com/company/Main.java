package com.company;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.CRC32;
import java.io.File;

public class Main {
    // хэш функция - это одностороннее преобразование произвольного числа байт
    // в число фиксированного размера (например, для CRC32 - это 32 бита, для MD5 - 128)
    // невозможно получить исходный набор байт по хэшу кроме как перебрать все
    // возможные комбинации (полный перебор, brute force)

    public static void main(String[] args) throws IOException { // указывается, что возникает исключение
        long hash1 = 0x0B91A851BL; // это CRC32 хэш пароля apple123
        long hash2 = 0x0BA02B6E1L; // хэш CRC32 пароля, который нужно угадать
        // пароль состоит из слова (из файла), за котрым следует число от 1 до 9999
        // примеры паролей: master111 shadow1234
        // посчитать хэш можно и  в браузере http://www.sunshine2k.de/coding/javascript/crc/crc_js.html

        // часть 1. проверяем, как пользоваться CRC32
        // используем простой алгоритм CRC32
        CRC32 crc32 = new  CRC32();
        String plaintext = "apple123";
        crc32.update(plaintext.getBytes());
        long checksum = crc32.getValue();
//        System.out.println(checksum);
//        System.out.println(Long.toHexString(checksum));
        crc32.reset();
        crc32.update(plaintext.getBytes());
        checksum = crc32.getValue();
//        System.out.println(Long.toHexString(checksum));
//
//        // часть 2. считываем список слов из файла
//        // https://github.com/danielmiessler/SecLists/blob/master/Passwords/Common-Credentials/10k-most-common.txt
//        // кстати, могу рекомендовать блог автора, пишет про инф. безопасность
//        // https://danielmiessler.com/blog/
//        // использовать класс Scanner и его методы hasNextLine(), nextLine()
        File f = new File("/home/stasy/IdeaProjects/untitled4/src/com/company/10k-most-common.txt");
        Scanner sc = new Scanner(f);
        boolean shouldBreak = false;
        while (sc.hasNextLine()){
            plaintext = sc.nextLine();
            for (int i1 = 0; i1 < 10; i1++){
                for (int i2 = 0; i2 < 10; i2++) {
                    for (int i3 = 0; i3 < 10; i3++) {
                        for (int i4 = 0; i4 < 10; i4++) {


                            crc32.update((plaintext + i1 + i2 + i3 + i4).getBytes());
                            checksum = crc32.getValue();
                            if (checksum == hash2) {
                                System.out.println(plaintext + i1 + i2 + i3 + i4);

                                shouldBreak = true;
                               break;
                            }

                            crc32.reset();
                        }
                        if (shouldBreak) break;
                    }
                    if (shouldBreak) break;
                }
                if (shouldBreak) break;
            }
            if (shouldBreak) break;
        }
        sc.close();


    }
}