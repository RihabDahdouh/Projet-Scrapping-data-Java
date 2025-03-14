/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scraper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author der_u
 */
public class scrapeemploima extends scraper {
    ArrayList<offre> ScrapeEmploiMa() throws Exception
    {
        ArrayList<offre> offres = new ArrayList<offre>();
        int nbrPages = 30;
        ArrayList<Integer> ex = new ArrayList<Integer>();
        ArrayList<Integer> exp = new ArrayList<Integer>();
        for (int i = 3; i <= nbrPages; ++i)
        {
            String url;
            if (i == 1)
            {
                url = "https://www.emploi.ma/recherche-jobs-maroc";
            }
            else
            {
                int tempI = i - 1;
                url = "https://www.emploi.ma/recherche-jobs-maroc?page=" + tempI;
            }
            
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
                    System.out.println("TRYING TO HANDLE MAJOR EXCEPTION AT OUTER EMPLOIMA");
                    ++currCount;
                    Thread.sleep(10000);
                }
            }
            if (doc == null)
            {
                return offres;
            }
            
            //Sleep after request
            Random random = new Random();
            int randomTimeDev = random.nextInt((1000 - 100) + 1) + 100;
            int totalSleepTime = scraperSleepTime + randomTimeDev;
            System.out.print("Sleeping between jobs for " + totalSleepTime + "ms ...\n");
            Thread.sleep(totalSleepTime);
            
            int currPost = 0;
            Elements offerContainers = doc.getElementsByClass("job-description-wrapper");
            for (Element offerContainer : offerContainers)
            {
                try
                {
                    ++currPost;
                    String offerlink = offerContainer.attr("data-href");
                    
                    Document offerDoc = null;
                    
                    int currCountinner = 0;
                    while (currCountinner < maxTries)
                    {
                        try
                        {
                            offerDoc = Jsoup.connect(offerlink).get();
                            break;
                        }
                        catch (Exception e)
                        {
                            System.out.println("TRYING TO HANDLE MAJOR EXCEPTION AT INNER EMPLOIMA");
                            ++currCountinner;
                            Thread.sleep(10000);
                        }
                    }
                    if (offerDoc == null)
                    {
                        return offres;
                    }
                    
                    System.out.println("Link " + currPost + " : " + offerlink);

                    //Sleep after request
                    Random random1 = new Random();
                    int randomTimeDev1 = random1.nextInt((1000 - 100) + 1) + 100;
                    int totalSleepTime1 = scraperSleepTime + randomTimeDev1;
                    System.out.print("Sleeping between jobs for " + totalSleepTime1 + "ms ...\n");
                    Thread.sleep(totalSleepTime);

                    String title = offerDoc.getElementsByTag("h1").get(0).text().trim();
                    String datePublication = offerDoc.getElementsByClass("job-ad-publication-date").get(0).text().trim();

                    SimpleDateFormat inputFormat = new SimpleDateFormat("'Publiée le 'dd.MM.yyyy");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date tempDate = inputFormat.parse(datePublication);
                    datePublication = outputFormat.format(tempDate);

                    String entreprise = offerDoc.getElementsByClass("company-title").get(0).text().trim();

                    String siteEntreprise = offerDoc.getElementsByClass("website-url").get(0).text();

                    String secteurActivite = offerDoc.getElementsByClass("sector-title").get(0).text().trim();

                    String descriptionEntreprise = offerDoc
                            .getElementsByClass("job-ad-company-description")
                            .get(0)
                            .ownText()
                            .trim();

                    Element rawBodyContent = offerDoc
                            .getElementsByClass("content")
                            .get(1);
                    
                    String descriptionPost = rawBodyContent
                            .getElementsByTag("div").get(1).text().trim();
                    
                    Elements rawTempContent = rawBodyContent.getElementsByTag("div");
                    int k = 2;
                    for (; k < rawTempContent.size(); ++k)
                    {
                        if (rawBodyContent.getElementsByTag("div").get(k).className().equals("job-ad-separator")) break;
                    }
                    
                    String descriptionProfil = null;
                    if (k != rawTempContent.size()) 
                    {
                        descriptionProfil = rawBodyContent
                            .getElementsByTag("div").get(k + 1).text().trim();
                    }
                    
                    Elements rawJobCriteria = offerDoc
                            .getElementsByClass("job-ad-criteria")
                            .get(0)
                            .getElementsByTag("tr");
                    
                    String metier = null;
                    String typeContrat = null;
                    String region = null;
                    String ville = null;
                    String experience = null;
                    String niveauEtudes = null;
                    String langues = null;
                    String competences = null;

                    for (Element criteria : rawJobCriteria)
                    {
                        switch (criteria.getElementsByTag("td").get(0).text().trim())
                        {
                            case "Métier :":
                                metier = criteria.getElementsByTag("td").get(1).text().trim();
                                break;
                            case "Type de contrat :":
                                typeContrat = criteria.getElementsByTag("td").get(1).text().trim();
                                break;
                            case "Région :":
                                region = criteria.getElementsByTag("td").get(1).text().trim();
                                break;
                            case "Ville :":
                                ville = criteria.getElementsByTag("td").get(1).text().trim();
                                break;
                            case "Niveau d'expérience :":
                                experience = criteria.getElementsByTag("td").get(1).text().trim();
                                break;
                            case "Niveau d'études :":
                                niveauEtudes = criteria.getElementsByTag("td").get(1).text().trim();
                                break;
                            case "Langues exigées :":
                                langues = criteria.getElementsByTag("td").get(1).text().trim();
                                break;
                            case "Compétences clés :":
                                competences = criteria.getElementsByTag("td").get(1).text().trim();
                                break;
                        }
                    }

                    offre tempOffre = new offre(
                            title,
                            offerlink,
                            "Emploi.ma",
                            datePublication,
                            null,
                            null,
                            siteEntreprise,
                            entreprise,
                            descriptionEntreprise,
                            descriptionPost,
                            region,
                            ville,
                            secteurActivite,
                            metier,
                            typeContrat,
                            niveauEtudes,
                            null,
                            experience,
                            descriptionProfil,
                            null,
                            null,
                            null,
                            competences,
                            langues,
                            null,
                            null,
                            null,
                            null
                    );
                    offres.add(tempOffre);
                }
                catch (Exception e)
                {
                    System.out.println("Exception occured on post number " + currPost);
                    e.printStackTrace();
                    ex.add(currPost);
                    exp.add(i);
                }
            }
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
