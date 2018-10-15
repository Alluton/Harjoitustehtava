package harjoitustehtava2;
public class Vastaus {
    private boolean oikein;
    private String vastausteksti;
    private Integer id;
    private Integer kysymysId;
    public Vastaus(Integer id,boolean oikein, String teksti,Integer kysymysId){
        this.id = id;
        this.oikein = oikein;
        this.vastausteksti = teksti;
        this.kysymysId = kysymysId;
    }
    public int getId(){
        return id;
    }
    public boolean getOikein(){
        return oikein;
    }
    public String getVastausTeksti(){
        return vastausteksti;
    }
    public int getKysymysId(){
        return kysymysId;
    }
    @Override
    public String toString(){
        return "Teksti: " + vastausteksti + " Oikein: " + oikein;
    }
    
    
}
