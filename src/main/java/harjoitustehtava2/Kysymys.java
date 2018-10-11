package harjoitustehtava2;


public class Kysymys {
    private String kurssi;
    private String aihe;
    private String teksti;
    private Integer id;
    public Kysymys(int id,String aihe,String kurssi,String teksti){
        this.id = id;
        this.kurssi = kurssi;
        this.aihe = aihe;
        this.teksti = teksti;
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
        return teksti;
    }
    @Override
    public String toString(){
        return "Aihe: " + aihe;
    }
    
    
}
