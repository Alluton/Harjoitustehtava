package harjoitustehtava2;


public class Vastaus {
    private boolean oikein;
    private String teksti;
    private Integer id;
    private Integer kysymysId;
    public Vastaus(Integer id,boolean oikein, String teksti,Integer kysymysId){
        this.id = id;
        this.oikein = oikein;
        this.teksti = teksti;
        this.kysymysId = kysymysId;
    }
    public int getId(){
        return id;
    }
    public boolean getOikein(){
        return oikein;
    }
    public String getTeksti(){
        return teksti;
    }
    public int getKysymysId(){
        return kysymysId;
    }
    public String toString(){
        return "Teksti: " + teksti + " Oikein: " + oikein;
    }
    
    
}
