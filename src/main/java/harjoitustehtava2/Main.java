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

            List<Kysymys> kysymykset = new ArrayList<>();

            // avaa yhteys tietokantaan
            Connection conn = getConnection();
            
            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("SELECT * FROM Kysymys");
            ResultSet tulos = stmt.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                Kysymys kysymys = new Kysymys(tulos.getInt("id"),tulos.getString("aihe"),tulos.getString("kurssi"),tulos.getString("kysymysteksti"));
                kysymykset.add(kysymys);
            }
            // sulje yhteys tietokantaan
            conn.close();

            HashMap map = new HashMap<>();

            map.put("lista", kysymykset);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/vastaukset/:id", (req, res) -> {

            List<Vastaus> vastaukset = new ArrayList<>();

            // avaa yhteys tietokantaan
            Connection conn = getConnection();
            
            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("SELECT * FROM Vastaus WHERE kysymysId = ?");
            stmt.setInt(1, Integer.parseInt(req.params(":id")));
            ResultSet tulos = stmt.executeQuery();
            

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                Vastaus vastaus = new Vastaus(tulos.getInt("id"),tulos.getBoolean("oikein"),tulos.getString("vastausteksti"),tulos.getInt("kysymysId"));
                vastaukset.add(vastaus);
            }
            // sulje yhteys tietokantaan
            conn.close();
            
            Connection conn2 = getConnection();
            List<Kysymys> kysymykset = new ArrayList<>();

            // tee kysely
            PreparedStatement stmt2
                    = conn2.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
            stmt2.setInt(1, Integer.parseInt(req.params(":id")));
            ResultSet tulos2 = stmt2.executeQuery();
            // käsittele kyselyn tulokset
            while (tulos2.next()) {
                Kysymys kysymys = new Kysymys(tulos2.getInt("id"),tulos2.getString("aihe"),tulos2.getString("kurssi"),tulos2.getString("kysymysteksti"));
                kysymykset.add(kysymys);
            }
            // sulje yhteys tietokantaan
            conn2.close();

            HashMap map = new HashMap<>();

            map.put("lista", kysymykset);

            map.put("lista2", vastaukset);

            return new ModelAndView(map, "index1");
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
        Spark.post("/vastaus/:id", (req, res) -> {
            // avaa yhteys tietokantaan
            Connection conn = getConnection();
            
            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("INSERT INTO Vastaus (vastausteksti,oikein,kysymysId) VALUES (?,?,?)");
            stmt.setString(1, req.queryParams("vastausteksti"));
            if(req.queryParams("oikein") !=null ){
                stmt.setBoolean(2,true);
            } else{
                stmt.setBoolean(2,false);
            }
            stmt.setInt(3, Integer.parseInt(req.params(":id")));

            stmt.executeUpdate();

            // sulje yhteys tietokantaan
            conn.close();
            //tähän lisää
            return "";
        });
        Spark.post("/poista/:id", (req, res) -> {
            Connection conn = getConnection();
            PreparedStatement stmt
                    = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(req.params(":id")));

            stmt.executeUpdate();

            conn.close();

            res.redirect("/");
            return "";
        });
        Spark.post("/poista/vastaus/:id", (req, res) -> {
            Connection conn = getConnection();
            PreparedStatement stmt
                    = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(req.params(":id")));

            stmt.executeUpdate();

            conn.close();
            int id = Integer.parseInt(req.params(":id"));
            res.redirect("/vastaukset/" +id);
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
