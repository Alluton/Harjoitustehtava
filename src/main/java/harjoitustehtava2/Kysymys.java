package harjoitustehtava2;

public class Kysymys {
    private String kurssi;
    private String aihe;
    private String kysymysteksti;
    private Integer id;
    public Kysymys(int id,String aihe,String kurssi,String teksti){
        this.id = id;
        this.kurssi = kurssi;
        this.aihe = aihe;
        this.kysymysteksti = teksti;
    }
    public Integer getId(){
        return id;
    }
    public String getKurssi(){
        return kurssi;
    }
    public String getAihe(){
        return aihe;
    }
    public String getKysymysTeksti(){
        return kysymysteksti;
    }
    public boolean hasErrors(String string){
        System.out.println("Mentiin kysymys.hasErrors");
        System.out.println("Stringi: " +string);
        return string.equals("");
    }
    @Override
    public String toString(){
        return "Aihe: " + aihe + " Kysymys: " + kysymysteksti + " Kurssi: " + kurssi;
    }
    
    
}
