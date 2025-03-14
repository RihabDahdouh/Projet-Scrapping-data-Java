/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scraper;

/**
 *
 * @author der_u
 */
public class words {
    public static String capitalizeWords(String input) 
    {
        if (input == null || input.isEmpty()) 
        {
            return input;
        }

        // Split the input string into words
        String[] words = input.split("\\s+");

        // Capitalize the first letter of each word
        for (int i = 0; i < words.length; i++) 
        {
            words[i] = capitalizeFirstLetter(words[i]);
        }

        // Join the words back into a single string
        return String.join(" ", words);
    }

    private static String capitalizeFirstLetter(String word) 
    {
        if (word == null || word.isEmpty()) 
        {
            return word;
        }

        // Capitalize the first letter of the word
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}
