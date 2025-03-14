/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scraper;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 *
 * @author der_u
 */
public class removeaccents {
    public static String removeAccents(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        // Use Normalizer to decompose and remove diacritical marks
        String normalized = Normalizer.normalize(word, Normalizer.Form.NFD);

        // Use a regular expression to remove non-ASCII characters
        Pattern pattern = Pattern.compile("[^\\p{ASCII}]");
        return pattern.matcher(normalized).replaceAll("");
    }
}
