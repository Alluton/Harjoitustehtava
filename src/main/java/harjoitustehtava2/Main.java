package harjoitustehtava2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.Spark;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        System.out.println("Hello world!");

        Spark.get("*", (req, res) -> {

            List<String> kysymykset = new ArrayList<>();

            // avaa yhteys tietokantaan
            Connection conn = getConnection();
            
            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("SELECT * FROM Kysymys");
            ResultSet tulos = stmt.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                String teksti = "Aihe: " + tulos.getString("Aihe") + "Kysymys: " + tulos.getString("Kysymysteksti") + " Kurssi: " +  tulos.getString("kurssi");
                kysymykset.add(teksti);
            }
            // sulje yhteys tietokantaan
            conn.close();

            HashMap map = new HashMap<>();

            map.put("lista", kysymykset);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/vastaukset", (req, res) -> {

            List<String> vastaukset = new ArrayList<>();

            // avaa yhteys tietokantaan
            Connection conn = getConnection();
            
            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("SELECT * FROM Vastaus");
            ResultSet tulos = stmt.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                String teksti = tulos.getString("vastausteksti");
                vastaukset.add(teksti);
            }
            // sulje yhteys tietokantaan
            conn.close();

            HashMap map = new HashMap<>();

            map.put("lista", vastaukset);

            return new ModelAndView(map, "index_1");
        }, new ThymeleafTemplateEngine());

        Spark.post("/kysymys", (req, res) -> {
            System.out.println("Hei maailma!");

            // avaa yhteys tietokantaan
            Connection conn = getConnection();
            
            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("INSERT INTO Kysymys (kysymysteksti,kurssi,aihe) VALUES (?,?,?)");
            stmt.setString(1, req.queryParams("kysymysteksti"));
            stmt.setString(2, req.queryParams("kurssi"));
            stmt.setString(3, req.queryParams("aihe"));

            stmt.executeUpdate();

            // sulje yhteys tietokantaan
            conn.close();

            res.redirect("/");
            return "";
        });
        Spark.post("/vastaus/{", (req, res) -> {
            System.out.println("Hei maailma!");


            // avaa yhteys tietokantaan
            Connection conn = getConnection();
            
            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("INSERT INTO Vastaus (vastausteksti,oikein) VALUES (?,?)");
            stmt.setString(1, req.queryParams("vastausteksti"));
            stmt.setString(2, req.queryParams("oikein"));

            stmt.executeUpdate();

            // sulje yhteys tietokantaan
            conn.close();

            res.redirect("/");
            return "";
        });
    }

    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection("jdbc:sqlite:db/tietokanta.db");
    }

}
