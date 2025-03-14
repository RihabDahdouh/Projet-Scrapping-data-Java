/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scraper;

import java.util.ArrayList;
import java.util.Random;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author der_u
 */
public class scraperekrute extends scraper{
    ArrayList<offre> ScrapeRekrute() throws Exception
    {
        ArrayList <offre> offres = new ArrayList<offre>();
        int nbrPages = 30;
        ArrayList<Integer> ex = new ArrayList<Integer>();
        ArrayList<Integer> exp = new ArrayList<Integer>();
        for (int i = 1; i <= nbrPages; ++i)
        {
            String url = "https://www.rekrute.com/fr/offres.html?s=3&p=" + i + "&o=1";
            Document doc = null;
            int currCount = 0;
            while (currCount < maxTries)
            {
                try
                {
                    doc = Jsoup.connect(url).get();
                    break;
                }
                catch (Exception e)
                {
                    System.out.println("TRYING TO HANDLE MAJOR EXCEPTION AT REKRUTE");
                    ++currCount;
                    Thread.sleep(10000);
                }
            }
            if (doc == null)
            {
                return offres;
            }
            Element postData = doc.getElementById("post-data");
            Elements allPost = postData.getElementsByClass("post-id");
            int postnum = 0;
            for(Element eachPost : allPost)
            {
                try
                {
                    ++postnum;
                    //pour chaque li (eachPost) extraire le premier div de class=col-sm-2 col-xs-12
                    Element firstdiv = eachPost.getElementsByClass("col-sm-2 col-xs-12").get(0);

                    //extraire le nom de l'entreprise
                    String entreprise = firstdiv.getElementsByTag("img").attr("alt").trim();
                    String siteEntreprise = "https://www.rekrute.com" + firstdiv.getElementsByTag("a").get(0).attr("href");


                    //extraire de deuxieme div pour les autres info
                    Element bodyDiv = eachPost.getElementsByClass("col-sm-10 col-xs-12").get(0);

                    //de hrefdiv extraire la balise a
                    Element aTitle = bodyDiv.getElementsByClass("titreJob").get(0);
                    //extraire le poste propos� et la ville
                    String rawPostTitle = aTitle.text().trim();
                    String cleanTitle = rawPostTitle.split("\\|")[0].trim();
                    String rawVilleRegion = rawPostTitle.split("\\|")[1].trim();
                    String cleanVille = rawVilleRegion.split("\\(")[0].trim();
                    
                    cleanVille = words.capitalizeWords(cleanVille);
                    cleanVille = removeaccents.removeAccents(cleanVille);
                    
                    
                    String cleanRegion = rawVilleRegion.split("\\(")[1].split("\\)")[0].trim();

                    //System.out.println(Natureposte);
                    //de aTag extraire le lien l'offre d'emploi
                    String rawLink = aTitle.attr("href");
                    String lien = "https://www.rekrute.com" + rawLink;

                    String descriptionEntreprise = bodyDiv.getElementsByClass("info").get(0).text().trim();
                    String descriptionPost = bodyDiv.getElementsByClass("info").get(1).text().trim();

                    Element rawDatesAndPosts = bodyDiv.getElementsByClass("date").get(0);
                    String datePublication = rawDatesAndPosts.getElementsByTag("span").get(0).text().trim();
                    String datePostuler = rawDatesAndPosts.getElementsByTag("span").get(1).text().trim();
                    Elements rawNBPostes = rawDatesAndPosts.getElementsByTag("span");
                    String CleanNbrPostes = null;
                    if (rawNBPostes.size() > 2)
                    {
                        CleanNbrPostes = rawNBPostes.get(2).text();
                    }

                    Element info3UL = bodyDiv.getElementsByClass("info").get(2).getElementsByTag("ul").get(0);
                    Elements rawSecteurActivite = info3UL.getElementsByTag("li").get(0).getElementsByTag("a");
                    String secteurActivite = "";
                    for (Element sa : rawSecteurActivite)
                    {
                        secteurActivite += sa.text().trim() + ' ';
                    }
                    secteurActivite = secteurActivite.trim();
                    
                    Elements rawFonction = info3UL.getElementsByTag("li").get(1).getElementsByTag("a");
                    String fonction = "";
                    for (Element fonc : rawFonction)
                    {
                        fonction += fonc.text().trim() + ' ';
                    }
                    fonction = fonction.trim();
                    
                    Elements rawExperience = info3UL.getElementsByTag("li").get(2).getElementsByTag("a");
                    String experience = "";
                    for (Element expe : rawExperience)
                    {
                        experience += expe.text().trim() + ' ';
                    }
                    experience = experience.trim();
                    
                    Elements rawNiveauEtudes = info3UL.getElementsByTag("li").get(3).getElementsByTag("a");
                    String niveauEtudes = "";
                    for (Element nivet : rawNiveauEtudes)
                    {
                        niveauEtudes += nivet.text().trim() + ' ';
                    }
                    niveauEtudes = niveauEtudes.trim();
                    
                    
                    String typeContrat = info3UL.getElementsByTag("li").get(4).getElementsByTag("a").get(0).text().trim();

                    String teletravail = info3UL.getElementsByTag("li").get(4).text().split(":")[2].trim();

                    System.out.print("post " + postnum + " page " + i + "\n"
                                + entreprise + "\n" 
                                + rawPostTitle + "\n" 
                                + rawLink + "\n" 
                                + lien + "\n" 
                                + cleanTitle + "\n" 
                                + cleanVille + "\n" 
                                + cleanRegion + "\n"
                                + descriptionEntreprise + "\n"
                                + descriptionPost + "\n"
                                + datePublication + "\n"
                                + datePostuler + "\n"
                                + CleanNbrPostes + "\n"
                                + secteurActivite + "\n"
                                + fonction + "\n"
                                + experience + "\n"
                                + niveauEtudes + "\n"
                                + typeContrat + "\n"
                                + teletravail + "\n\n\n\n\n");
                    offre currentJob = new offre(
                                cleanTitle, 
                                lien, 
                                "ReKrute", 
                                datePublication, 
                                datePostuler, 
                                null, 
                                siteEntreprise, 
                                entreprise,
                                descriptionEntreprise,
                                descriptionPost,
                                cleanRegion,
                                cleanVille,
                                secteurActivite,
                                fonction,
                                typeContrat,
                                niveauEtudes,
                                null,
                                experience,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                teletravail);
                    offres.add(currentJob);
                }
                catch (Exception e)
                {
                    System.out.println("Exception occured on post number " + postnum);
                    e.printStackTrace();
                    ex.add(postnum);
                    exp.add(i);
                }
            }

            Random random = new Random();
            int randomTimeDev = random.nextInt((1000 - 100) + 1) + 100;
            int totalSleepTime = scraperSleepTime + randomTimeDev;
            System.out.print("Sleeping between jobs for " + totalSleepTime + "ms ...\n");
            Thread.sleep(totalSleepTime);
        }

        System.out.println("\n\n\n*********************EXCEPTION REPORT:****************************");

        for (int i = 0; i < exp.size(); ++i)
        {
                System.out.println("Post: " + ex.get(i) + " Page: "+ exp.get(i));
        }

        System.out.println("******************************************************************");

        return offres;
    }
}
