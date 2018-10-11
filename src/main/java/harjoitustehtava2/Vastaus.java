package harjoitustehtava2;


public class Vastaus {
    boolean oikein;
    String teksti;
    Integer id;
    Integer kysymysId;
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
    public String toString(){
        return "Id: " + id + " " + "Teksti: " + teksti;
    }
    
    
}
