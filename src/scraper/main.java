package scraper;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public class main 
{    
    public static void main(String[] args) throws Exception 
    {
        scrapeemploima em = new scrapeemploima();
        scrapemjob mj = new scrapemjob();
        scraperekrute rk = new scraperekrute();

        ArrayList<offre> offresRekrute = null;
        try
        {
            offresRekrute = rk.ScrapeRekrute();
        }
        catch (Exception e)
        { 
            System.out.println("MAJOR EXCEPTION WITH REKRUTE");
            e.printStackTrace();
        }

        ArrayList<offre> offresMJob = null;
        try
        {
            offresMJob = mj.ScrapeMJob();
        }
        catch (Exception e)
        {
            System.out.println("MAJOR EXCEPTION WITH MJob");
            e.printStackTrace();
        }

        ArrayList<offre> offresEmploima = null;
        
        try
        {
            offresEmploima = em.ScrapeEmploiMa();
        }
        catch (Exception e)
        {
            System.out.println("MAJOR EXCEPTION WITH EmploiMA");
            e.printStackTrace();
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter("ScraperOutput.csv", StandardCharsets.UTF_8))) 
        {
            // Write the header
            String[] header = {
                "titre", 
                "URL", 
                "siteName", 
                "dateDePublication", 
                "datePourPostuler", 
                "adresseDEntreprise", 
                "siteWebDEntreprise", 
                "nomDEntreprise", 
                "descriptionDEntreprise",
                "descriptionDuPoste", 
                "region", 
                "ville", 
                "secteurActivite", 
                "metier", 
                "typeDuContrat", 
                "niveauDEtudes", 
                "specialiteDiplome", 
                "experience", 
                "profilRecherche", 
                "traitsDePersonnalite", 
                "hardSkills", 
                "softSkills", 
                "competencesRecommandees", 
                "langue", 
                "niveauDeLaLangue", 
                "Salaire", 
                "avantagesSociaux", 
                "teletravail"
            };
            writer.writeNext(header);

            if (offresRekrute != null)
            {
                // Write data
                for (offre offre : offresRekrute) 
                {
                    String[] data = {
                        offre.getTitre(),
                        offre.getURL(),
                        offre.getSiteName(),
                        offre.getDateDePublication(),
                        offre.getDatePourPostuler(),
                        offre.getAdresseDEntreprise(),
                        offre.getSiteWebDEntreprise(),
                        offre.getNomDEntreprise(),
                        offre.getDescriptionDEntreprise(),
                        offre.getDescriptionDuPoste(),
                        offre.getRegion(),
                        offre.getVille(),
                        offre.getSecteurActivite(),
                        offre.getMetier(),
                        offre.getTypeDuContrat(),
                        offre.getNiveauDEtudes(),
                        offre.getSpecialiteDiplome(),
                        offre.getExperience(),
                        offre.getProfilRecherche(),
                        offre.getTraitsDePersonnalite(),
                        offre.getHardSkills(),
                        offre.getSoftSkills(),
                        offre.getCompetencesRecommandees(),
                        offre.getLangue(),
                        offre.getNiveauDeLaLangue(),
                        offre.getSalaire(),
                        offre.getAvantagesSociaux(),
                        offre.getTeletravail()
                    };

                    writer.writeNext(data);
                }
            }

            if (offresMJob != null)
            {
                for (offre offre : offresMJob) 
                {
                    String[] data = {
                        offre.getTitre(),
                        offre.getURL(),
                        offre.getSiteName(),
                        offre.getDateDePublication(),
                        offre.getDatePourPostuler(),
                        offre.getAdresseDEntreprise(),
                        offre.getSiteWebDEntreprise(),
                        offre.getNomDEntreprise(),
                        offre.getDescriptionDEntreprise(),
                        offre.getDescriptionDuPoste(),
                        offre.getRegion(),
                        offre.getVille(),
                        offre.getSecteurActivite(),
                        offre.getMetier(),
                        offre.getTypeDuContrat(),
                        offre.getNiveauDEtudes(),
                        offre.getSpecialiteDiplome(),
                        offre.getExperience(),
                        offre.getProfilRecherche(),
                        offre.getTraitsDePersonnalite(),
                        offre.getHardSkills(),
                        offre.getSoftSkills(),
                        offre.getCompetencesRecommandees(),
                        offre.getLangue(),
                        offre.getNiveauDeLaLangue(),
                        offre.getSalaire(),
                        offre.getAvantagesSociaux(),
                        offre.getTeletravail()
                    };

                    writer.writeNext(data);
                }
            }

            if (offresEmploima != null)
            {
                for (offre offre : offresEmploima) 
                {
                    String[] data = {
                        offre.getTitre(),
                        offre.getURL(),
                        offre.getSiteName(),
                        offre.getDateDePublication(),
                        offre.getDatePourPostuler(),
                        offre.getAdresseDEntreprise(),
                        offre.getSiteWebDEntreprise(),
                        offre.getNomDEntreprise(),
                        offre.getDescriptionDEntreprise(),
                        offre.getDescriptionDuPoste(),
                        offre.getRegion(),
                        offre.getVille(),
                        offre.getSecteurActivite(),
                        offre.getMetier(),
                        offre.getTypeDuContrat(),
                        offre.getNiveauDEtudes(),
                        offre.getSpecialiteDiplome(),
                        offre.getExperience(),
                        offre.getProfilRecherche(),
                        offre.getTraitsDePersonnalite(),
                        offre.getHardSkills(),
                        offre.getSoftSkills(),
                        offre.getCompetencesRecommandees(),
                        offre.getLangue(),
                        offre.getNiveauDeLaLangue(),
                        offre.getSalaire(),
                        offre.getAvantagesSociaux(),
                        offre.getTeletravail()
                    };

                    writer.writeNext(data);
                }
            }

            System.out.println("CSV file written successfully");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
